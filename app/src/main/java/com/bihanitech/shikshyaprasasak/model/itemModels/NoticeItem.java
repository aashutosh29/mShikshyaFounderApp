package com.bihanitech.shikshyaprasasak.model.itemModels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class NoticeItem {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String detail;

    @DatabaseField
    private String pDate;

    @DatabaseField
    private String eDate;

    public NoticeItem(){

    }

    public NoticeItem(String title, String detail, String pDate, String eDate) {
        this.title = title;
        this.detail = detail;
        this.pDate = pDate;
        this.eDate = eDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }
}
