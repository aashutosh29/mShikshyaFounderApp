package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.model.NoticeResponse;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HomeFragmentPresenter {
    public static final String TAG = HomeFragmentPresenter.class.getSimpleName();
    HomeFragmentView homeFragmentView;
    private MetaDatabaseRepo metaDatabaseRepo;
    private CDSService cdsService;

    HomeFragmentPresenter(HomeFragmentView homeFragmentView) {
        this.homeFragmentView = homeFragmentView;
    }


    public void getNotices(String token) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }

        Log.v("TAGESTO ", token);
        Observable<NoticeResponse> newCall = cdsService.getNoticesData("Bearer " + token.trim());
        RequestHandler.asyncTask(newCall, new RequestHandler.RetroReactiveCallBack<NoticeResponse>() {
            @Override
            public void onComplete(NoticeResponse response) {
                List<Notice> noticeList = new ArrayList<>(response.getData());
                Log.d(TAG, "onComplete: " + noticeList);
                homeFragmentView.populateNotice(noticeList);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.d(TAG, "onError: " + e + code);
                homeFragmentView.noNoticeAvailable();
            }

            @Override
            public void onConnectionException(Exception e) {
                Log.d(TAG, "onConnectionException: " + e);

            }
        });


    }


}