package com.sajorahasan.shoppy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajora on 09-01-2017.
 */

public class ImageResponse {
    // variable name should be same as in the json response from php
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
