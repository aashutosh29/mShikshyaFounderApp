package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.ClassDueReport;
import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.StudentAttendance;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

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

        Observable<List<EmployeeGenderWise>> call = cdsService.getTotalGenderEmployee("bearer " + token);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<List<EmployeeGenderWise>>() {
            @Override
            public void onComplete(List<EmployeeGenderWise> response) {
                Log.v("AsTag", response.toString());
                getIncomeVsDueBalance(token);
                analyticsView.populateEmployeeGenderWise(response);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.v("AsTag", e.toString());
            }

            @Override
            public void onConnectionException(Exception e) {
                Log.v("AsTag", e.toString());

            }
        });


        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        Observable<List<StudentGenderWise>> studentCall = cdsService.getTotalGenderWiseStudent("bearer" + token);

        RequestHandler.asyncTask(studentCall, new RequestHandler.RetroReactiveCallBack<List<StudentGenderWise>>() {
            @Override
            public void onComplete(List<StudentGenderWise> response) {
                Log.v("AsTag", response.toString());
                List<StudentGenderWise> studentGenderWises = response;
                analyticsView.populateStudentGenderWise(studentGenderWises);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.v("AsTag", e.toString());
            }

            @Override
            public void onConnectionException(Exception e) {
                Log.v("AsTag", e.toString());
            }
        });
    }

    public void getAttendanceReport(String authToken, String newDate) {

        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        Observable<StudentAttendance> call = cdsService.getStudentAttendance("Bearer" + authToken, newDate);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<StudentAttendance>() {
            @Override
            public void onComplete(StudentAttendance response) {
                Log.d(TAG, "onComplete: " + response);
                analyticsView.onSuccess(newDate);

                //getIncomeVsDueBalance(authToken);
                analyticsView.populateStudentAttendance(response);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.d(TAG, "onError: " + e + code);
            }

            @Override
            public void onConnectionException(Exception e) {
                Log.d(TAG, "onConnectionException: " + e);

            }
        });


    }

    public void getIncomeVsDueBalance(String authToken) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        Observable<List<ClassDueReport>> call = cdsService.getClassDueReport("Bearer " + authToken);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<List<ClassDueReport>>() {
            @Override
            public void onComplete(List<ClassDueReport> response) {
                Log.d(TAG, "onComplete: " + response);
                analyticsView.populateIncomeVsDueBalance(response);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.d(TAG, "onError: " + e + code);

            }

            @Override
            public void onConnectionException(Exception e) {
                Log.d(TAG, "onConnectionException: " + e);

            }
        });
    }

}
