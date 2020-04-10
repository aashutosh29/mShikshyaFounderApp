package com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity;

import android.util.Log;


import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.remote.AUTHService;
import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dilip on 4/16/18.
 */

public class VerifyPresenter {
    private VerifyView view;
    private AUTHService authService;
    private MetaDatabaseRepo metaDatabaseRepo;
    private CDSService cdsService;
    private static final String TAG  = VerifyPresenter.class.getSimpleName();


    public VerifyPresenter(VerifyView view, MetaDatabaseRepo metaDatabaseRepo) {
        this.view = view;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void verifyOtpCode(String code, final String phoneNumber, final String schoolId, String fcmId, final String schoolName) {
        authService = ApiUtils.getAuthCDSService();

        authService.verifyOTP(schoolId,code,phoneNumber,fcmId,"true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = "";

                int status = response.code();
                Log.v(TAG,"Status code "+response.code());
                if(response.isSuccessful()) {

                    if(status == 200){
                        getAuthenticated("true",schoolId,phoneNumber);
                    }else{
                        Log.v("TAGG","error 1 "+ status);
                        view.showServerError();
                    }

                }else {
                    int mStatusCode  = response.code();
                    // handle request errors depending on status code
                    if(status == 404){
                        view.verified(false);
                    }else {
                        Log.v("TAGG","error 2 "+ status);
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
                t.printStackTrace();
                if(t instanceof Exception){
                    Log.v(TAG,"this is ioException");
                    view.showNetworkError();

                }

            }
        });


        /*if(code.trim().equalsIgnoreCase("123456")){
            view.verified(true);
        }else{
            view.verified(false);
        }*/
    }


    public void getAuthenticated(String isTeacherMobile, final String schoolId,final String phoneNumber) {
        if(cdsService == null)
            cdsService = ApiUtils.getCDSService();

        cdsService.getAuthenticated(isTeacherMobile,schoolId,phoneNumber).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = "";

                int status = response.code();
                Log.v(TAG,"Status code "+response.code());
                if(response.isSuccessful()) {

                    if(status == 200){

                        try {
                            res = response.body().string();
                            Log.v(TAG,"ResponseBody is : "+res );
                            if(res.length()!=0){

                                JSONObject post = new JSONObject(res);
                                Log.v(TAG,res);
                                String message = post.getString(Constant.MESSAGE);
                                if(message.equalsIgnoreCase(Constant.SUCCESS)) {
                                    String token = post.getString(Constant.TOKEN);
                                    JSONObject dataArray = post.getJSONObject(Constant.DATA);
                                    JSONObject teacherDetail = dataArray.getJSONObject(Constant.TEACHER_INFO);
                                    JSONArray contactArr = dataArray.getJSONArray(Constant.CONTACT);
                                    JSONArray noticeArr = dataArray.getJSONArray(Constant.NOTICE);
                                    JSONArray subjectArr = dataArray.getJSONArray(Constant.CLASS_SUBJECT);

                                    String teacherId = teacherDetail.getString(Constant.TEACHER_ID);
                                    String teacherName = teacherDetail.getString(Constant.TEACHER_NAME);
                                    String mobileNo = teacherDetail.getString(Constant.MOBILE_NO);
                                    Log.v(TAG, "teacherId " + teacherId + " teacher name " + teacherName);
                                  //  String className = studentDetailArr.getJSONObject(0).getString(Constant.ST_CLASS);

                                   /* String examId = examArr.getJSONObject(0).getString(Constant.EXAM_ID);
                                    String examName = examArr.getJSONObject(0).getString(Constant.EXAM_NAME_VERIFY);
*/

                                  //  view.saveTeacherDetail(regId, studentName,className,examId,examName);
                                    view.saveTeacherDetail(teacherId, teacherName,mobileNo,token);

                                    List<ContactsItem> contactsItemList = new ArrayList<>();
                                    for (int i = 0; i < contactArr.length(); i++) {
                                        ContactsItem contactsItem = new ContactsItem();
                                        contactsItem.setName(contactArr.getJSONObject(i).getString(Constant.NAME));
                                        contactsItem.setContacts(contactArr.getJSONObject(i).getString(Constant.CONTACT));
                                        contactsItemList.add(contactsItem);
                                    }

                                    metaDatabaseRepo.addContactList(contactsItemList);

                                    List<NoticeItem> noticeItems = new ArrayList<>();
                                    for (int i = 0; i < noticeArr.length(); i++) {
                                        NoticeItem noticeItem = new NoticeItem();
                                        noticeItem.setTitle(noticeArr.getJSONObject(i).getString(Constant.TITLE));
                                        noticeItem.setDetail(noticeArr.getJSONObject(i).getString(Constant.CONTENT));
                                        noticeItem.setpDate(noticeArr.getJSONObject(i).getString(Constant.PUBLISHED_TIME));

                                        noticeItems.add(noticeItem);
                                    }

                                    view.saveRecentNotices(noticeItems);
                                    view.verified(true);
                                }else{
                                    view.verified(false);
                                }

                            }else{
                                view.verified(false);
                            }
                        } catch (IOException e) {
                            view.showServerError();
                            Log.v("TAGG","error 3 "+ status);
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("TAGG","error 4 "+ status);
                            view.showServerError();
                        }

                    }else{
                        Log.v("TAGG","error 5 "+ status);
                        view.showServerError();
                    }

                }else {
                    int mStatusCode  = response.code();
                    // handle request errors depending on status code
                    if(status == 404){
                        view.verified(false);
                    }else {
                        Log.v("TAGG","error 6 "+ status);
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

    }

    }
