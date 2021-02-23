package com.bihanitech.shikshyaprasasak.remote;


import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.model.responseModel.HolidayEventResponse;
import com.bihanitech.shikshyaprasasak.model.responseModel.NoticeResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("lognotice")
    Observable<NoticeResponse> getNoticeList(@Header("Authorization") String authToken, @Query("page") int page);

}
