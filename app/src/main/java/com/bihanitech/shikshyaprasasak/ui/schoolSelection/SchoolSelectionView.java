package com.bihanitech.shikshyaprasasak.ui.schoolSelection;


import com.bihanitech.shikshyaprasasak.model.SchoolInfo;

import java.util.List;

/**
 * Created by dilip on 3/6/18.
 */

public interface SchoolSelectionView {


 //   Bitmap getBitmapFile(String schoolid);

    void setUpRecyclerView(List<SchoolInfo> schoolInfo);

    void sendToRegistration(SchoolInfo schoolInfo);
}
