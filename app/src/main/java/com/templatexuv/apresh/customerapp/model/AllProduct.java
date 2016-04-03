package com.templatexuv.apresh.customerapp.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Apresh on 10/7/2015.
 */
public class AllProduct {

    boolean issucess;
    boolean issuccess;
    String message;
    List<Product> products;
    int noOfProducts =-1;

    public boolean issucess() {
        return issucess;
    }

    public void setIssucess(boolean issucess) {
        this.issucess = issucess;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getNoOfProducts() {
        return noOfProducts;
    }

    public void setNoOfProducts(int noOfProducts) {
        this.noOfProducts = noOfProducts;
    }
}
