package com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment;

import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.acedamics.ClassData;

import java.util.List;

public interface AcademicsView {
    void loadDataOnGraph(List<ClassData> data);

    void getExams(List<ExamName> examList);

    void noDataAvailable();
}
