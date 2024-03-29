package com.bihanitech.shikshyaprasasak.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.UploadNotice;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by dilip on 1/11/18.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "shikshya_prasasak.db";

    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<SchoolInfo, Integer> schoolInfoDao = null;
    private RuntimeExceptionDao<ContactsItem, Integer> contactsItemsDao = null;
    private RuntimeExceptionDao<NoticeItem, Integer> noticeItemDao = null;
    private RuntimeExceptionDao<Event, Integer> eventsDao = null;
    private RuntimeExceptionDao<Classes, Integer> classesDao = null;
    private RuntimeExceptionDao<ExamName, Integer> examNameDao = null;
    private RuntimeExceptionDao<Section, Integer> sectionDao = null;
    private RuntimeExceptionDao<UploadNotice, Integer> uploadNoticeDao = null;
    private RuntimeExceptionDao<com.bihanitech.shikshyaprasasak.model.holiday.Holiday, Integer> holidayDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //create the tables
            TableUtils.createTable(connectionSource, SchoolInfo.class);
            TableUtils.createTable(connectionSource, ContactsItem.class);
            TableUtils.createTable(connectionSource, NoticeItem.class);
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, ExamName.class);
            TableUtils.createTable(connectionSource, Classes.class);
            TableUtils.createTable(connectionSource, UploadNotice.class);
            TableUtils.createTable(connectionSource, Section.class);
            TableUtils.createTable(connectionSource, com.bihanitech.shikshyaprasasak.model.holiday.Holiday.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, SchoolInfo.class, false);
            TableUtils.dropTable(connectionSource, ContactsItem.class, false);
            TableUtils.dropTable(connectionSource, NoticeItem.class, false);
            TableUtils.dropTable(connectionSource, Event.class, false);
            TableUtils.dropTable(connectionSource, ExamName.class, false);
            TableUtils.dropTable(connectionSource, Classes.class, false);
            TableUtils.dropTable(connectionSource, Section.class, false);
            TableUtils.dropTable(connectionSource, UploadNotice.class, false);
            TableUtils.dropTable(connectionSource, Holiday.class, false);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<SchoolInfo, Integer> getSchoolInfoDao() {
        if (schoolInfoDao == null) {
            schoolInfoDao = getRuntimeExceptionDao(SchoolInfo.class);
        }

        return schoolInfoDao;
    }

    public RuntimeExceptionDao<ContactsItem, Integer> getContactsInfo() {
        if (contactsItemsDao == null) {
            contactsItemsDao = getRuntimeExceptionDao(ContactsItem.class);
        }

        return contactsItemsDao;
    }

    public RuntimeExceptionDao<NoticeItem, Integer> getNoticeItemDao() {
        if (noticeItemDao == null) {
            noticeItemDao = getRuntimeExceptionDao(NoticeItem.class);
        }

        return noticeItemDao;

    }

    public RuntimeExceptionDao<Event, Integer> getEventsDao() {
        if (eventsDao == null) {
            eventsDao = getRuntimeExceptionDao(Event.class);
        }

        return eventsDao;

    }

    public RuntimeExceptionDao<Classes, Integer> getClassesDao() {

        if (classesDao == null) {
            classesDao = getRuntimeExceptionDao(Classes.class);
        }
        return classesDao;

    }

    public RuntimeExceptionDao<ExamName, Integer> getExamNameDao() {

        if (examNameDao == null) {
            examNameDao = getRuntimeExceptionDao(ExamName.class);
        }
        return examNameDao;

    }

    public RuntimeExceptionDao<Section, Integer> getSectionDao() {
        if (sectionDao == null) {
            sectionDao = getRuntimeExceptionDao(Section.class);
        }
        return sectionDao;
    }

    public RuntimeExceptionDao<UploadNotice, Integer> getUploadNoticeDao() {
        if (uploadNoticeDao == null) {
            uploadNoticeDao = getRuntimeExceptionDao(UploadNotice.class);
        }
        return uploadNoticeDao;
    }

    public RuntimeExceptionDao<com.bihanitech.shikshyaprasasak.model.holiday.Holiday, Integer> getHolidayDao() {
        if (holidayDao == null) {
            holidayDao = getRuntimeExceptionDao(com.bihanitech.shikshyaprasasak.model.holiday.Holiday.class);
        }
        return holidayDao;
    }
}
