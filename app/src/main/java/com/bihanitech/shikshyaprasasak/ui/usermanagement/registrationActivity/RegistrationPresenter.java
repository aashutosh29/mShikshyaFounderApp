package com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.remote.AUTHService;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    CDSService cdsService;


    private static final String TAG = RegistrationPresenter.class.getSimpleName();

    public RegistrationPresenter(RegistrationView view) {
        this.view = view;
    }

    public void authenticateNumber(String phoneNumber, String schoolId) {

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
                t.printStackTrace();
                //showErrorMessage();
                Log.d(TAG,call.toString());
                Log.d(TAG, "error loading from API");
                //   Log.d(TAG, t.getMessage());
                Log.d(TAG, t.toString());
                if(t instanceof Exception){
                    t.printStackTrace();
                    Log.v(TAG, "this is ioException");
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

    public void fetchSliderList(String schoolId) {

        if (cdsService == null) {
            cdsService = ApiUtils.getCDSService();
        }

        cdsService.getShikshyaNotice(schoolId).enqueue(new Callback<List<EventSlider>>() {


            @Override
            public void onResponse(Call<List<EventSlider>> call, Response<List<EventSlider>> response) {
                int status = response.code();
                if (status == 200) {
                    view.populateSliderList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EventSlider>> call, Throwable t) {
                List<EventSlider> eventSliders = new ArrayList<>();
                view.populateSliderList(eventSliders);
            }
        });

    }
}
