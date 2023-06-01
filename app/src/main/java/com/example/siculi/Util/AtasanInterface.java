package com.example.siculi.Util;

import com.example.siculi.Model.AtasanModel;
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

public interface AtasanInterface {

    @GET("atasan/getMyProfile")
    Call<AtasanModel> getMyProfile(
            @Query("user_id") String userId
    );

    @GET("atasan/getAllCutiByStatus")
    Call<List<CutiModel>> getCutiByStatus(
            @Query("user_id") String userId,
            @Query("status") String status
    );

    @GET("atasan/getAllCuti")
    Call<List<CutiModel>> getAllCuti(
            @Query("user_id") String userId
    );

    @GET("atasan/filterMyCuti")
    Call<List<CutiModel>> filterMyCuti(
            @Query("user_id") String userId,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );

    @GET("atasan/getAllIzin")
    Call<List<IzinModel>> getAllIzin(
            @Query("user_id") String userId
    );


    @GET("atasan/filterMyIzin")
    Call<List<IzinModel>> filterMyIzin(
            @Query("user_id") String userId,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEn
    );


    @Multipart
    @POST("atasan/insertIzin")
    Call<ResponseModel> insertIzin(
            @PartMap Map<String, RequestBody> textData
            );

    // insert cuti non surat
    @Multipart
    @POST("atasan/insertCutiAtasan")
    Call<ResponseModel> insertCutiNonSurat(
            @PartMap Map<String, RequestBody> textData
    );

    // insert Cuti surat
    @Multipart
    @POST("atasan/insertCutiAtasan")
    Call<ResponseModel> insertCutiSurat(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part surat
            );

    @FormUrlEncoded
    @POST("atasan/confirmCutiSelesai")
    Call<ResponseModel> confirmCutiSelesai(
            @Field("user_id") String userId
    );

    @Multipart
    @POST("atasan/editMyProfile")
    Call<ResponseModel> updateMyProfile(
            @PartMap Map<String, RequestBody> textData
    );

    @Multipart
    @POST("atasan/editPhotoProfile")
    Call<ResponseModel> editPhotoProfile(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
    );

    @GET("atasan/getAllAtasanByAtasanId")
    Call<List<AtasanModel>> getAllAtasanKaryawan(
            @Query("user_id") String userId
    );

    @GET("atasan/getAllKaryawanByAtasanId")
    Call<List<KaryawanModel>> getAllKaryawan(
            @Query("user_id") String userId
    );

    // GET PENGAJUAN IZIN  KARYAAWAN yang belum di validasi
    @GET("atasan/getAllPengajuanIzinKaryawanProses")
    Call<List<IzinModel>> getAllPengajuanIzinKaryawanProses(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("atasan/validateIzinKaryawan")
    Call<ResponseModel> validateIzinKaryawan(
            @Field("izin_id") String izinId,
            @Field("status") String status
    );

    // filter pengajuan izin karyawan
    @GET("atasan/getFilterPengajuanIzinKaryawanProses")
    Call<List<IzinModel>> getFilterPengajuanIzinKaryawanProses(
            @Query("user_id") String userid,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );


    // filter history pengajuan izin karyawan
    @GET("atasan/getFilterPengajuanIzinKaryawan")
    Call<List<IzinModel>> getFilterPengajuanIzinKaryawan(
            @Query("user_id") String userid,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );



    // GET PENGAJUAN IZIN KARYAWAN yang telah di validasi
    @GET("atasan/getAllPengajuanIzinKaryawanByatasanId")
    Call<List<IzinModel>> getAllPengajuanIzinKaryawanByatasanId(
            @Query("user_id") String userId
    );

    // GET PENGAJUAN IZIN  ATASAN yang belum di validasi
    @GET("atasan/getAllPengajuanIzinAtasanProses")
    Call<List<IzinModel>> getAllPengajuanIzinAtasanProses(
            @Query("user_id") String userId
    );

    // GET PENGAJUAN IZIN  ATASAN yang telah di validasi
    @GET("atasan/getAllPengajuanIzinAtasanByatasanId")
    Call<List<IzinModel>> getAllPengajuanIzinAtasanByatasanId(
            @Query("user_id") String userId
    );



    // filter pengajuan izin atasan
    @GET("atasan/filterPengajuanIzinAtasan")
    Call<List<IzinModel>> filterPengajuanIzinAtasan(
            @Query("user_id") String userid,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );

    // filter history pengajuan izin atasan
    @GET("atasan/filterHistoryPengajuanIzinAtasan")
    Call<List<IzinModel>> filterHistoryPengajuanIzinAtasan(
            @Query("user_id") String userid,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );














}
