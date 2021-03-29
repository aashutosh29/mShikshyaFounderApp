package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ExamName {

    @DatabaseField(generatedId = true)
    private int id;

    @SerializedName("examID")
    @DatabaseField
    private String examID;

    @SerializedName("examname")
    @DatabaseField
    private String examName;


    public ExamName(String examID, String examName) {
        this.examID = examID;
        this.examName = examName;
    }

    public ExamName() {
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
