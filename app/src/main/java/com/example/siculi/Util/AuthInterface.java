package com.example.siculi.Util;

import com.example.siculi.Model.AuthModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthInterface {
    @FormUrlEncoded
    @POST("auth/login")
    Call<AuthModel> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
