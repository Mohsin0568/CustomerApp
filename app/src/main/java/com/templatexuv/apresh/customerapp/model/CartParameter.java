package com.templatexuv.apresh.customerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apresh on 10/18/2015.
 */
public class CartParameter implements Parcelable{

    List params;

    public List getParams() {
        return params;
    }

    public void setParams(List params) {
        this.params = params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(params);

    }
    public CartParameter(){

    }
    public CartParameter(Parcel in){
        this.params = new ArrayList<Object>();
        in.readList(params,null);
    }

    public static final Parcelable.Creator<CartParameter> CREATOR = new Parcelable.Creator<CartParameter>() {

        @Override
        public CartParameter createFromParcel(Parcel source) {
            return new CartParameter(source);
        }

        @Override
        public CartParameter[] newArray(int size) {
            return new CartParameter[size];
        }
    };

}
