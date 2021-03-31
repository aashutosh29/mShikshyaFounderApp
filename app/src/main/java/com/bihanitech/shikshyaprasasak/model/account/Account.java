package com.bihanitech.shikshyaprasasak.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {
    @Expose
    private String transactionDate;
    @SerializedName("transaction_eng_date")
    @Expose
    private String transactionEngDate;
    @SerializedName("particular")
    @Expose
    private String particular;
    @SerializedName("dr")
    @Expose
    private Integer dr;
    @SerializedName("cr")
    @Expose
    private Integer cr;
    @SerializedName("vchrno")
    @Expose
    private String vchrno;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionEngDate() {
        return transactionEngDate;
    }

    public void setTransactionEngDate(String transactionEngDate) {
        this.transactionEngDate = transactionEngDate;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getVchrno() {
        return vchrno;
    }

    public void setVchrno(String vchrno) {
        this.vchrno = vchrno;
    }

}
