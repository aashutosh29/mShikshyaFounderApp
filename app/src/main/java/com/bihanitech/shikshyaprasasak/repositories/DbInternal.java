package com.bihanitech.shikshyaprasasak.repositories;


import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;

import java.util.List;

public interface DbInternal {


    void updateNotices(List<NoticeItem> noticeItems);

    void addEvents(List<Event> events);

    List<NoticeItem> getAllNotices();


    void deleteEventsAndHolidays();

    List<Event> getEventsList(int year, int month);

    void updateMetaAttend(int id);


    void addHolidays(List<Holiday> holidays);

    List<Holiday> getHolidaysList(int year, int month);
}
