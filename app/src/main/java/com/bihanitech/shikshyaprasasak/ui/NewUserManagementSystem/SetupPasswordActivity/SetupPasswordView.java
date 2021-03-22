package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.SetupPasswordActivity;

public interface SetupPasswordView {

    void showNetworkError();

    void showServerError();

    void showProgressBar();

    void sendTOLogin();

    void invalidUser();

    void gotoLogin();

    void retry();
}
