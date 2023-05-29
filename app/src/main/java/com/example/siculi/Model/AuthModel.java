package com.example.siculi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuthModel implements Serializable {
    @SerializedName("status")
    Integer status;
    @SerializedName("role")
    String role;
    @SerializedName("user_id")
    String userId;
    @SerializedName("nama")
    String nama;
    @SerializedName("atasan")
    Integer atasan;
    @SerializedName("message")
    String message;

    public AuthModel(Integer status, String role, String userId, String nama, Integer atasan, String message) {
        this.status = status;
        this.role = role;
        this.userId = userId;
        this.nama = nama;
        this.atasan = atasan;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getAtasan() {
        return atasan;
    }

    public void setAtasan(Integer atasan) {
        this.atasan = atasan;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
