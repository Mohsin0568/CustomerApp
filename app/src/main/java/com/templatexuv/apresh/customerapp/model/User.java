package com.templatexuv.apresh.customerapp.model;


public class User{

   boolean issuccess;
    boolean isLoginSuccess;
   String message;
   public long userId;
   public String firstName;
    public String surName;
   public String email;
   public String phoneNumber;


    public boolean isIssuccess() {
        return issuccess;
    }

    public String getMessage() {
        return message;
    }

    public long getUserId() { return userId; }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setIsLoginSuccess(boolean isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
