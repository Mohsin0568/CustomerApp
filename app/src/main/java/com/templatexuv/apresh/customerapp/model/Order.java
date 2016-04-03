package com.templatexuv.apresh.customerapp.model;

import android.net.Network;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 3/9/2016.
 */
public class Order implements Parcelable {
    double totalCost;
    String paymentMethod;
    //"08-Mar-2016 Tue 23:43:03
    String orderDate;
    String orderCode;
    int orderId;
    String deliverAddressName;
    String address;
    String landmark;
    String phone;
    String city;
    String pinCode;
    List<Product> products;

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDeliverAddressName() {
        return deliverAddressName;
    }

    public void setDeliverAddressName(String deliverAddressName) {
        this.deliverAddressName = deliverAddressName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Order(){

    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeDouble(totalCost);
        dest.writeString(paymentMethod);
        dest.writeString(orderDate);
        dest.writeString(orderCode);
        dest.writeInt(orderId);
        dest.writeString(deliverAddressName);
        dest.writeString(address);
        dest.writeString(landmark);
        dest.writeString(phone);
        dest.writeString(city);
        dest.writeString(pinCode);
        dest.writeList(products);
    }

    private Order(Parcel in){
        this.totalCost = in.readDouble();
        this.paymentMethod = in.readString();
        this.orderDate = in.readString();
        this.orderCode = in.readString();
        this.orderId = in.readInt();
        this.deliverAddressName = in.readString();
        this.address = in.readString();
        this.landmark = in.readString();
        this.phone = in.readString();
        this.city = in.readString();
        this.pinCode = in.readString();
        this.products = new ArrayList<Product>();
        in.readList(products, Product.class.getClassLoader());
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {

        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}
