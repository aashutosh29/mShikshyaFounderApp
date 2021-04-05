package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDueReport {
    @SerializedName("TotalCharge")
    @Expose
    private Integer totalCharge;
    @SerializedName("TotalPaid")
    @Expose
    private Integer totalPaid;
    @SerializedName("TotalDue")
    @Expose
    private Integer totalDue;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("sort")
    @Expose
    private Integer sort;

    public Integer getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Integer totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Integer getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Integer totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Integer getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Integer totalDue) {
        this.totalDue = totalDue;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}