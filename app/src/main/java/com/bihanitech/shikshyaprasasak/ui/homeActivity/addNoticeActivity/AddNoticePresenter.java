package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.UploadResponse;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.model.student.StudentResponse;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class AddNoticePresenter {
    public static List<Student> studentList1 = new ArrayList<>();
    private final AddNoticeView addNoticeView;
    private final MetaDatabaseRepo metaDatabaseRepo;
    CDSService cdsService;

    public AddNoticePresenter(AddNoticeView addNoticeView, MetaDatabaseRepo metaDatabaseRepo) {
        this.addNoticeView = addNoticeView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void uploadNotice(Boolean isEdit, String authToken, String grade, String title, String content, String publishOn, String section, String category) {

        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();

        }
        addNoticeView.showLoading();
        Observable<UploadResponse> call = cdsService.sendNoticeToServer("Bearer " + authToken, title, content, /*Integer.parseInt(category)*/2, section, grade);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<UploadResponse>() {
            @Override
            public void onComplete(UploadResponse response) {
                if (response.getResult().equals("success")) {
                    if (isEdit) {
                        addNoticeView.deletedLocally();
                    }
                    addNoticeView.showSuccess();

                }
            }

            @Override
            public void onError(Exception e, int code) {
                addNoticeView.showCantUpload();
                if (!(grade.equals("")))
                    addNoticeView.saveNoticeLocally();

            }

            @Override
            public void onConnectionException(Exception e) {
                addNoticeView.showNetworkError();
                if (!(grade.equals("")))
                    addNoticeView.saveNoticeLocally();
            }
        });

    }

    public void saveLocally(String title, String content, int category) {
        metaDatabaseRepo.addUnPublishedNotice(title, content, category);
        addNoticeView.savedLocally();
    }

    public void updateLocally(int id, String title, String content, int category) {
        metaDatabaseRepo.updateUnPublishedNotice(id, title, content, category);
        addNoticeView.savedLocally();
    }

    public void deleteLocally(int id) {
        metaDatabaseRepo.deleteUnpublishedNotice(id);
        addNoticeView.deletedLocally();
    }

    void getSpinnerData() {

        List<Classes> classesList = metaDatabaseRepo.getClassList();
        List<Section> sectionList = metaDatabaseRepo.getSectionList();

        addNoticeView.populateClassesAndSectionList(classesList, sectionList);

        Log.v("Astag", classesList.toString());

    }


    void getStudents(String studentClass, String studentSection, String token) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        addNoticeView.showLoadingForStudent();
        Observable<StudentResponse> call = cdsService.getStudentFiltered("Bearer " + token, studentClass, studentSection);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<StudentResponse>() {
            @Override
            public void onComplete(StudentResponse response) {
                List<Student> studentList = response.getData();
                if (studentList.size() > 0) {
                    studentList1.addAll(studentList);
                }

                addNoticeView.hideLoadingForStudent();

                addNoticeView.populateStudentList(studentList, false);
            }

            @Override
            public void onError(Exception e, int code) {
                addNoticeView.showError();
            }

            @Override
            public void onConnectionException(Exception e) {
                addNoticeView.showError();
            }
        });
    }
}
