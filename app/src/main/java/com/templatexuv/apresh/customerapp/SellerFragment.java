package com.templatexuv.apresh.customerapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.ProductsAdapter;
import com.templatexuv.apresh.customerapp.adapter.SellerAdapter;
import com.templatexuv.apresh.customerapp.model.AllSeller;
import com.templatexuv.apresh.customerapp.model.Seller;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.EndlessOnScrollListener;
import com.templatexuv.apresh.customerapp.util.HidingScrollListener;
import com.templatexuv.apresh.customerapp.util.OnScrollEndListener;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class SellerFragment extends BaseFragment implements DataListener, View.OnClickListener {

    private  float mActionBarHeight;
    private Button button_sellers;
    private GridView gridView;
    private SellerAdapter sellerAdapter;
    private List<Seller> sellers;
    private OnScrollEndListener mCallback;
    private TextView noProducts;
    private ProgressBar progressBar;
    public SellerFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setdisplayTitle(
                getActivity().getString(R.string.sellers));
        if(mCallback!=null)
            try {
                mCallback.hidePadding();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sellers, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        noProducts = (TextView) rootView.findViewById(R.id.noProducts);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* MyProductsRecyFragment fragment = MyProductsRecyFragment.newInstance("",String.valueOf(sellers.get(position).getSellerId()));
                 pushFragment(fragment);*/
                Intent intent = new Intent(getActivity(), ProductsActivity.class);
                Bundle b = new Bundle();
                b.putString("sellerId", String.valueOf(sellers.get(position).getSellerId()));
                b.putInt("hot_number", hot_number);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        rootView.setOnClickListener(SellerFragment.this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(sellerAdapter==null || sellerAdapter.getSellers().size()==0) {
            List params = constructParameters();
            HttpWorker worker = new HttpWorker(SellerFragment.this, getActivity(), progressBar);
            worker.execute(params);
        }
        //showLoader(getString(R.string.processing));
    }

    public List constructParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_SELLERS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_SELLERS);
        return params;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnScrollEndListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void dataDownloaded(Response response) {

        try {
            if(response!=null && response.method == ServiceMethods.WS_GET_SELLERS) {
                String message = "";
                String screenName = "Seller";
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }
                if (response != null && response.data != null && (response.data instanceof AllSeller) && (((AllSeller) response.data).issuccess())) {

                    AllSeller allSeller = ((AllSeller)response.data);

                    sellers = allSeller.getSeller();
                        if(sellers!=null && sellers.size()>0){
                        sellerAdapter = new SellerAdapter(getActivity(), sellers);
                        gridView.setAdapter(sellerAdapter);
                    }

                    if(sellerAdapter==null || sellerAdapter.getSellers().size()==0) {
                        noProducts.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.GONE);

                    }
                    // if(baseModel.getSellers()!=null&&baseModel.getSellers().size()==0&& productsAdapter!=null && productsAdapter.getItemCount()>5)
                    // Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getResources().getString(R.string.ok), null, screenName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
       // inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_newproduct) {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_body, CategoriesFragment.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            ((MainActivity)getActivity()).setdisplayTitle("Categories");

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}