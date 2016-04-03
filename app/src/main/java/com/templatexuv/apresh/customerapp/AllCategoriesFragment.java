package com.templatexuv.apresh.customerapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.templatexuv.apresh.customerapp.adapter.CategoryAdapter;
import com.templatexuv.apresh.customerapp.adapter.ParentAdapter;
import com.templatexuv.apresh.customerapp.adapter.SecondLevelAdapter;
import com.templatexuv.apresh.customerapp.datalayer.CategoryDA;
import com.templatexuv.apresh.customerapp.model.AllCategory;
import com.templatexuv.apresh.customerapp.model.Category;
import com.templatexuv.apresh.customerapp.model.ChildCategory;
import com.templatexuv.apresh.customerapp.model.SubCategory;
import com.templatexuv.apresh.customerapp.util.ChildListener;
import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SubCategoryListener;
import com.templatexuv.apresh.customerapp.webacces.DataListener;
import com.templatexuv.apresh.customerapp.webacces.HttpWorker;
import com.templatexuv.apresh.customerapp.webacces.Response;
import com.templatexuv.apresh.customerapp.webacces.ServiceMethods;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesFragment extends BaseFragment implements DataListener,SubCategoryListener,ChildListener {

    private List<Category> categories;
    private CategoryAdapter dataAdapter;
    private ExpandableListView productCategoriesListView;
    String url = "";


    List<Category> categoryList;
    ParentAdapter parentLevel;
    private int lastExpandedPosition = -1;
    public int LEVEL =1;
    private int groupPosition =-1;
    private int parentPosition =-1;
    private String categoryId;
    private View parentview;
    SecondLevelAdapter secondLevelAdapter;
    private CategoryDA categoryDA;
    private FragmentDrawerListener drawerListener;
    private View containerView;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private ImageView icon_right;
    private LinearLayout row_home;
    private FragmentDrawerListener mCallBack;
    private ProgressBar progressBar;

    public static AllCategoriesFragment newInstance() {
        AllCategoriesFragment fragment = new AllCategoriesFragment();
        return fragment;
    }
    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (FragmentDrawerListener)activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        categoryDA = new CategoryDA();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_productcategories, container, false);
        row_home = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.adapter_category_item, null);
        icon_right = (ImageView) row_home.findViewById(R.id.icon_right);
        productCategoriesListView = (ExpandableListView) rootView.findViewById(R.id.recyclerproductCategories);
        //progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        productCategoriesListView.setIndicatorBounds(width - getPixelFromDips(50), width - getPixelFromDips(10));

        productCategoriesListView.addHeaderView(row_home);


        icon_right.setVisibility(View.GONE);
        row_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mCallBack).closeDrawer();
                mCallBack.onDrawerItemSelected(null,0);
            }
        });
        productCategoriesListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                 //   productCategoriesListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        productCategoriesListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPos, long id) {
                LEVEL = 2;
              /*  if(categoryList.get(groupPos).getSubCategories()==null) {
                    categoryId = categoryList.get(groupPos).getCategoryId();

                }*/
                groupPosition = groupPos;

                return false;
            }
        });

        List<Category> categories = categoryDA.selectAllCategory();
        if(categories!=null && categories.size()>0){
            parentLevel = new ParentAdapter(getActivity(), categories,this);
            productCategoriesListView.setAdapter(parentLevel);
        }else {
            url = Constants.URL_GET_ALL_CATEGORIES;
            List params = constructParameters();
            HttpWorker worker = new HttpWorker(AllCategoriesFragment.this,getActivity(),null);
            worker.execute(params);
        }
        return rootView;
    }

    public List constructParameters(){
        List<Object> params = new ArrayList<Object>();
        params.add(Constants.URL_GET_PRODUCTS_BY_CATEGORY);
        params.add("GET");
        params.add(ServiceMethods.WS_GET_PRODUCTS_BY_ID);
        return params;
    }
    public int getPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
       // inflater.inflate(R.menu.menu_normal, menu);
       /* if (menu != null) {
            menu.findItem(R.id.action_newproduct).setVisible(false);
        }*/
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

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void setmDrawerToggle(){
        if(mDrawerToggle!=null) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
         //   mDrawerToggle = null;
         //   mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
    }
    @Override
    public void dataDownloaded(Response response) {

                try {
                    if(response!=null && response.method == ServiceMethods.WS_GET_PRODUCTS_BY_ID) {
                        String message = "";
                        String screenName = getActivity().getString(R.string.categories);

                        if (response != null && response.data instanceof AllCategory) {
                            message = ((AllCategory)response.data).getMessage()!=null?((AllCategory)response.data).getMessage():message;
                        }
                        if (response != null && response.data != null && (response.data instanceof AllCategory) && ((AllCategory) response.data).issuccess()) {

                            categoryList = ((AllCategory) response.data).getCategories();
                               if(categoryList!=null && categoryList.size()>0) {
                                   for(Category category : categoryList){
                                       categoryDA.insertCategory(category);
                                       for(SubCategory subcategory : category.getCatTwos()){
                                           subcategory.setCatOneId(category.getCatOneId());
                                           categoryDA.insertSubCategory(subcategory);
                                           for(ChildCategory childcategory : subcategory.getCatThrees()){
                                               childcategory.setCatTwoId(subcategory.getCatTwoId());
                                             categoryDA.insertChildCategory(childcategory);
                                           }
                                       }
                                   }
                                   parentLevel = new ParentAdapter(getActivity(), categoryList, this);
                                   productCategoriesListView.setAdapter(parentLevel);

                               }


                        } else {
                            showAlertDialog(getActivity().getResources().getString(R.string.app_name), message,
                                    getActivity().getResources().getString(R.string.ok), null, null);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    @Override
    public void subCategoryClick( View v,int grandparentPosition, int parentPosition,SecondLevelAdapter secondLevelAdapter) {

        LEVEL = 3;
        parentview  = v;
        this.parentPosition = parentPosition;

       /* if(categoryList.get(grandparentPosition).getSubCategories().get(parentPosition).getSubCategories()==null) {

            categoryId = categoryList.get(grandparentPosition).getSubCategories().get(parentPosition).getCategoryId();
            this.secondLevelAdapter = secondLevelAdapter;

        }*/
    }

    @Override
    public void onChildSelect(String catId,String catName) {

        CustomerApplication.getApplicationInstance().setSelectionPropValues(null);
      //  ((MainActivity)mCallBack).setdisplayTitle(catName);
                ((MainActivity) mCallBack).closeDrawer();

       // replaceFragment(MyProductsFragment.newInstance(catId, ""));

        Intent intent = new Intent(getActivity(),ProductsActivity.class);
        Bundle b = new Bundle();
        b.putString("catId", catId);
        b.putInt("hot_number", hot_number);
        b.putString("catName",catName);
        intent.putExtras(b);
        startActivity(intent);

    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}