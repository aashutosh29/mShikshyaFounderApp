package com.bihanitech.shikshyaprasasak.model;

public class NepDate {

    public String year;

    public String month;

    public String nDate;

    public String day;

    public String nMonth;

    public String nDay;

    public NepDate(String year, String month, String nDate, String day, String nMonth, String nDay) {
        this.year = year;
        this.month = month;
        this.nDate = nDate;
        this.day = day;
        this.nMonth = nMonth;
        this.nDay = nDay;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getnMonth() {
        return nMonth;
    }

    public void setnMonth(String nMonth) {
        this.nMonth = nMonth;
    }

    public String getnDay() {
        return nDay;
    }

    public void setnDay(String nDay) {
        this.nDay = nDay;
    }
}
