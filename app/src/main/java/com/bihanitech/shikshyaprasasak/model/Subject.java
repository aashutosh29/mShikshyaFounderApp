package com.bihanitech.shikshyaprasasak.model;

public class Subject {
    int id;
    String subjectName;
    String score;

    public Subject(String subjectName, String score) {
        this.subjectName = subjectName;
        this.score = score;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
