package com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity;

import android.util.Log;


import com.bihanitech.shikshyaprasasak.remote.AUTHService;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dilip on 4/4/18.
 */

public class RegistrationPresenter {

    RegistrationView view;
    AUTHService authService;
    private static final String TAG = RegistrationPresenter.class.getSimpleName();

    public RegistrationPresenter(RegistrationView view) {
        this.view = view;
    }

    public void authenticateNumber(String phoneNumber, String schoolId){

        authService = ApiUtils.getAuthCDSService();

        Log.v(TAG,"schoolId "+schoolId);

        Log.v(TAG,"number "+phoneNumber);

        authService.validateNumber(schoolId,phoneNumber,"true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = "";
//                Log.v(TAG,response.body().toString());
                int status = response.code();
                Log.v(TAG,"Status code "+response.code());
                Log.v(TAG,"response "+response);
                if(response.isSuccessful()) {

                    if(status == 200){

                    try {
                        res = response.body().string();
                        if(res.length()!=0){
                        //    view.showToast(res);
                       /*JsonObject post = new JsonObject().get(res.substring(0,res.length()-1)).getAsJsonObject();
                       String resJson = post.get("message").toString();
                       Log.v(TAG,resJson);
                       */
                            view.authenticated(true);

                        }else{
                            view.authenticated(false);
                        }
                    } catch (IOException e) {
                        view.showServerError();
                        e.printStackTrace();
                    }

                    }else{
                        view.showServerError();
                    }

                }else {
                    int mStatusCode  = response.code();
                    // handle request errors depending on status code
                    if(status == 404){
                        //view.showToast(res+"404");
                        view.authenticated(false);
                    }else if(status == 500){
                       view.authenticated(false);
                    }else {
                        view.showServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
                Log.d(TAG,call.toString());
                Log.d(TAG, "error loading from API");
                //   Log.d(TAG, t.getMessage());
                Log.d(TAG, t.toString());
                if(t instanceof Exception){
                    Log.v(TAG,"this is ioException");
                    view.showNetworkError();

                }

            }
        });


        /*if(phoneNumber.equalsIgnoreCase("9845924109")){
            view.authenticated(true);
        }else{
            view.authenticated(false);
        }*/
    }
}
