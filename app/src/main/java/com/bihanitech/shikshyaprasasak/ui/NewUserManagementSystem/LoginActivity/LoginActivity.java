package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.SliderAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.RegisterActivity.RegisterActivity;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.schoolSelection.SchoolSelection;
import com.bihanitech.shikshyaprasasak.ui.webViewAcitivity.WebViewActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginView {

    LoginPresenter presenter;
    SharedPrefsHelper sharedPrefsHelper;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.pv2Slider)
    ViewPager2 pv2Slider;
    @BindView(R.id.cvSlider)
    CardView cvSlider;

    @BindView(R.id.clCardSlider)
    ConstraintLayout clCardSlider;

    String schoolId;
    SliderAdapter sliderAdapter;
    Timer timer;
    Boolean validInputs = true;
    @BindView(R.id.productOf)
    TextView productOf;

    NumberInvalidDFragment numberInvalidDFragment;

    FragmentManager fm;
    ProgressDFragment progressDFragment;
    private DatabaseHelper databaseHelper;

    //https://www.youtube.com/watch?v=xQ2E9ZqI6KA

    public static String getDeviceId(Activity activity) {
        String androidId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId == null) {
            androidId = "0";
        }
        return androidId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_v2);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, new MetaDatabaseRepo(getHelper()));
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        fm = getSupportFragmentManager();
        productOf.setText(Html.fromHtml(getString(R.string.product_of)));

        tvName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        String imageUrl = sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, "");
        showSchoolLogo(imageUrl);
        setUpFCM();

        schoolId = sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "");
        timer = new Timer();
        presenter.fetchSliderList(schoolId);
        textWatcher(etPhoneNumber);
        textWatcher(etPassword);
    }

    @OnClick({R.id.tvForgetPassword, R.id.btLogin, R.id.tvRegister})
    public void onViewClicked(View view) {
        Intent i = new Intent(this, RegisterActivity.class);

        switch (view.getId()) {
            case R.id.tvForgetPassword:
                i.putExtra("Action", "reset");
                startActivity(i);
                break;
            case R.id.btLogin:
                validateInputs();
                if (validInputs) {
                    Log.v("fcm id ", sharedPrefsHelper.getValue(Constant.FCM_ID, ""));
                    presenter.login(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""), etPhoneNumber.getText().toString(), etPassword.getText().toString(), sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""), sharedPrefsHelper.getValue(Constant.FCM_ID, ""), getDeviceId(this));
                }
                break;
            case R.id.tvRegister:
                i.putExtra("Action", "register");
                startActivity(i);
                break;
        }
    }

    private void validateInputs() {
        validInputs = true;
        if (etPassword.getText().toString().length() < 6) {
            validInputs = false;
            etPassword.setBackgroundResource(R.drawable.custom_edittext_background_error);
            etPassword.setError("Password should be at list 6 digits long");
        }
        if (etPhoneNumber.getText().toString().length() != 10) {
            validInputs = false;
            etPhoneNumber.setBackgroundResource(R.drawable.custom_edittext_background_error);
            etPhoneNumber.setError("Please enter a valid Phone Number");
        }

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

            double cvHight = cvSlider.getWidth() / 2.6;

            clCardSlider.setMaxHeight((int) cvHight);
            clCardSlider.setMinHeight((int) cvHight);


            List<EventSlider> eventSliders = new ArrayList<>();
            EventSlider es = new EventSlider();
            es.setImage("no responce");
            eventSliders.add(es);

            SliderAdapter sliderAdapter = new SliderAdapter(eventSliders, this, this);
            pv2Slider.setAdapter(sliderAdapter);
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

    @OnClick(R.id.tvHelp)
    public void btHelpClicked() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.youtube.com/watch?v=xQ2E9ZqI6KA"));
        startActivity(i);
    }

    @Override
    public void retry() {
        validateInputs();
        if (validInputs) {
            presenter.login(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""), etPhoneNumber.getText().toString(), etPassword.getText().toString(), sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""), sharedPrefsHelper.getValue(Constant.FCM_ID, ""), getDeviceId(this));
        }
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, LoginActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showLoading() {
        progressDFragment = ProgressDFragment.newInstance("Verifying user");
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Verify").addToBackStack(null);
        ft.commitAllowingStateLoss();
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

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        if (sliderAdapter != null) {
            slideTheSlider();

        }

    }

    public void textWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().length() == 0) {
                    editText.setBackgroundResource(R.drawable.custom_edittext_background_error);
                    editText.setError("Input can't be empty");

                } else {
                    editText.setBackgroundResource(R.drawable.custom_edittext_background);
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showSchoolLogo(String imageUrl) {


        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivLogo);
    }

    @Override
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, LoginActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    // for handling multiple student's classid and sectionid subscription
    public void subscribeStudentForNotifications(StudentInfo studentInfo) {
        FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "n/a") + "_"
                + studentInfo.getStClassId());
        if (studentInfo.getStSectionId().length() != 0) {
            FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "n/a") + "_"
                    + studentInfo.getStClassId() + "_"
                    + studentInfo.getStSectionId());
        }
    }

    @Override
    public void verified(boolean flag, String message) {
        progressDFragment.dismissAllowingStateLoss();
//        Log.v(TAG, "Subscribed to 0 topic");

        if (flag) {
//            Log.v(TAG, "Subscribed to 1 topic");
            FirebaseMessaging.getInstance().subscribeToTopic(sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "UNKNOWN"));
            FirebaseMessaging.getInstance().subscribeToTopic("SHIKSHYA_PARENT");
            sharedPrefsHelper.saveValue(Constant.LOGGED_IN, true);
            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            numberInvalidDFragment = NumberInvalidDFragment.newInstance(message, getString(R.string.edit));
            numberInvalidDFragment.show(fm, "NumberInvalidFragment");
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }

        databaseHelper = null;
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

        presenter.saveNoticeToDB(noticeItems);


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
}
