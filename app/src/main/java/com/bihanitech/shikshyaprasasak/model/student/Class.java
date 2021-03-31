package com.bihanitech.shikshyaprasasak.model.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("SCHOOLID")
    @Expose
    private String schoolid;
    @SerializedName("SID")
    @Expose
    private String sid;
    @SerializedName("SGROUP")
    @Expose
    private String sgroup;
    @SerializedName("PARENT")
    @Expose
    private String parent;
    @SerializedName("CLASSNAME")
    @Expose
    private String classname;
    @SerializedName("TYPE")
    @Expose
    private String type;
    @SerializedName("CUSE")
    @Expose
    private Integer cuse;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSgroup() {
        return sgroup;
    }

    public void setSgroup(String sgroup) {
        this.sgroup = sgroup;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCuse() {
        return cuse;
    }

    public void setCuse(Integer cuse) {
        this.cuse = cuse;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
