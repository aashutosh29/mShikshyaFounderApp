package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import com.bihanitech.shikshyaprasasak.model.Notice;

import java.util.List;

public interface HomeFragmentView {
    void populateNotice(List<Notice> noticeList);

    void noNoticeAvailable();

    void dataSynced();
}
