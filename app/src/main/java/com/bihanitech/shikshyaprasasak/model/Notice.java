package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notice {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("backlink")
    @Expose
    private String backlink;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("classdetails")
    @Expose
    private ClassDetails classdetails;
    @SerializedName("sectiondetails")
    @Expose
    private SectionDetails sectiondetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBacklink() {
        return backlink;
    }

    public void setBacklink(String backlink) {
        this.backlink = backlink;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public ClassDetails getClassdetails() {
        return classdetails;
    }

    public void setClassdetails(ClassDetails classdetails) {
        this.classdetails = classdetails;
    }

    public SectionDetails getSectiondetails() {
        return sectiondetails;
    }

    public void setSectiondetails(SectionDetails sectiondetails) {
        this.sectiondetails = sectiondetails;
    }
}

