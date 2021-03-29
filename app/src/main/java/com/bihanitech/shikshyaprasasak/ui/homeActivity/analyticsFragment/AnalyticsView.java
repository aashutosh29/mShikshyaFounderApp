package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;

import java.util.List;

public interface AnalyticsView {
    void showLoading();

    void populateEmployeeGenderWise(List<EmployeeGenderWise> employeeGenderWises);

    void populateStudentGenderWise(List<StudentGenderWise> studentGenderWises);
}
