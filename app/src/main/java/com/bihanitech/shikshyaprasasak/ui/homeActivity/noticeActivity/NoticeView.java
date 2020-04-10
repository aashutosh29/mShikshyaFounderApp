package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity;


import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;

import java.util.List;

public interface NoticeView {
    void populateList(List<NoticeItem> noticeItems);

    void sendToDetailView(NoticeItem noticeItem);

    void showNetworkError();

    void showServerError();

    void proceedAfterDownload();
}
