package com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.service.SmsBroadcastReceiver;
import com.bihanitech.shikshyaprasasak.service.SmsBroadcastReceiverListener;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.BaseActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity.RegistrationActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActivity extends BaseActivity implements VerifyView {

    private static final int VERIFYING_OTP = 1;
    private static final int RESENDING_OTP = 0;
    private static final int REQ_USER_CONSENT = 200;
    private static final String TAG = VerifyOtpActivity.class.getSimpleName();
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
    ProgressDFragment progressDFragment;
    Handler handler;
    Runnable runnable;
    VerifyPresenter presenter;
    SharedPrefsHelper sharedPrefsHelper;
    NumberInvalidDFragment numberInvalidDFragment;
    FragmentManager fm;
    @BindView(R.id.tvResend)
    TextView tvResend;
    @BindView(R.id.btEdit)
    Button btEdit;
    @BindView(R.id.tvContactsTitle)
    TextView tvContactsTitle;
    @BindView(R.id.call1)
    TextView call1;
    @BindView(R.id.call2)
    TextView call2;
    @BindView(R.id.call3)
    TextView call3;
    SmsBroadcastReceiver smsBroadcastReceiver;
    Boolean isFirstOtp = true;
    private CountDownTimer countDownTimer;
    private DatabaseHelper databaseHelper;
    private int currentTask = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);

        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);

        if (sharedPrefsHelper.getValue(Constant.OLD_LANGUAGE_IS_NEPALI, false)) {
            setLocale("ne");
        }

        presenter = new VerifyPresenter(this, new MetaDatabaseRepo(getHelper()));
        fm = getSupportFragmentManager();
        startSmsUserConsent();

        setUpFCM();
        String label1 = getString(R.string.the_code_was_sent_to) + sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, "") + getString(R.string.it_may_take_few_minutes);
        tvLabel2.setText(Html.fromHtml(label1));
        tvTime.setVisibility(View.INVISIBLE);
        listenForNumbers();
        setUpTime();

    }

    private void setUpTime() {
        final long lastTime = Long.parseLong(sharedPrefsHelper.getValue(Constant.TIME_RESEND, "0"));
        final long currentTime = System.currentTimeMillis() / 1000;

        if (lastTime != 0) {
            if (lastTime - currentTime > 0) {
                btResend.setTextColor(Color.LTGRAY);
                btResend.setEnabled(false);
                tvTime.setVisibility(View.VISIBLE);
                final String TAG = "timeer";
                Log.v(TAG, "lastTime " + lastTime);
                Log.v(TAG, "currentTime " + currentTime);

                countDownTimer = new CountDownTimer((lastTime - currentTime) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long newTime = lastTime - System.currentTimeMillis() / 1000;
                        long second = (newTime / 1) % 60;
                        long minute = (newTime / (1 * 60)) % 60;
                        Log.v(TAG, "second  " + second);
                        Log.v(TAG, "minute  " + minute);

                        String time = String.format("%02d:%02d", minute, second);

                        tvTime.setText(time);

                    }

                    @Override
                    public void onFinish() {
                        tvTime.setVisibility(View.GONE);
                        sharedPrefsHelper.saveValue(Constant.TIME_RESEND, "0");
                        btResend.setTextColor(Color.WHITE);
                        btResend.setEnabled(true);
                    }
                };

                countDownTimer.start();

            } else {
                sharedPrefsHelper.saveValue(Constant.TIME_RESEND, "0");
                btResend.setTextColor(Color.WHITE);
                btResend.setEnabled(true);
            }

        } else {
            btResend.setTextColor(Color.WHITE);
            btResend.setEnabled(true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (databaseHelper != null) {
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
                if (etOtp.getText().toString().length() == 6) {
                    sendtoVerify();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.btEdit)
    public void editClicked() {
        sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING, false);
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }

    public void sendtoVerify() {
        currentTask = VERIFYING_OTP;
        progressDFragment = ProgressDFragment.newInstance(getString(R.string.verifying_code));
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Verify").addToBackStack(null);
        ft.commitAllowingStateLoss();
        Log.v("FCM", sharedPrefsHelper.getValue(Constant.FCM_ID, ""));
        presenter.verifyOtpCode(etOtp.getText().toString(), sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""), sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""),
                sharedPrefsHelper.getValue(Constant.FCM_ID, ""), sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
    }

    @Override
    public void verified(boolean flag) {
        progressDFragment.dismissAllowingStateLoss();
        Log.v(TAG, "Subscribed to 0 topic");

        if (flag) {
            Log.v(TAG, "Subscribed to 1 topic");
            FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "UNKNOWN"));
            FirebaseMessaging.getInstance().subscribeToTopic("SHIKSHYA_PARENT");
            if (sharedPrefsHelper.getValue(Constant.ST_SECTION, "").length() == 0) {
                FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "n/a") + "_"
                        + sharedPrefsHelper.getValue(Constant.ST_CLASS_ID, "n/a"));
            } else {
                FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "n/a") + "_"
                        + sharedPrefsHelper.getValue(Constant.ST_CLASS_ID, "n/a"));

                FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "n/a") + "_"
                        + sharedPrefsHelper.getValue(Constant.ST_CLASS_ID, "n/a") + "_"
                        + sharedPrefsHelper.getValue(Constant.ST_SECTION, "n/a"));
            }
            Log.v(TAG, "Subscribed to news topic");
            Log.v(TAG, "schoold id is topic " + sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "UNKNOWN"));
            Log.v(TAG, "schoold id is topic " + sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "n/a") + "_"
                    + sharedPrefsHelper.getValue(Constant.ST_CLASS_ID, "n/a"));

            sharedPrefsHelper.saveValue(Constant.LOGGED_IN, true);
            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            String message = getString(R.string.number_invalid);
            numberInvalidDFragment = NumberInvalidDFragment.newInstance(message, getString(R.string.edit));
            numberInvalidDFragment.show(fm, "NumberInvalidFragment");
        }
    }

    @OnClick(R.id.btResend)
    public void btResendClicked() {
        currentTask = RESENDING_OTP;

        progressDFragment = ProgressDFragment.newInstance(getString(R.string.resending_otp));
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Resend").addToBackStack(null);
        ft.commitAllowingStateLoss();

        presenter.resendOTPCode(sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""),
                sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""));
    }

    @Override
    public void retry() {
        if (currentTask == VERIFYING_OTP) {
            sendtoVerify();
        } else {
            btResendClicked();
        }
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, VerifyOtpActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, VerifyOtpActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void saveStudentDetail(StudentInfo studentInfo, String token) {
        sharedPrefsHelper.saveValue(Constant.REGNO, studentInfo.getRegNo());
        sharedPrefsHelper.saveValue(Constant.STNAME, studentInfo.getStName());
        sharedPrefsHelper.saveValue(Constant.ST_CLASS, studentInfo.getStClass());
        sharedPrefsHelper.saveValue(Constant.ST_CLASS_ID, studentInfo.getStClassId());
        sharedPrefsHelper.saveValue(Constant.STPPHOTO, studentInfo.getStPhoto());
        if (studentInfo.getStSectionId().length() != 0) {
            sharedPrefsHelper.saveValue(Constant.ST_SECTION, studentInfo.getStSectionId());
            sharedPrefsHelper.saveValue(Constant.ST_SECTION_NAME, studentInfo.getStSectionName());
        } else {
            sharedPrefsHelper.saveValue(Constant.ST_SECTION, "");
        }
        sharedPrefsHelper.saveValue(Constant.TOKEN, token);
    }

    @Override
    public void addCurrentStudentId(String regNo) {
        sharedPrefsHelper.saveValue(Constant.CURRENT_STUDENT, regNo);
    }

    @Override
    public void resendOTPSent() {
        progressDFragment.dismissAllowingStateLoss();
        long time = (System.currentTimeMillis() / (1000)) + 600;
        sharedPrefsHelper.saveValue(Constant.TIME_RESEND, time + "");
        setUpTime();
    }

    @Override
    public void saveRecentNotices(List<NoticeItem> noticeItems) {
        sharedPrefsHelper.saveValue(Constant.NOTICE_COUNT, noticeItems.size());

        if (noticeItems.size() > 0) {
            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE1, noticeItems.get(0).getTitle());
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL1, noticeItems.get(0).getDetail());
            sharedPrefsHelper.saveValue(Constant.P_DATE1, noticeItems.get(0).getpDate());
        } else {

            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE1, "");
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL1, "");
            sharedPrefsHelper.saveValue(Constant.P_DATE1, "");

        }

        if (noticeItems.size() > 1) {
            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE2, noticeItems.get(1).getTitle());
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL2, noticeItems.get(1).getDetail());
            sharedPrefsHelper.saveValue(Constant.P_DATE2, noticeItems.get(1).getpDate());

        } else {
            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE2, "");
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL2, "");
            sharedPrefsHelper.saveValue(Constant.P_DATE2, "");
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    private void setUpFCM() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        sharedPrefsHelper.saveValue(Constant.FCM_ID, task.getResult().getToken());
                        Log.v("Firebase token", task.getResult().getToken());
