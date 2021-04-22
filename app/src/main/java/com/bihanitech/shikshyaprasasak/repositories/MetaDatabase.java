package com.bihanitech.shikshyaprasasak.repositories;


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

import java.util.List;

/**
 * Created by dilip on 3/6/18.
 */

public interface MetaDatabase {
    void addAllMetaSchool(MetaSchool body);

    List<SchoolInfo> getSchoolInfo();

    void addContactList(List<ContactsItem> contactsItems);

    List<ContactsItem> getContactsList();

    void addClassSubject(List<ClassSubject> classSubjects);

    List<ClassSubject> getAllSubjectList();

    void addStudentInfo(List<StudentInfo> studentInfos);

    void addExamName(List<ExamName> examNames);

    void addClasses(List<Classes> classes);

    void addSection(List<Section> sectionList);

    void addNoticeItems(List<NoticeItem> noticeItems);

    List<Classes> getClassList();

    List<Section> getSectionList();

    List<ExamName> getExamList();

    void addUnPublishedNotice(String title, String content, int category);

    List<UploadNotice> getAllUnpublishedNotice();
}
