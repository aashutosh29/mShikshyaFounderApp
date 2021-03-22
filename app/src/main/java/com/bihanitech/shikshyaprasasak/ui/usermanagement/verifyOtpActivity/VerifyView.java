package com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity;

import com.bihanitech.shikshyaprasasak.model.StudentInfo;
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

    void saveStudentDetail(StudentInfo studentInfo, String token);

    void saveRecentNotices(List<NoticeItem> noticeItems);

    void addCurrentStudentId(String regNo);

    void resendOTPSent();
}
