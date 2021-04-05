package com.bihanitech.shikshyaprasasak.model.acedamics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassData {


    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("exam_flag")
    @Expose
    private String examFlag;
    @SerializedName("data")
    @Expose
    private List<DivisionAndGradeData> data = null;

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getExamFlag() {
        return examFlag;
    }

    public void setExamFlag(String examFlag) {
        this.examFlag = examFlag;
    }

    public List<DivisionAndGradeData> getData() {
        return data;
    }

    public void setData(List<DivisionAndGradeData> data) {
        this.data = data;
    }

}
