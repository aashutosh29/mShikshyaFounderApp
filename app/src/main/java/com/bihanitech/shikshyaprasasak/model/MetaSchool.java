package com.bihanitech.shikshyaprasasak.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dilip on 3/6/18.
 */


public class MetaSchool {

    @SerializedName("data")
    @Expose
    private List<SchoolInfo> data = null;

    public List<SchoolInfo> getData() {
        return data;
    }

    public void setData(List<SchoolInfo> data) {
        this.data = data;
    }

}