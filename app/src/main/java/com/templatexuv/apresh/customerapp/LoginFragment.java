package com.templatexuv.apresh.customerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.CartParameter;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.User;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Apresh on 5/6/2015.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener,DataListener {
    /**
     * Returns a new instance of this fragment for the given screenname
     * number.
     */
    private static final String SCREEN_NAME= "screen_name";

    EditText editTextUserName;
    EditText editTextPassword;
    Button buttonForgotPin;
    Button buttonRegister;
    private Button buttonLogin;

    private String emailOrPhoneValue,pinValue;
    List params;
    CartParameter cartParameter;
    private HttpWorker worker;
    private ProgressBar progressBar;

    public static LoginFragment newInstance(String screenname,CartParameter cartParameter) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME,screenname);
        args.putParcelable("cartParameter",cartParameter);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
    }
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartParameter = getArguments().getParcelable("cartParameter");

        if(getActivity() instanceof  MainActivity)
        ((MainActivity) getActivity()).setdisplayTitle(
                getArguments().getString(SCREEN_NAME));
        if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));
        else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getArguments().getString(SCREEN_NAME));
        }
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        editTextUserName       = (EditText) rootView.findViewById(R.id.editTextUserName);
        editTextPassword       = (EditText) rootView.findViewById(R.id.editTextPassword);
        buttonForgotPin        = (Button) rootView.findViewById(R.id.buttonForgotPin);
        buttonRegister         = (Button) rootView.findViewById(R.id.buttonRegister);
        buttonLogin            = (Button) rootView.findViewById(R.id.buttonLogin);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        if(SharedPrefUtils.readBooleanPreferenceValue(Constants.PREF_REMEMBER_ME)){
            editTextUserName.setText(SharedPrefUtils.readStringPreferenceValue(Constants.PREF_EMAILORPHONEVALUE));
            editTextPassword.setText(SharedPrefUtils.readStringPreferenceValue(Constants.PREF_PIN_VALUE));
        }
        buttonForgotPin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
    public void onResume() {
        super.onResume();
        //getActivity().ge

    }
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.buttonForgotPin:
               /* fragmentManager.beginTransaction()
                        .replace(R.id.container_body, ForgotPasswordFragment.newInstance(getActivity().getString(R.string.forgotpassword)))
                        .addToBackStack(null)
                        .commit();
*/
                break;
            case R.id.buttonRegister:
                pushFragment(RegisterFragment.newInstance(getActivity().getString(R.string.register)));
                break;
            case R.id.buttonLogin:
                try {

                     emailOrPhoneValue = editTextUserName.getText().toString();
                     pinValue      = editTextPassword.getText().toString();

                    boolean isValid   = false;
                    String message    = null;

                    if(emailOrPhoneValue.length()==0){
                        message = getActivity().getResources().getString(R.string.invalidusername);
                    }else if(pinValue.length()<4){
                        message = getActivity().getResources().getString(R.string.invalidpinlength);
                    }else{
                        isValid = true;
                    }

                    if(!isValid){
                        showAlertDialog(getActivity().getResources().getString(R.string.app_name),message,
                                getActivity().getResources().getString(R.string.ok),null,null);
                    }else {
                        List params = constructParameters(emailOrPhoneValue,pinValue);
                        worker = new HttpWorker(LoginFragment.this, getActivity(),progressBar);
                        worker.execute(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    public List constructParameters(String emailOrPhoneValue,String pinValue){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_LOGIN);
        params.add("POST");
        params.add(ServiceMethods.WS_LOGIN);
        params.add(new String[]{"phoneOrEmail","password"});
        params.add(new String[]{emailOrPhoneValue, pinValue});
        return params;
    }
    public List constructGetCartParameters(long userId){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_NO_OF_PRODUCTS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_NO_OF_PRODUCTS);
        params.add(new String[]{"customerId"});
        params.add(new String[]{"" + userId});
        return params;
    }
    @Override
    public void dataDownloaded(Response response) {

        try {
            if(response!=null && response.method == ServiceMethods.WS_LOGIN) {
                String message = "";
                String screenName = null;
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof User) && ((User) response.data).isIssuccess()) {
                    User userData = (User) response.data;

                    if(userData.isLoginSuccess()) {
                        SharedPrefUtils.writePreferenceValue(Constants.PREF_EMAILORPHONEVALUE, emailOrPhoneValue);
                        SharedPrefUtils.writePreferenceValue(Constants.PREF_PIN_VALUE, pinValue);

                        //SharedPrefUtils.writePreferenceValue(Constants.PREF_SELLER_ID, userData.getSellerId());

                        SharedPrefUtils.writePreferenceValue(Constants.PREF_USER_ID, userData.getUserId());
                        SharedPrefUtils.writePreferenceValue(Constants.PREF_USER_EMAIL, userData.getEmail());
                        SharedPrefUtils.writePreferenceValue(Constants.PREF_USER_MOBILENUMBER, userData.getPhoneNumber());
                        if(userData.getUserId()>0) {
                            List params = constructGetCartParameters(userData.getUserId());
                            HttpWorker worker = new HttpWorker(LoginFragment.this, getActivity(), null);
                            worker.execute(params);
                        }

                        if(cartParameter!=null) {
                            params = cartParameter.getParams();
                            if (params != null) {
                                String[] values = (String[]) params.get(4);
                                values[0] = "" + userData.getUserId();
                                ((String[]) params.get(4))[0] = values[0];
                            }
                            worker = new HttpWorker(LoginFragment.this, getActivity(),progressBar);
                            worker.execute(params);
                        }else{
                            Fragment fragment = fragmentManager.findFragmentByTag("cart");
                            if(fragment == null)
                                fragment = new CartFragment();
                            replaceFragment(fragment, "cart");
                        }
                    }else{
                        showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                                getActivity().getResources().getString(R.string.ok), null, screenName);
                    }

                } else {

                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }else  if(response!=null && response.method == ServiceMethods.WS_ADD_PRODUCT_TO_CART) {
                String message = "";
                String screenName = null;
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllProduct) && ((AllProduct) response.data).issucess()) {
                    AllProduct allProductData = (AllProduct) response.data;

                    if(allProductData.issucess()) {
                        Toast.makeText(getActivity(), "Item Added to Cart.", Toast.LENGTH_SHORT).show();
                        Fragment fragment = fragmentManager.findFragmentByTag("cart");
                        if(fragment == null)
                            fragment = new CartFragment();
                        replaceFragment(fragment, "cart");
                    }else{
                        showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                                getActivity().getResources().getString(R.string.ok), null, screenName);
                    }

                } else {

                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }else  if(response!=null && response.method == ServiceMethods.WS_GET_NO_OF_PRODUCTS) {
                String message = "";
                String screenName = null;
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllProduct) && (((AllProduct) response.data).issuccess())) {

                    updateHotCount(((AllProduct) response.data).getNoOfProducts());

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigatetoHomeScreen(){
        getActivity().finish();
        Intent intent = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(intent);
        SharedPrefUtils.writePreferenceValue(Constants.PREF_IS_LOGGED_IN, true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
      menu.clear();
    }


}