// Get the Instance ID token//
                        String token = task.getResult().getToken();
                    }
                });
    }

    private void setLocale(String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        tvLabel1.setText(getString(R.string.enter_the_6_digit_code_you_received_via_sms));
        etOtp.setHint(R.string.code);
        btResend.setText(R.string.resend_sms);
        btEdit.setText(R.string.wrong_number);
        tvResend.setText(R.string.didn_t_receive_sms);
        tvContactsTitle.setText((R.string.if_it_takes_too_long_for_otp_please_contact_following_numbers));


    }

    @OnClick({R.id.call1, R.id.call2, R.id.call3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call1:
                phoneCall(call1.getText().toString());
                break;
            case R.id.call2:
                phoneCall(call2.getText().toString());
                break;
            case R.id.call3:
                phoneCall(call3.getText().toString());
                break;
        }
    }

    private void phoneCall(String number) {

        String phone = number;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);

    }


    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);

        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                registerBroadcastReceiver();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void getOtpFromMessage(String message) {
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            if (isFirstOtp) {
                isFirstOtp = false;
                etOtp.setText(matcher.group(0));
            }

        }
    }


    private void registerBroadcastReceiver() {
        SmsBroadcastReceiverListener smsBroadcastReceiverListener = new SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(String message) {
                getOtpFromMessage(message);
            }

            @Override
            public void onFailure() {

            }
        };

        smsBroadcastReceiver = new SmsBroadcastReceiver(smsBroadcastReceiverListener);

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

}