package com.bihanitech.shikshyaprasasak.model.itemModels;

public class CDayItem {

    private int englishDate;

    private int nepDate;

    private int day;

    private int currentDate;

    public CDayItem(){


    }


    /**
     * this javaDoc comments looks better in dark version of android studio
     *
     * @param englishDate
     * @param nepDate
     * @param day
     * @param currentDate
     */
    public CDayItem(int englishDate, int nepDate, int day, int currentDate) {
        this.englishDate = englishDate;
        this.nepDate = nepDate;
        this.day = day;
        this.currentDate = currentDate;
    }

    /**
     *
     * @return
     */
    public int getCurrentDate() {
        return currentDate;
    }

    /**
     *
     * @param currentDate
     */
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
