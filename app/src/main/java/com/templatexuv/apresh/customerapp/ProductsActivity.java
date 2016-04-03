package com.templatexuv.apresh.customerapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.GridAdapter;
import com.templatexuv.apresh.customerapp.adapter.OptionsAdapter;
import com.templatexuv.apresh.customerapp.adapter.ProductsAdapter;
import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.AllProperty;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.util.Constants;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ProductsActivity extends BaseActivity implements OnRecyclerScrollListener,OnScrollEndListener,MyProductsRecyFragment.SetProductsListListener {

    private Toolbar toolbar;
    private String catId,sellerId;
    int hot_number;
    private FrameLayout container_body;

    private int mPreviousBackStackCount;
    Stack<String> headings;
    String screenName;
    private RelativeLayout filterLay,sortByLay;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertBuilder;
    private int selectedPostion;
    private List<Product> productList;
    private LinearLayout optionsLay;
    private GridAdapter productsAdapter;
    private LinearLayout toolbarOptionsLay;
    Context context;
    private View seperatorView;
    private TextView noOfProductsMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        toolbarOptionsLay = (LinearLayout) findViewById(R.id.toolbarOptionsLay);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // progressBar  = (ProgressBar) findViewById(R.id.progressBar);
        seperatorView = (View) findViewById(R.id.seperatorView);
        optionsLay = (LinearLayout) findViewById(R.id.optionsLay);
        container_body = (FrameLayout) findViewById(R.id.container_body);
        filterLay   = (RelativeLayout) findViewById(R.id.filterLay);
        sortByLay   = (RelativeLayout) findViewById(R.id.sortByLay);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        headings = new Stack<>();

        Bundle b = getIntent().getExtras();
        catId = b.getString("catId");
        sellerId = b.getString("sellerId");
        hot_number = b.getInt("hot_number");
        screenName = b.getString("catName");
        if(screenName==null)
         screenName = getResources().getString(R.string.nav_products);
        setTitle(screenName);

        headings.push(screenName);

        if(sellerId!=null && sellerId.length()>0){
            filterLay.setVisibility(View.GONE);
            seperatorView.setVisibility(View.GONE);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, MyProductsRecyFragment.newInstance(catId,sellerId));
        fragmentTransaction.commit();

        getSupportFragmentManager().addOnBackStackChangedListener(result);

        sortByLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSortOptionsDialog();
            }
        });

        filterLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductsActivity.this,FilterActivity.class);
                context.startActivity(intent);

            }
        });

        /*case R.id.action_sort:
        if(productsAdapter!=null &&  productsAdapter.getProducts()!=null && productsAdapter.getProducts().size()>0) {
            Collections.sort(productsAdapter.getProducts());
            productsAdapter.notifyDataSetChanged();
        }
        return true;
        case R.id.action_filter:

        return true;*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        reDrawToolbar();
        if(noOfProductsMenuItem!=null)
            updateHotCount(BaseFragment.hot_number,noOfProductsMenuItem );
    }

    FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
    {
        public void onBackStackChanged()
        {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            showView();
            resetPadding(true);
            if(mPreviousBackStackCount >= backStackEntryCount) {
                headings.pop();
                if(headings.size()>0)
                    setdisplayTitle(headings.peek());
                mPreviousBackStackCount--;


            }
            else{
                headings.push(screenName);
                mPreviousBackStackCount++;
            }

        }
    };

    public void updateHotCount(final int new_hot_number,TextView orderCount) {
        if (orderCount == null) return;
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {*/
        if (new_hot_number == 0)
            orderCount.setVisibility(View.INVISIBLE);
        else {
            orderCount.setVisibility(View.VISIBLE);
            orderCount.setText(Integer.toString(new_hot_number));
        }
        // }
        //});
    }




    public void setdisplayTitle(String title){
        try {
            screenName = title;
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetPadding(boolean reset) {
       /* if(reset) {
            containerView.setPadding(0, getSupportActionBar().getHeight(), 0, 0);
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
                containerView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
            getSupportActionBar().show();

        }
        else {
            containerView.setPadding(0, 0, 0, 0);

        }*/
    }

    @Override
    public void hidePadding() {
      //  containerView.setPadding(0, 0, 0, 0);
    }

    @Override
    public void hideView() {
        // resetPadding(false);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
            toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    @Override
    public void showView() {
        // resetPadding(true);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getSupportFragmentManager().getBackStackEntryCount()>0)
                    getSupportFragmentManager().popBackStack();
                else
                finish();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSortOptionsDialog() {
        runOnUiThread(new RunshowCustomOptionsDialog());
    }

    @Override
    public void onProductsloaded(List<Product> productList,GridAdapter productsAdapter,HashMap selectionPropValues) {
            if((productList == null || productList.size()==0) && selectionPropValues == null)
                optionsLay.setVisibility(View.GONE);
            else
                optionsLay.setVisibility(View.VISIBLE);

        this.productList = productList;
        this.productsAdapter = productsAdapter;
    }

    @Override
    public void hideOptions() {
        optionsLay.setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void hideView(int distance) {
       toolbarOptionsLay.setTranslationY(-distance);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void reDrawToolbar() {
        toolbarOptionsLay.setTranslationY(0);
    }

    @Override
    public void setNoOfProductsMenuItem(TextView noOfProductsMenuItem) {
        this.noOfProductsMenuItem = noOfProductsMenuItem;
    }

    class RunshowCustomOptionsDialog implements Runnable {

        private ListView optionListView;

        @Override
        public void run()
        {
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();

            alertBuilder = new AlertDialog.Builder(ProductsActivity.this);
            alertBuilder.setCancelable(true);

            final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_sortoptions, null);
            optionListView  = (ListView) linearLayout.findViewById(R.id.optionListView);
            String[] options = getResources().getStringArray(R.array.sort_options);
            optionListView.setAdapter(new OptionsAdapter(ProductsActivity.this,options,selectedPostion));
            optionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selectedPostion = position;

                    if(selectedPostion==0)
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product lhs, Product rhs) {
                            return new Double(lhs.price).compareTo(((Product)rhs).price);
                        }
                    });
                    else
                        Collections.sort(productList, new Comparator<Product>() {
                            @Override
                            public int compare(Product lhs, Product rhs) {
                                return new Double(rhs.price).compareTo(((Product) lhs).price);
                            }
                        });
                    productsAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }
            });
            try
            {
                alertDialog = alertBuilder.create();
                alertDialog.setView(linearLayout,0,0,0,0);
                alertDialog.setInverseBackgroundForced(true);
                alertDialog.show();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
