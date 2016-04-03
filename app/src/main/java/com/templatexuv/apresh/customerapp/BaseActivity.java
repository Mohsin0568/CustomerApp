package com.templatexuv.apresh.customerapp;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Apresh on 2/14/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private String screenName;

    public void setdisplayTitle(String title){
        try {
            screenName = title;
            getSupportActionBar().setTitle(screenName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
