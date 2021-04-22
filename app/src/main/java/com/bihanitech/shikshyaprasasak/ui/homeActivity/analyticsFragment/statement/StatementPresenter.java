package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.statement;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.TitleWise;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;

import java.util.List;

import io.reactivex.Observable;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class StatementPresenter {
    StatementView statementView;
    CDSService cdsService;

    public StatementPresenter(StatementView statementView) {
        this.statementView = statementView;
    }

    void populateStatementRecord(String authToken, String from, String to) {

        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        statementView.loadingData();

        Observable<List<TitleWise>> call = cdsService.getTitleWiseData("Bearer" + authToken, from, to);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<List<TitleWise>>() {
            @Override
            public void onComplete(List<TitleWise> response) {
                Log.d(TAG, " StatementPresenterOnComplete: " + response);
                statementView.hideLoading();
                statementView.populateData(response);
            }

            @Override
            public void onError(Exception e, int code) {
                statementView.showError();
                Log.d(TAG, "onError: " + e + code);

            }

            @Override
            public void onConnectionException(Exception e) {
                statementView.showError();
                Log.d(TAG, "onConnectionException: " + e);

            }
        });

    }

}
