package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class AddNoticePresenter {
    private final AddNoticeView addNoticeView;
    private final MetaDatabaseRepo metaDatabaseRepo;
    CDSService cdsService;


    public AddNoticePresenter(AddNoticeView addNoticeView, MetaDatabaseRepo metaDatabaseRepo) {
        this.addNoticeView = addNoticeView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void uploadNotice(String authToken, String grade, String title, String content, String publishOn, String Section, String category) {

        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();

        }


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("class", grade)
                .addFormDataPart("content", content)
                .addFormDataPart("publishon", publishOn)
                .addFormDataPart("section", Section)
                .addFormDataPart("title", title)
                .addFormDataPart("type", category)
                .build();

        Log.d(TAG, "uploadNotice: " + requestBody.toString());

        Observable<ResponseBody> call = cdsService.sendNoticeToServer("Bearer " + authToken, requestBody);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<ResponseBody>() {
            @Override
            public void onComplete(ResponseBody response) {
                addNoticeView.showSuccess();
            }

            @Override
            public void onError(Exception e, int code) {
                addNoticeView.showCantUpload();
                addNoticeView.saveNoticeLocally();

            }

            @Override
            public void onConnectionException(Exception e) {
                addNoticeView.showNetworkError();
                addNoticeView.saveNoticeLocally();
            }
        });

    }

    public void saveLocally(String title, String content, int category) {
        metaDatabaseRepo.addUnPublishedNotice(title, content, category);
        addNoticeView.savedLocally();
    }
}
