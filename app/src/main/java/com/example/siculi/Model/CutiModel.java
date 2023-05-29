package com.example.siculi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CutiModel implements Serializable {
    @SerializedName("id")
    String id;

    @SerializedName("id_karyawan")
    String idKaryawan;

    @SerializedName("atasan")
    String atasan;

    @SerializedName("tgl_cuti")
    String tglCuti;

    @SerializedName("jenis_cuti")
    String jenisCuti;

    @SerializedName("jumlah_cuti")
    String jumlahCuti;

    @SerializedName("keperluan")
    String keperluan;

    @SerializedName("alamat")
    String alamat;

    @SerializedName("status")
    String status;

    @SerializedName("tgl_masuk")
    String tglMasuk;

    @SerializedName("surat")
    String surat;

    @SerializedName("tgl_upload")
    String tglUpload;

    @SerializedName("sisa_cuti")
    String sisaCuti;

    @SerializedName("keterangan_sisa")
    String keteranganSisa;

    public CutiModel(String id, String idKaryawan, String atasan, String tglCuti, String jenisCuti, String jumlahCuti, String keperluan, String alamat, String status, String tglMasuk, String surat, String tglUpload, String sisaCuti, String keteranganSisa) {
        this.id = id;
        this.idKaryawan = idKaryawan;
        this.atasan = atasan;
        this.tglCuti = tglCuti;
        this.jenisCuti = jenisCuti;
        this.jumlahCuti = jumlahCuti;
        this.keperluan = keperluan;
        this.alamat = alamat;
        this.status = status;
        this.tglMasuk = tglMasuk;
        this.surat = surat;
        this.tglUpload = tglUpload;
        this.sisaCuti = sisaCuti;
        this.keteranganSisa = keteranganSisa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getAtasan() {
        return atasan;
    }

    public void setAtasan(String atasan) {
        this.atasan = atasan;
    }

    public String getTglCuti() {
        return tglCuti;
    }

    public void setTglCuti(String tglCuti) {
        this.tglCuti = tglCuti;
    }

    public String getJenisCuti() {
        return jenisCuti;
    }

    public void setJenisCuti(String jenisCuti) {
        this.jenisCuti = jenisCuti;
    }

    public String getJumlahCuti() {
        return jumlahCuti;
    }

    public void setJumlahCuti(String jumlahCuti) {
        this.jumlahCuti = jumlahCuti;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public String getSurat() {
        return surat;
    }

    public void setSurat(String surat) {
        this.surat = surat;
    }

    public String getTglUpload() {
        return tglUpload;
    }

    public void setTglUpload(String tglUpload) {
        this.tglUpload = tglUpload;
    }

    public String getSisaCuti() {
        return sisaCuti;
    }

    public void setSisaCuti(String sisaCuti) {
        this.sisaCuti = sisaCuti;
    }

    public String getKeteranganSisa() {
        return keteranganSisa;
    }

    public void setKeteranganSisa(String keteranganSisa) {
        this.keteranganSisa = keteranganSisa;
    }
}
