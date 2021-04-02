package com.bihanitech.shikshyaprasasak.model.holiday;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HolidayResponse {
    @SerializedName("data")
    @Expose
    private List<Holiday> data = null;

    public List<Holiday> getData() {
        return data;
    }

    public void setData(List<Holiday> data) {
        this.data = data;
    }

}
