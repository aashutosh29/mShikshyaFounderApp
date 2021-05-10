package com.bihanitech.shikshyaprasasak.model.incomeSummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomeSummaryList {

    @SerializedName("data")
    @Expose
    private List<IncomeSummaryReport> data = null;
    @SerializedName("pageno")
    @Expose
    private Integer pageno;
    @SerializedName("totalpages")
    @Expose
    private Integer totalpages;

    public List<IncomeSummaryReport> getData() {
        return data;
    }

    public void setData(List<IncomeSummaryReport> data) {
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