package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity;

import com.bihanitech.shikshyaprasasak.model.IncomeSummaryList;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;

import java.util.List;

import io.reactivex.Observable;

public class IncomeSummaryListPresenter {
    IncomeSummaryListView view;
    CDSService cdsService;


    IncomeSummaryListPresenter(IncomeSummaryListView view) {
        this.view = view;
    }

    public void fetchIncomeSummaryList(String authToken, String fromDate, String toDate, int page) {
        if (cdsService == null) {
            cdsService = ApiUtils.getCDSService();
        }
        Observable<List<IncomeSummaryList>> call = cdsService.fetchFilteredIncomeSummaryList("Bearer " + authToken, fromDate, toDate, page);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<List<IncomeSummaryList>>() {
            @Override
            public void onComplete(List<IncomeSummaryList> response) {
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
