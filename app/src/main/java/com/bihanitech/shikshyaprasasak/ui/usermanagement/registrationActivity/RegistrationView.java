package com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity;

import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;

import java.util.List;

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

    void populateSliderList(List<EventSlider> body);

    void sliderItemClicked(EventSlider eventSlider);
}
