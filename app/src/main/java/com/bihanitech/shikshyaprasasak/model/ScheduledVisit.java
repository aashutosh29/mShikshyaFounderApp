package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ScheduledVisit {

    @DatabaseField(id = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("dealer_code")
    @Expose
    private String dealerCode;

    @DatabaseField
    @SerializedName("dealer_name")
    @Expose
    private String dealerName;

    @DatabaseField
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @DatabaseField
    @SerializedName("purpose")
    @Expose
    private String purpose;

    @DatabaseField
    @SerializedName("date")
    @Expose
    private String date;

    @DatabaseField
    @SerializedName("remarks")
    @Expose
    private String remarks;

    @DatabaseField
    @SerializedName("is_visit")
    @Expose
    private Integer isVisit;

    @DatabaseField
    @SerializedName("created_at")
    @Expose
    private String createdAt;


    public ScheduledVisit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(Integer isVisit) {
        this.isVisit = isVisit;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}