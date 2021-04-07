package com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment;

import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

public class MorePresenter {

    MetaDatabaseRepo metaDatabaseRepo;
    MoreView moreView;

    public MorePresenter(MoreView moreView, MetaDatabaseRepo metaDatabaseRepo) {
        this.metaDatabaseRepo = metaDatabaseRepo;
        this.moreView = moreView;
    }


}
