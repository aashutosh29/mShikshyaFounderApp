package com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity.RegistrationActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActivity extends AppCompatActivity implements VerifyView{

    @BindView(R.id.tvLabel2)
    TextView tvLabel2;

    @BindView(R.id.btResend)
    Button btResend;

    @BindView(R.id.tvOtp)
    EditText etOtp;

    @BindView(R.id.tvLabel1)
    TextView tvLabel1;

    @BindView(R.id.tvTime)
    TextView tvTime;

    FragmentManager fm ;
    ProgressDFragment progressDFragment;

    Handler handler;
    Runnable runnable;

    VerifyPresenter presenter;
    SharedPrefsHelper sharedPrefsHelper;
    NumberInvalidDFragment numberInvalidDFragment;
    private CountDownTimer countDownTimer;

    private DatabaseHelper databaseHelper;

    private static final String TAG = VerifyOtpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);
        presenter = new VerifyPresenter(this,new MetaDatabaseRepo(getHelper()));
        fm = getSupportFragmentManager();
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        setUpFCM();
        String label1 = "The code was sent to <b>"+sharedPrefsHelper.getValue(Constant.PHONE_NUMBER,"") +"</b> it may take few minutes.";
        tvLabel2.setText(Html.fromHtml(label1));
        tvTime.setVisibility(View.INVISIBLE);
        listenForNumbers();

        setUpTime();

    }

    private void setUpTime() {
        final long lastTime = Long.parseLong(sharedPrefsHelper.getValue(Constant.TIME_RESEND,"0"));
        final long currentTime = System.currentTimeMillis() / 1000;

        if(lastTime != 0) {
            if (lastTime - currentTime > 0) {
                btResend.setTextColor(Color.LTGRAY);
                btResend.setEnabled(false);
                tvTime.setVisibility(View.VISIBLE);
                final String TAG = "timeer";
                Log.v(TAG, "lastTime " + lastTime);
                Log.v(TAG, "currentTime " + currentTime);

                countDownTimer = new CountDownTimer((lastTime-currentTime)*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long newTime = lastTime - System.currentTimeMillis() / 1000;
                        long second = (newTime / 1) % 60;
                        long minute = (newTime / (1 * 60)) % 60;
                        Log.v(TAG,"second  "+second);
                        Log.v(TAG,"minute  "+minute);

                        String time = String.format("%02d:%02d", minute, second);

                        tvTime.setText(time);

                    }

                    @Override
                    public void onFinish() {
                        tvTime.setVisibility(View.GONE);
                        sharedPrefsHelper.saveValue(Constant.TIME_RESEND,"0");
                        btResend.setTextColor(Color.WHITE);
                        btResend.setEnabled(true);
                    }
                };

                countDownTimer.start();

            }else{
                sharedPrefsHelper.saveValue(Constant.TIME_RESEND,"0");
                btResend.setTextColor(Color.WHITE);
                btResend.setEnabled(true);
            }

        }else{
            btResend.setTextColor(Color.WHITE);
            btResend.setEnabled(true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if(databaseHelper != null){
            OpenHelperManager.releaseHelper();
        }

        databaseHelper = null;
    }

    private void listenForNumbers() {
        etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(etOtp.getText().toString().length()==6){
                    sendtoVerify();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.btEdit)
    public void editClicked(){
        sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING,false);

        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }

    public void sendtoVerify(){
        progressDFragment  = ProgressDFragment.newInstance("Verifying Code");
        progressDFragment.show(fm,"Verify");
        presenter.verifyOtpCode(etOtp.getText().toString(),sharedPrefsHelper.getValue(Constant.PHONE_NUMBER,""),sharedPrefsHelper.getValue(Constant.SCHOOL_ID,""),
                sharedPrefsHelper.getValue(Constant.FCM_ID,""),sharedPrefsHelper.getValue(Constant.SCHOOL_NAME,""));
    }

    @Override
    public void verified(boolean flag) {
        progressDFragment.dismiss();

        if(flag){
            FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID,"UNKNOWN")+"_"+"teacher");
            FirebaseMessaging.getInstance().subscribeToTopic("SHIKSHYA_TEACHER");
//            List<ClassSubject> classSubjects = presenter.getClassInfo();

//            for (ClassSubject classSubject : classSubjects) {
//                FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID,"UNKNOWN")+"_"+
//                        classSubject.getClassId()+"_"+classSubject.getSectionId());
//
//            }

            Log.d(TAG, "Subscribed to news topic");

            sharedPrefsHelper.saveValue(Constant.LOGGED_IN,true);
            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }else{
            String message = "The code provided is <b>invalid</b>. Please try again.";
            numberInvalidDFragment = NumberInvalidDFragment.newInstance(message);
            numberInvalidDFragment.show(fm,"NumberInvalidFragment");
        }
    }

    @OnClick(R.id.btResend)
    public void btResendClicked(){
        long time= (System.currentTimeMillis() / (1000) )+6000 ;
        sharedPrefsHelper.saveValue(Constant.TIME_RESEND,time+"");
        // resend is clicked but the resend task is not performed.
        Log.v(TAG,"Restend not working");
        setUpTime();
    }

    @Override
    public void retry() {
        sendtoVerify();
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR,VerifyOtpActivity.class.getSimpleName());
        networkErrorDFragment.show(fm,"NetworkError");

    }

    @Override
    public void showServerError() {
        progressDFragment.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR,VerifyOtpActivity.class.getSimpleName());
        networkErrorDFragment.show(fm,"NetworkError");

    }

   /* @Override
    public void saveStudentDetail(String regId, String studentName, String stClass, String examId, String examName) {
        sharedPrefsHelper.saveValue(Constant.REGNO,regId);
        sharedPrefsHelper.saveValue(Constant.STNAME,studentName);
        sharedPrefsHelper.saveValue(Constant.ST_CLASS,stClass);
        sharedPrefsHelper.saveValue(Constant.EXAM_ID,examId);
        sharedPrefsHelper.saveValue(Constant.EXAM_NAME,examName);
    }*/

    @Override
    public void addCurrentStudentId(String regNo) {
        sharedPrefsHelper.saveValue(Constant.CURRENT_STUDENT,regNo);
    }



    @Override
    public void saveTeacherDetail(String teacherId, String teacherName, String mobileNo,String token) {
        sharedPrefsHelper.saveValue(Constant.TEACHER_ID,teacherId);
        sharedPrefsHelper.saveValue(Constant.TEACHER_NAME,teacherName);
        sharedPrefsHelper.saveValue(Constant.MOBILE_NO,mobileNo);
        sharedPrefsHelper.saveValue(Constant.TOKEN,token);
    }

    @Override
    public void saveRecentNotices(List<NoticeItem> noticeItems) {
        sharedPrefsHelper.saveValue(Constant.NOTICE_COUNT,noticeItems.size());

        if(noticeItems.size()>0){
            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE1,noticeItems.get(0).getTitle());
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL1,noticeItems.get(0).getDetail());
            sharedPrefsHelper.saveValue(Constant.P_DATE1,noticeItems.get(0).getpDate());
        }

        if(noticeItems.size()>1){
            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE2,noticeItems.get(1).getTitle());
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL2,noticeItems.get(1).getDetail());
            sharedPrefsHelper.saveValue(Constant.P_DATE2,noticeItems.get(1).getpDate());

        }
    }

    private DatabaseHelper getHelper(){
        if(databaseHelper == null){
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }

        return databaseHelper;
    }


    private void setUpFCM(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        sharedPrefsHelper.saveValue(Constant.FCM_ID,task.getResult().getToken());
                        Log.v("Firebase token",task.getResult().getToken());
// Get the Instance ID token//
                        String token = task.getResult().getToken();


                    }
                });
    }
}
