package com.sajorahasan.shoppy;

import com.sajorahasan.shoppy.model.ServerRequest;
import com.sajorahasan.shoppy.model.ServerResponse;
import com.sajorahasan.shoppy.model.ShoppyBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Sajora on 23-12-2016.
 */

public interface RequestInterface {

    @POST("shoppy/api/")
    Call<ServerResponse> operation(@Body ServerRequest request);

    @FormUrlEncoded
    @POST("shoppy/api/showCart.php")
    Call<ShoppyBean> getCart(@Field("u_id") String u_id);

    @FormUrlEncoded
    @POST("shoppy/api/deleteItemFromCart.php")
    Call<ShoppyBean> remCart(@Field("id") String id);
}
