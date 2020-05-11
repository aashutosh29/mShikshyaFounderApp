package com.bihanitech.shikshyaprasasak.model.eventHolidays;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Holiday {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String detail;

    @DatabaseField
    private int nepDay;

    @DatabaseField
    private int nepMonth;

    @DatabaseField
    private int nepYear;

    @DatabaseField
    private String engDate;


    public Holiday() {

    }

    public Holiday(int id, String detail, int nepDay, int nepMonth, int nepYear, String engDate) {
        this.id = id;
        this.detail = detail;
        this.nepDay = nepDay;
        this.nepMonth = nepMonth;
        this.nepYear = nepYear;
        this.engDate = engDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getNepDay() {
        return nepDay;
    }

    public void setNepDay(int nepDay) {
        this.nepDay = nepDay;
    }

    public int getNepMonth() {
        return nepMonth;
    }

    public void setNepMonth(int nepMonth) {
        this.nepMonth = nepMonth;
    }

    public int getNepYear() {
        return nepYear;
    }

    public void setNepYear(int nepYear) {
        this.nepYear = nepYear;
    }

    public String getEngDate() {
        return engDate;
    }

    public void setEngDate(String engDate) {
        this.engDate = engDate;
    }


}
