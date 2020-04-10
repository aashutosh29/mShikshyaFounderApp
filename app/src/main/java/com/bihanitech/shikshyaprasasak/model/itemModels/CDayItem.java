package com.bihanitech.shikshyaprasasak.model.itemModels;

public class CDayItem {

    private int englishDate;

    private int nepDate;

    private int day;

    private int currentDate;

    public CDayItem(){


    }

    public CDayItem(int englishDate, int nepDate, int day, int currentDate) {
        this.englishDate = englishDate;
        this.nepDate = nepDate;
        this.day = day;
        this.currentDate = currentDate;
    }

    public int getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(int currentDate) {
        this.currentDate = currentDate;
    }

    public int getEnglishDate() {
        return englishDate;
    }

    public void setEnglishDate(int englishDate) {
        this.englishDate = englishDate;
    }

    public int getNepDate() {
        return nepDate;
    }

    public void setNepDate(int nepDate) {
        this.nepDate = nepDate;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
