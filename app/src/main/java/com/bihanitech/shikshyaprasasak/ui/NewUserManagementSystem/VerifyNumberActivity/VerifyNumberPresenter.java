package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.VerifyNumberActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.remote.AUTHService;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity.VerifyPresenter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyNumberPresenter {
    private static final String TAG = VerifyPresenter.class.getSimpleName();
    VerifyNumberView view;
    AUTHService authService;

    public VerifyNumberPresenter(VerifyNumberView view) {
        this.view = view;
    }

    public void verifyOtpCode(String code, final String phoneNumber, final String schoolId, String fcmId, final String schoolName) {
        authService = ApiUtils.getAuthCDSService();

        authService.verifyOTP(schoolId, code, phoneNumber, fcmId, "true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String res = "";

                int status = response.code();
                Log.v(TAG, "Status code " + response.code());
                if (response.isSuccessful()) {
                    if (status == 200) {
                        view.setUpPassword(schoolId, phoneNumber, schoolName);
                    } else {
                        Log.v("TAGG", "error 1 " + status);
                        view.showServerError();
                    }
                } else {
                    int mStatusCode = response.code();
                    // handle request errors depending on status code
                    if (status == 404) {
                        view.verified(false);
                    } else {
                        Log.v("TAGG", "error 2 " + status);
                        view.showServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
                Log.d(TAG, call.toString());
                Log.d(TAG, "error loading from API");
                //   Log.d(TAG, t.getMessage());
                Log.d(TAG, t.toString());
                if (t instanceof Exception) {
                    Log.v(TAG, "this is ioException");
                    view.showNetworkError();
                }
            }
        });
    }

    public void resendOTPCode(String phoneNumber, String schoolId) {


        authService = ApiUtils.getAuthCDSService();

        Log.v(TAG, "schoolId " + schoolId);

        Log.v(TAG, "number " + phoneNumber);

        authService.validateNumber(schoolId, phoneNumber, "true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = "";
//                Log.v(TAG,response.body().toString());
                int status = response.code();
                Log.v(TAG, "Status code " + response.code());
                Log.v(TAG, "response " + response);
                if (response.isSuccessful()) {

                    if (status == 200) {

                        try {
                            res = response.body().string();
                            if (res.length() != 0) {
                                view.resendOTPSent();
                            }
                        } catch (IOException e) {
                            view.showServerError();
                            e.printStackTrace();
                        }

                    } else {
                        view.showServerError();
                    }

                } else {
                    int mStatusCode = response.code();
                    // handle request errors depending on status code

                    view.showServerError();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
                Log.d(TAG, call.toString());
                Log.d(TAG, "error loading from API");
                //   Log.d(TAG, t.getMessage());
                Log.d(TAG, t.toString());
                if (t instanceof Exception) {
                    t.printStackTrace();
                    Log.v(TAG, "this is ioException");
                    view.showNetworkError();

                }

            }
        });

    }
}
