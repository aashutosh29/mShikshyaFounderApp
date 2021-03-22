package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.LoginActivity;

import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;

import java.util.List;

public interface LoginView {


    void populateSliderList(List<EventSlider> body);

    void sliderItemClicked(EventSlider eventSlider);

    void retry();

    void showNetworkError();

    void showLoading();

    void showServerError();

    void verified(boolean b, String s);

    void saveStudentDetail(StudentInfo studentInfo, String token);

    void addCurrentStudentId(String regNo);

    void saveRecentNotices(List<NoticeItem> noticeItems);

    void subscribeStudentForNotifications(StudentInfo studentInfo);
}
