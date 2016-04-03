package com.templatexuv.apresh.customerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 9/28/2015.
 */

public class Cart implements Parcelable {

    public int cartId;
    public int cartQuantity;
    public int productQuantity;
    public double price;
    public String productName;
    public int categoryId;
    public String categoryOneName;
    public String categoryTwoName;
    public String categoryThreeName;
    public boolean available;
    public boolean isQuantityAvailable;

   public String imageURL;

    public int productId;

    public int sellerId;

    public int getCartQuantity() {
        return cartQuantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryOneName() {
        return categoryOneName;
    }

    public void setCategoryOneName(String categoryOneName) {
        this.categoryOneName = categoryOneName;
    }

    public String getCategoryTwoName() {
        return categoryTwoName;
    }

    public void setCategoryTwoName(String categoryTwoName) {
        this.categoryTwoName = categoryTwoName;
    }

    public String getCategoryThreeName() {
        return categoryThreeName;
    }

    public void setCategoryThreeName(String categoryThreeName) {
        this.categoryThreeName = categoryThreeName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isQuantityAvailable() {
        return isQuantityAvailable;
    }

    public void setIsQuantityAvailable(boolean isQuantityAvailable) {
        this.isQuantityAvailable = isQuantityAvailable;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Cart(){

    }
    private Cart(Parcel in) {
        this.cartId = in.readInt();
        this.cartQuantity = in.readInt();
        this.productQuantity = in.readInt();
        this.price = in.readDouble();
        this.productName = in.readString();
        this.categoryId = in.readInt();
        this.categoryOneName = in.readString();
        this.categoryTwoName = in.readString();
        this.categoryThreeName = in.readString();
        this.available = in.readByte() != 0;
        this.isQuantityAvailable = in.readByte() != 0;
        this.imageURL = in.readString();
        this.productId = in.readInt();
        this.sellerId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cartId);
        dest.writeInt(cartQuantity);
        dest.writeInt(productQuantity);
        dest.writeDouble(price);
        dest.writeString(productName);
        dest.writeInt(categoryId);
        dest.writeString(categoryOneName);
        dest.writeString(categoryTwoName);
        dest.writeString(categoryThreeName);
        dest.writeByte((byte) (isAvailable() ? 1 : 0));
        dest.writeByte((byte) (isQuantityAvailable() ? 1 : 0));
        dest.writeString(imageURL);
        dest.writeInt(productId);
        dest.writeInt(sellerId);

    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
