package com.example.siculi.Util;

import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.KetuaModel;
import com.example.siculi.Model.ResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    @GET("ketua/getAllPengajuanCutiKaryawanKetua")
    Call<List<CutiModel>> getAllPengajuanCutiKaryawanKetua(
            @Query("status") String status
    );

    // get pengajuan cuti atasan
    @GET("ketua/getAllPengajuanCutiAtasanKetua")
    Call<List<CutiModel>> getAllPengajuanCutiAtasanKetua(
            @Query("status") String status
    );

    @Multipart
    @POST("ketua/validasiPermohonanCutiKaryawan")
    Call<ResponseModel> validasiPengajuanCutiKaryawan(
            @PartMap Map<String, RequestBody> textData
            );

    @GET("ketua/getAllHistoryPengajuanCutiKaryawanKetua")
    Call<List<CutiModel>> ggetAllHistoryPengajuanCutiKaryawanKetua();

    @GET("ketua/getAllHistoryPengajuanCutiAtasanKetua")
    Call<List<CutiModel>> ggetAllHistoryPengajuanCutiAtasanKetua();

    @GET("ketua/filterAllPengajuanCutiKaryawanKetua")
    Call<List<CutiModel>> filterAllPengajuanCutiKaryawanKetua(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );

    @GET("ketua/filterAllPengajuanCutiAtasanKetua")
    Call<List<CutiModel>> filterAllPengajuanCutiAtasanKetua(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );

    @GET("ketua/filterAllHistoryPengajuanCutiKaryawanKetua")
    Call<List<CutiModel>> filterAllHistoryPengajuanCutiKaryawanKetua(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );



}
