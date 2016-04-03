package com.templatexuv.apresh.customerapp;

import android.app.Application;
import android.content.Context;

import com.templatexuv.apresh.customerapp.imagelibrary.ImageLoader;
import com.templatexuv.apresh.customerapp.model.Property;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Apresh on 5/26/2015.
 */
public class CustomerApplication extends Application {

    private static CustomerApplication applicationInstance;
    public static Context context;
    public static String DATABASE_LOCK = "Lock";

    private boolean fetchAddress = false;
    ImageLoader imageLoader;
    HashMap<String,Property> selectionPropValues;
    List<Property> propAndValues;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        applicationInstance = new CustomerApplication();
        imageLoader = new ImageLoader(context);
    }

    public static Context getContext() {
        return context;
    }

    public static CustomerApplication getApplicationInstance() {
        return applicationInstance;
    }


    public ImageLoader getImageLoader() {
        if(imageLoader==null){
            imageLoader = new ImageLoader(context);
        }
        return imageLoader;
    }

    public HashMap<String, Property> getSelectionPropValues() {
        return selectionPropValues;
    }

    public void setSelectionPropValues(HashMap<String, Property> selectionPropValues) {
        this.selectionPropValues = selectionPropValues;
    }

    public List<Property> getPropAndValues() {
        return propAndValues;
    }

    public void setPropAndValues(List<Property> propAndValues) {
        this.propAndValues = propAndValues;
    }

    public boolean isFetchAddress() {
        return fetchAddress;
    }

    public void setFetchAddress(boolean fetchAddress) {
        this.fetchAddress = fetchAddress;
    }
}
