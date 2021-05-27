package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity;

import com.bihanitech.shikshyaprasasak.model.incomeSummary.Datum;
import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryList;

public interface IncomeSummaryListView {
    void applyFilter(String fromDate, String toDate);

    void populateIncomeSummaryList(IncomeSummaryList response);

    void showLoading();

    void showServerError();

    void showNetworkError();

    void getIncomeSummaryActivity(Datum datum);
}
