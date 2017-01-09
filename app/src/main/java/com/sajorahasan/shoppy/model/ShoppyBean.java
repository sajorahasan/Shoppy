package com.sajorahasan.shoppy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajora on 30-12-2016.
 */

public class ShoppyBean {
    @SerializedName("cart")
    @Expose
    private List<Bean> cart = null;

    public List<Bean> getCart() {
        return cart;
    }

    public void setCart(List<Bean> cart) {
        this.cart = cart;
    }
}
