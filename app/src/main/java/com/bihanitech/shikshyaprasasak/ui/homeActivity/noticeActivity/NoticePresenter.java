package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity;


import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class NoticePresenter {

    private final NoticeView noticeView;
    private CDSService cdsService;
    private final DbInternalRepo dbInternal;
    private static final String TAG = NoticePresenter.class.getSimpleName();

    public NoticePresenter(NoticeView noticeView,DbInternalRepo dbInternal)
    {
        this.noticeView = noticeView;
        this.dbInternal = dbInternal;
    }


    public void fetchNoticeList(String auth) {

        cdsService = ApiUtils.getCDSService();


        Observable<ResponseBody> call = cdsService.getPublicNotice("bearer"+auth,"1");

        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<ResponseBody>() {
            @Override
            public void onComplete(ResponseBody response) {
                //Log.v(TAG,response.getUser().getName());
                String res = "";

                try {
                    res = response.string();
                    if(res.length()!=0){

                        //    view.showToast(res);
                       /*JsonObject post = new JsonObject().get(res.substring(0,res.length()-1)).getAsJsonObject();
                       String resJson = post.get("message").toString();
                       Log.v(TAG,resJson);
                       */

                        JSONObject jsonObject = new JSONObject(res);
                        JSONArray dataArray = jsonObject.getJSONArray(Constant.DATA);
                        List<NoticeItem> noticeItems = new ArrayList<>();

                        for (int i = 0; i < dataArray.length(); i++){
                            JSONObject noticeObj = dataArray.getJSONObject(i);
                            NoticeItem noticeItem = new NoticeItem();
                            noticeItem.setTitle(noticeObj.getString(Constant.TITLE));
                            noticeItem.setDetail(noticeObj.getString(Constant.CONTENT));
                            noticeItem.setpDate(noticeObj.getString(Constant.DATE));

                            noticeItems.add(noticeItem);

                        }

                        dbInternal.updateNotices(noticeItems);
                        noticeView.proceedAfterDownload();

                      //  noticeView.populateList(noticeItems);

                    }else{
                        noticeView.showServerError();
                    }
                } catch (IOException e) {
                    noticeView.showServerError();
                    e.printStackTrace();
                }catch (JSONException e){
                    noticeView.showServerError();
                    e.printStackTrace();
                }


                //Log.v(TAG,response.getUser().getName());
                //Log.v(TAG,response.getUser().getName());
            }

            @Override
            public void onError(Exception e, int code) {
                //e.printStackTrace();

                e.printStackTrace();
                noticeView.showServerError();

            }

            @Override
            public void onConnectionException(Exception e) {
                e.printStackTrace();
                noticeView.showNetworkError();
            }
        });
    }

    public void getNoticeFromDb() {
        List<NoticeItem> notices = dbInternal.getAllNotices();
        noticeView.populateList(notices);

    }
}
