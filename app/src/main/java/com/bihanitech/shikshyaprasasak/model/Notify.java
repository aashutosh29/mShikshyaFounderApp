package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Notify {
    @DatabaseField
    @SerializedName("expiry_date")
    @Expose
    String expiryDate;
    @DatabaseField
    @SerializedName("date")
    @Expose
    String date;
    @DatabaseField
    @SerializedName("notice_type")
    @Expose
    String noticeType;
    @DatabaseField
    @SerializedName("created_by")
    @Expose
    String createdBy;
    @DatabaseField
    @SerializedName("publish")
    @Expose
    int publish;
    @DatabaseField
    @SerializedName("created_at")
    @Expose
    String createdAt;
    @DatabaseField(generatedId = true)
    @Expose
    private int id;
    @DatabaseField
    @SerializedName("title")
    @Expose
    private String title;
    @DatabaseField
    @SerializedName("notice")
    @Expose
    private String notice;
    @DatabaseField
    @SerializedName("image")
    @Expose
    private String image;
    @DatabaseField
    @SerializedName("flag")
    @Expose
    private String flag;

    public Notify(int id, String title, String notice, String image, String expiryDate, String date, String noticeType, String createdBy, int publish, String createdAt, String flag) {
        this.id = id;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.expiryDate = expiryDate;
        this.date = date;
        this.noticeType = noticeType;
        this.createdBy = createdBy;
        this.publish = publish;
        this.createdAt = createdAt;
        this.flag = flag;
    }

    public Notify() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
