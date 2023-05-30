package com.example.siculi.Util;

import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.Model.KaryawanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KaryawanInterface {

    @GET("karyawan/getMyProfile")
    Call<KaryawanModel> getMyProfile(
            @Query("user_id") String userId
    );

    @GET("karyawan/getAllCutiByStatus")
    Call<List<CutiModel>> getCutiByStatus(
            @Query("user_id") String userId,
            @Query("status") String status
    );

    @GET("karyawan/getAllCuti")
    Call<List<CutiModel>> getAllCuti(
            @Query("user_id") String userId
    );

    @GET("karyawan/filterMyCuti")
    Call<List<CutiModel>> filterMyCuti(
            @Query("user_id") String userId,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );



}
