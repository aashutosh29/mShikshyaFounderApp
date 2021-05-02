package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.ArrayList;
import java.util.List;

public class UpcomingHolidayPresenter {

    private final MetaDatabaseRepo metaDatabaseRepo;
    UpcomingHolidayView upcomingHolidayView;

    public UpcomingHolidayPresenter(UpcomingHolidayView upcomingHolidayView, MetaDatabaseRepo metaDatabaseRepo) {
        this.upcomingHolidayView = upcomingHolidayView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void getHoliday(String fetchDate) {

        fetchDate = fetchDate.split("-")[0] + "-" +
                fetchDate.split("-")[1];
        List<Holiday> holidayList = new ArrayList<>(metaDatabaseRepo.fetchLocallySavedNotice());

        List<Holiday> filterHolidayList = new ArrayList<>();
        for (Holiday holiday : holidayList) {
            if (fetchDate.equalsIgnoreCase(holiday.getStartNepaliDate().split("-")[0] + "-" +
                    holiday.getStartNepaliDate().split("-")[1])) {

                filterHolidayList.add(holiday);
            }
        }


        if (filterHolidayList.size() == 0) {
            upcomingHolidayView.showNoDataFound();
        }


        upcomingHolidayView.populateHolidayList(filterHolidayList);

    }


}
