package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.SetupPasswordActivity;

import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupPasswordPresenter {
    SetupPasswordView view;
    CDSService cdsService;

    public SetupPasswordPresenter(SetupPasswordView view) {
        this.view = view;
    }

    public void setPassword(String password, String number, String schoolId) {

        cdsService = ApiUtils.getCDSService();
        view.showProgressBar();
        cdsService.setPassword(password, "true", number, password, schoolId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int status = response.code();
                String res = "";

                if (response.isSuccessful()) {
                    if (status == 200) {
                        try {
                            res = response.body().string();
                            if (res.length() != 0) {
                                String unRead = "";
                                //    view.showToast(res);
                       /*JsonObject post = new JsonObject().get(res.substring(0,res.length()-1)).getAsJsonObject();
                       String resJson = post.get("message").toString();
                       Log.v(TAG,resJson);
                       */

                                JSONObject jsonObject = new JSONObject(res);
                                String result = jsonObject.getString("result");

                                if (result.equalsIgnoreCase("success")) {
                                    view.sendTOLogin();
                                } else {
                                    view.invalidUser();
                                }

                            } else {
                                view.showServerError();
                            }
                        } catch (Exception e) {
                            view.showServerError();

                        }
                    } else {
                        view.invalidUser();
                    }
                } else {
                    view.invalidUser();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showNetworkError();

            }
        });
    }
}
