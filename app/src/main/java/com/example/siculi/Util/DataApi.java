package com.example.siculi.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApi {
    public static final String IP_ADDRESS = "192.168.33.200"; // isi ip address
    public static final String BASE_URL = "http://" + IP_ADDRESS + "/siculi/api/";
    public static final String URL_IMAGE_PROFILE_ADMIN = "http://" + IP_ADDRESS + "/siculi/assets/data/admin/profil/";
    public static final String URL_IMAGE_PROFILE_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/assets/data/karyawan/profil/";
    public static final String URL_IMAGE_PROFILE_ATASAN = "http://" + IP_ADDRESS + "/siculi/assets/data/atasan/profil/";
    public static final String URL_IMAGE_PROFILE_KETUA = "http://" + IP_ADDRESS + "/siculi/assets/data/ketua/profil/";


    // URL DOWNLOAD FILE ROLE KARYAWAN
    public static final String URL_DOWNLOAD_SURAT_IZIN_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/karyawan/downloadSuratIzin/";
    public static final String URL_DOWNLOAD_SURAT_CUTI_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/karyawan/downloadSuratCuti/";

    // URL DOWNLOAD FILE ROLE ATASAN

    // URL DOWNLOAD SURAT IZIN DAN CUTI ATASAN
    public static final String URL_DOWNLOAD_SURAT_CUTI_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratcuti/";
    public static final String URL_DOWNLOAD_SURAT_IZIN_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratIzin/";


    // URLL DOWNLOAD SURAT PENGAJUAN IZIN DAN CUTI KARYAWAN DAN ATASAN
    public static final String URL_DOWNLOAD_SURAT_IZIN_KARYAWAN_CEPAT = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratIzinKaryawanCepat/";
    public static final String URL_DOWNLOAD_SURAT_IZIN_KARYAWAN_NORMAL = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratIzinKaryawanNormal/";
    public static final String URL_DOWNLOAD_SURAT_IZIN_ATASAN_NORMAL = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratIzinNormalAtasan/";
    public static final String URL_DOWNLOAD_SURAT_IZIN_ATASAN_CEPAT = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratIzinCepatAtasan/";

    // URL DOWNLOAD LAPORAN REKAP IZIN KARYAWAN DAN ATASAN
    public static final String URL_DOWNLOAD_REKAP_LAPORAN_IZIN_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadRekapLaporanIzinKaryawan/";
    public static final String URL_DOWNLOAD_REKAP_LAPORAN_IZIN_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadRekapLaporanIzinAtasan/";

    // URL DOWNLOAD LAMPIRAN SURAT CUTI KARYAWAN DAN ATASAN
    public static final String URL_DOWNLOAD_LAMPIRAN_SURAT_CUTI_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratLampiranCutiKaryawan/";
    public static final String URL_DOWNLOAD_LAMPIRAN_SURAT_CUTI_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratLampiranCutiAtasan/";


    // URL DOWNLOAD LAPORAN REKAP CUTI KARYAWAN DAN ATASAN
    public static final String URL_DOWNLOAD_REKAP_LAPORAN_CUTI_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadRekapLaporanCutiKaryawan/";
    public static final String URL_DOWNLOAD_REKAP_LAPORAN_CUTI_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadRekapLaporanCutiAtasan/";


    // URL DOWNLOAD SURAT CUTI KARYAWAN DAN ATASAN
    public static final String URL_DOWNLOAD_HISTORY_SURAT_CUTI_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/atasan/downloadSuratCutiAtasan/";


    // URL DOWNLOAD REKAP LAPORAN PENGAJUAN IZIN KARYAWAN DAN ATASA
    public static final String URL_DOWNLOAD_REKAP_PENGAJUAN_IZIN_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/ketua/downloadRekapPengajuanIzinKaryawan/";
    public static final String URL_DOWNLOAD_REKAP_PENGAJUAN_IZIN_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/ketua/downloadRekapPengajuanIzinAtasan/";

    // URL DOWNLOAD REKAP LAPORAN PENGAJUAN CUTI KARYAWAN DAN ATASAN
    public static final String URL_DOWNLOAD_REKAP_PENGAJUAN_CUTI_KARYAWAN = "http://" + IP_ADDRESS + "/siculi/api/ketua/downloadRekapLaporanPengajuanCutiKaryawan/";
    public static final String URL_DOWNLOAD_REKAP_PENGAJUAN_CUTI_ATASAN = "http://" + IP_ADDRESS + "/siculi/api/ketua/downloadRekapLaporanPengajuanCutiAtasan/";
    public static final String URL_DOWNLOAD_SURAT_CUTI_ATASAN_KETUA = "http://" + IP_ADDRESS + "/siculi/api/ketua/downloadSuratCutiAtasanKetua/";










    public static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
