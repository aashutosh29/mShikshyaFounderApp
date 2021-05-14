package com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.StudentInformation;
import com.bihanitech.shikshyaprasasak.model.account.Account;
import com.bihanitech.shikshyaprasasak.model.account.AccountResponse;
import com.bihanitech.shikshyaprasasak.model.examResult.ExamResponse;
import com.bihanitech.shikshyaprasasak.model.examResult.Result;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;

import java.util.List;

import io.reactivex.Observable;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class StudentProfilePresenter {
    StudentProfileView studentProfileView;
    CDSService cdsService;

    public StudentProfilePresenter(StudentProfileView studentProfileView) {
        this.studentProfileView = studentProfileView;
    }

    void getAccountDetails(String regNo, String authToken) {

        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();
        }
        studentProfileView.showLoading();
        Observable<AccountResponse> call = cdsService.getAccountDetails(regNo, "bearer" + authToken);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<AccountResponse>() {
            @Override
            public void onComplete(AccountResponse response) {
                List<Account> accountList = response.getData();
                studentProfileView.populateAccountDetails(accountList);
            }

            @Override
            public void onError(Exception e, int code) {
                studentProfileView.showError(1);
            }

            @Override
            public void onConnectionException(Exception e) {
                studentProfileView.showError(1);

            }
        });


        Observable<StudentInformation> newCall = cdsService.getStudentInfo(regNo, "bearer" + authToken);
        RequestHandler.asyncTask(newCall, new RequestHandler.RetroReactiveCallBack<StudentInformation>() {
            @Override
            public void onComplete(StudentInformation response) {
                studentProfileView.hideLoading();
                studentProfileView.populateStudentInformation(response);
                Log.d(TAG, "onComplete: " + response);
            }

            @Override
            public void onError(Exception e, int code) {
                studentProfileView.showError(2);
                Log.d(TAG, "onError: " + e + code);
            }

            @Override
            public void onConnectionException(Exception e) {
                studentProfileView.showError(2);
                Log.d(TAG, "onConnectionException: " + e);

            }
        });
    }

    void getExamDetails(String regNo, String studentClass, String authToken) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();

        }

        Observable<ExamResponse> call = cdsService.getExamResult("bearer" + authToken, studentClass, regNo);
        int a;
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<ExamResponse>() {
            @Override
            public void onComplete(ExamResponse response) {

                List<Result> results = response.getResult();
                studentProfileView.populateExamResult(results);
                Log.d(TAG, "onComplete: " + response);
            }

            @Override
            public void onError(Exception e, int code) {
                Log.d(TAG, "onError: " + e + code);
                studentProfileView.showError(2);
            }

            @Override
            public void onConnectionException(Exception e) {
                Log.d(TAG, "onConnectionException: " + e);
                studentProfileView.showError(2);
            }
        });

    }

}
