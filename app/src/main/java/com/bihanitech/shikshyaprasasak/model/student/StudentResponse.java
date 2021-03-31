package com.bihanitech.shikshyaprasasak.model.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentResponse {
    @SerializedName("data")
    @Expose
    private List<Student> data = null;

    public List<Student> getData() {
        return data;
    }

    public void setData(List<Student> data) {
        this.data = data;
    }

}

