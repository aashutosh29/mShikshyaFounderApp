package com.bihanitech.shikshyaprasasak.model.acedamics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DivisionAndGradeData {


    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
