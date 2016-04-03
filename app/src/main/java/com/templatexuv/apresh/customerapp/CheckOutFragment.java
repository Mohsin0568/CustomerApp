package com.templatexuv.apresh.customerapp;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.adapter.AddressAdapter;
import com.templatexuv.apresh.customerapp.adapter.CartAdapter;
import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.model.AllCart;
import com.templatexuv.apresh.customerapp.model.AllCheckoutResponse;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.util.CartListener;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 2/14/2016.
 */
public class CheckOutFragment extends BaseFragment implements CartListener,DataListener, View.OnClickListener {

    private long userId;
    private ArrayList<Cart> cartItems;
    private Address address;
    private RelativeLayout parentView;
    private ListView listView;
    private ProgressBar progressBar;
    private Button buttonProceed;
    private TextView nameView,addressView,landmarkView,phoneView,cityView,pincodeView;
    ImageView remove_productView,edit_addressView;
    CheckBox checkboxView;
    private CartAdapter cartsAdapter;
    private int cartId;
    private CardView cardTotalLay;
    private TextView totalValue;

    public static CheckOutFragment newInstance(List<Cart> cartItems,Address address) {
        CheckOutFragment fragment = new CheckOutFragment();
        Bundle args = new Bundle();
        args.putParcelable("address", address);
        args.putParcelableArrayList("cartItems", (ArrayList) cartItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
        setHasOptionsMenu(true);

       /* if(getActivity() instanceof  MainActivity) {
            ((MainActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.checkout));
        }else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.checkout));
        }
        else if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.checkout));
        else if(getActivity() instanceof ProductDetailsActivity)
            ((ProductDetailsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.checkout));*/

        ((BaseActivity) getActivity()).setdisplayTitle(
                getActivity().getString(R.string.review));

        Bundle bundle = getArguments();
        if(bundle!=null)
            address = bundle.getParcelable("address");
        cartItems = bundle.getParcelableArrayList("cartItems");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);
        parentView = (RelativeLayout) rootView.findViewById(R.id.parentView);
        listView = (ListView) rootView.findViewById(R.id.listView);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);
        buttonProceed = (Button) rootView.findViewById(R.id.buttonProceed);
        cardTotalLay = (CardView) rootView.findViewById(R.id.cardTotalLay);
        totalValue  = (TextView) rootView.findViewById(R.id.totalValue);


        View view = inflater.inflate(R.layout.fragement_address_detail,null);
        nameView = (TextView) view.findViewById(R.id.name);
        addressView = (TextView) view.findViewById(R.id.address);
        landmarkView = (TextView) view.findViewById(R.id.landmark);
        phoneView = (TextView) view.findViewById(R.id.phone);
        cityView              = (TextView) view.findViewById(R.id.city);
        pincodeView           =  (TextView) view.findViewById(R.id.pincode);
        remove_productView    =  (ImageView) view.findViewById(R.id.remove_address);
        edit_addressView      =  (ImageView) view.findViewById(R.id.edit_address);
        checkboxView      =  (CheckBox) view.findViewById(R.id.checkbox);

