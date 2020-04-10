package com.bihanitech.shikshyaprasasak.repositories;

import android.util.Log;


import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbInternalRepo implements DbInternal {


    private DatabaseHelper databaseHelper;

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
    public List<NoticeItem> getAllNotices() {
        RuntimeExceptionDao<NoticeItem,Integer> noticeItemsDao = databaseHelper.getNoticeItemDao();
        return noticeItemsDao.queryForAll();
    }

    @Override
    public void updateMetaAttend(int id) {

    }


}
