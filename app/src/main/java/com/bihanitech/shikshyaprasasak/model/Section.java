package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Section {

    @DatabaseField(generatedId = true)
    private int id;

    @SerializedName("class")
    @Expose
    @DatabaseField
    private String _class;


    @SerializedName("classID")
    @Expose
    @DatabaseField
    private String classID;


    public Section(String _class, String classID) {
        this._class = _class;
        this.classID = classID;
    }

    public Section() {
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

}