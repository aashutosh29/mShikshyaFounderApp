package com.bihanitech.shikshyaprasasak.model.incomeSummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomeSummaryList {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("current_page")
    @Expose
    private Integer pageno;
    @SerializedName("total")
    @Expose
    private Integer totalpages;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(Integer totalpages) {
        this.totalpages = totalpages;
    }

}