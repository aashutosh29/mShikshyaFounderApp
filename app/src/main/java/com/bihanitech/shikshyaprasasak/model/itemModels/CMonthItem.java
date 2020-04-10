package com.bihanitech.shikshyaprasasak.model.itemModels;

import java.util.List;

public class CMonthItem {

    int month;

    int year;

    List<CDayItem> cDayItemList;

    public CMonthItem(){

    }

    public CMonthItem(int month, int year, List<CDayItem> cDayItemList) {
        this.month = month;
        this.year = year;
        this.cDayItemList = cDayItemList;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<CDayItem> getcDayItemList() {
        return cDayItemList;
    }

    public void setcDayItemList(List<CDayItem> cDayItemList) {
        this.cDayItemList = cDayItemList;
    }
}
