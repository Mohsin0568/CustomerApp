package com.templatexuv.apresh.customerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Apresh on 12/30/2015.
 */
public class Address implements Parcelable {

    public long addressId;
    public String address;
    public String landmark;
    public String name;
    public String phoneNumber;
    public String phone;
    public String city;
    public String pinCode;
    public long userId;
    public boolean isChecked;


    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Address(){

    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(addressId);
        dest.writeString(address);
        dest.writeString(landmark);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(phone);
        dest.writeString(city);
        dest.writeString(pinCode);
        dest.writeLong(userId);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }


    private Address(Parcel in){
        this.addressId = in.readLong();
        this.address = in.readString();
        this.landmark = in.readString();
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.phone = in.readString();
        this.city = in.readString();
        this.pinCode = in.readString();
        this.userId = in.readLong();
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {

        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

}
