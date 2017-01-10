package com.sajorahasan.shoppy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajora on 09-01-2017.
 */

public class ShoppyProfile {

    @SerializedName("profiledata")
    @Expose
    private List<ProfileData> profiledata = null;

    public List<ProfileData> getProfiledata() {
        return profiledata;
    }

    public void setProfiledata(List<ProfileData> profiledata) {
        this.profiledata = profiledata;
    }

}
