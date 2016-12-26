package com.sajorahasan.shoppy.model;

/**
 * Created by Sajora on 23-12-2016.
 */

public class ServerRequest {

    private String operation;
    private User user;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
