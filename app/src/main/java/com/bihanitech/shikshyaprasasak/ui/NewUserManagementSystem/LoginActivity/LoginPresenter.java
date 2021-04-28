package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.LoginActivity;

import android.util.Log;

import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.Section;
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


    public void login(/*String schoolId,*/ String email, int rememberMe, String schoolName, String password/*, String FCM, String deviceID*/) {

//        if (cdsService == null)
        cdsService = ApiUtils.getDummyCDSService();

        view.showLoading();

        cdsService.getLogin(email, password, rememberMe/*, "true", password, FCM, deviceID*/).enqueue(new Callback<ResponseBody>() {
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
                                String result = post.getString(Constant.RESULT);
                                if (result.equalsIgnoreCase(Constant.SUCCESS)) {
                                    String message = post.getString(Constant.MESSAGE);
                                    JSONObject newMessage = new JSONObject(message);

                                    String token = newMessage.getString(Constant.TOKEN);
                                    String schoolID = newMessage.getString(Constant.SCHOOL_ID);
                                    String logoImage = newMessage.getString(Constant.SCHOOL_LOGO);
                                    String schoolName = newMessage.getString(Constant.SCHOOL_NAME);
                                    String lastSyncDate = newMessage.getString(Constant.LAST_DATA_SYNC);
                                    String userName = newMessage.getString(Constant.USER_NAME);

                                    JSONArray classesArray = newMessage.getJSONArray(Constant.CLASSES);
                                    JSONArray examNameArray = newMessage.getJSONArray(Constant.EXAM_NAME);
                                    JSONArray sectionArray = newMessage.getJSONArray(Constant.SECTION_NAME);


                                    List<Classes> classes = new ArrayList<>();
                                    List<ExamName> examNames = new ArrayList<>();
                                    List<Section> sectionList = new ArrayList<>();


                                    for (int i = 0; i < classesArray.length(); i++) {
                                        Classes classes1 = new Classes(classesArray.getJSONObject(i).getString(Constant.CLASS_ID), classesArray.getJSONObject(i).getString(Constant.CLASS));
                                        classes.add(classes1);
                                    }
                                    metaDatabaseRepo.addClasses(classes);
                                    for (int i = 0; i < examNameArray.length(); i++) {
                                        ExamName examName = new ExamName(examNameArray.getJSONObject(i).getString(Constant.EXAM_ID), examNameArray.getJSONObject(i).getString(Constant.EXAM_NAME));
                                        examNames.add(examName);
                                    }
                                    metaDatabaseRepo.addExamName(examNames);

                                    for (int i = 0; i < sectionArray.length(); i++) {
                                        Section section = new Section(sectionArray.getJSONObject(i).getString(Constant.CLASS), sectionArray.getJSONObject(i).getString(Constant.CLASS_ID));
                                        sectionList.add(section);
                                    }
                                    metaDatabaseRepo.addSection(sectionList);


                                    view.savePrimaryData(schoolID, logoImage, schoolName, lastSyncDate, userName, token.trim());
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
