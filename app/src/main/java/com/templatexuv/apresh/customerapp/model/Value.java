package com.templatexuv.apresh.customerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Apresh on 8/19/2015.
 */
public class Value implements Parcelable{

    String valueId;
    String valueName;

    String propertyId;
    String propertyName;
    boolean isChecked;

    public static final Creator<Value> CREATOR = new Creator<Value>() {
        @Override
        public Value createFromParcel(Parcel in) {
            return new Value(in);
        }

        @Override
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return valueName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(valueId);
        dest.writeString(valueName);
        dest.writeString(propertyId);
        dest.writeString(propertyName);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }


    public Value(){

    }
    private Value(Parcel in){

        this.valueId = in.readString();
        this.valueName = in.readString();
        this.propertyId = in.readString();
        this.propertyName = in.readString();
        this.isChecked = in.readByte() != 0;

    }


    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }


    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
