package com.bihanitech.shikshyaprasasak.model.examResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("SUBJECTNAME")
    @Expose
    private String subjectname;
    @SerializedName("NDATE")
    @Expose
    private String ndate;
    @SerializedName("EXAMNAME")
    @Expose
    private String examname;
    @SerializedName("EXAMID")
    @Expose
    private String examid;
    @SerializedName("FISCALYEAR")
    @Expose
    private String fiscalyear;
    @SerializedName("STIME")
    @Expose
    private String stime;

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public String getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(String fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

}