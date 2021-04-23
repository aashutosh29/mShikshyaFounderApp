package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.student.Student;

import java.util.List;

public interface AddNoticeView {
    void showSuccess();

    void showCantUpload();

    void showNetworkError();

    void retry();

    void savedLocally();

    void saveNoticeLocally();

    void deletedLocally();

    void showLoading();

    void populateClassesAndSectionList(List<Classes> classesList, List<Section> sectionList);

    void showLoadingForStudent();

    void hideLoadingForStudent();

    void populateStudentList(List<Student> studentList);

    void showError();
}
