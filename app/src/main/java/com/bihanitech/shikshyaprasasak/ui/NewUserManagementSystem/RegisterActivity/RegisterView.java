package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.RegisterActivity;

public interface RegisterView {
    void authenticateNumber();

    void authenticated(boolean b);

    void showServerError();

    void showNetworkError();

    void retry();
}
