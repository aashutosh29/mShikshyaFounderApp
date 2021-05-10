package com.bihanitech.shikshyaprasasak.model.incomeSummary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IncomeSummaryReport {

    @SerializedName("SN")
    @Expose
    private String sn;
    @SerializedName("recordNo")
    @Expose
    private String recordNo;
    @SerializedName("nameOfStudent")
    @Expose
    private String nameOfStudent;
    @SerializedName("classInformation")
    @Expose
    private String classInformation;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("penalty")
    @Expose
    private String penalty;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("preBalance")
    @Expose
    private String preBalance;
    @SerializedName("subBalance")
    @Expose
    private String subBalance;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("dues")
    @Expose
    private String dues;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getNameOfStudent() {
        return nameOfStudent;
    }

    public void setNameOfStudent(String nameOfStudent) {
        this.nameOfStudent = nameOfStudent;
    }

    public String getClassInformation() {
        return classInformation;
    }

    public void setClassInformation(String classInformation) {
        this.classInformation = classInformation;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(String preBalance) {
        this.preBalance = preBalance;
    }

    public String getSubBalance() {
        return subBalance;
    }

    public void setSubBalance(String subBalance) {
        this.subBalance = subBalance;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDues() {
        return dues;
    }

    public void setDues(String dues) {
        this.dues = dues;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}