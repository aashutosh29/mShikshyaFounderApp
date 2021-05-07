package com.bihanitech.shikshyaprasasak.remote;


import com.bihanitech.shikshyaprasasak.model.ClassDueReport;
import com.bihanitech.shikshyaprasasak.model.ClassSectionResponse;
import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.IncomeSummaryList;
import com.bihanitech.shikshyaprasasak.model.MetaSchool;
import com.bihanitech.shikshyaprasasak.model.NoticeResponse;
import com.bihanitech.shikshyaprasasak.model.StudentAttendance;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;
import com.bihanitech.shikshyaprasasak.model.StudentInformation;
import com.bihanitech.shikshyaprasasak.model.TitleWise;
import com.bihanitech.shikshyaprasasak.model.UploadResponse;
import com.bihanitech.shikshyaprasasak.model.account.AccountResponse;
import com.bihanitech.shikshyaprasasak.model.acedamics.AcademicResponse;
import com.bihanitech.shikshyaprasasak.model.examResult.ExamResponse;
import com.bihanitech.shikshyaprasasak.model.holiday.HolidayResponse;
import com.bihanitech.shikshyaprasasak.model.responseModel.HolidayEventResponse;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.model.student.StudentResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("v2/v2.1/auth/login")
    Call<ResponseBody> getLogin(@Query("email") String emailId, @Query("password") String loginPassword, @Query("rememberme") int rememberMe);

    @POST("makeParentUser")
    Call<ResponseBody> setPassword(@Query("con_password") String con_password, @Query("parentMobile") String parentMobile, @Query("parent_numb") String parent_numb, @Query("password") String password, @Query("schoolid") String schoolid);

    @GET("emptotalgenderStudent")
    Observable<List<EmployeeGenderWise>> getTotalGenderEmployee(@Header("Authorization") String authToken);

    @GET("emptotalgenderStudent")
    Call<ResponseBody> getNewTotalGenderEmployee(@Header("Authorization") String authToken);

    @GET("totalgenderStudent")
    Observable<List<StudentGenderWise>> getTotalGenderWiseStudent(@Header("Authorization") String authToken);

    @GET("getClassSection")
    Observable<ClassSectionResponse> getClassSection(@Header("Authorization") String authToken);

    @GET("publicNotice")
    Observable<NoticeResponse> getNoticesData(@Header("Authorization") String authToken);

    @GET("student")
    Observable<StudentResponse> getStudentFiltered(@Header("Authorization") String authToken, @Query("class") String studentClass, @Query("section") String studentSection);

    @GET("feeLedger/{regNo}")
    Observable<AccountResponse> getAccountDetails(@Path("regNo") String regNo, @Header("Authorization") String authToken);

    @GET("student/{regNo}")
    Observable<StudentInformation> getStudentInfo(@Path("regNo") String regNo, @Header("Authorization") String authToken);

    @GET("v2/resultRoutine")
    Observable<ExamResponse> getExamResult(@Header("Authorization") String authToken, @Query("class") String StudentClass, @Query("regno") String regNo);

    @GET("holiday")
    Observable<HolidayResponse> getUpComingHoliday(@Header("Authorization") String authToken);

    @GET("v2/getSchoolAttendance")
    Observable<StudentAttendance> getStudentAttendance(@Header("Authorization") String authToken, @Query("date") String date);

    @GET("classDueReport")
    Observable<List<ClassDueReport>> getClassDueReport(@Header("Authorization") String authToken);

    @POST("publicNotice")
    Observable<UploadResponse> sendNoticeToServer(@Header("Authorization") String authToken, @Query("title") String title, @Query("content") String content, @Query("type") int type, @Query("section") String section, @Query("class") String class_, @Query("regnos[]") String[] regno);


    @POST("publicNotice")
    Observable<UploadResponse> sendNoticeToServerWithOutRegNo(@Header("Authorization") String authToken, @Query("title") String title, @Query("content") String content, @Query("type") int type, @Query("section") String section, @Query("class") String class_);


    @GET("v2/getSchoolResult")
    Observable<AcademicResponse> getAcademicBarData(@Header("Authorization") String authToken, @Query("exam_id") String examId);

    @GET("titleWise")
    Observable<List<TitleWise>> getTitleWiseData(@Header("Authorization") String authToken, @Query("from") String fromDate, @Query("to") String toDate);

    @GET("v2/getAllStudent")
    Observable<StudentResponse> getStudentTotal(@Header("Authorization") String authToken);

    @GET("getList")
    Observable<List<IncomeSummaryList>> fetchFilteredIncomeSummaryList(String s, String fromDate, String toDate, int page);




   /* @GET("publicNotice")
    Call<ResponseBody> getNoticeList(@Header("Authorization") String AuthToken);*/

}
