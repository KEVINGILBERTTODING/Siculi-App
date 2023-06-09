package com.example.siculi.Util;

import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.Model.JenisCutiModel;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.Model.ResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @GET("karyawan/getAllIzin")
    Call<List<IzinModel>> getAllIzin(
            @Query("user_id") String userId
    );


    @GET("karyawan/filterMyIzin")
    Call<List<IzinModel>> filterMyIzin(
            @Query("user_id") String userId,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );


    @Multipart
    @POST("karyawan/insertIzin")
    Call<ResponseModel> insertIzin(
            @PartMap Map<String, RequestBody> textData
            );

    // insert cuti non surat
    @Multipart
    @POST("karyawan/insertCutiKaryawan")
    Call<ResponseModel> insertCutiNonSurat(
            @PartMap Map<String, RequestBody> textData
    );

    // insert Cuti surat
    @Multipart
    @POST("karyawan/insertCutiKaryawan")
    Call<ResponseModel> insertCutiSurat(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part surat
            );

    @GET("karyawan/getAllJenisCuti")
    Call<List<JenisCutiModel>> getAllJenisCuti();

    @FormUrlEncoded
    @POST("karyawan/confirmCutiSelesai")
    Call<ResponseModel> confirmCutiSelesai(
            @Field("user_id") String userId
    );

    @Multipart
    @POST("karyawan/editMyProfile")
    Call<ResponseModel> updateMyProfile(
            @PartMap Map<String, RequestBody> textData
    );

    @Multipart
    @POST("karyawan/editPhotoProfile")
    Call<ResponseModel> editPhotoProfile(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
    );






}
