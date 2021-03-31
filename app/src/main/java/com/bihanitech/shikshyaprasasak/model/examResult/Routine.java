package com.bihanitech.shikshyaprasasak.model.examResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routine {

    @SerializedName("terminal")
    @Expose
    private String terminal;
    @SerializedName("Year")
    @Expose
    private String year;
    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects = null;

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}