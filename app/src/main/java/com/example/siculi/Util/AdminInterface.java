package com.example.siculi.Util;

import com.example.siculi.Model.AdminModel;
import com.example.siculi.Model.AtasanModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.Model.JabatanModel;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.Model.ResponseModel;
import com.example.siculi.Model.UnitKerjaModel;

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

    @GET("admin/getAllAtasan")
    Call<List<AtasanModel>> getAllAtasan();

    @GET("admin/getAllJabatan")
    Call<List<JabatanModel>> getAllJabatan();

    @GET("admin/getAllUnitKerja")
    Call<List<UnitKerjaModel>> getAllUnitKerja();

    @Multipart
    @POST("admin/insertKaryawan")
    Call<ResponseModel> insertKaryawan(
            @PartMap Map<String, RequestBody> textData
            );

    @Multipart
    @POST("admin/updateKaryawan")
    Call<ResponseModel> updateKaryawan(
            @PartMap Map<String, RequestBody> textData
            );


    @GET("admin/getKaryawanById")
    Call<KaryawanModel> getKaryawanById(
            @Query("user_id") String userId
    );


    @GET("admin/getAllAtasanExceptKetua")
    Call<List<AtasanModel>> getAllAtasanExceptKetua();

    @GET("admin/getAtasanById")
    Call<AtasanModel> getAtasanById(
            @Query("id") String id
    );

    @Multipart
    @POST("admin/insertAtasan")
    Call<ResponseModel> insertAtasan(
            @PartMap Map<String, RequestBody> textData
    );

    @Multipart
    @POST("admin/updateAtasan")
    Call<ResponseModel> updateAtasan(
            @PartMap Map<String, RequestBody> textData
    );

    @GET("admin/getAllCutiKaryawan")
    Call<List<CutiModel>> getAllCutiKaryawan();

    @FormUrlEncoded
    @POST("admin/deleteCutiKaryawan")
    Call<ResponseModel> deleteCutiKaryawan(
            @Field("id_cuti") String idCuti,
            @Field("user_id") String userId
    );

    @GET("admin/filterDataCutiKaryawan")
    Call<List<CutiModel>> filterDataCutiKaryawan(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );


    @GET("admin/getAllCutiAtasan")
    Call<List<CutiModel>> getAllCutiAtasan();

    @FormUrlEncoded
    @POST("admin/deleteCutiatasan")
    Call<ResponseModel> deleteCutiatasan(
            @Field("id_cuti") String idCuti,
            @Field("user_id") String userId
    );

    @GET("admin/filterDataCutiatasan")
    Call<List<CutiModel>> filterDataCutiatasan(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );

    @GET("admin/getAllIzinKaryawan")
    Call<List<IzinModel>> getAllIzinKaryawan();

    @GET("admin/filterDataIzinKaryawan")
    Call<List<IzinModel>> filterDataIzinKaryawan(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );

    @FormUrlEncoded
    @POST("admin/deleteIzin")
    Call<ResponseModel> deleteIzin(
            @Field("id") String id
    );

    @GET("admin/getAllIzinatasan")
    Call<List<IzinModel>> getAllIzinAtasan();

    @GET("admin/filterDataIzinAtasan")
    Call<List<IzinModel>> filterDataIzinAtasan(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );

    @Multipart
    @POST("admin/editMyProfile")
    Call<ResponseModel> updateMyProfile(
            @PartMap Map<String, RequestBody> textData
    );

    @Multipart
    @POST("admin/editPhotoProfile")
    Call<ResponseModel> editPhotoProfile(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
            );



















}
