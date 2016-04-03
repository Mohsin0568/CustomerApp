package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 10/7/2015.
 */
public class AllCheckoutResponse {

    boolean issuccess;
    boolean isProductAvailable;
    int prodId;//Product id of the product which is not available
    String message;//	Message which should be displayed when product is not available
    String lockId;//should be used when booking order


    public boolean issuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public boolean isProductAvailable() {
        return isProductAvailable;
    }

    public void setIsProductAvailable(boolean isProductAvailable) {
        this.isProductAvailable = isProductAvailable;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }
}
