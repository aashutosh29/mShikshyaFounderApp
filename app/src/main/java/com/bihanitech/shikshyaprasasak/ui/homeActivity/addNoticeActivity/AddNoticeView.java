package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

public interface AddNoticeView {
    void showSuccess();

    void showCantUpload();

    void showNetworkError();

    void retry();

    void savedLocally();

    void saveNoticeLocally();
}
