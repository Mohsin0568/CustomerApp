package com.templatexuv.apresh.customerapp.model;

import java.util.List;

/**
 * Created by Apresh on 10/7/2015.
 */
public class AllComments {

    boolean issuccess;
    int totalComments;
    List<Comment> comments;
    String message;

    public boolean issuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
