package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

public class AddNoticePresenter {
    private AddNoticeView addNoticeView;
    private MetaDatabaseRepo metaDatabaseRepo;

    public AddNoticePresenter(AddNoticeView addNoticeView, MetaDatabaseRepo metaDatabaseRepo) {
        this.addNoticeView = addNoticeView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }


}
