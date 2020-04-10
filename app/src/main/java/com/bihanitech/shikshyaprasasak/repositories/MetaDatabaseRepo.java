package com.bihanitech.shikshyaprasasak.repositories;


import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Created by dilip on 3/6/18.
 */

public class MetaDatabaseRepo implements MetaDatabase {

    private DatabaseHelper databaseHelper;

    public MetaDatabaseRepo(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void addAllMetaSchool(MetaSchool body) {
        RuntimeExceptionDao<SchoolInfo, Integer> schoolInfoDao = databaseHelper.getSchoolInfoDao();
        for (SchoolInfo schoolInfo : body.getData()) {
            schoolInfoDao.createIfNotExists(schoolInfo);
        }

    }


    @Override
    public List<SchoolInfo> getSchoolInfo() {
        return databaseHelper.getSchoolInfoDao().queryForAll();
    }

    @Override
    public void addContactList(List<ContactsItem> contactsItems){
        databaseHelper.getContactsInfo().create(contactsItems);
    }

    @Override
    public List<ContactsItem> getContactsList() {
        return databaseHelper.getContactsInfo().queryForAll();
    }









}
