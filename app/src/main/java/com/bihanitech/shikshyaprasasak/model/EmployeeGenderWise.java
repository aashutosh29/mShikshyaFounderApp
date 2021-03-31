package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeGenderWise {

    @SerializedName("GENDER")
    @Expose
    private Integer gENDER;
    @SerializedName("total")
    @Expose
    private Integer total;

    public EmployeeGenderWise(Integer gENDER, Integer total) {
        this.gENDER = gENDER;
        this.total = total;
    }

    public Integer getGENDER() {
        return gENDER;
    }

    public void setGENDER(Integer gENDER) {
        this.gENDER = gENDER;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
