package com.bihanitech.shikshyaprasasak.model.holiday;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Holiday {


    @SerializedName("title")
    @Expose
    @DatabaseField
    private String title;
    @SerializedName("start")
    @Expose
    @DatabaseField
    private String start;
    @SerializedName("end")
    @Expose
    @DatabaseField
    private String end;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
