package com.example.siculi.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UnitKerjaModel implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("dept")
    String dept;

    public UnitKerjaModel(String id, String dept) {
        this.id = id;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }


}
