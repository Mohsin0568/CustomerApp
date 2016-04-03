package com.templatexuv.apresh.customerapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.templatexuv.apresh.customerapp.adapter.ProductsPagerAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CartDA;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class ProductDetailsActivity extends BaseActivity implements ZoomFragment.OnFragmentInteractionListener {

    private TextView productTitleView;
    private ImageView productPreviewView;
    private TextView productCostView;
    private TextView quantityView;
    String urlToDispaly;

    List<String> productUrls;
    private ImageView preview1, preview2, preview3, preview4;
    Product product;
    private TextView textDescriptionView;
    private TextView emailView, phoneView;
    private ImageButton imageButton;
    CartDA cartDA;
    private String sellerId;
    private TextView sellerComments;
    long userId;
    private EditText selectedQuantity;
    private int squantity;
    List<Property> propAndValues;
    private LinearLayout dynamiclay;
    private HashMap<String, Property> selectionPropValues;
    private ProgressBar progressBar;
    private MyProductsRecyFragment.SetProductsListListener setProductsListListener;
    private Toolbar toolbar;
    Context context;

    private ArrayList<Product> productsList;
    private ViewPager pager;
    private String screenName;
    private int mPreviousBackStackCount;
    Stack<String> headings;
    private int position;

    public ProductDetailsActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("PRODUCTDETAILS", "onCreate");
        setContentView(R.layout.activity_product_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pager = (ViewPager) findViewById(R.id.pager);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        headings = new Stack<>();
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = this.getIntent().getExtras();

        if(bundle!=null) {
              position = bundle.getInt("position");
              product = bundle.getParcelable("prodcut");
              sellerId = bundle.getString("sellerId");
              productsList = bundle.getParcelableArrayList("productsList");
        }

        headings.push("Product Details");

        if(product!=null) {
            productUrls = product.getImageURL();
            cartDA = new CartDA();
            userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
            setTitle("Product Details");
            if (productsList != null) {
                pager.setAdapter(new ProductsPagerAdapter(context, getSupportFragmentManager(), productsList, sellerId));
                pager.setCurrentItem(position);
            }
        }
        getSupportFragmentManager().addOnBackStackChangedListener(result);


    }
    FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
    {
        public void onBackStackChanged()
        {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            toolbar.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
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

    public void setdisplayTitle(String title){
        try {
            screenName = title;
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onZoomFragmentInitialized() {
        // resetPadding(false);
     //   toolbar.setVisibility(View.GONE);
     //   if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
     //       toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

    }
}