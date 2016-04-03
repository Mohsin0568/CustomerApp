package com.templatexuv.apresh.customerapp;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.templatexuv.apresh.customerapp.adapter.PreviewAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CartDA;
import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.CartParameter;
import com.templatexuv.apresh.customerapp.model.Product;
import com.templatexuv.apresh.customerapp.model.Property;
import com.templatexuv.apresh.customerapp.model.Value;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.HorizontalListView;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailsFragment extends BaseFragment implements DataListener{

    private TextView productTitleView;
    private HorizontalListView previewListView;
    private ImageView productPreviewView;
    private TextView productCostView;
    private TextView quantityView;
    String urlToDispaly;

    List<String> productUrls;
    private ImageView preview1,preview2,preview3,preview4;
    Product product;
    private TextView textDescriptionView;
    private TextView emailView,phoneView;
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
    FrameLayout containerLayout;
    private Animator mCurrentAnimator;

    private ImageView productZoomView;
    private int mShortAnimationDuration;
    private boolean isProcessing;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("PRODUCTDETAILS", "onCreate");
        Bundle bundle = getArguments();
        product = bundle.getParcelable("prodcut");
        sellerId = String.valueOf(product.getSellerId());

        productUrls = product.getImageURL();
        setHasOptionsMenu(true);
        cartDA = new CartDA();
        userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
        if( getActivity() instanceof  MainActivity)
            ((MainActivity) getActivity()).setdisplayTitle("Product Details");
        else if( getActivity() instanceof  ProductsActivity)
            ((ProductsActivity) getActivity()).setdisplayTitle("Product Details");
        selectionPropValues = new HashMap<String,Property>();

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("PRODUCTDETAILS", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_productdetails, container, false);
        productTitleView     = (TextView)rootView.findViewById(R.id.productTitle);
        previewListView      = (HorizontalListView)rootView.findViewById(R.id.previewList);
        productPreviewView   = (ImageView)rootView.findViewById(R.id.productPreview);
        productCostView      = (TextView)rootView.findViewById(R.id.productCost);
        quantityView         = (TextView)rootView.findViewById(R.id.quantity);
        selectedQuantity     = (EditText)rootView.findViewById(R.id.selectedQuantity);
        textDescriptionView  = (TextView) rootView.findViewById(R.id.textDescription);
        emailView            = (TextView) rootView.findViewById(R.id.email);
        phoneView            = (TextView) rootView.findViewById(R.id.phone);
        imageButton          = (ImageButton) rootView.findViewById(R.id.imageButton);
        sellerComments       = (TextView) rootView.findViewById(R.id.sellerComments);
        dynamiclay          = (LinearLayout) rootView.findViewById(R.id.dynamiclay);
        progressBar         = (ProgressBar) rootView.findViewById(R.id.progressBar);
        containerLayout            = (FrameLayout) rootView.findViewById(R.id.containerLayout);

        productZoomView = (ImageView) rootView.findViewById(R.id.productZoomView);

        productTitleView.setText(product.getProdName());
        productCostView.setText("Rs:"+product.getPrice());
        quantityView.setText("Quantity:"+product.quantity);
        textDescriptionView.setText(product.getProdDesc());

        productPreviewView.setTag(0);
        if(cartDA.checkCart(product.getProdName())>0) {
            imageButton.setVisibility(View.GONE);
        }

        propAndValues = product.getPropAndValues();

        if (propAndValues != null) {
            for (Property property : propAndValues) {

                LinearLayout contentLay = new LinearLayout(getActivity());
                contentLay.setOrientation(LinearLayout.HORIZONTAL);
                contentLay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView headingView = new TextView(getActivity());
                headingView.setTextSize(16);
                headingView.setTextColor(getActivity().getResources().getColor(R.color.black_color));
                headingView.setPadding(10, 20, 10, 0);
                headingView.setGravity(Gravity.CENTER_VERTICAL);
                headingView.setText(property.getPropName() + " : " + property.getPropValue());

                contentLay.addView(headingView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


                //contentLay.addView(spinner, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
              /*  Spinner spinner = (Spinner) getActivity().getLayoutInflater().inflate(R.layout.layout_spinner, null);
                contentLay.addView(spinner, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ValuesAdapter valuesAdapter = new ValuesAdapter(getActivity(), property.getPropValues(),property.getPropertyName(),property.getPropertyId());
                valuesAdapter.setDropDownViewResource(R.layout.adapter_value_item);
                spinner.setAdapter(valuesAdapter);*/

                //spinner.setSelected(false);

                dynamiclay.addView(contentLay,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                if(property!=null && property.getPropValues()!=null && property.getPropValues().size()>0){
                    Property propertyObj = new Property();
                    propertyObj.setPropertyName(property.getPropertyName());
                    propertyObj.setPropertyId(property.getPropertyId());
                    ArrayList<Value> values = new ArrayList<Value>();
                    Value value = new Value();
                    value.setValueId(property.getPropValues().get(0).getValueId());
                    value.setValueName(property.getPropValues().get(0).getValueName());
                    values.add(value);
                    property.setPropValues(values);
                    selectionPropValues.put(property.getPropertyName(), propertyObj);
                }
               /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            Value value = (Value)view.getTag();
                            String selection = value.getValueName();
                            Property property = new Property();
                            property.setPropertyName(value.getPropertyName());
                            property.setPropertyId(value.getPropertyId());
                            ArrayList<Value> values= new ArrayList<Value>();
                            values.add(value);
                            property.setPropValues(values);
                            selectionPropValues.put(value.getPropertyName(),property);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/


            }
        }

        sellerComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sellerId", sellerId);
                SellerCommentsFragment fragment = new SellerCommentsFragment();
                fragment.setArguments(bundle);
                pushFragment(fragment);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // userId = 6;
                 squantity = Integer.parseInt(selectedQuantity.getText().toString());
                int maxquantity = product.quantity;

                if(squantity == 0){
                    showAlertDialog(getString(R.string.app_name), "Your quantity cannot be zero.", getString(R.string.ok
                    ), "", "");
                    return;
                }
                else if(squantity>maxquantity){
                    showAlertDialog(getString(R.string.app_name), "Your Selection exceeds Maximum available quantity.", getString(R.string.ok
                    ), "", "");
                    return;
                }
               /* else if(propAndValues!=null && propAndValues.size()>0 && propAndValues.size() != selectionPropValues.size()){
                    showAlertDialog(getString(R.string.app_name), "Please select all Properties.", getString(R.string.ok
                    ), "", "");
                    return;
                }*/
                List params = constructAddCartParameters();
                CartParameter cartParameter = new CartParameter();
                cartParameter.setParams(params);

                if(userId>0) {
                    isProcessing = true;
                    HttpWorker worker = new HttpWorker(ProductDetailsFragment.this, getActivity(),progressBar);
                    worker.execute(params);
                }else{
                    pushFragment(LoginFragment.newInstance(getString(R.string.login,cartParameter),cartParameter));
                }
                    /*Cart cart = new Cart();
                    cart.product_display_name = product.getProdName();
                    cart.product_imageUrl = product.getImageURL().get(0);
                    cart.product_cost = product.getPrice();
                    cart.quantity = product.getQuantity();
                    cart.seller_info = "";
                    cart.delivery_by = "";
                    if(cartDA.insertCart(cart)>0)
                        imageButton.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "Item Added to Cart.", Toast.LENGTH_SHORT).show();*/

               // Toast.makeText(getActivity(),"Hai",Toast.LENGTH_SHORT).show();
               /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle b = new Bundle();
                b.putParcelable("prodcut", product);
                UpdateProductFragment fragment = new UpdateProductFragment();
                fragment.setArguments(b);
                fragmentTransaction.add(R.id.container_body,fragment );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });
     //   urlToDispaly = productUrls.size()>0 ?productUrls.get(0):"";
        //To display 1.jpg file
        if(productUrls!=null && productUrls.size()>0) {
            for (int i = 0; i < productUrls.size(); i++) {
                String imageUrl = productUrls.get(i);
                Log.v("imageUrl", imageUrl);
                if (imageUrl != null) {
                    String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));
                    if (filename != null && filename.equalsIgnoreCase("1")) {
                        Log.v("ProductDetails", filename);
                        CustomerApplication.getApplicationInstance().getImageLoader().DisplayImage(imageUrl, productPreviewView);
                        break;
                    }
                }

            }
        }
        if(urlToDispaly!=null && urlToDispaly.length()>0){
            CustomerApplication.getApplicationInstance().getImageLoader().DisplayImage(urlToDispaly, productPreviewView);
            previewListView.setAdapter(new PreviewAdapter(getActivity(), productUrls));
            previewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    productPreviewView.setTag(position);
                    urlToDispaly = (String) ((LinearLayout)view).getChildAt(0).getTag();
                    CustomerApplication.getApplicationInstance().getImageLoader().DisplayImage(urlToDispaly, productPreviewView);
                }
            });
        }

        productPreviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ZoomFragment zoomFragment = new ZoomFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("prodcut",product);
                bundle.putInt("position",position);
                zoomFragment.setArguments(bundle);
                pushFragment(zoomFragment, "zoom");
            }
        });

        return rootView;
    }

    public List constructAddCartParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_ADD_PRODUCT_TO_CART);
        params.add("POST");
        params.add(ServiceMethods.WS_ADD_PRODUCT_TO_CART);
        params.add(new String[]{"customerId","productid","quantity"});
        params.add(new String[]{""+userId,product.getProdId(),""+squantity});
        return params;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //super.onPrepareOptionsMenu(menu);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_myproducts, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        item.setActionView(R.layout.layout_badge_notification);
        final View menu_hotlist = item.getActionView();

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("PRODUCTDETAILS", "onAttach");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("PRODUCTDETAILS", "onStart");
    }

    @Override
    public void onResume() {
        Log.v("PRODUCTDETAILS", "onResume");
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("PRODUCTDETAILS", "onDestroyView");
    }

    @Override
        public void onDestroy() {
        Log.v("PRODUCTDETAILS", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {

        Log.v("PRODUCTDETAILS", "onDetach");
        super.onDetach();
    }


    private void alignGalleryToLeft(Gallery gallery) {
    WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

    int galleryWidth = manager.getDefaultDisplay().getWidth();

    // We are taking the item widths and spacing from a dimension resource
    // because:
    // 1. No way to get spacing at runtime (no accessor in the Gallery
    // class)
    // 2. There might not yet be any item view created when we are calling
    // this
    // function
    int itemWidth = getsize(105);
    int spacing = getsize(10);

    // The offset is how much we will pull the gallery to the left in order
    // to simulate left alignment of the first item
    final int offset;
    if (galleryWidth <= itemWidth) {
        offset = galleryWidth / 2 - itemWidth / 2 - spacing;
    } else {
        offset = galleryWidth - itemWidth - 2 * spacing;
    }

    // Now update the layout parameters of the gallery in order to set the
    // left margin
    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gallery.getLayoutParams();
    mlp.setMargins(-offset, mlp.topMargin, mlp.rightMargin,
    mlp.bottomMargin);
}

    public int getsize(int sizeInDip) {
        DisplayMetrics metrics = new DisplayMetrics();
        metrics = getResources().getDisplayMetrics();
        return (int) ((sizeInDip * metrics.density) + 0.5);
    }


    @Override
    public void dataDownloaded(Response response) {

        try {
            isProcessing =false;
            if(response!=null && response.method == ServiceMethods.WS_ADD_PRODUCT_TO_CART) {
                String message = "";
                String screenName = null;
                //hideloader();
                if (response != null) {
                    message = response.message != null ? response.message : message;
                }
               
                if (response != null && response.data != null && (response.data instanceof AllProduct) && (((AllProduct) response.data).issuccess())) {

                    imageButton.setVisibility(View.GONE);
                    updateHotCount(BaseFragment.hot_number+1);

                    Toast.makeText(getActivity(), "Item Added to Cart.", Toast.LENGTH_SHORT).show();

                } else {
                    showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                            getActivity().getResources().getString(R.string.ok), null, screenName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }







}