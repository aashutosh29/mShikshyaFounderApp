package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity;

import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryList;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;

import io.reactivex.Observable;

public class IncomeSummaryListPresenter {
    IncomeSummaryListView view;
    CDSService cdsService;


    IncomeSummaryListPresenter(IncomeSummaryListView view) {
        this.view = view;
    }

    public void fetchIncomeSummaryList(String authToken, String fromDate, String toDate, int page) {
        if (page == 1) {
            view.showLoading();
        }
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        Observable<IncomeSummaryList> call = cdsService.fetchFilteredIncomeSummaryList("bearer " + authToken, "2018-05-20", "2021-05-20", String.valueOf(page));
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<IncomeSummaryList>() {
            @Override
            public void onComplete(IncomeSummaryList response) {
                view.populateIncomeSummaryList(response);
            }

            @Override
            public void onError(Exception e, int code) {
                view.showServerError();

            }

            @Override
            public void onConnectionException(Exception e) {
                view.showNetworkError();

            }
        });

    }
}
