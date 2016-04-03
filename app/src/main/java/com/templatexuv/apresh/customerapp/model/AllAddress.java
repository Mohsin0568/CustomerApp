package com.templatexuv.apresh.customerapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 7/4/2015.
 */
public class AllAddress {

    boolean issuccess;
    boolean issucess;
    String message;
    int noOfAddress;
    ArrayList<Address> addresses;
    long addressId;

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

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }



    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public int getNoOfAddress() {
        return noOfAddress;
    }

    public void setNoOfAddress(int noOfAddress) {
        this.noOfAddress = noOfAddress;
    }
}
