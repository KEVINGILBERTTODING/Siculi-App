package com.example.siculi.Model;

import com.example.siculi.Util.DataApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KaryawanModel implements Serializable {
    @SerializedName("id")
    String id;

    @SerializedName("nik")
    String nik;

    @SerializedName("nama")
    String nama;

    @SerializedName("id_role")
    String id_role;

    @SerializedName("foto")
    String foto;



    @SerializedName("masuk_kerja")
    String masuk_kerja;

    @SerializedName("jenis_kelamin")
    String jenis_kelamin;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("alamat")
    String alamat;

    @SerializedName("telp")
    String telp;

    @SerializedName("atasan")
    String atasan;

    @SerializedName("jabatan")
    String jabatan;

    @SerializedName("golongan")
    String golongan;

    @SerializedName("sisa_cuti")
    String sisa_cuti;

    @SerializedName("tgl_masuk")
    String tgl_masuk;

    @SerializedName("status")
    String status;

    @SerializedName("nama_atasan")
    String nama_atasan;

    public KaryawanModel(String id, String nik, String nama, String id_role, String foto, String masuk_kerja, String jenis_kelamin, String email, String password, String alamat, String telp, String atasan, String jabatan, String golongan, String sisa_cuti, String tgl_masuk, String status, String nama_atasan) {
        this.id = id;
        this.nik = nik;
        this.nama = nama;
        this.id_role = id_role;
        this.foto = foto;
        this.masuk_kerja = masuk_kerja;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.telp = telp;
        this.atasan = atasan;
        this.jabatan = jabatan;
        this.golongan = golongan;
        this.sisa_cuti = sisa_cuti;
        this.tgl_masuk = tgl_masuk;
        this.status = status;
        this.nama_atasan = nama_atasan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getFoto() {
        return DataApi.URL_IMAGE_PROFILE_KARYAWAN + foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMasuk_kerja() {
        return masuk_kerja;
    }

    public void setMasuk_kerja(String masuk_kerja) {
        this.masuk_kerja = masuk_kerja;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAtasan() {
        return atasan;
    }

    public void setAtasan(String atasan) {
        this.atasan = atasan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getSisa_cuti() {
        return sisa_cuti;
    }

    public void setSisa_cuti(String sisa_cuti) {
        this.sisa_cuti = sisa_cuti;
    }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama_atasan() {
        return nama_atasan;
    }

    public void setNama_atasan(String nama_atasan) {
        this.nama_atasan = nama_atasan;
    }


}
