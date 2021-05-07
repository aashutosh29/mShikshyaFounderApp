package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity;

import com.bihanitech.shikshyaprasasak.model.IncomeSummaryList;

import java.util.List;

public interface IncomeSummaryListView {
    void applyFilter(String fromDate, String toDate);

    void populateIncomeSummaryList(List<IncomeSummaryList> response);

    void showLoading();

    void showServerError();

    void showNetworkError();
}
