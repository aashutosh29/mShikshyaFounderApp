package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity;

import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryList;
import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryReport;

public interface IncomeSummaryListView {
    void applyFilter(String fromDate, String toDate);

    void populateIncomeSummaryList(IncomeSummaryList response);

    void showLoading();

    void showServerError();

    void showNetworkError();

    void getIncomeSummaryActivity(IncomeSummaryReport incomeSummaryReport);
}
