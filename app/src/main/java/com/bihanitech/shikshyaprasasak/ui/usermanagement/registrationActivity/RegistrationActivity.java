package com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.SliderAdapter;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberConfirmDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.BaseActivity;
import com.bihanitech.shikshyaprasasak.ui.schoolSelection.SchoolSelection;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity.VerifyOtpActivity;
import com.bihanitech.shikshyaprasasak.ui.webViewAcitivity.WebViewActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistrationActivity extends BaseActivity implements RegistrationView {

    @BindView(R.id.mbsCountry)
    MaterialBetterSpinner mbsCountry;

    @BindView(R.id.tvPhone)
    AppCompatEditText tvPhone;

    @BindView(R.id.tvCountryCode)
    TextView tvCountryCode;

    @BindView(R.id.btSignin)
    Button btSignin;

    @BindView(R.id.pv2Slider)
    ViewPager2 pv2Slider;
    @BindView(R.id.cvSlider)
    CardView cvSlider;

    @BindView(R.id.clCardSlider)
    ConstraintLayout clCardSlider;

    Timer timer;
    String schoolId;
    RegistrationPresenter presenter;
    ProgressDFragment progressDFragment;
    FragmentManager fm;
    SharedPrefsHelper sharedPrefsHelper;
    SliderAdapter sliderAdapter;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        presenter = new RegistrationPresenter(this);
        timer = new Timer();
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        setUpTime();
        schoolId = sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "");
        presenter.fetchSliderList(schoolId);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Nepal"});
        mbsCountry.setAdapter(adapter);
        mbsCountry.setText("Nepal");
        btSignin.setTextColor(Color.GRAY);
        fm = getSupportFragmentManager();
        if (!sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, "").equalsIgnoreCase("")) {
            tvPhone.setText(sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""));
            btSignin.setEnabled(true);
            btSignin.setTextColor(Color.WHITE);
        }

        listenForNumberET();
    }

    private void listenForNumberET() {
        tvPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tvPhone.getText().toString().length() >= 1) {
                    btSignin.setEnabled(true);
                    btSignin.setTextColor(Color.WHITE);
                } else {
                    btSignin.setEnabled(false);
                    btSignin.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    @OnClick(R.id.btSignin)
    public void btSigninClicked() {
        if (tvPhone.getText().toString().length() == 10) {
            confirmNumber();
        } else if (tvPhone.getText().toString().length() < 10) {
            showNumberTooShort();
        } else {
            showNumberTooLong();

        }
    }

    @Override
    public void showProgressBar() {
        progressDFragment = ProgressDFragment.newInstance(getString(R.string.authenticating_your_number));
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Authenticate_Number").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    private void showNumberTooShort() {
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance(getString(R.string.number_too_short_for_nepal), getString(R.string.ok));
        numberInvalidDFragment.show(fm, "invalid_fragment");
    }

    private void showNumberTooLong() {
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance(getString(R.string.number_too_long_for_nepal), getString(R.string.ok));
        numberInvalidDFragment.show(fm, "invalid_fragment");

    }

    private void confirmNumber() {
        if ((sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, "").equalsIgnoreCase(tvPhone.getText().toString())) && (!sharedPrefsHelper.getValue(Constant.TIME_RESEND, "").equalsIgnoreCase("0"))) {
            sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, tvPhone.getText().toString());
            sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING, true);
//            sendToVerifyOTP(true);

//            final Dialog dialog = new Dialog(this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//            dialog.setContentView(R.layout.dialog_otp_already_sent);
//            dialog.setCancelable(true);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//
//            dialog.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {

            sendToVerifyOTP(false);
