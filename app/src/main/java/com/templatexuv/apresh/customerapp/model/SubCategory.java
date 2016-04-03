package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 9/14/2015.
 */
public class SubCategory {

    public String catOneId;
    public String catTwoId;

    public String catTwoName;

    List<ChildCategory> catThrees;

    public String getCatTwoId() {
        return catTwoId;
    }

    public String getCatOneId() {
        return catOneId;
    }

    public void setCatOneId(String catOneId) {
        this.catOneId = catOneId;
    }

    public void setCatTwoId(String catTwoId) {
        this.catTwoId = catTwoId;
    }

    public String getCatTwoName() {
        return catTwoName;
    }

    public void setCatTwoName(String catTwoName) {
        this.catTwoName = catTwoName;
    }

    public List<ChildCategory> getCatThrees() {
        return catThrees;
    }

    public void setCatThrees(List<ChildCategory> catThrees) {
        this.catThrees = catThrees;
    }
}