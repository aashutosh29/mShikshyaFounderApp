package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;

public class AnalyticsPresenter {

    public static final String TAG = AnalyticsFragment.class.getSimpleName();
    private final MetaDatabaseRepo metaDatabaseRepo;
    AnalyticsView analyticsView;
    CDSService cdsService;

    public AnalyticsPresenter(AnalyticsView analyticsView, MetaDatabaseRepo metaDatabaseRepo) {
        this.analyticsView = analyticsView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void getGenderWiseStaffAndStudent(String token) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        analyticsView.showLoading();
        Observable<EmployeeGenderWise> call = cdsService.getTotalGenderEmployee("bearer " + token);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<EmployeeGenderWise>() {
            @Override
            public void onComplete(EmployeeGenderWise response) {
                List<EmployeeGenderWise> employeeGenderWises = new ArrayList<>();
                employeeGenderWises.add(response);
                analyticsView.populateEmployeeGenderWise(employeeGenderWises);
            }

            @Override
            public void onError(Exception e, int code) {

            }

            @Override
            public void onConnectionException(Exception e) {

            }
        });
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        Observable<StudentGenderWise> studentCall = cdsService.getTotalGenderWiseStudent("bearer" + token);

        RequestHandler.asyncTask(studentCall, new RequestHandler.RetroReactiveCallBack<StudentGenderWise>() {
            @Override
            public void onComplete(StudentGenderWise response) {
                List<StudentGenderWise> studentGenderWises = new ArrayList<>((Collection<? extends StudentGenderWise>) response);
                analyticsView.populateStudentGenderWise(studentGenderWises);
            }

            @Override
            public void onError(Exception e, int code) {

            }

            @Override
            public void onConnectionException(Exception e) {

            }
        });
    }
}
