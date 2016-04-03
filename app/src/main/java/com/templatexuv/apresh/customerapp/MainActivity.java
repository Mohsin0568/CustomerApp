package com.templatexuv.apresh.customerapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.OnRecyclerScrollListener;
import com.templatexuv.apresh.customerapp.util.OnScrollEndListener;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;

import java.util.Stack;

public class MainActivity extends BaseActivity
        implements AllCategoriesFragment.FragmentDrawerListener,OnScrollEndListener,OnRecyclerScrollListener {

    private static String TAG = MainActivity.class.getSimpleName();
    public Toolbar mToolbar;
    private AllCategoriesFragment drawerFragment;
    private FrameLayout containerView;
    private DrawerLayout drawerLayout;

    String screenName;
    private int mPreviousBackStackCount;
    Stack<String> headings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MAINACTIVITY", "onCreate");
        setContentView(R.layout.activity_main);
        containerView  = (FrameLayout) findViewById(R.id.container_body);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        drawerFragment = (AllCategoriesFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        headings = new Stack<>();
        headings.push("Home");
        displayView(0);

        getSupportFragmentManager().addOnBackStackChangedListener(result);
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
                if(headings.size()>0) {
                    String displayName = headings.peek();
                    setdisplayTitle(displayName);
                    if(displayName !=null && displayName.equalsIgnoreCase("Home")){
                       // drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
                       // drawerFragment.setDrawerListener(MainActivity.this);

                        //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                      //  getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }
                }
                mPreviousBackStackCount--;
            }
            else{
               // if(!screenName.equalsIgnoreCase("Login"))
                headings.push(screenName);
                if(screenName!=null && (screenName.equalsIgnoreCase("Cart")||screenName.equalsIgnoreCase("Address"))){
                   // drawerFragment.setmDrawerToggle();
                  //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                  //  getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
               /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentManager.findFragmentByTag("AddressFragment");
                if(fragment == null) {
                    fragment = new AddressFragment();
                    pushFragment(fragment,"AddressFragment");
                }*/
                mPreviousBackStackCount++;
            }

        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("MAINACTIVITY", "onCreate");
    }

    @Override
    protected void onResume() {

        Log.v("MAINACTIVITY", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v("MAINACTIVITY", "onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("MAINACTIVITY", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("MAINACTIVITY", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("MAINACTIVITY", "onRestart");
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
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.nav_item_home);
                break;
            case 1:
                //fragment = new CategoriesFragment();
                title = getString(R.string.categories);
                break;
            case 2:
                fragment = new MyProductsFragment();
                //title = getString(R.string.nav_item_myproducts);
                break;
            case 3:
                fragment = new HomeFragment();
               // title = getString(R.string.nav_item_orderhistory);
                break;
            case 4:
                //title = getString(R.string.nav_item_logout);
                if(!SharedPrefUtils.readBooleanPreferenceValue(Constants.PREF_REMEMBER_ME)){
                    SharedPrefUtils.writePreferenceValue(Constants.PREF_USER_ID, "");
                    SharedPrefUtils.writePreferenceValue(Constants.PREF_EMAILORPHONEVALUE, "");
                    SharedPrefUtils.writePreferenceValue(Constants.PREF_PIN_VALUE, "");
                    SharedPrefUtils.writePreferenceValue(Constants.PREF_REMEMBER_ME, false);

                    SharedPrefUtils.writePreferenceValue(Constants.PREF_USER_EMAIL, "");
                    SharedPrefUtils.writePreferenceValue(Constants.PREF_USER_MOBILENUMBER, "");
                }
                SharedPrefUtils.writePreferenceValue(Constants.PREF_IS_LOGGED_IN, false);
                getSupportFragmentManager().popBackStack();
                finish();
                Intent intent = new Intent(getApplicationContext(),LaunchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_body, fragment);
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);

        }


    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }


    private void hideViews() {

       /* FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();*/
    }

    private void showViews() {
        /*      mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();*/
    }



    @Override
    public void resetPadding(boolean reset) {
        if(reset) {
            containerView.setPadding(0, getSupportActionBar().getHeight(), 0, 0);
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
                containerView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
            getSupportActionBar().show();

        }
        else {
            containerView.setPadding(0, 0, 0, 0);

        }
    }

    @Override
    public void hidePadding() {
        containerView.setPadding(0, 0, 0, 0);
    }

    @Override
    public void hideView() {
       // resetPadding(false);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
            mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    @Override
    public void showView() {
       // resetPadding(true);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
            mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public void hideView(int distance) {
        mToolbar.setTranslationY(-distance);

    }
}