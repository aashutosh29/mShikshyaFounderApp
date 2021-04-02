package com.bihanitech.shikshyaprasasak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAttendance {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_students_count")
    @Expose
    private Integer totalStudentsCount;
    @SerializedName("present_count")
    @Expose
    private Integer presentCount;
    @SerializedName("absent_count")
    @Expose
    private Integer absentCount;
    @SerializedName("date")
    @Expose
    private String date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalStudentsCount() {
        return totalStudentsCount;
    }

    public void setTotalStudentsCount(Integer totalStudentsCount) {
        this.totalStudentsCount = totalStudentsCount;
    }

    public Integer getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(Integer presentCount) {
        this.presentCount = presentCount;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}