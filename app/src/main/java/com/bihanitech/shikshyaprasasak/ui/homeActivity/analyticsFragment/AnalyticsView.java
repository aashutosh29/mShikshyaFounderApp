package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import com.bihanitech.shikshyaprasasak.model.ClassDueReport;
import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.StudentAttendance;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;

import java.util.List;

public interface AnalyticsView {
    void showLoading();

    void populateEmployeeGenderWise(List<EmployeeGenderWise> employeeGenderWise);

    void populateStudentGenderWise(List<StudentGenderWise> studentGenderWises);

    void populateStudentAttendance(StudentAttendance response);

    void populateIncomeVsDueBalance(List<ClassDueReport> response);

    void onSuccessAR(String newDate);

    void onSuccessIVDB();

    void onSuccessTSASR();

    void LoadingScreenOnAttendance();

    void loadingScreenOnIVDB();

    void loadingScreenOnTSASR();

    void showNetworkErrorOnAttendance(String type);

    void showNetworkErrorOnIVDB(String type);

    void showNetworkErrorOnTSASR(String type);
}
