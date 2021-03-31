package com.bihanitech.shikshyaprasasak.model.examResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mark {
    @SerializedName("SCHOOLID")
    @Expose
    private String schoolid;
    @SerializedName("REGNO")
    @Expose
    private String regno;
    @SerializedName("STDEXAMID")
    @Expose
    private String stdexamid;
    @SerializedName("SUBNAME")
    @Expose
    private String subname;
    @SerializedName("STOM")
    @Expose
    private String stom;
    @SerializedName("STGRADE")
    @Expose
    private String stgrade;
    @SerializedName("STREMARK")
    @Expose
    private String stremark;

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getStdexamid() {
        return stdexamid;
    }

    public void setStdexamid(String stdexamid) {
        this.stdexamid = stdexamid;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getStom() {
        return stom;
    }

    public void setStom(String stom) {
        this.stom = stom;
    }

    public String getStgrade() {
        return stgrade;
    }

    public void setStgrade(String stgrade) {
        this.stgrade = stgrade;
    }

    public String getStremark() {
        return stremark;
    }

    public void setStremark(String stremark) {
        this.stremark = stremark;
    }

}

