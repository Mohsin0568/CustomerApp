package com.templatexuv.apresh.customerapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.templatexuv.apresh.customerapp.util.Constants;
import com.templatexuv.apresh.customerapp.util.SharedPrefUtils;

import java.util.Stack;

public class OrderActivity extends BaseActivity{

    private Toolbar toolbar;
    private Stack<String> headings;
    Context context;
    private int mPreviousBackStackCount;
    private String screenName;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        headings = new Stack<>();
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userId = SharedPrefUtils.readLongPreferenceValue(Constants.PREF_USER_ID);
        Fragment fragment;
        if(userId>0) {
            fragment = getSupportFragmentManager().findFragmentByTag("Order");
            if(fragment == null)
            fragment = new OrderFragment();
        }else{
            fragment = getSupportFragmentManager().findFragmentByTag("login");
            if(fragment == null)
                fragment = LoginFragment.newInstance(getString(R.string.login),null);
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();

        headings.push("Cart");
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
}
