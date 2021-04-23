package com.bihanitech.shikshyaprasasak.repositories;


import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.ClassSubject;
import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.model.UploadNotice;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dilip on 3/6/18.
 */

public class MetaDatabaseRepo implements MetaDatabase {

    private final DatabaseHelper databaseHelper;

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
    public void addClassSubject(List<ClassSubject> classSubjects) {
//        RuntimeExceptionDao<ClassSubject, Integer> classSubjectsDao = databaseHelper.getClassSubjectsDao();
//        classSubjectsDao.create(classSubjects);
    }


    @Override
    public List<SchoolInfo> getSchoolInfo() {
        return databaseHelper.getSchoolInfoDao().queryForAll();
    }

    @Override
    public void addContactList(List<ContactsItem> contactsItems) {
        databaseHelper.getContactsInfo().create(contactsItems);
    }

    @Override
    public List<ClassSubject> getAllSubjectList() {
//        RuntimeExceptionDao<ClassSubject, Integer> classSubjectRuntimeExceptionDao = databaseHelper.getClassSubjectsDao();
//        return classSubjectRuntimeExceptionDao.queryForAll();
        return null;
    }

    @Override
    public List<ContactsItem> getContactsList() {
        return databaseHelper.getContactsInfo().queryForAll();
    }

    @Override
    public void addStudentInfo(List<StudentInfo> studentInfos) {
//        databaseHelper.getStudentInfosDao().create(studentInfos);
    }

    @Override
    public void addExamName(List<ExamName> examNames) {

        RuntimeExceptionDao<ExamName, Integer> examNameDao = databaseHelper.getExamNameDao();
        examNameDao.create(examNames);

    }

    @Override
    public void addClasses(List<Classes> classes) {

        RuntimeExceptionDao<Classes, Integer> examNameDao = databaseHelper.getClassesDao();
        examNameDao.create(classes);

    }

    @Override
    public void addSection(List<Section> sectionList) {
        RuntimeExceptionDao<Section, Integer> sectionDao = databaseHelper.getSectionDao();
        sectionDao.create(sectionList);
    }


    @Override
    public void addNoticeItems(List<NoticeItem> noticeItems) {
//        RuntimeExceptionDao<NoticeItem, Integer> noticeItemsDao = databaseHelper.getNoticeItemsDao();
//        for (NoticeItem noticeItem : noticeItems) {
//            noticeItemsDao.createOrUpdate(noticeItem);
//        }
//

    }

    @Override
    public List<Classes> getClassList() {
        RuntimeExceptionDao<Classes, Integer> classesItem = databaseHelper.getClassesDao();
        return classesItem.queryForAll();

    }

    @Override
    public List<Section> getSectionList() {
        RuntimeExceptionDao<Section, Integer> sectionItem = databaseHelper.getSectionDao();
        return sectionItem.queryForAll();
    }

    @Override
    public List<ExamName> getExamList() {
        RuntimeExceptionDao<ExamName, Integer> examNames = databaseHelper.getExamNameDao();
        return examNames.queryForAll();
    }

    @Override
    public void addUnPublishedNotice(String title, String content, int category) {
        RuntimeExceptionDao<UploadNotice, Integer> uploadNotices = databaseHelper.getUploadNoticeDao();
        UploadNotice uploadNotice = new UploadNotice(title, category, content, 1);
        uploadNotices.create(uploadNotice);
    }

    @Override
    public List<UploadNotice> getAllUnpublishedNotice() {
        RuntimeExceptionDao<UploadNotice, Integer> uploadNoticeDao = databaseHelper.getUploadNoticeDao();
        return uploadNoticeDao.queryForAll();

    }

    @Override
    public void updateUnPublishedNotice(int id, String title, String content, int category) {

        RuntimeExceptionDao<UploadNotice, Integer> uploadNoticeDao = databaseHelper.getUploadNoticeDao();
        QueryBuilder<UploadNotice, Integer> queryBuilder = uploadNoticeDao.queryBuilder();
        try {
            queryBuilder.where().eq("id", id);
            PreparedQuery<UploadNotice> pq = queryBuilder.prepare();
            UploadNotice uploadNotice = uploadNoticeDao.queryForFirst(pq);
            uploadNotice.setContent(content);
            uploadNotice.setTitle(title);
            uploadNotice.setCategory(category);
            uploadNoticeDao.createOrUpdate(uploadNotice);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    public void deleteUnpublishedNotice(int id) {
        RuntimeExceptionDao<UploadNotice, Integer> uploadNoticeDao = databaseHelper.getUploadNoticeDao();
        DeleteBuilder<UploadNotice, Integer> deleteBuilder = uploadNoticeDao.deleteBuilder();
        try {
            deleteBuilder.where().eq("id", id);
            deleteBuilder.delete();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

}
