package com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile;

import com.bihanitech.shikshyaprasasak.model.StudentInformation;
import com.bihanitech.shikshyaprasasak.model.account.Account;

import java.util.List;

public interface StudentProfileView {
    void showLoading();

    void hideLoading();

    void populateAccountDetails(List<Account> accountList);

    void showError(int a);

    void populateStudentInformation(StudentInformation studentInformation);
}
