package com.example.siculi.Model;

import com.example.siculi.Util.DataApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KetuaModel implements Serializable {
    @SerializedName("kd_ketua")
    private String kdKetua;

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
    private String idRole;

    @SerializedName("jeniskel")
    private String jenisKel;

    @SerializedName("status")
    private String status;

    @SerializedName("foto")
    private String foto;

    public KetuaModel(String kdKetua, String nama, String nik, String jabatan, String golongan, String email, String password, String idRole, String jenisKel, String status, String foto) {
        this.kdKetua = kdKetua;
        this.nama = nama;
        this.nik = nik;
        this.jabatan = jabatan;
        this.golongan = golongan;
        this.email = email;
        this.password = password;
        this.idRole = idRole;
        this.jenisKel = jenisKel;
        this.status = status;
        this.foto = foto;
    }

    public String getKdKetua() {
        return kdKetua;
    }

    public void setKdKetua(String kdKetua) {
        this.kdKetua = kdKetua;
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

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getJenisKel() {
        return jenisKel;
    }

    public void setJenisKel(String jenisKel) {
        this.jenisKel = jenisKel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto() {
        return DataApi.URL_IMAGE_PROFILE_KETUA + foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
