package com.bihanitech.shikshyaprasasak.model.section;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionResponse {
    @SerializedName("data")
    @Expose
    private List<Section> data = null;

    public List<Section> getData() {
        return data;
    }

    public void setData(List<Section> data) {
        this.data = data;
    }
}
