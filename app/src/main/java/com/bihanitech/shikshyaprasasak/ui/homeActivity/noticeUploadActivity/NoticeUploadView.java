package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity;

import com.bihanitech.shikshyaprasasak.model.UploadNotice;

import java.util.List;

public interface NoticeUploadView {
    void populateUnpublishedNotice(List<UploadNotice> allUnpublishedNotice);

    void onClickOnItem(UploadNotice uploadNotice);
}
