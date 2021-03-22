package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.LoginActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
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

public class LoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private final MetaDatabaseRepo metaDatabaseRepo;
    LoginView view;
    AUTHService authService;
    CDSService cdsService;


    public LoginPresenter(LoginView view, MetaDatabaseRepo metaDatabaseRepo) {
        this.view = view;
        this.metaDatabaseRepo = metaDatabaseRepo;
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


    public void login(String schoolId, String phone, String password, String schoolName, String FCM, String deviceID) {

//        if (cdsService == null)
        cdsService = ApiUtils.getV2AuthCDSService();

        view.showLoading();

        cdsService.getLogin(schoolId, phone, "true", password, FCM, deviceID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = "";
                int status = response.code();
                Log.v(TAG, "Status code " + response.code());
                if (response.isSuccessful()) {

                    if (status == 200) {

                        try {
                            res = response.body().string();
                            Log.v(TAG, "ResponseBody is : " + res);
                            if (res.length() != 0) {

                                JSONObject post = new JSONObject(res);
                                Log.v(TAG, res);
                                String message = post.getString(Constant.MESSAGE);
                                if (message.equalsIgnoreCase(Constant.SUCCESS)) {
                                    String token = post.getString(Constant.TOKEN);

                                    JSONObject dataArray = post.getJSONObject(Constant.DATA);
                                    JSONArray studentDetailArr = dataArray.getJSONArray(Constant.STUDENT);
                                    JSONArray contactArr = dataArray.getJSONArray(Constant.CONTACT);
                                    JSONArray noticeArr = dataArray.getJSONArray(Constant.NOTICE);
                                    JSONArray examArr = dataArray.getJSONArray(Constant.EXAM_TERMINAL);

                                    String regId = studentDetailArr.getJSONObject(0).getString(Constant.REGNO);
                                    String studentName = studentDetailArr.getJSONObject(0).getString(Constant.STNAME);
                                    Log.v(TAG, "regID " + regId + " student name " + studentName);
                                    String className = studentDetailArr.getJSONObject(0).getString(Constant.ST_CLASS);
                                    String classid = studentDetailArr.getJSONObject(0).getString(Constant.ST_CLASS_ID);


                                    List<ContactsItem> contactsItemList = new ArrayList<>();
                                    for (int i = 0; i < contactArr.length(); i++) {
                                        ContactsItem contactsItem = new ContactsItem();
                                        contactsItem.setName(contactArr.getJSONObject(i).getString(Constant.NAME));
                                        contactsItem.setContacts(contactArr.getJSONObject(i).getString(Constant.CONTACT));
                                        contactsItemList.add(contactsItem);
                                    }

                                    List<NoticeItem> noticeItems = new ArrayList<>();
                                    for (int i = 0; i < noticeArr.length(); i++) {
                                        NoticeItem noticeItem = new NoticeItem();
                                        noticeItem.setTitle(noticeArr.getJSONObject(i).getString(Constant.TITLE));
                                        noticeItem.setDetail(noticeArr.getJSONObject(i).getString(Constant.CONTENT));
                                        noticeItem.setpDate(noticeArr.getJSONObject(i).getString(Constant.PUBLISHED_TIME));
                                        noticeItems.add(noticeItem);
                                    }

                                    List<StudentInfo> studentInfoList = new ArrayList<>();
                                    for (int i = 0; i < studentDetailArr.length(); i++) {
                                        StudentInfo studentInfo = new StudentInfo();
                                        studentInfo.setRegNo(studentDetailArr.getJSONObject(i).getString(Constant.REGNO));
                                        studentInfo.setStName(studentDetailArr.getJSONObject(i).getString(Constant.STNAME));
                                        studentInfo.setStClass(studentDetailArr.getJSONObject(i).getString(Constant.ST_CLASS));
                                        studentInfo.setStPhoto(studentDetailArr.getJSONObject(i).getString(Constant.STPPHOTO));
                                        studentInfo.setStSchool(schoolName);
                                        studentInfo.setStClassId(studentDetailArr.getJSONObject(i).getString(Constant.ST_CLASS_ID));
                                        studentInfo.setResultRoutine(0);

                                        if (studentDetailArr.getJSONObject(i).getString(Constant.ST_SECTION).length() != 0) {
                                            studentInfo.setStSectionId(studentDetailArr.getJSONObject(i).getString(Constant.ST_SECTION));
                                            studentInfo.setStSectionName(studentDetailArr.getJSONObject(i).getJSONObject("section").getString("CLASSNAME"));
                                        } else {
                                            studentInfo.setStSectionId("");

                                        }

                                        view.subscribeStudentForNotifications(studentInfo);
                                        studentInfoList.add(studentInfo);
                                    }
                                    view.saveStudentDetail(studentInfoList.get(0), token);

                                    metaDatabaseRepo.addStudentInfo(studentInfoList);
                                    view.addCurrentStudentId(studentInfoList.get(0).getRegNo());
                                    metaDatabaseRepo.addContactList(contactsItemList);
                                    view.saveRecentNotices(noticeItems);
                                    view.verified(true, "");
                                } else {
                                    view.verified(false, "Email and password not registered or incorrect");
                                }

                            } else {
                                view.verified(false, "Email and password not registered or incorrect");
                            }
                        } catch (IOException e) {
                            view.showServerError();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            view.showServerError();
                        }

                    } else {
                        if (status == 401) {
                            view.verified(false, "Email and password not registered or incorrect");
                        } else {
                            view.showServerError();
                        }
                    }

                } else {
                    int mStatusCode = response.code();
                    // handle request errors depending on status code
                    if (status == 401) {
                        view.verified(false, "Email and password not registered or incorrect");
                    } else {
                        Log.v("TAGESTO", "status code is " + mStatusCode);
                        view.showServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
                t.printStackTrace();
                Log.d(TAG, call.toString());
                Log.d(TAG, "error loading from API");
                //   Log.d(TAG, t.getMessage());
                Log.d(TAG, t.toString());
                if (t instanceof Exception) {
                    Log.v(TAG, "this is ioException");
                    view.showNetworkError();

                }

            }

        });
    }

    public void saveNoticeToDB(List<NoticeItem> noticeItems) {

        metaDatabaseRepo.addNoticeItems(noticeItems);

    }
}
