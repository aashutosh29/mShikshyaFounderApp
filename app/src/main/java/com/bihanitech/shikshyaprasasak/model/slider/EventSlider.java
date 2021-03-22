package com.bihanitech.shikshyaprasasak.model.slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class EventSlider {

    @DatabaseField(id = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @DatabaseField
    @SerializedName("title")
    @Expose
    private String title;

    @DatabaseField
    @SerializedName("description")
    @Expose
    private String description;

    @DatabaseField
    @SerializedName("image")
    @Expose
    private String image;

    @DatabaseField
    @SerializedName("type")
    @Expose
    private String type;

    @DatabaseField
    @SerializedName("url")
    @Expose
    private String url;

    @DatabaseField
    @SerializedName("schoolid")
    @Expose
    private String schoolid;

    public EventSlider() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }
}
