package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 7/4/2015.
 */
public class Category {
    public String catOneId;

    public String catOneName;

    List<SubCategory> catTwos;

    public List<SubCategory> getCatTwos() {
        return catTwos;
    }

    public void setCatTwos(List<SubCategory> catTwos) {
        this.catTwos = catTwos;
    }

    public String getCatOneId() {
        return catOneId;
    }

    public void setCatOneId(String catOneId) {
        this.catOneId = catOneId;
    }

    public String getCatOneName() {
        return catOneName;
    }

    public void setCatOneName(String catOneName) {
        this.catOneName = catOneName;
    }
}
