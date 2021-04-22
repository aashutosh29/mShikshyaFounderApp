package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.statement;

import com.bihanitech.shikshyaprasasak.model.TitleWise;

import java.util.List;

public interface StatementView {
    void populateData(List<TitleWise> response);

    void loadingData();

    void hideLoading();

    void showError();
}
