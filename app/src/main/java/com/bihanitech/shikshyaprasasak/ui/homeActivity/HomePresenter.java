package com.bihanitech.shikshyaprasasak.ui.homeActivity;

import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

public class HomePresenter {

    private final MetaDatabaseRepo metaDatabaseRepo;
    private CDSService cdsService;
    private final HomeView homeView;

    public HomePresenter(MetaDatabaseRepo metaDatabaseRepo, HomeView homeView) {
        this.metaDatabaseRepo = metaDatabaseRepo;
        this.homeView = homeView;
    }


    public void getStudentsList() {

    }


}
