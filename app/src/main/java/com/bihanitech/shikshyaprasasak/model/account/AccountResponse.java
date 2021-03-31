package com.bihanitech.shikshyaprasasak.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponse {
    @SerializedName("data")
    @Expose
    private List<Account> data = null;
    @SerializedName("onlinepaymentdata")
    @Expose
    private List<Object> onlinepaymentdata = null;

    public List<Account> getData() {
        return data;
    }

    public void setData(List<Account> data) {
        this.data = data;
    }

    public List<Object> getOnlinepaymentdata() {
        return onlinepaymentdata;
    }

    public void setOnlinepaymentdata(List<Object> onlinepaymentdata) {
        this.onlinepaymentdata = onlinepaymentdata;
    }

}

