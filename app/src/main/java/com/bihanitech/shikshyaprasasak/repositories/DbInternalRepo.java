package com.bihanitech.shikshyaprasasak.repositories;

import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbInternalRepo implements DbInternal {


    private final DatabaseHelper databaseHelper;

    public DbInternalRepo(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }


    @Override
    public void updateNotices(List<NoticeItem> noticeItems) {
        RuntimeExceptionDao<NoticeItem,Integer> noticeItemsDao = databaseHelper.getNoticeItemDao();
        List<NoticeItem> oldStatements = noticeItemsDao.queryForAll();
        for (NoticeItem oldStatement : oldStatements) {
            noticeItemsDao.delete(oldStatement);

        }
        if(noticeItems.size()!=0)
            noticeItemsDao.create(noticeItems);
    }

    @Override
    public void addEvents(List<Event> events) {
        databaseHelper.getEventsDao().create(events);
    }
    @Override
    public List<NoticeItem> getAllNotices() {
        RuntimeExceptionDao<NoticeItem,Integer> noticeItemsDao = databaseHelper.getNoticeItemDao();
        return noticeItemsDao.queryForAll();
    }

    @Override
    public void deleteEventsAndHolidays() {
/*        RuntimeExceptionDao<Holiday, Integer> holidayDao = databaseHelper.getHolidaysDao();
        List<Holiday> holidays = holidayDao.queryForAll();
        holidayDao.delete(holidays);*/

        RuntimeExceptionDao<Event, Integer> events = databaseHelper.getEventsDao();
        List<Event> eventList = events.queryForAll();
        events.delete(eventList);

    }

    @Override
    public List<Event> getEventsList(int year, int month) {
        RuntimeExceptionDao<Event, Integer> eventsDao = databaseHelper.getEventsDao();
        List<Event> events = new ArrayList<>();
        try {
            QueryBuilder<Event, Integer> qb = eventsDao.queryBuilder();
            qb.where().eq("nepYear", year).and().eq("nepMonth", month);
            PreparedQuery<Event> pq = qb.prepare();
            events = eventsDao.query(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;

    }

    @Override
    public void updateMetaAttend(int id) {

    }

    @Override
    public void addHolidays(List<Holiday> holidays) {
//        databaseHelper.getHolidaysDao().create(holidays);
    }


    @Override
    public List<Holiday> getHolidaysList(int year, int month) {
//        RuntimeExceptionDao<Holiday, Integer> holidaysDao = databaseHelper.getHolidaysDao();
//        List<Holiday> holidays = new ArrayList<>();
//        try {
//            QueryBuilder<Holiday, Integer> qb = holidaysDao.queryBuilder();
//            qb.where().eq("nepYear", year).and().eq("nepMonth", month);
//            PreparedQuery<Holiday> pq = qb.prepare();
//            holidays = holidaysDao.query(pq);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return holidays;
        return null;

    }





}
