package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 11/4/2015.
 */
public class AllProperty {
    List<Property> propAndValues;
    boolean issuccess;
    String message;

    public List<Property> getPropAndValues() {
        return propAndValues;
    }

    public void setPropAndValues(List<Property> propAndValues) {
        this.propAndValues = propAndValues;
    }

    public boolean issuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
