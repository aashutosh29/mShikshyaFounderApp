package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.RegisterActivity;

import com.bihanitech.shikshyaprasasak.remote.AUTHService;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterPresenter {

    RegisterView view;
    AUTHService authService;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
    }

    public void authenticateNumber(String phoneNumber, String schoolId) {

        authService = ApiUtils.getAuthCDSService();

        authService.validateNumber(schoolId, phoneNumber, "true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = "";
                int status = response.code();
                if (response.isSuccessful()) {

                    if (status == 200) {

                        try {
                            res = response.body().string();
                            //    view.showToast(res);
                            /*JsonObject post = new JsonObject().get(res.substring(0,res.length()-1)).getAsJsonObject();
                       String resJson = post.get("message").toString();
                       Log.v(TAG,resJson);
                       */
                            view.authenticated(res.length() != 0);
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
                    if (status == 404) {
                        //view.showToast(res+"404");
                        view.authenticated(false);
                    } else if (status == 500) {
                        view.authenticated(false);
                    } else {
                        view.showServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof Exception) {
                    t.printStackTrace();
                    view.showNetworkError();

                }

            }
        });

    }

}
