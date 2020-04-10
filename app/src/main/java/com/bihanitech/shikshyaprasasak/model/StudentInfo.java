package com.bihanitech.shikshyaprasasak.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class StudentInfo {

    @DatabaseField(generatedId = true)
    private int stId;

    @DatabaseField
    private String regNo;

    @DatabaseField
    private String stName;

    @DatabaseField
    private String stPhoto;

    @DatabaseField
    private String stClass;

    @DatabaseField
    private String stClassId;

    @DatabaseField
    private String stSchool;


    //1 if downloaded else 0
    @DatabaseField
    private int resultRoutine;


    public StudentInfo(){}

    public StudentInfo(int stId, String regNo, String stName, String stPhoto, String stClass, String stSchool, int resultRoutine) {
        this.stId = stId;
        this.regNo = regNo;
        this.stName = stName;
        this.stPhoto = stPhoto;
        this.stClass = stClass;
        this.stSchool = stSchool;
        this.resultRoutine = resultRoutine;
    }

    public int getStId() {
        return stId;
    }

    public void setStId(int stId) {
        this.stId = stId;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStPhoto() {
        return stPhoto;
    }

    public void setStPhoto(String stPhoto) {
        this.stPhoto = stPhoto;
    }

    public String getStClass() {
        return stClass;
    }

    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    public String getStSchool() {
        return stSchool;
    }

    public void setStSchool(String stSchool) {
        this.stSchool = stSchool;
    }

    public int getResultRoutine() {
        return resultRoutine;
    }

    public void setResultRoutine(int resultRoutine) {
        this.resultRoutine = resultRoutine;
    }

    public String getStClassId() {
        return stClassId;
    }

    public void setStClassId(String stClassId) {
        this.stClassId = stClassId;
    }
}
