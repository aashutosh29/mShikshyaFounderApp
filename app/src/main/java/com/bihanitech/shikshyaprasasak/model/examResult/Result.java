package com.bihanitech.shikshyaprasasak.model.examResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("SCHOOLID")
    @Expose
    private String schoolid;
    @SerializedName("REGNO")
    @Expose
    private String regno;
    @SerializedName("STDEXAMID")
    @Expose
    private String stdexamid;
    @SerializedName("STDEXAM")
    @Expose
    private String stdexam;
    @SerializedName("EXAMYEAR")
    @Expose
    private String examyear;
    @SerializedName("STDNAME")
    @Expose
    private String stdname;
    @SerializedName("STCLASSID")
    @Expose
    private String stclassid;
    @SerializedName("STCLASS")
    @Expose
    private String stclass;
    @SerializedName("STSECTION")
    @Expose
    private String stsection;
    @SerializedName("STROLLNO")
    @Expose
    private String strollno;
    @SerializedName("STDTOTALOM")
    @Expose
    private String stdtotalom;
    @SerializedName("STRESULT")
    @Expose
    private String stresult;
    @SerializedName("STPERCENTAGE")
    @Expose
    private String stpercentage;
    @SerializedName("STGPA")
    @Expose
    private String stgpa;
    @SerializedName("STDDIVGRADE")
    @Expose
    private String stddivgrade;
    @SerializedName("STDRANK")
    @Expose
    private String stdrank;
    @SerializedName("STATT")
    @Expose
    private String statt;
    @SerializedName("STREMAKRS")
    @Expose
    private String stremakrs;
    @SerializedName("YEARID")
    @Expose
    private Integer yearid;
    @SerializedName("STEXAMFLG")
    @Expose
    private Integer stexamflg;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("marks")
    @Expose
    private List<Mark> marks = null;
    @SerializedName("section")
    @Expose
    private Section section;

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

    public String getStdexam() {
        return stdexam;
    }

    public void setStdexam(String stdexam) {
        this.stdexam = stdexam;
    }

    public String getExamyear() {
        return examyear;
    }

    public void setExamyear(String examyear) {
        this.examyear = examyear;
    }

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public String getStclassid() {
        return stclassid;
    }

    public void setStclassid(String stclassid) {
        this.stclassid = stclassid;
    }

    public String getStclass() {
        return stclass;
    }

    public void setStclass(String stclass) {
        this.stclass = stclass;
    }

    public String getStsection() {
        return stsection;
    }

    public void setStsection(String stsection) {
        this.stsection = stsection;
    }

    public String getStrollno() {
        return strollno;
    }

    public void setStrollno(String strollno) {
        this.strollno = strollno;
    }

    public String getStdtotalom() {
        return stdtotalom;
    }

    public void setStdtotalom(String stdtotalom) {
        this.stdtotalom = stdtotalom;
    }

    public String getStresult() {
        return stresult;
    }

    public void setStresult(String stresult) {
        this.stresult = stresult;
    }

    public String getStpercentage() {
        return stpercentage;
    }

    public void setStpercentage(String stpercentage) {
        this.stpercentage = stpercentage;
    }

    public String getStgpa() {
        return stgpa;
    }

    public void setStgpa(String stgpa) {
        this.stgpa = stgpa;
    }

    public String getStddivgrade() {
        return stddivgrade;
    }

    public void setStddivgrade(String stddivgrade) {
        this.stddivgrade = stddivgrade;
    }

    public String getStdrank() {
        return stdrank;
    }

    public void setStdrank(String stdrank) {
        this.stdrank = stdrank;
    }

    public String getStatt() {
        return statt;
    }

    public void setStatt(String statt) {
        this.statt = statt;
    }

    public String getStremakrs() {
        return stremakrs;
    }

    public void setStremakrs(String stremakrs) {
        this.stremakrs = stremakrs;
    }

    public Integer getYearid() {
        return yearid;
    }

    public void setYearid(Integer yearid) {
        this.yearid = yearid;
    }

    public Integer getStexamflg() {
        return stexamflg;
    }

    public void setStexamflg(Integer stexamflg) {
        this.stexamflg = stexamflg;
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

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

}