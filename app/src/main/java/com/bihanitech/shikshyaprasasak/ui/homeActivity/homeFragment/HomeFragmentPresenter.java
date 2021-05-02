package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import android.content.Context;
import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.model.NoticeResponse;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.model.holiday.HolidayResponse;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter {
    public static final String TAG = HomeFragmentPresenter.class.getSimpleName();
    HomeFragmentView homeFragmentView;
    Context context;
    private final MetaDatabaseRepo metaDatabaseRepo;
    private CDSService cdsService;


    public HomeFragmentPresenter(HomeFragmentView homeFragmentView, Context context, MetaDatabaseRepo metaDatabaseRepo) {
        this.homeFragmentView = homeFragmentView;
        this.context = context;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    void getLocallySavedUpComingNotice() {
        homeFragmentView.populateHolidayList(metaDatabaseRepo.fetchLocallySavedNotice(), true);
    }

    void saveNepaliDate() {


    }


    public void getNotices(String token, boolean refresh) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }


        Observable<NoticeResponse> newCall = cdsService.getNoticesData("Bearer " + token);
        RequestHandler.asyncTask(newCall, new RequestHandler.RetroReactiveCallBack<NoticeResponse>() {
            @Override
            public void onComplete(NoticeResponse response) {
                homeFragmentView.dataSynced();
                if (refresh) {
                    homeFragmentView.onComplete();
                }
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
                for (int i = 0; i < holidayList.size(); i++) {
                    holidayList.get(i).setStartNepaliDate(homeFragmentView.dateConverterMachine(holidayList.get(i).getStart()));
                    holidayList.get(i).setEndNepaliDate(homeFragmentView.dateConverterMachine(holidayList.get(i).getEnd()));
                }
                homeFragmentView.hideLoading();
                saveHoliday(holidayList);
                homeFragmentView.populateHolidayList(metaDatabaseRepo.fetchLocallySavedNotice(), true);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.d(TAG, "onError: " + e + code);
            }

            @Override
            public void onConnectionException(Exception e) {
                Log.d(TAG, "onError: " + e);
            }
        });
    }

    public void fetchSliderList(String schoolId) {
        if (cdsService == null) {
            cdsService = ApiUtils.getCDSService();
        }

        cdsService.getShikshyaNotice(schoolId).enqueue(new Callback<List<EventSlider>>() {


            @Override
            public void onResponse(Call<List<EventSlider>> call, Response<List<EventSlider>> response) {
                int status = response.code();
                if (status == 200) {
                    homeFragmentView.populateSliderList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EventSlider>> call, Throwable t) {
                List<EventSlider> eventSliders = new ArrayList<>();
                homeFragmentView.populateSliderList(eventSliders);
            }
        });

    }

   /* public String dateConverterMachine(String aDate) {
        Model specificDate;
        String[] dateParts = aDate.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        LightDateConverter dateConverter = new LightDateConverter();
        specificDate = dateConverter.getNepaliDate(year, month, day);
        return specificDate.getYear() + "-" + specificDate.getMonth() + "-" + specificDate.getDay();
    }*/

    public void saveHoliday(List<Holiday> holidays) {
        metaDatabaseRepo.saveHolidayResponse(holidays);
    }
}