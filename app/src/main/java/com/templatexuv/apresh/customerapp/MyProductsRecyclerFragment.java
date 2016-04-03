package com.templatexuv.apresh.customerapp;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.GridAdapter;
import com.templatexuv.apresh.customerapp.adapter.ProductsAdapter;
import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.AllProperty;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.EndlessOnScrollListener;
import com.templatexuv.apresh.customerapp.util.GridSpacingItemDecoration;
import com.templatexuv.apresh.customerapp.util.MyScrollListener;
import com.templatexuv.apresh.customerapp.util.OnRecyclerScrollListener;
import com.templatexuv.apresh.customerapp.util.OnScrollEndListener;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyProductsRecyclerFragment extends BaseFragment implements DataListener,GridAdapter.GridItemClickListener {
    public static final int NO_OF_RECORDS =20;
    public static int INDEX =1;
    //Display Records
    public static final int ENABLED =0;
    public static final int DISABLED =1;
    public static final int BOTH =2;
    private RecyclerView recyclerproduct;
    private GridAdapter productsAdapter;


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private OnRecyclerScrollListener mCallback;

    RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    String catId;
    String searchString;
    String sellerId;
  //  private TextView noProducts;
  //  private ProgressBar progressBar;
    private List<Property> propAndValues;
    private long userId;
    private HashMap selectionPropValues;
    JSONArray jsonArray = null;
    private List<Product> productsList;
    ProgressBar progressBar;

    public static MyProductsRecyclerFragment newInstance(String catId,String sellerId) {
        MyProductsRecyclerFragment fragment = new MyProductsRecyclerFragment();
        Bundle b = new Bundle();
        b.putString("catId",catId);
        b.putString("sellerId", sellerId);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MYPRODUCTS", "onCreate");
        setHasOptionsMenu(true);
        Bundle b = getArguments();
        catId = b.getString("catId");
        sellerId = b.getString("sellerId");
        ((ProductsActivity) getActivity()).setdisplayTitle(
                getActivity().getString(R.string.nav_products));

        selectionPropValues = new HashMap<String,Property>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("MYPRODUCTS", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_recycler_products, container, false);

       // noProducts = (TextView) rootView.findViewById(R.id.noProducts);
        //progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 2);


        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setOnScrollListener(new MyScrollListener(getActivity()) {
            @Override
            public void onMoved(int distance) {

                mCallback.hideView(distance);
            }
        });

        INDEX = 1;

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("MYPRODUCTS", "onAttach");
        try {
            mCallback = (OnRecyclerScrollListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("MYPRODUCTS", "onActivityCreated");
        INDEX = 1;

        selectionPropValues = CustomerApplication.getApplicationInstance().getSelectionPropValues();
        if(selectionPropValues!=null) {
            try {
                jsonArray = new JSONArray();

                Iterator it = selectionPropValues.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    Property property = (Property) pair.getValue();
                    JSONObject jsonObject = new JSONObject();
                    //jsonObject.put("propertyName", property.getPropertyName());
                   // jsonObject.put("propertyId", property.getPropertyId());
                    jsonObject.put("valueId", Integer.parseInt(property.getPropValues().get(0).getValueId()));
                   // jsonObject.put("valueName", property.getPropValues().get(0).getValueName());
                    jsonArray.put(Integer.parseInt(property.getPropValues().get(0).getValueId()));
                    //Log.v("jsonArray",""+jsonArray.toString());
                }
                List params = constructFilterParameters();
                HttpWorker worker = new HttpWorker(MyProductsRecyclerFragment.this, getActivity(), progressBar);
                worker.execute(params);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            if (catId!=null && catId.length() > 0) {
                searchString = "";
                if(productsList!=null && productsList.size()>0){
                    productsAdapter = new GridAdapter(productsList,this);
                    mRecyclerView.setAdapter(productsAdapter);
                }else {

                    List params = constructPropertyParameters();
                    HttpWorker worker = new HttpWorker(MyProductsRecyclerFragment.this, getActivity(), null);
                    worker.execute(params);

                    params = constructParameters();
                    worker = new HttpWorker(MyProductsRecyclerFragment.this, getActivity(), progressBar);
                    worker.execute(params);
                    //showLoader(getString(R.string.processing));

                }
            } else {
                List params = constructSellerParameters();
                HttpWorker worker = new HttpWorker(MyProductsRecyclerFragment.this, getActivity(), progressBar);
                worker.execute(params);
                //showLoader(getString(R.string.processing));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("MYPRODUCTS", "onStart");
    }

    @Override
    public void onResume() {
        Log.v("MYPRODUCTS", "onResume");
        super.onResume();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_myproducts, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setActionView(R.layout.layout_badge_notification);
        final View menu_hotlist = item.getActionView();

        //.getActionView();
        orderCount = (TextView) menu_hotlist.findViewById(R.id.orderCount);
        updateHotCount(hot_number);

        menu_hotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
                if (userId > 0) {
                    Fragment fragment = fragmentManager.findFragmentByTag("cart");
                    if (fragment == null)
                        fragment = new CartFragment();
                    pushFragment(fragment, "cart");
                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag("login");
                    if (fragment == null)
                        fragment = LoginFragment.newInstance(getString(R.string.login), null);
                    pushFragment(fragment, "login");
                }
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List constructParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_PRODUCTS_BY_CATEGORY);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_PRODUCTS_BY_ID);
        params.add(new String[]{"catId","NumOfRecords","index","searchString"});
        params.add(new String[]{catId, "" + NO_OF_RECORDS, "" + INDEX, searchString});
        return params;
    }

    public List constructSellerParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_PRODUCT_BY_SELLER);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_PRODUCTS_BY_ID);
        params.add(new String[]{"sellerId", "NumOfRecords", "index", "disabled", "searchString", "catOneId", "catTwoId", "catThreeId"});
        params.add(new String[]{sellerId,
                ""+NO_OF_RECORDS, "" + INDEX, "" + 2, "", "0", "0", "0"});
        return params;
    }

    public List constructPropertyParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_PROPERTYVALUES);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_PROPERTIES);
        params.add(new String[]{"categoryThreeId"});
        params.add(new String[]{catId});
        return params;
    }
    public List constructFilterParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_PRODUCTS_BY_FILTER);
        params.add("POST");
        params.add(ServiceMethods.WS_GET_PRODUCTS_BY_FILTER);
        params.add(new String[]{"catId","propAndValues","numOfProducts","index","maxPrice","minPrice"});
        params.add(new Object[]{catId, jsonArray,"" + NO_OF_RECORDS, "" + INDEX, 50000,1});
        return params;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("MYPRODUCTS", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("MYPRODUCTS", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        Log.v("MYPRODUCTS", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {

        Log.v("MYPRODUCTS", "onDetach");
        super.onDetach();
    }

    @Override
    public void dataDownloaded(Response response) {

        try {
            if(response!=null && response.method == ServiceMethods.WS_GET_PRODUCTS_BY_ID || response!=null && response.method == ServiceMethods.WS_GET_PRODUCTS_BY_FILTER) {
                String message = "";
                String screenName = null;
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }
                loading = true;
                if (response != null && response.data != null && (response.data instanceof AllProduct) && (((AllProduct) response.data).issuccess())) {

                    AllProduct allProduct = ((AllProduct)response.data);
                    if(INDEX>1){
                        if(allProduct.getProducts()!=null && allProduct.getProducts().size()>0) {
                            
                            productsAdapter.addProducts(allProduct.getProducts());
                            productsList = productsAdapter.getProducts();
                        }
                    }else{
                        productsAdapter = new GridAdapter(allProduct.getProducts(),this);
                        mRecyclerView.setAdapter(productsAdapter);
                        productsList = productsAdapter.getProducts();
                    }
                    if(productsAdapter==null || productsAdapter.getProducts().size()==0) {
                        //noProducts.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);

                    }
                   // if(allProduct.getSellers()!=null&&allProduct.getSellers().size()==0&& productsAdapter!=null && productsAdapter.getItemCount()>5)
                   // Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                }
                else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }else  if (response.method == ServiceMethods.WS_GET_PROPERTIES) {

                if(((AllProperty) response.data)!=null){
                    propAndValues = new ArrayList<>();
                    propAndValues.addAll(((AllProperty) response.data).getPropAndValues());
                    CustomerApplication.getApplicationInstance().setPropAndValues(propAndValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        }

    @Override
    public void onGridItemClick(int position) {

    }
}