////                    Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            });
//
//            dialog.show();
//            dialog.getWindow().setAttributes(lp);


        } else {
            FragmentManager fm = getSupportFragmentManager();
            NumberConfirmDFragment numberConfirmDFragment = NumberConfirmDFragment.newInstance("(" + tvCountryCode.getText().toString() + ") " + tvPhone.getText().toString());
            numberConfirmDFragment.show(fm, "number_confirm_fragment");
        }

    }

    @Override
    public void authenticated(boolean authorized) {
        if (authorized) {
            progressDFragment.dismissAllowingStateLoss();
            sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, tvPhone.getText().toString());
            sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING, true);
            sendToVerifyOTP(true);
        } else {
            progressDFragment.dismissAllowingStateLoss();
            showInvalidDialog(getString(R.string.invalid_no_provided));
        }
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, RegistrationActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, RegistrationActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void retry() {
        authenticateNumber();
    }

    private void sendToVerifyOTP(Boolean restTimer) {
        if (timer != null) {
            timer.cancel();
        }
        if (restTimer) {
            long time = (System.currentTimeMillis() / (1000)) + 600;
            sharedPrefsHelper.saveValue(Constant.TIME_RESEND, time + "");
        }

        Intent i = new Intent(this, VerifyOtpActivity.class);
        startActivity(i);
        finish();
    }

    private void showInvalidDialog(String message) {
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance(message, getString(R.string.edit));
        numberInvalidDFragment.show(fm, "invalid_fragment");

    }

    @Override
    public void showToast(String res) {
        Toast.makeText(this, "res :" + res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateSliderList(List<EventSlider> response) {
        if (response.size() >= 1) {

            cvSlider.setVisibility(View.VISIBLE);
            double cvHight = Resources.getSystem().getDisplayMetrics().widthPixels / 2.5;
            clCardSlider.setMaxHeight((int) cvHight);
            clCardSlider.setMinHeight((int) cvHight);

            List<EventSlider> eventSliders = response;
            sliderAdapter = new SliderAdapter(eventSliders, this, this);
            pv2Slider.setAdapter(sliderAdapter);

            slideTheSlider();

        } else {
            cvSlider.setVisibility(View.VISIBLE);

            double cvHight = Resources.getSystem().getDisplayMetrics().widthPixels / 2.5;
            clCardSlider.setMaxHeight((int) cvHight);
            clCardSlider.setMinHeight((int) cvHight);


            List<EventSlider> eventSliders = new ArrayList<>();
            EventSlider es = new EventSlider();
            es.setImage("no responce");
            eventSliders.add(es);

            SliderAdapter sliderAdapter = new SliderAdapter(eventSliders, this, this);
            pv2Slider.setAdapter(sliderAdapter);
//            new TabLayoutMediator(tlSlider, pv2Slider,
//                    (tab, position) -> tab.setText("")).attach();

        }

    }

    @Override
    public void sliderItemClicked(EventSlider eventSlider) {
        if (eventSlider.getUrl() != null) {
            if (eventSlider.getUrl().length() > 4) {
                if (timer != null) {
                    timer.cancel();
                }
                Intent i = new Intent(this, WebViewActivity.class);
                i.putExtra("url", eventSlider.getUrl());
                startActivity(i);
            }
        }
    }

//    @OnClick(R.id.btForm)
//    public void visitorClicked(){
//        Intent i = new Intent(this, AdmissionEnquiryForm.class);
//        startActivity(i);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (timer != null) {
            timer.cancel();
        }

        Intent i = new Intent(this, SchoolSelection.class);
        startActivity(i);
        finish();
    }

    public void authenticateNumber() {
        showProgressBar();
        presenter.authenticateNumber(tvPhone.getText().toString(), schoolId);
    }

    private void setUpTime() {
        final long lastTime = Long.parseLong(sharedPrefsHelper.getValue(Constant.TIME_RESEND, "0"));
        final long currentTime = System.currentTimeMillis() / 1000;

        if (lastTime != 0) {
            if (lastTime - currentTime > 0) {

                final String TAG = "timeer";
                Log.v(TAG, "lastTime " + lastTime);
                Log.v(TAG, "currentTime " + currentTime);

                countDownTimer = new CountDownTimer((lastTime - currentTime) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long newTime = lastTime - System.currentTimeMillis() / 1000;
                        long second = (newTime / 1) % 60;
                        long minute = (newTime / (2 * 60)) % 60;
                        Log.v(TAG, "second  " + second);
                        Log.v(TAG, "minute  " + minute);

                        String time = String.format("%02d:%02d", minute, second);

                    }

                    @Override
                    public void onFinish() {
                        sharedPrefsHelper.saveValue(Constant.TIME_RESEND, "0");
//                        sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, "");

                    }
                };

                countDownTimer.start();

            } else {
                sharedPrefsHelper.saveValue(Constant.TIME_RESEND, "0");
//                sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, "");

            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpTime();
        timer = new Timer();
//        presenter.fetchSliderList(schoolId);
//        slideTheSlider();

    }

    public void slideTheSlider() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pv2Slider.post(new Runnable() {

                    @Override
                    public void run() {
                        pv2Slider.setCurrentItem((pv2Slider.getCurrentItem() + 1) % sliderAdapter.getItemCount());
                    }
                });
            }
        };
        timer.schedule(timerTask, 6000, 8000);


    }

}
