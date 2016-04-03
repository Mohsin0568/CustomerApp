package com.templatexuv.apresh.customerapp;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.CartAdapter;
import com.templatexuv.apresh.customerapp.adapter.ProductsAdapter;
import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.BaseModel;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.EndlessOnScrollListener;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements DataListener {
    public static final int NO_OF_RECORDS =5;
    public static int INDEX =1;
    private ProductsAdapter productsAdapter;
    private EditText editSearch;
    private ImageButton icon_search;
    private boolean loading = true;
    private OnScrollEndListener mCallback;
    private RelativeLayout searchView;
    private Button button_sellers;
    private GridView gridView;

    String searchString;
    private long userId;
    private ProgressBar progressBar;


    public interface OnScrollEndListener {
        public void resetPadding(boolean reset);
        public void hideView();
        public void showView();
    }

    public  HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle b = getArguments();
        try {
            ((MainActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.nav_item_home));
            userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
            updateHotCount(BaseFragment.hot_number);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("HomeFragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("HomeFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        editSearch = (EditText) rootView.findViewById(R.id.editSearch);
        button_sellers = (Button) rootView.findViewById(R.id.button_sellers);
        icon_search = (ImageButton) rootView.findViewById(R.id.icon_search);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        button_sellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerApplication.getApplicationInstance().setSelectionPropValues(null);
                pushFragment(new SellerFragment());
            }
        });

        icon_search.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               searchString = editSearch.getText().toString();
                                               if (searchString.length() > 0) {
                                                   List params = constructParameters();
                                                   HttpWorker worker = new HttpWorker(HomeFragment.this, getActivity(),progressBar);
                                                   worker.execute(params);
                                                   //showLoader(getString(R.string.processing));
                                               }
                                           }
                                       }
        );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Log.v("position", "" + position);
                bundle.putParcelable("prodcut", productsAdapter.getProducts().get(position));
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                productDetailsFragment.setArguments(bundle);
                pushFragment(productDetailsFragment);
            }
        });

       /* recyclerproduct.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {

                editSearch.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                mCallback.hideView();
            }
            @Override
            public void onShow() {
                editSearch.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                mCallback.showView();
            }
        });*/

        gridView.setOnScrollListener(new EndlessOnScrollListener(1
        ) {
            @Override
            public void onHide() {
                mCallback.hideView();
            }

            @Override
            public void onShow() {
                mCallback.showView();
            }

            @Override
            public void loadMore(int page, int totalItemsCount) {
                INDEX = INDEX + 1;

                List params = constructParameters();
                HttpWorker worker = new HttpWorker(HomeFragment.this, getActivity(),progressBar);
                worker.execute(params);

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {

        Log.v("HomeFragment", "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.v("HomeFragment", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        Log.v("HomeFragment", "onResume");
        super.onResume();
        updateHotCount(hot_number);
    }

    public List constructParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_PRODUCTS_BY_CATEGORY);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_PRODUCTS_BY_ID);
        params.add(new String[]{"catId","NumOfRecords","index","searchString"});
        params.add(new String[]{"", "" + NO_OF_RECORDS, "" + INDEX, searchString});
        return params;
    }


    @Override
    public void onDetach() {

        Log.v("HomeFragment", "onDetach");
        super.onDetach();
    }

    @Override
    public void dataDownloaded(Response response) {

        try {
            if(response!=null && response.method == ServiceMethods.WS_GET_PRODUCTS_BY_ID) {
                String message = "";
                String screenName = null;
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }
                loading = true;
                if (response != null && response.data != null && (response.data instanceof AllProduct) && (((AllProduct) response.data).issuccess() || ((BaseModel) response.data).issucess())) {

                    AllProduct baseModel = ((AllProduct)response.data);
                    if(INDEX>1){
                        if(baseModel.getProducts()!=null && baseModel.getProducts().size()>0)
                            productsAdapter.addProducts(baseModel.getProducts());
                    }else{
                        productsAdapter = new ProductsAdapter(getActivity(), baseModel.getProducts());
                        gridView.setAdapter(productsAdapter);
                    }
                    // if(baseModel.getSellers()!=null&&baseModel.getSellers().size()==0&& productsAdapter!=null && productsAdapter.getItemCount()>5)
                    // Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setActionView(R.layout.layout_badge_notification);
        final View menu_hotlist = item.getActionView();

        //.getActionView();
        orderCount = (TextView) menu_hotlist.findViewById(R.id.orderCount);
        updateHotCount(hot_number);

        menu_hotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CheckoutActivity.class));
                userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:

                return true;
            case R.id.action_order:
                startActivity(new Intent(getActivity(),OrderActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    }