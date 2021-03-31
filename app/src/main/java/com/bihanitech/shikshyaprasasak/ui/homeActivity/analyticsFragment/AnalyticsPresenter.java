package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
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
     /*   cdsService.getNewTotalGenderEmployee("bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray object = new JSONArray(res);
                    List<EmployeeGenderWise> employeeGenderWises = new ArrayList<>();
                   *//* for (int i = 0; i <=object.getString("gender").length()-1;i++){
                       EmployeeGenderWise employeeGenderWise =  new EmployeeGenderWise(object.getJSONObject(i).)
                    }*//*
                    Log.v("AsTag", object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("AsTag", call.toString());
            }
        });*/

        Observable<List<EmployeeGenderWise>> call = cdsService.getTotalGenderEmployee("bearer " + token);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<List<EmployeeGenderWise>>() {
            @Override
            public void onComplete(List<EmployeeGenderWise> response) {
                Log.v("AsTag", response.toString());
                List<EmployeeGenderWise> employeeGenderWises = response;

                analyticsView.populateEmployeeGenderWise(employeeGenderWises);
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
}
