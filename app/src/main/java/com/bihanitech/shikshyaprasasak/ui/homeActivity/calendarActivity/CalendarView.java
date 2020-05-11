package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity;


import com.bihanitech.shikshyaprasasak.model.itemModels.CDayItem;

import java.util.List;

public interface CalendarView {
    void populateCalendar(List<CDayItem> dayItems);

    void showServerError();

    void showNetworkError();

    void retry();

    void showSnackBar(int i);

    void proceedToShowEventHolidayList();

    void setCalendarInfoDownloaded();

    void abort();

    void showUnauthorized();
}
