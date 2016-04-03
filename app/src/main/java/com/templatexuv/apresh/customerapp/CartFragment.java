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
import android.widget.Toast;

import com.templatexuv.apresh.customerapp.adapter.CartAdapter;
import com.templatexuv.apresh.customerapp.adapter.SellerAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CartDA;
import com.templatexuv.apresh.customerapp.model.Address;
import com.templatexuv.apresh.customerapp.model.AllAddress;
import com.templatexuv.apresh.customerapp.model.AllCart;
import com.templatexuv.apresh.customerapp.model.Cart;
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

public class CartFragment extends BaseFragment implements DataListener,CartListener{

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


    public CartFragment() {
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
                    getActivity().getString(R.string.cart));
        }
        else if(getActivity() instanceof  CheckoutActivity) {
            ((CheckoutActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.cart));
        }
        else if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.cart));
        else if(getActivity() instanceof ProductDetailsActivity)
            ((ProductDetailsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.cart));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        parentView = (RelativeLayout) rootView.findViewById(R.id.parentView);
        listView = (ListView) rootView.findViewById(R.id.listView);
        noitems = (TextView) rootView.findViewById(R.id.noitems);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);
        buttonProceed = (Button) rootView.findViewById(R.id.buttonProceed);
        cardTotalLay = (CardView) rootView.findViewById(R.id.cardTotalLay);
        totalValue  = (TextView) rootView.findViewById(R.id.totalValue);
       // mCallback.hidePadding();
        progressBar.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MyProductsFragment fragment = MyProductsFragment.newInstance("",String.valueOf(sellers.get(position).getSellerId()));
                fragmentTransaction.add(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartsAdapter!=null && cartsAdapter.getCartItems().size()>0) {
                    boolean isValidCart = true;
                    List<Cart> cartItemsList = cartsAdapter.getCartItems();
                    for (Cart cart : cartItemsList) {
                        if (cart.getCartQuantity() > cart.getProductQuantity()) {
                            isValidCart = false;
                            break;
                        }
                    }
                    cartsAdapter.notifyDataSetChanged();
                    if (isValidCart) {
                        List params = constructGetAllAddressParameters();
                        HttpWorker worker = new HttpWorker(CartFragment.this, getActivity(), progressBar);
                        worker.execute(params);
                    } else {
                        showToast(getString(R.string.cartQuantityExceeds), Toast.LENGTH_SHORT);

                    }
                }
            }
        });
            if(getActivity() instanceof ProductsActivity){
                parentView.setPadding(0,((ProductsActivity)getActivity()).getSupportActionBar().getHeight(),0,0);
            }else if(getActivity() instanceof ProductDetailsActivity){
                parentView.setPadding(0,((ProductDetailsActivity)getActivity()).getSupportActionBar().getHeight(),0,0);
            }
        listView.setOnScrollListener(new EndlessOnScrollListener(1
        ) {
            @Override
            public void onHide() {

                // mCallback.hideView();

               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.resetPadding(false);
                    }
                }, 100);*/
            }

            @Override
            public void onShow() {
                /*mCallback.showView();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.resetPadding(true);

                    }
                }, 100);*/

            }

            @Override
            public void loadMore(int page, int totalItemsCount) {

            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*if(cartItems == null || cartItems.size()==0) {
            List params = constructGetCartParameters();
            HttpWorker worker = new HttpWorker(CartFragment.this, getActivity(), progressBar);
            worker.execute(params);
        }else{
            if(cartItems!=null && cartItems.size()>0){
                progressBar.setVisibility(View.GONE);
                cartsAdapter = new CartAdapter(getActivity(),cartItems,this);
                listView.setAdapter(cartsAdapter);
                buttonProceed.setVisibility(View.VISIBLE);
            }
        }*/
        List params = constructGetCartParameters();
        HttpWorker worker = new HttpWorker(CartFragment.this, getActivity(), progressBar);
        worker.execute(params);
    }

    public List constructGetCartParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_VIEW_CART);
        params.add("GET");
        params.add(ServiceMethods.WS_GELL_ALL_CART_PRODUCTS);
        params.add(new String[]{"customerId"});
        params.add(new String[]{"" + userId});
        return params;
    }

    public List constructGetAllAddressParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_ADDRESS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_ALL_ADDRESS);
        params.add(new String[]{"userId"});
        params.add(new String[]{"" + userId});
        return params;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mCallback = (OnScrollEndListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }*/
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
            if(response!=null && response.method == ServiceMethods.WS_GELL_ALL_CART_PRODUCTS) {
                String message = "";
                String screenName = null;
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllCart) && (((AllCart) response.data).issucess())) {

                    cartItems = ((AllCart) response.data).getProducts();
                    if(cartItems!=null && cartItems.size()>0){
                        cartsAdapter = new CartAdapter(getActivity(),cartItems,this);
                        listView.setAdapter(cartsAdapter);
                        buttonProceed.setVisibility(View.VISIBLE);
                        cardTotalLay.setVisibility(View.VISIBLE);
                        totalValue.setText(String.valueOf(cartsAdapter.getCartItemsTotal()));
                    }else {
                        noitems.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        buttonProceed.setVisibility(View.GONE);
                        cardTotalLay.setVisibility(View.GONE);
                    }
                } else {
                    try {
                        cartItems = ((AllCart) response.data).getProducts();
                        if(cartItems.size()==0){
                            message = "No Items in Cart";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }else if(response!=null && response.method == ServiceMethods.WS_DELETE_PRODUCT) {
                String message = "";
                String screenName = null;
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllCart) && (((AllCart) response.data).issucess())) {

                    Toast.makeText(getActivity(),"Item removed Successfully.",Toast.LENGTH_SHORT).show();
                    cartsAdapter.removeItem(cartId);
                    cartsAdapter.notifyDataSetChanged();
                    if(BaseFragment.hot_number>0)
                    BaseFragment.hot_number--;
                    if(cartsAdapter.getCartProducts().size()==0){
                        noitems.setVisibility(View.VISIBLE);
                        buttonProceed.setVisibility(View.GONE);
                        cardTotalLay.setVisibility(View.GONE);
                    }else {
                        buttonProceed.setVisibility(View.VISIBLE);
                        cardTotalLay.setVisibility(View.VISIBLE);
                        totalValue.setText(String.valueOf(cartsAdapter.getCartItemsTotal()));
                    }

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }else if(response!=null && response.method == ServiceMethods.WS_GET_ALL_ADDRESS) {
                String message = "";
                String screenName = null;
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }

                if (response != null && response.data != null && (response.data instanceof AllAddress) && (((AllAddress) response.data).issuccess())) {

                    addresses = ((AllAddress)response.data).getAddresses();
                    if(addresses!=null && addresses.size()>0) {
                        AddressFragment addressFragment = AddressFragment.newInstance(cartItems, ((AllAddress) response.data).getAddresses());

                        CustomerApplication.getApplicationInstance().setFetchAddress(false);
                       // pushFragment(addressFragment,"AddressFragment");
                        //replaceAddFragmentByTag(addressFragment, "AddressFragment");
                        replaceAddFragmentByTag(addressFragment, "AddressFragment");
                    }else{
                        SaveAddressFragment saveAddressFragment = SaveAddressFragment.newInstance("Add Address",true,null);
                        replaceAddFragmentByTag(saveAddressFragment, "SaveAddressFragment");
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
        HttpWorker worker = new HttpWorker(CartFragment.this, getActivity(),progressBar);
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