package com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.StudentInfo;
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
    private static final String TAG = VerifyPresenter.class.getSimpleName();
    private AUTHService authService;
    private CDSService cdsService;
    private final VerifyView view;
    private final MetaDatabaseRepo metaDatabaseRepo;


    public VerifyPresenter(VerifyView view, MetaDatabaseRepo metaDatabaseRepo) {
        this.view = view;
        this.metaDatabaseRepo = metaDatabaseRepo;
    }

    public void verifyOtpCode(String code, final String phoneNumber, final String schoolId, String fcmId, final String schoolName) {
        authService = ApiUtils.getAuthCDSService();

        authService.verifyOTP(schoolId, code, phoneNumber, fcmId, "true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String res = "";

                int status = response.code();
                Log.v(TAG, "Status code " + response.code());
                if (response.isSuccessful()) {
                    if (status == 200) {
                        getAuthenticated("true", schoolId, phoneNumber, schoolName);
                    } else {
                        Log.v("TAGG", "error 1 " + status);
                        view.showServerError();
                    }
                } else {
                    int mStatusCode = response.code();
                    // handle request errors depending on status code
                    if (status == 404) {
                        view.verified(false);
                    } else {
                        Log.v("TAGG", "error 2 " + status);
                        view.showServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
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


    public void getAuthenticated(String isParentMobile, final String schoolId, final String phoneNumber, final String schoolName) {
        if (cdsService == null)
            cdsService = ApiUtils.getCDSService();

        cdsService.getAuthenticated(isParentMobile, schoolId, phoneNumber).enqueue(new Callback<ResponseBody>() {
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


                                        studentInfoList.add(studentInfo);
                                    }
                                    view.saveStudentDetail(studentInfoList.get(0), token);

                                    metaDatabaseRepo.addStudentInfo(studentInfoList);
                                    view.addCurrentStudentId(studentInfoList.get(0).getRegNo());
                                    metaDatabaseRepo.addContactList(contactsItemList);
                                    view.saveRecentNotices(noticeItems);
                                    view.verified(true);
                                } else {
                                    view.verified(false);
                                }

                            } else {
                                view.verified(false);
                            }
                        } catch (IOException e) {
                            view.showServerError();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            view.showServerError();
                        }

                    } else {
                        view.showServerError();
                    }

                } else {
                    int mStatusCode = response.code();
                    // handle request errors depending on status code
                    if (status == 404) {
                        view.verified(false);
                    } else {
                        view.showServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
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


    //to resend the otp code
    public void resendOTPCode(String phoneNumber, String schoolId) {


        authService = ApiUtils.getAuthCDSService();

        Log.v(TAG, "schoolId " + schoolId);

        Log.v(TAG, "number " + phoneNumber);

        authService.validateNumber(schoolId, phoneNumber, "true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = "";
//                Log.v(TAG,response.body().toString());
                int status = response.code();
                Log.v(TAG, "Status code " + response.code());
                Log.v(TAG, "response " + response);
                if (response.isSuccessful()) {

                    if (status == 200) {

                        try {
                            res = response.body().string();
                            if (res.length() != 0) {
                                view.resendOTPSent();
                            }
                        } catch (IOException e) {
                            view.showServerError();
                            e.printStackTrace();
                        }

                    } else {
                        view.showServerError();
                    }

                } else {
                    int mStatusCode = response.code();
                    // handle request errors depending on status code

                    view.showServerError();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //showErrorMessage();
                Log.d(TAG, call.toString());
                Log.d(TAG, "error loading from API");
                //   Log.d(TAG, t.getMessage());
                Log.d(TAG, t.toString());
                if (t instanceof Exception) {
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
}
