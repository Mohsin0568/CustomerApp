package com.templatexuv.apresh.customerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 8/19/2015.
 */
public class Property implements Parcelable{

    String prodPropertyId;
    String propName;
    String propValue;
    String propertyName;
    String propertyId;
    List<Value> propValues ;
    boolean isChecked;

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prodPropertyId);
        dest.writeString(propName);
        dest.writeString(propValue);
        dest.writeString(propertyName);
        dest.writeString(propertyId);
        dest.writeList(propValues);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }


    public Property(){

    }
    private Property(Parcel in){

        this.prodPropertyId = in.readString();
        this.propName = in.readString();
        this.propValue = in.readString();
        this.propertyName = in.readString();
        this.propertyId = in.readString();

        this.propValues = new ArrayList<Value>();
        in.readList(propValues, Value.class.getClassLoader());

        this.isChecked = in.readByte() != 0;
    }


    public String getProdPropertyId() {
        return prodPropertyId;
    }

    public void setProdPropertyId(String prodPropertyId) {
        this.prodPropertyId = prodPropertyId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public List<Value> getPropValues() {
        return propValues;
    }

    public void setPropValues(List<Value> propValues) {
        this.propValues = propValues;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
