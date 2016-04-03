package com.templatexuv.apresh.customerapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.CartAdapter;
import com.templatexuv.apresh.customerapp.adapter.OrderAdapter;
import com.templatexuv.apresh.customerapp.adapter.SellerAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CartDA;
import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.model.AllPaymentResponse;
import com.templatexuv.apresh.customerapp.model.Cart;
import com.templatexuv.apresh.customerapp.model.Order;
import com.templatexuv.apresh.customerapp.model.Seller;
import com.templatexuv.apresh.customerapp.util.CartListener;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.EndlessOnScrollListener;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment implements DataListener,CartListener{

    private  float mActionBarHeight;
    private Button button_sellers;
    private ListView listView;
    private SellerAdapter sellerAdapter;
    private List<Seller> sellers;
    //private OnScrollEndListener mCallback;
    CartDA cartDA;
    private CartAdapter cartsAdapter;
    private TextView noitems;
    private List<Cart> cartItems;
    private long userId;
    private ProgressBar progressBar;
    private int cartId;
    private RelativeLayout parentView;
    private Button buttonProceed;
    private ArrayList<Address> addresses;
    private CardView cardTotalLay;
    private TextView totalValue;
    int INDEX=1;
    int NO_OF_COMMENTS = 10;
    private OrderAdapter orderAdapter;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartDA = new CartDA();
        userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
        setHasOptionsMenu(true);


        if(getActivity() instanceof  MainActivity) {
            ((MainActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.order));
        }
        else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.order));
        }
        else if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.order));
        else if(getActivity() instanceof ProductDetailsActivity)
            ((ProductDetailsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.order));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        parentView = (RelativeLayout) rootView.findViewById(R.id.parentView);
        listView = (ListView) rootView.findViewById(R.id.listView);
        noitems = (TextView) rootView.findViewById(R.id.noitems);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderItem",orderAdapter.getOrderItems().get(position));
                orderDetailsFragment.setArguments(bundle);
                pushFragment(orderDetailsFragment);
            }
        });

        listView.setOnScrollListener(new EndlessOnScrollListener(1
        ) {
            @Override
            public void onHide() {

            }

            @Override
            public void onShow() {

            }

            @Override
            public void loadMore(int page, int totalItemsCount) {
                INDEX = INDEX + 1;

                List params = constructGetOrderParameters();
                HttpWorker worker = new HttpWorker(OrderFragment.this, getActivity(), progressBar);
                worker.execute(params);

            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List params = constructGetOrderParameters();
        HttpWorker worker = new HttpWorker(OrderFragment.this, getActivity(), progressBar);
        worker.execute(params);
    }

    public List constructGetOrderParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_ORDERS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_ALL_ORDERS);
        params.add(new String[]{"userId","index","noOfOrders"});
        params.add(new String[]{"" + userId, "" + INDEX, "" + NO_OF_COMMENTS});
        return params;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
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
    public void dataDownloaded(Response response) {

        try {
                if(response!=null && response.method == ServiceMethods.WS_GET_ALL_ORDERS) {
                String message = "";
                String screenName = null;
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllPaymentResponse) && (((AllPaymentResponse) response.data).issuccess())) {

                    AllPaymentResponse paymentResponse = (AllPaymentResponse) response.data;
                    List<Order> orders = paymentResponse.getOrders();
                    if(orders!=null && orders.size()>0){
                        orderAdapter = new OrderAdapter(getActivity(),orders);
                        listView.setAdapter(orderAdapter);
                    }else{
                        listView.setVisibility(View.GONE);
                        noitems.setVisibility(View.VISIBLE);
                    }

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemRemoved(int cartId) {
        this.cartId = cartId;
        List params = constructRemoveCartParameters(cartId);
        HttpWorker worker = new HttpWorker(OrderFragment.this, getActivity(),progressBar);
        worker.execute(params);
    }

    public List constructRemoveCartParameters(int cartId){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_DELETE_PRODUCT_FROM_CART);
        params.add("GET");
        params.add(ServiceMethods.WS_DELETE_PRODUCT);
        params.add(new String[]{"cartId"});
        params.add(new String[]{""+cartId});
        return params;
    }
}