package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.model.BaseModel;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.User;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.Utils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Apresh on 5/6/2015.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener,DataListener {
    /**
     * Returns a new instance of this fragment for the given screenname
     * number.
     */
    private static final String SCREEN_NAME= "screen_name";
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextPassword,editTextConfirmPassword;
    private Button buttonRegistration;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextaltPhone;
    private ProgressBar progressBar;

    public static RegisterFragment newInstance(String screenname) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME,screenname);
        fragment.setArguments(args);
        return fragment;
    }
    public RegisterFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof MainActivity)
        ((MainActivity) getActivity()).setdisplayTitle(
                getArguments().getString(SCREEN_NAME));
        if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));
        else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView            = inflater.inflate(R.layout.fragment_register, container, false);
        editTextFirstName        = (EditText) rootView.findViewById(R.id.editTextFirstName);
        editTextLastName         = (EditText) rootView.findViewById(R.id.editTextLastName);
        editTextPassword         = (EditText) rootView.findViewById(R.id.editTextPassword);
        editTextConfirmPassword  = (EditText) rootView.findViewById(R.id.editTextConfirmPassword);
        editTextEmail            = (EditText) rootView.findViewById(R.id.editTextEmail);
        editTextPhone            = (EditText) rootView.findViewById(R.id.editTextPhone);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        buttonRegistration       = (Button) rootView.findViewById(R.id.buttonRegistration);
        buttonRegistration.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        {
            FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
            switch (v.getId()) {

                case R.id.buttonRegistration:
                    try {

                        String firstNameValue = editTextFirstName.getText().toString().trim();
                        String lastNameValue = editTextLastName.getText().toString().trim();
                        String pinValue   = editTextPassword.getText().toString().trim();
                        String confirmpinValue   = editTextConfirmPassword.getText().toString().trim();
                        String phoneValue = editTextPhone.getText().toString().trim();
                        String emailValue = editTextEmail.getText().toString().trim();

                        boolean isValid   = false;
                        String message    = null;
                        if(firstNameValue.length()==0){
                            message = getActivity().getResources().getString(R.string.invalidfirstname);
                        }
                        else  if(lastNameValue.length()==0){
                            message = getActivity().getResources().getString(R.string.invalidlastname);
                        }
                        else if(pinValue.length()<4){
                            message = getActivity().getResources().getString(R.string.invalidpinlength);
                        }
                        else if(confirmpinValue.length()<4){
                            message = getResources().getString(R.string.invalidconfirmpinlength);
                        }else if(!pinValue.equals(confirmpinValue)){
                            message = getResources().getString(R.string.invalidconfirmpinmatch);
                        }
                        else if(emailValue.length()==0 || !Utils.isValidEmail(emailValue)){
                            message = getResources().getString(R.string.invalidemail);
                        } else if(phoneValue.length()<10){
                            message = getResources().getString(R.string.invalidphonelength);
                        }
                       else{
                            isValid = true;
                        }

                        if(!isValid){
                            showAlertDialog(getResources().getString(R.string.app_name),message,
                                    getResources().getString(R.string.ok),null,null);
                        }else {
                            List params = constructParameters(firstNameValue,lastNameValue,pinValue,phoneValue,emailValue);
                            HttpWorker worker = new HttpWorker(RegisterFragment.this, getActivity(),progressBar);
                            worker.execute(params);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }
    public List constructParameters(String firstNameValue,String lastNameValue,String pinValue,String phoneValue,String emailValue){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_REGISTRATION);
        params.add("POST");
        params.add(ServiceMethods.WS_REGISTRATION);
        params.add(new String[]{"firstName","surName","emailId","phoneNumber","password"});
        params.add(new String[]{firstNameValue,lastNameValue,emailValue,phoneValue,pinValue});
        return params;
    }

    @Override
    public void dataDownloaded(Response response) {

        try {
            String message = "";
            String screenName = null;
            if (response != null) {
                message = response.message != null ? response.message : message;
            }
            if (response.method == ServiceMethods.WS_REGISTRATION) {

                if (response != null && response.data != null && (response.data instanceof User) && ((User) response.data).isIssuccess()) {
                    User userData = (User) response.data;
                        screenName = "Register";
                        //long userId = userData.getUserid();
                        message = "You are successfully registered.";

                    }else{
                        message = "Registration Failed.";
                    }
            }

            showAlertDialog(getActivity().getResources().getString(R.string.app_name),message,
                    getActivity().getResources().getString(R.string.ok),null,screenName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
