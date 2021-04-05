package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class AddNoticePresenter {
    private final AddNoticeView addNoticeView;
    private final MetaDatabaseRepo metaDatabaseRepo;
    CDSService cdsService;


    public AddNoticePresenter(AddNoticeView addNoticeView, MetaDatabaseRepo metaDatabaseRepo) {
        this.addNoticeView = addNoticeView;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void uploadNotice(String authToken, String etTitle, String etContentBody, String uploadNotice) {
        if (cdsService == null) {
            cdsService = ApiUtils.getDummyCDSService();

        }

        String finalJson = "";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), finalJson);
        Observable<ResponseBody> call = cdsService.sendNoticeToServer("Bearer " + authToken, body);
        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<ResponseBody>() {
            @Override
            public void onComplete(ResponseBody response) {
                addNoticeView.showSuccess();
            }

            @Override
            public void onError(Exception e, int code) {
                addNoticeView.showCantUpload();

            }

            @Override
            public void onConnectionException(Exception e) {
                addNoticeView.showNetworkError();

            }
        });

    }

}
