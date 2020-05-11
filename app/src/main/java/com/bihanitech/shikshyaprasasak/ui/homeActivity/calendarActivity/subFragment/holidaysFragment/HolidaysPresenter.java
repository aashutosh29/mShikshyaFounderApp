package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity.subFragment.holidaysFragment;


import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;

import java.util.List;

public class HolidaysPresenter {

    private HolidaysView holidaysView;
    private DbInternalRepo dbInternalRepo;

    public HolidaysPresenter(HolidaysView holidaysView, DbInternalRepo dbInternalRepo) {
        this.holidaysView = holidaysView;
        this.dbInternalRepo = dbInternalRepo;
    }

    public void fetchList(int year, int month) {
        List<Holiday> holidays = dbInternalRepo.getHolidaysList(year, month);

        holidaysView.populateList(holidays);
    }
}
