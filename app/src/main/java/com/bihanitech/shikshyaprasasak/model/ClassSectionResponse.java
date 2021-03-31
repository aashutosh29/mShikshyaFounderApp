package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassSectionResponse {


    @SerializedName("data")
    @Expose
    private List<ClassSection> data = null;

    public ClassSectionResponse() {
    }

    public List<ClassSection> getData() {
        return data;
    }

    public void setData(List<ClassSection> data) {
        this.data = data;
    }

}
