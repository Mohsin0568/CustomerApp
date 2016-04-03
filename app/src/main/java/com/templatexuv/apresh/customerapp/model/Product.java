package com.templatexuv.apresh.customerapp.model;

import android.net.Network;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arokalla on 7/10/2015.
 */
public class Product implements Parcelable{

    //{"issuccess":true,"NumOfProducts":5,
    // "products":[{"prodId":3,"prodName":"Product1","prodDesc":"god quality top rated check it noe","quantity":2,
    // "price":200,"catId":1,"disabled":0,"propAndValues":[{"prodPropertyId":"1","propName":"Size","propValue":"19.5"}]
    // ,"catOneName":"Mens","catTwoName":"Topi","catThreeName":"White Topi",
    // "imageOneURL":"http:\/fantabazaar.com\/images\/Mens\/Topi\/White%20Topi\/1.jpg",
    // "imageTwoURL":"http:\/fantabazaar.com\/images\/Mens\/Topi\/White%20Topi\/2.jpg",
    // "imageThreeURL":"http:\/fantabazaar.com\/images\/Mens\/Topi\/White%20Topi\/3.jpg"}

    public String prodId;
    public String prodName;
    public int quantity;
    public double price;
    public int catId;
    public int disabled;
    public String prodDesc;
    public String catOneName ="";
    public String catTwoName ="";
    public String catThreeName ="";
    public boolean isApproved;

    List<Property> propAndValues;
    double sellerRatings;

    public List<String> imageURL = new ArrayList<>();

    int orderSellerId;
    int sellerId;
    int productId;
    double totalCost;
    int orderStatus;
    String productName;
    String imagePath;

    public Product(){

    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prodId);
        dest.writeString(prodName);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeInt(catId);
        dest.writeInt(disabled);
        dest.writeString(prodDesc);
        dest.writeString(catOneName);
        dest.writeString(catTwoName);
        dest.writeString(catThreeName);
        dest.writeByte((byte) (isApproved ? 1 : 0));

        dest.writeList(propAndValues);

        dest.writeStringList(imageURL);
        dest.writeDouble(sellerRatings);

        dest.writeInt(orderSellerId);
        dest.writeInt(sellerId);
        dest.writeInt(productId);
        dest.writeDouble(totalCost);
        dest.writeInt(orderStatus);
        dest.writeString(productName);
        dest.writeString(imagePath);

    }

    /**
     * A constructor that initializes the Product object
     **//*
    public Product(String prodId,String prodName, int quantity,double price,int catId, String prodDesc, String catOne,String catTwo,String catThree,String imageOneURL,String imageTwoURL,String imageThreeURL){
        this.prodId = prodId;
        this.prodName = prodName;
        this.quantity = quantity;
        this.price = price;
        this.catId = catId;
        this.disabled = disabled;
        this.prodDesc = prodDesc;
        this.catOne = catOne;
        this.catTwo = catTwo;
        this.catThree = catThree;
        this.imageOneURL = imageOneURL;
        this.imageTwoURL = imageTwoURL;
        this.imageThreeURL = imageThreeURL;
    }
*/


    private Product(Parcel in){

        this.prodId = in.readString();
        this.prodName = in.readString();
        this.quantity = in.readInt();
        this.price = in.readDouble();
        this.catId = in.readInt();
        this.disabled = in.readInt();
        this.prodDesc = in.readString();
        this.catOneName = in.readString();
        this.catTwoName = in.readString();
        this.catThreeName = in.readString();
        this.isApproved = in.readByte() != 0;
        this.propAndValues = new ArrayList<Property>();
        in.readList(propAndValues, Property.class.getClassLoader());

        in.readStringList(imageURL);
        this.sellerRatings = in.readDouble();

        this.orderSellerId = in.readInt();
        this.sellerId = in.readInt();
        this.productId = in.readInt();
        this.totalCost = in.readDouble();
        this.orderStatus = in.readInt();
        this.productName = in.readString();
        this.imagePath = in.readString();

    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {

        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };



    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getCatOneName() {
        return catOneName;
    }

    public void setCatOneName(String catOneName) {
        this.catOneName = catOneName;
    }

    public String getCatTwoName() {
        return catTwoName;
    }

    public void setCatTwoName(String catTwoName) {
        this.catTwoName = catTwoName;
    }

    public String getCatThreeName() {
        return catThreeName;
    }

    public void setCatThreeName(String catThreeName) {
        this.catThreeName = catThreeName;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public List<Property> getPropAndValues() {
        return propAndValues;
    }

    public void setPropAndValues(List<Property> propAndValues) {
        this.propAndValues = propAndValues;
    }

    public List<String> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<String> imageURL) {
        this.imageURL = imageURL;
    }

    public double getSellerRatings() {
        return sellerRatings;
    }

    public void setSellerRatings(double sellerRatings) {
        this.sellerRatings = sellerRatings;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public int getOrderSellerId() {
        return orderSellerId;
    }

    public void setOrderSellerId(int orderSellerId) {
        this.orderSellerId = orderSellerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
