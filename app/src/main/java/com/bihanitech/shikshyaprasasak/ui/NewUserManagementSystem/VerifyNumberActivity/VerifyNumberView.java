package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.VerifyNumberActivity;

public interface VerifyNumberView {
    void showServerError();

    void verified(boolean b);

    void showNetworkError();

    void resendOTPSent();

    void setUpPassword(String schoolId, String phoneNumber, String schoolName);

    void retry();
}
