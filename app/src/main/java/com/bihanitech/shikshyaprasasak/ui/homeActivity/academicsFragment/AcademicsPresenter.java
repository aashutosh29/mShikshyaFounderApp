package com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.acedamics.AcademicResponse;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import io.reactivex.Observable;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.AnalyticsPresenter.TAG;

public class AcademicsPresenter {
    MetaDatabaseRepo metaDatabaseRepo;
    AcademicsView academicsView;
    CDSService cdsService;

    public AcademicsPresenter(MetaDatabaseRepo metaDatabaseRepo, AcademicsView academicsView) {
        this.metaDatabaseRepo = metaDatabaseRepo;
        this.academicsView = academicsView;
    }

    public void getGraphData(String authToken, String examId) {

        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        Observable<AcademicResponse> call = cdsService.getAcademicBarData("Bearer " + authToken, examId);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<AcademicResponse>() {
            @Override
            public void onComplete(AcademicResponse response) {
                academicsView.loadDataOnGraph(response.getData());
                Log.d(TAG, "onComplete: " + response);
            }

            @Override
            public void onError(Exception e, int code) {

                Log.d(TAG, "onError: " + e);

            }

            @Override
            public void onConnectionException(Exception e) {
                Log.d(TAG, "onConnectionException: " + e);

            }
        });


    }


}
