package com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.model.student.StudentResponse;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.List;

import io.reactivex.Observable;

public class SearchPresenter {

    private final MetaDatabaseRepo metaDatabaseRepo;
    CDSService cdsService;
    SearchView searchView;

    public SearchPresenter(MetaDatabaseRepo metaDatabaseRepo, SearchView searchView) {
        this.metaDatabaseRepo = metaDatabaseRepo;
        this.searchView = searchView;
    }

    void getSpinnerData() {
        List<Classes> classesList = metaDatabaseRepo.getClassList();
        List<Section> sectionList = metaDatabaseRepo.getSectionList();

        searchView.populateClassesAndSectionList(classesList, sectionList);

        Log.v("Astag", classesList.toString());

    }

    void getStudents(String studentClass, String studentSection, String token) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        searchView.showLoading();
        Observable<StudentResponse> call = cdsService.getStudent("Bearer " + token, studentClass, studentSection);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<StudentResponse>() {
            @Override
            public void onComplete(StudentResponse response) {
                List<Student> studentList = response.getData();
                searchView.hideLoading();
                searchView.populateStudentList(studentList);
            }

            @Override
            public void onError(Exception e, int code) {
                searchView.showError();
            }

            @Override
            public void onConnectionException(Exception e) {
                searchView.showError();
            }
        });


    }


}
