package com.bihanitech.shikshyaprasasak.remote;


import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.model.NoticeResponse;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;
import com.bihanitech.shikshyaprasasak.model.responseModel.HolidayEventResponse;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by dilip on 8/28/17.
 */

public interface CDSService {

    @GET("school")
    Call<MetaSchool> getMetaSchoolServer();

    @GET
    Call<ResponseBody> getSchoolLogo(@Url String shcoolLogoUrl);

    @GET("publicNotice")
    Observable<ResponseBody> getPublicNotice(@Header("Authorization") String authToken, @Query("type") String type);

    @GET("auth/login")
    Call<ResponseBody> getAuthenticated(@Query("teacherMobile") String aTrue, @Query("schoolid") String schoolId,
                                        @Query("parent_numb") String phoneNumber);

    @GET("holidayEvents")
    Call<HolidayEventResponse> fetchHolidayEventList(@Header("Authorization") String authToken);

//    @GET("lognotice")
//    Observable<NoticeResponse> getNoticeList(@Header("Authorization") String authToken, @Query("page") int page);

    //for login
    @GET("shikshyanotice")
    Call<List<EventSlider>> getShikshyaNotice(@Query("schoolid") String schoolId);

    @GET("auth/login")
    Call<ResponseBody> getLogin(@Query("email") String emailId, @Query("password") String loginPassword, @Query("rememberme") int rememberMe);

    @POST("makeParentUser")
    Call<ResponseBody> setPassword(@Query("con_password") String con_password, @Query("parentMobile") String parentMobile, @Query("parent_numb") String parent_numb, @Query("password") String password, @Query("schoolid") String schoolid);

    @GET("emptotalgenderStudent")
    Observable<EmployeeGenderWise> getTotalGenderEmployee(@Header("Authorization") String authToken);

    @GET("totalgenderStudent")
    Observable<StudentGenderWise> getTotalGenderWiseStudent(@Header("Authorization") String authToken);


    @GET("publicNotice")
    Observable<NoticeResponse> getNoticesData(@Header("Authorization") String authToken);


}
