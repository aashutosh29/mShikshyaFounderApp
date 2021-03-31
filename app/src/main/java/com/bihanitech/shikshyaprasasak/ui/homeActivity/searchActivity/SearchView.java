package com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity;


import com.bihanitech.shikshyaprasasak.model.student.Student;

import java.util.List;

public interface SearchView {


    void getClickedStudentDetails(Student searchProfile);

    void populateClasses(List<String> className);

    void populateStudentList(List<Student> studentList);

    void showLoading();

    void hideLoading();

    void showError();
}
