package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.model.AllAddress;
import com.templatexuv.apresh.customerapp.model.User;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.util.Utils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Apresh on 5/6/2015.
 */
public class SaveAddressFragment extends BaseFragment implements View.OnClickListener,DataListener {
    /**
     * Returns a new instance of this fragment for the given screenname
     * number.
     */
    private static final String SCREEN_NAME= "screen_name";

    private Button buttonSave;
    private ProgressBar progressBar;
    private EditText editTextName;
    private EditText editTextPhone,editTextAddress,editTextLandMark,editTextCity,editTextPinCode;
    private String addressId;
    boolean issave;
    private Address address;
    private RelativeLayout parentView;

    public static SaveAddressFragment newInstance(String screenname,boolean issave,Address address) {
        SaveAddressFragment fragment = new SaveAddressFragment();
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME,screenname);
        args.putBoolean("issave", issave);
        args.putParcelable("address",address);
        fragment.setArguments(args);
        return fragment;
    }
    public SaveAddressFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof MainActivity)
        ((MainActivity) getActivity()).setdisplayTitle(
                getArguments().getString(SCREEN_NAME));
        else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));
        }
        else if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));
        else if(getActivity() instanceof ProductDetailsActivity)
            ((ProductDetailsActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));

        issave = getArguments().getBoolean("issave");
        address = getArguments().getParcelable("address");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView     = inflater.inflate(R.layout.fragment_new_address, container, false);
        editTextName      = (EditText) rootView.findViewById(R.id.editTextName);
        editTextPhone     = (EditText) rootView.findViewById(R.id.editTextPhone);
        editTextAddress   = (EditText) rootView.findViewById(R.id.editTextAddress);
        editTextLandMark  = (EditText) rootView.findViewById(R.id.editTextLandMark);
        editTextCity      = (EditText) rootView.findViewById(R.id.editTextCity);
        editTextPinCode   = (EditText) rootView.findViewById(R.id.editTextPinCode);
        progressBar       = (ProgressBar) rootView.findViewById(R.id.progressBar);
        parentView = (RelativeLayout) rootView.findViewById(R.id.parentView);
        buttonSave        = (Button) rootView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        if(getActivity() instanceof ProductsActivity){
            parentView.setPadding(25, ((ProductsActivity) getActivity()).getSupportActionBar().getHeight(), 25, 25);
        }else if(getActivity() instanceof ProductDetailsActivity){
            parentView.setPadding(25,((ProductDetailsActivity)getActivity()).getSupportActionBar().getHeight(),25,25);
        }

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(address!=null){
            try {
                editTextName.setText(URLDecoder.decode(address.getName(), "UTF-8"));
                editTextPhone.setText(URLDecoder.decode(address.getPhone(), "UTF-8"));
                editTextAddress.setText(URLDecoder.decode(address.getAddress(), "UTF-8"));
                editTextLandMark.setText(URLDecoder.decode(address.getLandmark(), "UTF-8"));
                editTextCity.setText(URLDecoder.decode(address.getCity(), "UTF-8"));
                editTextPinCode.setText(URLDecoder.decode(address.getPinCode(), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            buttonSave.setText("Edit Address");
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main, menu);
        menu.clear();;

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onClick(View v) {
        {
            FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
            switch (v.getId()) {


                case R.id.buttonSave:
                    try {

                        String nameValue = editTextName.getText().toString().trim();
                        String phoneValue = editTextPhone.getText().toString().trim();
                        String addressValue   = editTextAddress.getText().toString().trim();
                        String cityValue   = editTextCity.getText().toString().trim();
                        String landmarkValue = editTextLandMark.getText().toString().trim();
                        String pinCodeValue = editTextPinCode.getText().toString().trim();

                        boolean isValid   = false;
                        String message    = null;
                        if(nameValue.length()==0){
                            message = getActivity().getResources().getString(R.string.invalidname);
                        }
                        else if(phoneValue.length()<10){
                            message = getActivity().getResources().getString(R.string.invalidphonelength);
                        }
                        else if(addressValue.length()<4){
                            message = getResources().getString(R.string.invalidaddresslength);
                        } else if(cityValue.length()<4){
                            message = getResources().getString(R.string.invalidcitylength);
                        } else if(landmarkValue.length()<4){
                            message = getResources().getString(R.string.invalidlandmarklength);
                        } else if(pinCodeValue.length()<4){
                            message = getResources().getString(R.string.invalidpincodelength);
                        }
                       else{
                            isValid = true;
                        }

                        if(!isValid){
                            showAlertDialog(getResources().getString(R.string.app_name),message,
                                    getResources().getString(R.string.ok),null,null);
                        }else {
                            List params = constructParameters(nameValue, phoneValue, addressValue, cityValue, landmarkValue, pinCodeValue, issave);

                            HttpWorker worker = new HttpWorker(SaveAddressFragment.this, getActivity(),progressBar);
                            worker.execute(params);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    public List constructParameters(String nameValue,String phoneValue,String addressValue,String cityValue,String landmarkValue,String pinCodeValue,boolean issave){
        List<Object> params = new ArrayList<Object>();
        if(issave){
            params.add(Constants.URL_ADD_ADDRESS);
            params.add("POST");
            params.add(ServiceMethods.WS_ADD_ADDRESS);
            params.add(new String[]{"userId", "name", "address", "landmark", "phoneNumber", "city", "pinCode"});
            params.add(new String[]{""+SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID), nameValue, addressValue, landmarkValue, phoneValue, cityValue, pinCodeValue});
        }else {
            params.add(Constants.URL_EDIT_ADDRESS);
            params.add("POST");
            params.add(ServiceMethods.WS_EDIT_ADDRESS);
            params.add(new String[]{"addressId", "userId", "name", "address", "landmark", "phone", "city", "pinCode"});
            params.add(new String[]{""+address.getAddressId(), ""+SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID), nameValue, addressValue, landmarkValue, phoneValue, cityValue, pinCodeValue});
        }
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
            if (response.method == ServiceMethods.WS_ADD_ADDRESS) {

                if (response != null && response.data != null && (response.data instanceof AllAddress) && ((AllAddress) response.data).issuccess()) {
                    AllAddress allAddress = (AllAddress) response.data;
                        screenName = "Save Address";
                        //long userId = userData.getUserid();
                        message = "Address saved successfully.";
                    CustomerApplication.getApplicationInstance().setFetchAddress(true);
                    }else{
                        message = "Saving address failed.";
                    }

            }else if (response.method == ServiceMethods.WS_EDIT_ADDRESS) {

                if (response != null && response.data != null && (response.data instanceof AllAddress) && ((AllAddress) response.data).issuccess()) {
                    AllAddress allAddress = (AllAddress) response.data;
                    screenName = "Edit Address";
                    //long userId = userData.getUserid();
                    message = "Address Updated successfully.";
                    CustomerApplication.getApplicationInstance().setFetchAddress(true);
                }else{
                    message = "Updating address failed.";
                }
            }

            showAlertDialog(getActivity().getResources().getString(R.string.app_name),message,
                    getActivity().getResources().getString(R.string.ok),null,screenName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
