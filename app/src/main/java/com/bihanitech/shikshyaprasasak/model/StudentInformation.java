package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentInformation {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("SCHOOLID")
    @Expose
    private String schoolid;
    @SerializedName("REGNO")
    @Expose
    private String regno;
    @SerializedName("STNAME")
    @Expose
    private String stname;
    @SerializedName("STCLASS")
    @Expose
    private String stclass;
    @SerializedName("ROLLNO")
    @Expose
    private Integer rollno;
    @SerializedName("DOBEDATE")
    @Expose
    private String dobedate;
    @SerializedName("DOBNDATE")
    @Expose
    private String dobndate;
    @SerializedName("GENDER")
    @Expose
    private String gender;
    @SerializedName("STDCONTACT")
    @Expose
    private String stdcontact;
    @SerializedName("STADDRESS")
    @Expose
    private String staddress;
    @SerializedName("STDISTRICT")
    @Expose
    private String stdistrict;
    @SerializedName("FATNAME")
    @Expose
    private String fatname;
    @SerializedName("MOTNAME")
    @Expose
    private String motname;
    @SerializedName("GUDNAME")
    @Expose
    private String gudname;
    @SerializedName("STDSTATUS")
    @Expose
    private String stdstatus;
    @SerializedName("STPPHOTO")
    @Expose
    private String stpphoto;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("STCLASSID")
    @Expose
    private String stclassid;
    @SerializedName("STSECTION")
    @Expose
    private String stsection;
    @SerializedName("YEARID")
    @Expose
    private Integer yearid;

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

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getStclass() {
        return stclass;
    }

    public void setStclass(String stclass) {
        this.stclass = stclass;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    public String getDobedate() {
        return dobedate;
    }

    public void setDobedate(String dobedate) {
        this.dobedate = dobedate;
    }

    public String getDobndate() {
        return dobndate;
    }

    public void setDobndate(String dobndate) {
        this.dobndate = dobndate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStdcontact() {
        return stdcontact;
    }

    public void setStdcontact(String stdcontact) {
        this.stdcontact = stdcontact;
    }

    public String getStaddress() {
        return staddress;
    }

    public void setStaddress(String staddress) {
        this.staddress = staddress;
    }

    public String getStdistrict() {
        return stdistrict;
    }

    public void setStdistrict(String stdistrict) {
        this.stdistrict = stdistrict;
    }

    public String getFatname() {
        return fatname;
    }

    public void setFatname(String fatname) {
        this.fatname = fatname;
    }

    public String getMotname() {
        return motname;
    }

    public void setMotname(String motname) {
        this.motname = motname;
    }

    public String getGudname() {
        return gudname;
    }

    public void setGudname(String gudname) {
        this.gudname = gudname;
    }

    public String getStdstatus() {
        return stdstatus;
    }

    public void setStdstatus(String stdstatus) {
        this.stdstatus = stdstatus;
    }

    public String getStpphoto() {
        return stpphoto;
    }

    public void setStpphoto(String stpphoto) {
        this.stpphoto = stpphoto;
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

    public String getStclassid() {
        return stclassid;
    }

    public void setStclassid(String stclassid) {
        this.stclassid = stclassid;
    }

    public String getStsection() {
        return stsection;
    }

    public void setStsection(String stsection) {
        this.stsection = stsection;
    }

    public Integer getYearid() {
        return yearid;
    }

    public void setYearid(Integer yearid) {
        this.yearid = yearid;
    }
}