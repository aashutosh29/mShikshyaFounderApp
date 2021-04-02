package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;

import java.util.List;

public interface HomeFragmentView {
    void populateNotice(List<Notice> noticeList);

    void noNoticeAvailable();

    void dataSynced();

    void populateHolidayList(List<Holiday> holidayList);

    void hideLoading();
}
