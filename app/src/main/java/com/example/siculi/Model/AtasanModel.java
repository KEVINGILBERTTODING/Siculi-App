package com.example.siculi.Model;

import com.example.siculi.Util.DataApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AtasanModel implements Serializable {
    @SerializedName("kd_atasan")
    String kd_atasan;

    @SerializedName("nama")
    String nama;

    @SerializedName("nik")
    String nik;

    @SerializedName("masuk_kerja")
    String masuk_kerja;

    @SerializedName("jabatan")
    String jabatan;

    @SerializedName("golongan")
    String golongan;

    @SerializedName("atasan")
    String atasan;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("telp")
    String telp;

    @SerializedName("id_role")
    String id_role;

    @SerializedName("jeniskel")
    String jeniskel;

    @SerializedName("status")
    String status;

    @SerializedName("foto")
    String foto;

    @SerializedName("sisa_cuti")
    String sisa_cuti;

    @SerializedName("tgl_masuk")
    String tgl_masuk;

    public AtasanModel(String kd_atasan, String nama, String nik, String masuk_kerja, String jabatan, String golongan, String atasan, String email, String password, String telp, String id_role, String jeniskel, String status, String foto, String sisa_cuti, String tgl_masuk) {
        this.kd_atasan = kd_atasan;
        this.nama = nama;
        this.nik = nik;
        this.masuk_kerja = masuk_kerja;
        this.jabatan = jabatan;
        this.golongan = golongan;
        this.atasan = atasan;
        this.email = email;
        this.password = password;
        this.telp = telp;
        this.id_role = id_role;
        this.jeniskel = jeniskel;
        this.status = status;
        this.foto = foto;
        this.sisa_cuti = sisa_cuti;
        this.tgl_masuk = tgl_masuk;
    }

    public String getKd_atasan() {
        return kd_atasan;
    }

    public void setKd_atasan(String kd_atasan) {
        this.kd_atasan = kd_atasan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getMasuk_kerja() {
        return masuk_kerja;
    }

    public void setMasuk_kerja(String masuk_kerja) {
        this.masuk_kerja = masuk_kerja;
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

    public String getAtasan() {
        return atasan;
    }

    public void setAtasan(String atasan) {
        this.atasan = atasan;
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

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getJeniskel() {
        return jeniskel;
    }

    public void setJeniskel(String jeniskel) {
        this.jeniskel = jeniskel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto() {
        return DataApi.URL_IMAGE_PROFILE_ATASAN + foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
}
