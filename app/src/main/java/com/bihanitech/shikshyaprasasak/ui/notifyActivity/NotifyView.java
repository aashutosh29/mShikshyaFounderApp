package com.bihanitech.shikshyaprasasak.ui.notifyActivity;

import com.bihanitech.shikshyaprasasak.model.Notify;

import java.util.List;

public interface NotifyView {
    void Perlogout();

    void showServerError();

    void showNetworkError();

    void emptyNotices();

    void showLoading();

    void showNoticeList(List<Notify> response, Integer currentPage, Integer totalPages);

    void correspondingActivity(String flag);
}
