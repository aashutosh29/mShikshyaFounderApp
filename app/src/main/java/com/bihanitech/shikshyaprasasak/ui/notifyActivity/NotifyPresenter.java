package com.bihanitech.shikshyaprasasak.ui.notifyActivity;

import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;


public class NotifyPresenter {

    NotifyView view;
    private CDSService cdsService;

    public NotifyPresenter(NotifyView notifyView) {
        this.view = notifyView;
    }



    public void fetchNoticeList(int userId, int page,String token) {
        if (cdsService == null)
            cdsService = ApiUtils.getCDSService();
        if (page == 1) {
            view.showLoading();
        }

        if (page == 0) {
            page = 1;
        }
//       // Observable<NoticeResponse> call = cdsService.getNoticeList("bearer "+token, page);
//
//        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<NoticeResponse>() {
//
//
//            @Override
//            public void onComplete(NoticeResponse response) {
//
//                if(response.getNotice()!=null && response.getNotice().size()>0){
//                    view.showNoticeList(response.getNotice(),response.getCurrentPage(),response.getTotalPages());
//
//                }else {
//                    view.emptyNotices();
//                }
//
//            }
//
//            @Override
//            public void onError(Exception e, int code) {
//                if(code==401){
//                    view.Perlogout();
//                }else {
//
//                    e.printStackTrace();
//                    Log.d(TAG, "onError: " + e+code);
//                    view.showServerError();
//                }
//            }
//
//            @Override
//            public void onConnectionException(Exception e) {
//                e.printStackTrace();
//                view.showNetworkError();
//            }
//        });


    }



}
