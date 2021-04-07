package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.model.holiday.HolidayResponse;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class UpcomingHolidayPresenter {

    UpcomingHolidayView upcomingHolidayView;
    CDSService cdsService;

    public UpcomingHolidayPresenter(UpcomingHolidayView upcomingHolidayView) {
        this.upcomingHolidayView = upcomingHolidayView;
    }

    public void getHoliday(String authToken) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }

        Observable<HolidayResponse> called = cdsService.getUpComingHoliday("Bearer" + authToken);
        RequestHandler.asyncTask(called, new RequestHandler.RetroReactiveCallBack<HolidayResponse>() {
            @Override
            public void onComplete(HolidayResponse response) {
                upcomingHolidayView.onComplete();
                List<Holiday> holidayList = new ArrayList<>(response.getData());
                upcomingHolidayView.populateHolidayList(holidayList);
            }

            @Override
            public void onError(Exception e, int code) {

            }

            @Override
            public void onConnectionException(Exception e) {

            }
        });
    }

}
