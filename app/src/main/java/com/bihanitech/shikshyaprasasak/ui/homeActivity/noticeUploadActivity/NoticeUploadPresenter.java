package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity;

import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

public class NoticeUploadPresenter {
    MetaDatabaseRepo metaDatabaseRepo;
    NoticeUploadView noticeUploadView;


    public NoticeUploadPresenter(MetaDatabaseRepo metaDatabaseRepo, NoticeUploadView noticeUploadView) {
        this.metaDatabaseRepo = metaDatabaseRepo;
        this.noticeUploadView = noticeUploadView;
    }

    void queryForData() {
        noticeUploadView.populateUnpublishedNotice(metaDatabaseRepo.getAllUnpublishedNotice());
    }


}
