package com.example.siculi.Util;

import com.airbnb.lottie.L;
import com.example.siculi.Model.AdminModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.KaryawanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AdminInterface {
    @GET("admin/getallcuti")
    Call<List<CutiModel>> getAllCuti(
            @Query("status") String status
    );

    @GET("admin/getMyProfile")
    Call<AdminModel> getMyProfile(
            @Query("user_id") String userId
    );

    @GET("admin/getAllKaryawan")
    Call<List<KaryawanModel>> getAllKaryawan();


}
