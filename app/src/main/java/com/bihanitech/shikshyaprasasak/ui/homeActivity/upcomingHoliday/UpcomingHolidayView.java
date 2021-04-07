package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;

import java.util.List;

public interface UpcomingHolidayView {
    void populateHolidayList(List<Holiday> holidayList);

    void onComplete();
}
