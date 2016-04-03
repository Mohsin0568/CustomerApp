package com.templatexuv.apresh.customerapp.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;


public class BaseModel {

    boolean issuccess;
    boolean issucess;
    boolean status;
    String message;
    long sellerId;
    int NumOfProducts;
    int NumberOfProducts;

    List<Category> categories;
    List<Product> products;
    List<Property> propAndValues;

    int totalSllers;
    List<Seller> seller;

    long productId;

    public BaseModel(){
    }

    public BaseModel(boolean issuccess,String message){
        this.issuccess = issuccess;
        this.message = message;
    }

    public boolean issucess() {
        return issucess;
    }

    public void setIssucess(boolean issucess) {
        this.issucess = issucess;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    @JsonProperty("NumOfProducts")
    public int getNumOfProducts() {
        return NumOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        NumOfProducts = numOfProducts;
    }
    @JsonProperty("NumberOfProducts")
    public int getNumberOfProducts() {
        return NumberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        NumberOfProducts = numberOfProducts;
    }

    /*@JsonProperty("NumberOfProducts")
        public int getNumOfProducts() {
            return NumOfProducts;
        }

        @JsonProperty
        public void setNumOfProducts(int numOfProducts) {
            NumOfProducts = numOfProducts;
        }
    */
    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<Category> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Property> getPropAndValues() {
        return propAndValues;
    }

    public void setPropAndValues(List<Property> propAndValues) {
        this.propAndValues = propAndValues;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public boolean issuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getTotalSllers() {
        return totalSllers;
    }

    public void setTotalSllers(int totalSllers) {
        this.totalSllers = totalSllers;
    }

    public List<Seller> getSeller() {
        return seller;
    }

    public void setSeller(List<Seller> seller) {
        this.seller = seller;
    }
}
