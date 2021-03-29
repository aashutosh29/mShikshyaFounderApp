package com.bihanitech.shikshyaprasasak.ui.schoolSelection;

import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

/**
 * Created by dilip on 3/6/18.
 */

public class SchoolSelectionPresenter {

    SchoolSelectionView view;
    MetaDatabaseRepo databaseRepo;

    public SchoolSelectionPresenter(SchoolSelectionView view, MetaDatabaseRepo databaseRepo) {
        this.view = view;
        this.databaseRepo = databaseRepo;
    }


    public void setUpSchoolList() {
        view.setUpRecyclerView(databaseRepo.getSchoolInfo());

    }
}
