package com.bihanitech.shikshyaprasasak.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.ClassSubject;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;
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
    private RuntimeExceptionDao<Holiday, Integer> holidaysDao = null;
    private RuntimeExceptionDao<ClassSubject, Integer> classSubjectsDao = null;
    private RuntimeExceptionDao<StudentInfo, Integer> studentInfosDao = null;
    private RuntimeExceptionDao<NoticeItem, Integer> noticeItemsDao = null;


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
            TableUtils.createTable(connectionSource, ClassSubject.class);
            TableUtils.createTable(connectionSource, StudentInfo.class);
            TableUtils.createTable(connectionSource, NoticeItem.class);


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
            TableUtils.dropTable(connectionSource, Holiday.class, false);
            TableUtils.dropTable(connectionSource, ClassSubject.class, false);
            TableUtils.dropTable(connectionSource, StudentInfo.class, false);
            TableUtils.dropTable(connectionSource, NoticeItem.class, false);
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

    public RuntimeExceptionDao<Holiday, Integer> getHolidaysDao() {
        if (holidaysDao == null) {
            holidaysDao = getRuntimeExceptionDao(Holiday.class);
        }

        return holidaysDao;
    }

    public RuntimeExceptionDao<ClassSubject, Integer> getClassSubjectsDao() {
        if (classSubjectsDao == null) {
            classSubjectsDao = getRuntimeExceptionDao(ClassSubject.class);
        }
        return classSubjectsDao;
    }

    public RuntimeExceptionDao<StudentInfo, Integer> getStudentInfosDao() {
        if (studentInfosDao == null) {
            studentInfosDao = getRuntimeExceptionDao(StudentInfo.class);
        }

        return studentInfosDao;
    }

    public RuntimeExceptionDao<NoticeItem, Integer> getNoticeItemsDao() {
        if (noticeItemsDao == null) {
            noticeItemsDao = getRuntimeExceptionDao(NoticeItem.class);
        }

        return noticeItemsDao;
    }
}
