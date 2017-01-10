package com.sajorahasan.shoppy.model;

/**
 * Created by Sajora on 23-12-2016.
 */

public class ServerResponse {

    private String result;
    private String message;
    private User user;
    boolean success;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getSuccess() {
        return success;
    }
}
