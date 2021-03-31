package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassSection {

    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("classID")
    @Expose
    private String classID;

    public ClassSection() {
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
