package com.bihanitech.shikshyaprasasak.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TStudentInfo {

    @DatabaseField(id = true)
    private String regNo;

    @DatabaseField
    private int rollNo;

    @DatabaseField
    private String name;

    @DatabaseField
    private String classId;

    @DatabaseField
    private String sectionId;

    public TStudentInfo(){

    }

    public TStudentInfo(String regNo, int rollNo, String name, String classId, String sectionId) {
        this.regNo = regNo;
        this.rollNo = rollNo;
        this.name = name;
        this.classId = classId;
        this.sectionId = sectionId;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}
