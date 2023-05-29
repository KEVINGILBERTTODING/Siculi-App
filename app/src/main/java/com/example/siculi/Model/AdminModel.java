package com.example.siculi.Model;

import com.example.siculi.Util.DataApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdminModel implements Serializable {
    @SerializedName("")
    private String kd_admin;

    @SerializedName("nama")
    private String nama;

    @SerializedName("nik")
    private String nik;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("golongan")
    private String golongan;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("id_role")
    private String id_role;

    @SerializedName("jeniskel")
    private String jeniskel;

    @SerializedName("status")
    private String status;

    @SerializedName("foto")
    private String foto;


    public AdminModel(String kd_admin, String nama, String nik, String jabatan, String golongan, String email, String password, String id_role, String jeniskel, String status, String foto) {
        this.kd_admin = kd_admin;
        this.nama = nama;
        this.nik = nik;
        this.jabatan = jabatan;
        this.golongan = golongan;
        this.email = email;
        this.password = password;
        this.id_role = id_role;
        this.jeniskel = jeniskel;
        this.status = status;
        this.foto = foto;
    }

    public String getKd_admin() {
        return kd_admin;
    }

    public void setKd_admin(String kd_admin) {
        this.kd_admin = kd_admin;
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
        return DataApi.URL_IMAGE_PROFILE_ADMIN + foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
