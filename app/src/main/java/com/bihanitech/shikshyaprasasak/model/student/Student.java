package com.bihanitech.shikshyaprasasak.model.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("regno")
    @Expose
    private String regno;
    @SerializedName("rollno")
    @Expose
    private Integer rollno;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("class")
    @Expose
    private Class _class;
    @SerializedName("dobn")
    @Expose
    private String dobn;
    @SerializedName("dobe")
    @Expose
    private String dobe;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("fathername")
    @Expose
    private String fathername;
    @SerializedName("mothername")
    @Expose
    private String mothername;
    @SerializedName("gurdainname")
    @Expose
    private String gurdainname;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("section")
    @Expose
    private Section section;

    private Boolean CheckStatus;

    public Boolean getCheckStatus() {
        return CheckStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        CheckStatus = checkStatus;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClass_() {
        return _class;
    }

    public void setClass_(Class _class) {
        this._class = _class;
    }

    public String getDobn() {
        return dobn;
    }

    public void setDobn(String dobn) {
        this.dobn = dobn;
    }

    public String getDobe() {
        return dobe;
    }

    public void setDobe(String dobe) {
        this.dobe = dobe;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getGurdainname() {
        return gurdainname;
    }

    public void setGurdainname(String gurdainname) {
        this.gurdainname = gurdainname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

}
