package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.VerifyNumberActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.service.SmsBroadcastReceiver;
import com.bihanitech.shikshyaprasasak.service.SmsBroadcastReceiverListener;
import com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.SetupPasswordActivity.SetupPasswordActivity;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity.VerifyOtpActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyNumberActivity extends AppCompatActivity implements VerifyNumberView {

    private static final int VERIFYING_OTP = 1;
    private static final int RESENDING_OTP = 0;
    private static final String TAG = VerifyOtpActivity.class.getSimpleName();
    VerifyNumberPresenter presenter;
    SharedPrefsHelper sharedPrefsHelper;
    FragmentManager fm;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.etOtpCode)
    EditText etOtpCode;
    @BindView(R.id.btResend)
    Button btResend;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.productOf)
    TextView productOf;
    @BindView(R.id.tvDetails)
    TextView tvDetails;
    @BindView(R.id.tvContactUs)
    TextView tvContactUs;
    SmsBroadcastReceiver smsBroadcastReceiver;
    Boolean isFirstOtp = true;
    ProgressDFragment progressDFragment;
    NumberInvalidDFragment numberInvalidDFragment;
    private CountDownTimer countDownTimer;
    private int currentTask = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_v2);
        ButterKnife.bind(this);

        presenter = new VerifyNumberPresenter(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        productOf.setText(Html.fromHtml(getString(R.string.product_of)));

        tvDetails.setText(getString(R.string.otp_was_send_to, sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, "")));
        tvName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        String imageUrl = sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, "");
        showSchoolLogo(imageUrl);
        fm = getSupportFragmentManager();

//        if (sharedPrefsHelper.getValue(Constant.OLD_LANGUAGE_IS_NEPALI, false)) {
//            setLocale("ne");
//        }

        fm = getSupportFragmentManager();
        startSmsUserConsent();

        setUpFCM();

        tvTimer.setVisibility(View.INVISIBLE);
        listenForNumbers();
        setUpTime();


    }

    private void showSchoolLogo(String imageUrl) {


        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivLogo);
    }

    @OnClick(R.id.btResend)
    public void onViewClicked() {
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
                etOtpCode.setText(matcher.group(0));
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


    private void listenForNumbers() {
        etOtpCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etOtpCode.getText().toString().length() == 6) {
                    sendtoVerify();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void sendtoVerify() {
        currentTask = VERIFYING_OTP;
        progressDFragment = ProgressDFragment.newInstance(getString(R.string.verifying_code));
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Verify").addToBackStack(null);
        ft.commitAllowingStateLoss();
//        Log.v("FCM", sharedPrefsHelper.getValue(Constant.FCM_ID, ""));
        presenter.verifyOtpCode(etOtpCode.getText().toString(), sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""), sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""),
                sharedPrefsHelper.getValue(Constant.FCM_ID, ""), sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
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

    private void setUpTime() {
        final long lastTime = Long.parseLong(sharedPrefsHelper.getValue(Constant.TIME_RESEND, "0"));
        final long currentTime = System.currentTimeMillis() / 1000;

        if (lastTime != 0) {
            if (lastTime - currentTime > 0) {
                btResend.setTextColor(Color.LTGRAY);
                btResend.setEnabled(false);
                tvTimer.setVisibility(View.VISIBLE);
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

                        tvTimer.setText(time);

                    }

                    @Override
                    public void onFinish() {
                        tvTimer.setVisibility(View.GONE);
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
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, VerifyNumberActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
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

        } else {
            String message = getString(R.string.number_invalid);
            numberInvalidDFragment = NumberInvalidDFragment.newInstance(message, getString(R.string.edit));
            numberInvalidDFragment.show(fm, "NumberInvalidFragment");
        }
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, VerifyNumberActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @OnClick({R.id.tvContactUs, R.id.btResend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvContactUs:
                String phone = "056571259";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;
            case R.id.btResend:

                currentTask = RESENDING_OTP;

                progressDFragment = ProgressDFragment.newInstance(getString(R.string.resending_otp));
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(progressDFragment, "Resend").addToBackStack(null);
                ft.commitAllowingStateLoss();

                presenter.resendOTPCode(sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""),
                        sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""));

                break;
        }
    }


    @Override
    public void resendOTPSent() {
        progressDFragment.dismissAllowingStateLoss();
        long time = (System.currentTimeMillis() / (1000)) + 600;
        sharedPrefsHelper.saveValue(Constant.TIME_RESEND, time + "");
        setUpTime();
    }

    @Override
    public void setUpPassword(String schoolId, String phoneNumber, String schoolName) {

        sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING, false);
        sharedPrefsHelper.saveValue(Constant.PASSWORD_SET_WAITING, true);
        Intent i = new Intent(this, SetupPasswordActivity.class);
        sharedPrefsHelper.saveValue(Constant.SCHOOL_ID, schoolId);
        sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, phoneNumber);
        sharedPrefsHelper.saveValue(Constant.SCHOOL_NAME, schoolName);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);
        finish();
    }

    @Override
    public void retry() {
        sendtoVerify();
    }
}
