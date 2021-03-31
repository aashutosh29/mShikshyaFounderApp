package com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.model.StudentInformation;
import com.bihanitech.shikshyaprasasak.model.account.Account;
import com.bihanitech.shikshyaprasasak.model.examResult.Result;

import java.util.List;

public interface StudentProfileView {
    void showLoading();

    void hideLoading();

    void populateAccountDetails(List<Account> accountList);

    void showError(int a);

    void populateStudentInformation(StudentInformation studentInformation);

    void populateExamResult(List<Result> results);

    void ivDownUpArrowClicked(Result result, ConstraintLayout clSubjects, ImageView ivDownUPArrow, RecyclerView rvSubjects);
}
