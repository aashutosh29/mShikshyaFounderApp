package com.bihanitech.shikshyaprasasak.ui.splashScreen;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dilip on 3/5/18.
 */

public class SplashScreenPresenter {

    SplashScreenView view;
    MetaDatabaseRepo databaseRepo;
    private CDSService mService;

    List<String> downloadedId = new ArrayList<>();

    private static final String TAG = SplashScreenPresenter.class.getSimpleName();

    public SplashScreenPresenter(SplashScreenView view, MetaDatabaseRepo databaseRepo) {
        this.view = view;
        this.databaseRepo = databaseRepo;
    }

    public void loadMetaSchool() {
        view.showProgressBar();
        mService = ApiUtils.getCDSService();

        mService.getMetaSchoolServer().enqueue(new Callback<MetaSchool>() {
            @Override
            public void onResponse(Call<MetaSchool> call, Response<MetaSchool> response) {

                if(response.isSuccessful()) {
                    databaseRepo.addAllMetaSchool(response.body());
                    Log.d(TAG, "posts loaded from API");
                    view.setMetaDataDownloaded();
                    view.sendToHome();
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    view.showServerError();
                }
            }

            @Override
            public void onFailure(Call<MetaSchool> call, Throwable t) {
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
    }


}
