package com.example.siculi.Util;

import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.KetuaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KetuaInterface {


    @GET("ketua/getMyProfile")
    Call<KetuaModel> getKetuaProfile(
            @Query("user_id") String userId
    );

    @GET("ketua/getCountAllCuti")
    Call<List<CutiModel>> countAllCuti(
            @Query("status") String status
    );
}