        try {
            checkboxView.setVisibility(View.GONE);
            cartsAdapter = new CartAdapter(getActivity(),cartItems,CheckOutFragment.this);
            listView.setAdapter(cartsAdapter);
            listView.addFooterView(view);
            cardTotalLay.setVisibility(View.VISIBLE);
            totalValue.setText(String.valueOf(cartsAdapter.getCartItemsTotal()));
            nameView.setText(URLDecoder.decode(address.getName(), "UTF-8"));
            addressView.setText(URLDecoder.decode(address.getAddress(), "UTF-8"));
            landmarkView.setText(URLDecoder.decode(address.getLandmark(), "UTF-8"));
            phoneView.setText(URLDecoder.decode(address.getPhone(), "UTF-8"));
            cityView.setText(URLDecoder.decode(address.getCity(), "UTF-8"));
            pincodeView.setText(URLDecoder.decode(address.getPinCode(), "UTF-8"));
            remove_productView.setVisibility(View.GONE);
            edit_addressView.setVisibility(View.GONE);
            checkboxView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(getActivity() instanceof ProductsActivity){
            parentView.setPadding(0, ((ProductsActivity) getActivity()).getSupportActionBar().getHeight(), 0, 0);
        }else if(getActivity() instanceof ProductDetailsActivity){
            parentView.setPadding(0,((ProductDetailsActivity)getActivity()).getSupportActionBar().getHeight(),0,0);
        }
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    List params = constructCheckoutCartParameters(cartItems.get(0).getCartId());
                    HttpWorker worker = new HttpWorker(CheckOutFragment.this, getActivity(), progressBar);
                    worker.execute(params);


            }
        });
        rootView.setOnClickListener(CheckOutFragment.this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemRemoved(int cartId) {
        this.cartId = cartId;
        List params = constructRemoveCartParameters(cartId);
        HttpWorker worker = new HttpWorker(CheckOutFragment.this, getActivity(),progressBar);
        worker.execute(params);
    }
    public List constructRemoveCartParameters(int cartId){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_DELETE_PRODUCT_FROM_CART);
        params.add("GET");
        params.add(ServiceMethods.WS_DELETE_PRODUCT);
        params.add(new String[]{"cartId"});
        params.add(new String[]{"" + cartId});
        return params;
    }


    public List constructCheckoutCartParameters(int cartId){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_CHECK_OUT_CART);
        params.add("POST");
        params.add(ServiceMethods.WS_CHECK_OUT);
        params.add(new String[]{"userId","totalProducts","products"});
        JSONArray jsonArray = new JSONArray();
        cartItems = (ArrayList)cartsAdapter.getCartProducts();
        for(Cart cart:cartItems){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("prodId", cart.getProductId());
                jsonObject.put("quantity",cart.getCartQuantity());
                Log.v("quantity", ""+cart.getCartQuantity());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        params.add(new Object[]{"" + SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID),""+cartItems.size(),jsonArray});
        return params;
    }

    @Override
    public void dataDownloaded(Response response) {
        if(response!=null && response.method == ServiceMethods.WS_DELETE_PRODUCT) {
            String message = "";
            String screenName = null;
            //hideloader();
            if (response != null) {
                message = response.message != null ? response.message : message;
            }

            if (response != null && response.data != null && (response.data instanceof AllCart) && (((AllCart) response.data).issucess())) {
                Toast.makeText(getActivity(), "Item removed Successfully.", Toast.LENGTH_SHORT).show();
                cartsAdapter.removeItem(cartId);
                cartsAdapter.notifyDataSetChanged();
                BaseFragment.hot_number--;
                if(cartsAdapter.getCartProducts().size()==0){
                    buttonProceed.setVisibility(View.GONE);
                }else {
                    buttonProceed.setVisibility(View.VISIBLE);
                }
            } else {
                showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                        getActivity().getResources().getString(R.string.ok), null, screenName);
            }
        }else if(response!=null && response.method == ServiceMethods.WS_CHECK_OUT) {
            String message = "";
            String screenName = null;
            //hideloader();
            if (response != null) {
                message = response.message != null ? response.message : message;
            }

            if (response != null && response.data != null && (response.data instanceof AllCheckoutResponse) && (((AllCheckoutResponse) response.data).issuccess())) {

                AllCheckoutResponse allCheckoutResponse = ((AllCheckoutResponse) response.data);
                boolean isProductsAvailable = allCheckoutResponse.isProductAvailable();
                if(!isProductsAvailable){
//                    int productId =
                    int unavailableProductId = allCheckoutResponse.getProdId();
                    message = allCheckoutResponse.getMessage();
                    if(message!=null && message.length()>0){
                        cartsAdapter.setUnavailablePositionMessage(unavailableProductId,message);
                    }
                }else{
                    //{"issuccess":true,"isProductAvailable":true,"prodId":0,"message":"","lockId":"150220162318066"}
                    String lockId = allCheckoutResponse.getLockId();
                    pushFragment(PaymentFragment.newInstance(lockId,cartsAdapter.getCartItemsTotal(),address.getAddressId(),cartItems));
                }
              /*  showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                        getActivity().getResources().getString(R.string.ok), null, screenName);*/
            } else {
                showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                        getActivity().getResources().getString(R.string.ok), null, screenName);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
