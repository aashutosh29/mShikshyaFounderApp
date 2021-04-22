package com.bihanitech.shikshyaprasasak.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TitleWise {

    @SerializedName("PARTICULAR")
    @Expose
    private String particular;

    @SerializedName("TOTALPAID")
    @Expose
    private Integer totalpaid;

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Integer getTotalpaid() {
        return totalpaid;
    }

    public void setTotalpaid(Integer totalpaid) {
        this.totalpaid = totalpaid;
    }

}