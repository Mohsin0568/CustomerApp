package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 7/4/2015.
 */
public class AllCategory {

    boolean issuccess;
    String message;
    List<Category> categories;

    public boolean issuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    }
