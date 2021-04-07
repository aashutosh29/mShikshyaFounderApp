package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import android.content.Context;
import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.model.NoticeResponse;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.model.holiday.HolidayResponse;
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
    Context context;
    private MetaDatabaseRepo metaDatabaseRepo;
    private CDSService cdsService;


    public HomeFragmentPresenter(HomeFragmentView homeFragmentView, Context context) {
        this.homeFragmentView = homeFragmentView;
        this.context = context;
    }


    public void getNotices(String token) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }


        Observable<NoticeResponse> newCall = cdsService.getNoticesData("Bearer " + token);
        RequestHandler.asyncTask(newCall, new RequestHandler.RetroReactiveCallBack<NoticeResponse>() {
            @Override
            public void onComplete(NoticeResponse response) {
                homeFragmentView.dataSynced();
                getHoliday(token);
                homeFragmentView.onComplete();
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

    public void getHoliday(String authToken) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }


        Observable<HolidayResponse> called = cdsService.getUpComingHoliday("Bearer" + authToken);
        RequestHandler.asyncTask(called, new RequestHandler.RetroReactiveCallBack<HolidayResponse>() {
            @Override
            public void onComplete(HolidayResponse response) {
                List<Holiday> holidayList = new ArrayList<>(response.getData());
                homeFragmentView.hideLoading();
                homeFragmentView.populateHolidayList(holidayList);

            }

            @Override
            public void onError(Exception e, int code) {

            }

            @Override
            public void onConnectionException(Exception e) {

            }
        });
    }



  /*  public void fetchNoticeList(String token, final int ReFetch, String undeadNotice) {
        cdsService = ApiUtils.getDummyCDSService();
        cdsService.getNoticeList("bearer" + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "Notice Loaded from api");
                String res="";
                Boolean save = true;
                String readNotices = "";
                int status = response.code();
                Log.v(TAG, "Status code"+ response.code());
                Log.v(TAG, "response"+response);
                if (response.isSuccessful()){
                    if (status == 200){
                        try{
                            res = response.body().string();
                            if (res.length()!=0){
                                String unRead = "";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }*/


}