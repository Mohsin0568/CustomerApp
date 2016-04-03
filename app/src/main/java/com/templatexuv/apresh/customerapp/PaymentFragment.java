package com.templatexuv.apresh.customerapp;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.templatexuv.apresh.customerapp.model.AllPaymentResponse;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends BaseFragment implements DataListener, View.OnClickListener {
    List<Cart> cartItems;
    private String lockId;
    double cartItemsTotal;
    long addressId;
    private Button buttonSuccessPayment,buttonFailurePayment;
    private ProgressBar progressBar;

    public static PaymentFragment newInstance(String lockId,double cartItemsTotal,long addressId,List<Cart> cartItems) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lockId",lockId);
        bundle.putDouble("cartItemsTotal",cartItemsTotal);
        bundle.putLong("addressId",addressId);
        bundle.putParcelableArrayList("cartItems", (ArrayList<? extends Parcelable>) cartItems);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).setdisplayTitle(
                getActivity().getString(R.string.payment));
        Bundle bundle = getArguments();
        cartItems = bundle.getParcelableArrayList("cartItems");
        lockId = bundle.getString("lockId");
        cartItemsTotal = bundle.getDouble("cartItemsTotal");
        addressId = bundle.getLong("addressId");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_status, container, false);
        progressBar          = (ProgressBar) view.findViewById(R.id.progressBar);
        buttonSuccessPayment = (Button) view.findViewById(R.id.buttonSuccessPayment);
        buttonFailurePayment = (Button) view.findViewById(R.id.buttonFailurePayment);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSuccessPayment.setOnClickListener(PaymentFragment.this);
        buttonFailurePayment.setOnClickListener(PaymentFragment.this);
    }

    public List constructConfirmOrderParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_CONFIRM_ORDER);
        params.add("POST");
        params.add(ServiceMethods.WS_CONFIRM_ORDER);
        params.add(new String[]{"lockId","totalCost","userId","addressId","paymentMethod","totalProducts","products"});
        JSONArray jsonArray = new JSONArray();
        for(Cart cart:cartItems){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("productId", cart.getProductId());
                jsonObject.put("quantity",cart.getCartQuantity());
                jsonObject.put("sellerId",cart.getSellerId());
                jsonObject.put("totalCost", cart.getCartQuantity() * cart.getPrice());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        params.add(new Object[]{lockId,cartItemsTotal,"" + SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID),addressId,"Internet banking",
                ""+cartItems.size(),jsonArray});
        return params;
    }


    public List constructUnlockOrderParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_UNLOCK_PRODUCTS);
        params.add("POST");
        params.add(ServiceMethods.WS_UNLOCK_PRODUCTS);
        params.add(new String[]{"lockId","products"});
        JSONArray jsonArray = new JSONArray();
        for(Cart cart:cartItems){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("prodId", cart.getProductId());
                jsonObject.put("quantity",cart.getCartQuantity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        params.add(new Object[]{"" + lockId,jsonArray});
        return params;
    }

    @Override
    public void dataDownloaded(Response response) {
        if(response!=null && response.method == ServiceMethods.WS_UNLOCK_PRODUCTS) {
            String message = "";
            String screenName = null;
            //hideloader();
            if (response != null) {
                message = response.message != null ? response.message : message;
            }

            if (response != null && response.data != null && (response.data instanceof AllPaymentResponse) && (((AllPaymentResponse) response.data).issuccess())) {
                    message = "Payment Received.Your Order is Confirmed.Your Products will be delivered Soon.Within in a Week.";
            }
            showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                    getActivity().getResources().getString(R.string.ok), null, screenName);
        }else if(response!=null && response.method == ServiceMethods.WS_CONFIRM_ORDER) {
            String message = "";
            String screenName = null;
            //hideloader();
            if (response != null) {
                message = response.message != null ? response.message : message;
            }

            if (response != null && response.data != null && (response.data instanceof AllPaymentResponse) && (((AllPaymentResponse) response.data).issuccess())) {
                message = "Your Order is Failed.";
            }  showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                    getActivity().getResources().getString(R.string.ok), null, screenName);
        }
    }
    
    @Override
    public void onClick(View v) {
        if(v == buttonSuccessPayment){
            List params = constructConfirmOrderParameters();
            HttpWorker worker = new HttpWorker(PaymentFragment.this, getActivity(),progressBar);
            worker.execute(params);
        }else if(v== buttonFailurePayment){
            List params = constructUnlockOrderParameters();
            HttpWorker worker = new HttpWorker(PaymentFragment.this, getActivity(),progressBar);
            worker.execute(params);
        }
    }
}
