package com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity;

/**
 * Created by dilip on 4/4/18.
 */

public interface RegistrationView {

    void showProgressBar();

    void authenticated(boolean authorized);

    void showNetworkError();

    void showServerError();

    void retry();

    void showToast(String res);
}
