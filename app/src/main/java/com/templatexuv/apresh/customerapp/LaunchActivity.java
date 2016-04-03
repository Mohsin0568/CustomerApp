package com.templatexuv.apresh.customerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class LaunchActivity extends AppCompatActivity {

    private String mTitle;
    private LinearLayout toolbarLay;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        toolbarLay = (LinearLayout) findViewById(R.id.toolbarLay);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SplashFragment())
                    .commit();
        }
    }
    public void onSectionAttached(String screenName,boolean isBackEnabled) {
        if(screenName != null){
            mTitle = screenName;
            getSupportActionBar().show();
            getSupportActionBar().setTitle(screenName);

        }else{
            if(getSupportActionBar()!=null)
             getSupportActionBar().hide();
        }

            getSupportActionBar().setHomeButtonEnabled(isBackEnabled);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBackEnabled);
            getSupportActionBar().setDisplayShowHomeEnabled(isBackEnabled);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
