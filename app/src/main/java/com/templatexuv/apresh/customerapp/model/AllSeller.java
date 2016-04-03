package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 10/7/2015.
 */
public class AllSeller {

    boolean issuccess;
    String message;
    List<Seller> seller;
    String totalSllers;

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

    public List<Seller> getSeller() {
        return seller;
    }

    public void setSeller(List<Seller> seller) {
        this.seller = seller;
    }

    public String getTotalSllers() {
        return totalSllers;
    }

    public void setTotalSllers(String totalSllers) {
        this.totalSllers = totalSllers;
    }
}
