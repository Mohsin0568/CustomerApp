package com.templatexuv.apresh.customerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 10/24/2015.
 */
public class AllCart implements Parcelable{


    public boolean issucess;
    public String message;
    public int noOfProducts;
    public List<Cart> products;

    public boolean issucess() {
        return issucess;
    }

    public void setIssucess(boolean issucess) {
        this.issucess = issucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNoOfProducts() {
        return noOfProducts;
    }

    public void setNoOfProducts(int noOfProducts) {
        this.noOfProducts = noOfProducts;
    }

    public List<Cart> getProducts() {
        return products;
    }

    public void setProducts(List<Cart> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte)(issucess?1:0));
        dest.writeString(message);
        dest.writeInt(noOfProducts);
        dest.writeList(products);
    }

    public AllCart(){

    }
    public AllCart(Parcel in){
        this.issucess = in.readByte()!=0;
        this.message = in.readString();
        this.noOfProducts = in.readInt();
        this.products = new ArrayList<Cart>();
        in.readList(products,AllCart.class.getClassLoader());
    }
    public static final Creator<AllCart> CREATOR = new Creator<AllCart>() {
        @Override
        public AllCart createFromParcel(Parcel source) {
            return new AllCart(source);
        }

        @Override
        public AllCart[] newArray(int size) {
            return new AllCart[size];
        }
    };
}
