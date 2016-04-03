package com.templatexuv.apresh.customerapp;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.CommentAdapter;
import com.templatexuv.apresh.customerapp.adapter.ProductsAdapter;
import com.templatexuv.apresh.customerapp.adapter.SellerAdapter;
import com.templatexuv.apresh.customerapp.model.AllComments;
import com.templatexuv.apresh.customerapp.model.AllComments;
import com.templatexuv.apresh.customerapp.model.Comment;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.Seller;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.EndlessOnScrollListener;
import com.templatexuv.apresh.customerapp.util.OnScrollEndListener;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class SellerCommentsFragment extends BaseFragment implements DataListener {

    private  float mActionBarHeight;
    private Button button_sellers;
    private ListView listView;
    private CommentAdapter commentsAdapter;
    private List<Comment> comments;
    //private OnScrollEndListener mCallback;

    String sellerId;
    int INDEX=1;
    int NO_OF_COMMENTS = 10;
    private TextView noComments;
    private ProgressBar progressBar;

    public SellerCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellerId = getArguments().getString("sellerId");
        if(getActivity() instanceof MainActivity)
        ((MainActivity) getActivity()).setdisplayTitle(
                getActivity().getString(R.string.sellers));
        else if(getActivity() instanceof ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.sellers));
        else if(getActivity() instanceof ProductDetailsActivity)
            ((ProductDetailsActivity) getActivity()).setdisplayTitle(
                    getActivity().getString(R.string.sellers));


       // mCallback.hidePadding();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sellercomments, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        noComments = (TextView) rootView.findViewById(R.id.noComments);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        listView.setOnScrollListener(new EndlessOnScrollListener(1
        ) {
            @Override
            public void onHide() {

                // mCallback.hideView();
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mCallback.resetPadding(false);
                    }
                }, 100);*/
            }

            @Override
            public void onShow() {
                // mCallback.showView();

               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.resetPadding(true);

                    }
                }, 100);*/
            }

            @Override
            public void loadMore(int page, int totalItemsCount) {
                INDEX = INDEX + 1;
                List params = constructParameters();
                HttpWorker worker = new HttpWorker(SellerCommentsFragment.this, getActivity(), progressBar);
                worker.execute(params);

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List params = constructParameters();
        HttpWorker worker = new HttpWorker(SellerCommentsFragment.this,getActivity(),progressBar);
        worker.execute(params);

    }

    public List constructParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_SELLERCOMMENTS);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_SELLER_COMMENTS);
        params.add(new String[]{"sellerId","index","noOfComments"});
        params.add(new String[]{"" + sellerId, "" + INDEX, "" + NO_OF_COMMENTS});
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
    public void dataDownloaded(Response response) {

        try {
            if(response!=null && response.method == ServiceMethods.WS_GET_SELLER_COMMENTS) {
                String message = "";
                String screenName = "Comments";
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }
                if (response != null && response.data != null && (response.data instanceof AllComments) && (((AllComments) response.data).issuccess())) {

                    AllComments allComments = ((AllComments)response.data);
                    comments = allComments.getComments();
                    if(INDEX>1){
                        if(comments!=null && comments.size()>0)
                            commentsAdapter.addComments(comments);
                    }else{
                        if(comments!=null && comments.size()>0){
                            commentsAdapter = new CommentAdapter(getActivity(), comments);
                            listView.setAdapter(commentsAdapter);
                    }

                        if(commentsAdapter==null || commentsAdapter.getComments().size()==0){
                            noComments.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }
                    }
                } else {
                    showAlertDialog(getResources().getString(R.string.app_name), message,
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
        progressBar.setVisibility(View.GONE);
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

}