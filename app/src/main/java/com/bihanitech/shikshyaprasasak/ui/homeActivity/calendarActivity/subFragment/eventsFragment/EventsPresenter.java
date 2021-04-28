package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity.subFragment.eventsFragment;

import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;

import java.util.List;

public class EventsPresenter {

    private final EventsView eventsView;
    private final DbInternalRepo dbInternalRepo;

    public EventsPresenter(EventsView eventsView, DbInternalRepo dbInternalRepo) {
        this.eventsView = eventsView;
        this.dbInternalRepo = dbInternalRepo;
    }


    public void fetchList(int year, int month) {

        List<Event> events = dbInternalRepo.getEventsList(year, month);

        eventsView.populateList(events);
    }
}
