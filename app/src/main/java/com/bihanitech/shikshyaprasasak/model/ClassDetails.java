package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDetails {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("SCHOOLID")
    @Expose
    private String schoolID;
    @SerializedName("SID")
    @Expose
    private String sID;
    @SerializedName("SGROUP")
    @Expose
    private String sGROUP;
    @SerializedName("PARENT")
    @Expose
    private String parent;
    @SerializedName("CLASSNAME")
    @Expose
    private String className;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("CUSE")
    @Expose
    private Integer cUSE;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public ClassDetails() {
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsGROUP() {
        return sGROUP;
    }

    public void setsGROUP(String sGROUP) {
        this.sGROUP = sGROUP;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String gettYPE() {
        return tYPE;
    }

    public void settYPE(String tYPE) {
        this.tYPE = tYPE;
    }

    public Integer getcUSE() {
        return cUSE;
    }

    public void setcUSE(Integer cUSE) {
        this.cUSE = cUSE;
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




