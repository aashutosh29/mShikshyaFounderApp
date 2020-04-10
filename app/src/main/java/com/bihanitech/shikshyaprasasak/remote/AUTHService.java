package com.bihanitech.shikshyaprasasak.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AUTHService {

    @POST("auth/generateOTP")
    @FormUrlEncoded
    Call<ResponseBody> validateNumber(@Field("schoolid") String schoolId, @Field("contactNum") String contactNum, @Field("teacherMobile") String isTeacherMobile);


    @POST("auth/verifyOTP")
    @FormUrlEncoded
    Call<ResponseBody> verifyOTP(@Field("schoolid") String shccolId, @Field("otp") String otpNum, @Field("contactNum") String contactNum, @Field("fcmid") String fcmId,
                                 @Field("teacherMobile") String teacherMobile);



}
