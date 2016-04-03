package com.templatexuv.apresh.customerapp.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Apresh on 10/7/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllPaymentResponse {

    boolean issuccess;

    String message;//	Message which should be displayed when product is not available

    List<Order> orders;

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
