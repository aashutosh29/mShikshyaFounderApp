package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dilip on 3/6/18.
 */
@DatabaseTable
public class SchoolInfo {

    @DatabaseField(id = true)
    @SerializedName("schoolid")
    @Expose
    private String schoolid;

    @DatabaseField
    @SerializedName("name")
    @Expose
    private String name;

    @DatabaseField
    @SerializedName("logo")
    @Expose
    private String logo;

    @DatabaseField
    @SerializedName("address")
    @Expose
    private String address;

    public SchoolInfo(){

    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
