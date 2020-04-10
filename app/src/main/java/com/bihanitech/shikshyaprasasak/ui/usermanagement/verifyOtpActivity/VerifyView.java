package com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity;


import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;

import java.util.List;

/**
 * Created by dilip on 4/16/18.
 */

public interface VerifyView {
    void verified(boolean b);

    void retry();

    void showNetworkError();

    void showServerError();


    void saveRecentNotices(List<NoticeItem> noticeItems);

    void addCurrentStudentId(String regNo);

    void saveTeacherDetail(String teacherId, String teacherName, String mobileNo, String token);
}
