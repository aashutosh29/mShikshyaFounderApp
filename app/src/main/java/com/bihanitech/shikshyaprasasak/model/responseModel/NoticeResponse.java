package com.bihanitech.shikshyaprasasak.model.responseModel;

import com.bihanitech.shikshyaprasasak.model.Notify;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeResponse {
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;

    @SerializedName("data")
    @Expose
    private List<Notify> notice = null;

    @SerializedName("last_page")
    @Expose
    private Integer totalPages;

    @SerializedName("total")
    @Expose
    private Integer totalNotice;

    public NoticeResponse() {}

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Notify> getNotice() {
        return notice;
    }

    public void setNotice(List<Notify> notice) {
        this.notice = notice;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalNotice() {
        return totalNotice;
    }

    public void setTotalNotice(Integer totalNotice) {
        this.totalNotice = totalNotice;
    }
}
