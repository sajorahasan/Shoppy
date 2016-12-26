package com.sajorahasan.shoppy;

import com.sajorahasan.shoppy.model.ServerRequest;
import com.sajorahasan.shoppy.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sajora on 23-12-2016.
 */

public interface RequestInterface {

    @POST("shoppy/api/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
