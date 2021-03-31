package com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity;


import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.student.Student;

import java.util.List;

public interface SearchView {


    void getClickedStudentDetails(Student searchProfile);

    void populateClasses(List<Classes> classes);

    void populateStudentList(List<Student> studentList);

    void showLoading();

    void hideLoading();

    void showError();
}
