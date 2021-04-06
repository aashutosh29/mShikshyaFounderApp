package com.bihanitech.shikshyaprasasak.model.acedamics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcademicResponse {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ClassData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClassData> getData() {
        return data;
    }

    public void setData(List<ClassData> data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}