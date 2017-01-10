package com.sajorahasan.shoppy;

import com.sajorahasan.shoppy.model.ServerRequest;
import com.sajorahasan.shoppy.model.ServerResponse;
import com.sajorahasan.shoppy.model.ShoppyBean;
import com.sajorahasan.shoppy.model.ShoppyProfile;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("/shoppy/api/uploadPic.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("file") RequestBody name,
                                    @Part("u_id") RequestBody u_id,
                                    @Part("nm") RequestBody rbName,
                                    @Part("phone") RequestBody rbPhone,
                                    @Part("address") RequestBody rbAddress,
                                    @Part("city") RequestBody rbCity,
                                    @Part("pin") RequestBody rbPin);

    @FormUrlEncoded
    @POST("shoppy/api/profileShowWithId.php")
    Observable<ShoppyProfile> getUserData(@Field("u_id") String u_id);
}
