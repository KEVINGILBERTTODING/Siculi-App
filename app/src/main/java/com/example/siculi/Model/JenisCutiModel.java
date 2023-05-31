package com.example.siculi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JenisCutiModel implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("jenis")
    String jenis;

    public JenisCutiModel(String id, String jenis) {
        this.id = id;
        this.jenis = jenis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
