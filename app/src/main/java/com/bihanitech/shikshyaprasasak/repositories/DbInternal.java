package com.bihanitech.shikshyaprasasak.repositories;



import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;

import java.util.List;

public interface DbInternal {


    void updateNotices(List<NoticeItem> noticeItems);

    List<NoticeItem> getAllNotices();



    void updateMetaAttend(int id);



}
