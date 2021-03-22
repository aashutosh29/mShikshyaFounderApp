package com.bihanitech.shikshyaprasasak.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClassSubject {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String classId;

    @DatabaseField
    private String subjectId;

    @DatabaseField
    private String className;

    @DatabaseField
    private String subjectName;

    @DatabaseField
    private String sectionId;

    @DatabaseField
    private String sectionName;

    @DatabaseField
    private int subjectStatus;

    //   @DatabaseField
    //   private int active;


    public ClassSubject() {

    }


    public ClassSubject(int id, String classId, String subjectId, String className, String subjectName, String sectionId, String sectionName/*, int active*/) {
        this.id = id;
        this.classId = classId;
        this.subjectId = subjectId;
        this.className = className;
        this.subjectName = subjectName;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        //     this.active = active;
    }

   /* public int getActive() {
        return active;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(int subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

/*public void setActive(int active) {
        this.active = active;
    }*/
}
