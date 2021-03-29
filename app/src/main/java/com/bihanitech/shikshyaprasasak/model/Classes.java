package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Classes {
    @DatabaseField(generatedId = true)
    private int id;

    @SerializedName("class")
    @DatabaseField
    private String grade;

    @SerializedName("classID")
    @DatabaseField
    private String gradeId;


    public Classes(String grade, String gradeId) {
        this.grade = grade;
        this.gradeId = gradeId;
    }

    public Classes() {
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}
