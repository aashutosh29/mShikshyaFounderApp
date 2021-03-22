package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.RegisterActivity;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.VerifyNumberActivity.VerifyNumberActivity;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberConfirmDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RegisterPresenter presenter;
    SharedPrefsHelper sharedPrefsHelper;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;

    @BindView(R.id.productOf)
    TextView productOf;
    String schoolId;
    FragmentManager fm;
    @BindView(R.id.tvDetails)
    TextView tvDetails;
    @BindView(R.id.btRegister)
    Button btRegister;
    ProgressDFragment progressDFragment;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_v2);
        ButterKnife.bind(this);
        Intent intent = getIntent();
//        intent.getStringExtra("Action");
        if (intent.getStringExtra("Action").equalsIgnoreCase("reset")) {
            btRegister.setText("Reset Password");
        }
        presenter = new RegisterPresenter(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        productOf.setText(Html.fromHtml(getString(R.string.product_of)));
        tvName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        String imageUrl = sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, "");
        showSchoolLogo(imageUrl);
        setUpTime();
        schoolId = sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "");
        fm = getSupportFragmentManager();
        if (!sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, "").equalsIgnoreCase("")) {
            etPhoneNumber.setText(sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""));
        }
        listenForNumberET();
    }

    @OnClick({R.id.btRegister, R.id.tvContactUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btRegister:
                if (etPhoneNumber.getText().toString().length() == 10) {
                    etPhoneNumber.setBackgroundResource(R.drawable.custom_edittext_background);
                    etPhoneNumber.setError(null);
                    confirmNumber();
                } else if (etPhoneNumber.getText().toString().length() < 10) {
                    etPhoneNumber.setBackgroundResource(R.drawable.custom_edittext_background_error);
                    etPhoneNumber.setError(getString(R.string.number_too_short_for_nepal));
                } else {
                    etPhoneNumber.setBackgroundResource(R.drawable.custom_edittext_background_error);
                    etPhoneNumber.setError(getString(R.string.number_too_long_for_nepal));
                }
                break;
            case R.id.tvContactUs:
                //FragmentManager fm = getSupportFragmentManager();
                //ContactDFragment contactDFragment = new ContactDFragment();
                //contactDFragment.show(fm, "contact_us");
                String phone = "056571259";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;
        }
    }

    private void showSchoolLogo(String imageUrl) {


        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivLogo);
    }


    private void listenForNumberET() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etPhoneNumber.getText().toString().length() >= 1) {
                    etPhoneNumber.setBackgroundResource(R.drawable.custom_edittext_background);
                    etPhoneNumber.setError(null);
                } else {
                    etPhoneNumber.setBackgroundResource(R.drawable.custom_edittext_background_error);
                    etPhoneNumber.setError("phone number can't be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void confirmNumber() {
        if ((sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, "").equalsIgnoreCase(etPhoneNumber.getText().toString())) && (!sharedPrefsHelper.getValue(Constant.TIME_RESEND, "").equalsIgnoreCase("0"))) {
            sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, etPhoneNumber.getText().toString());
            sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING, true);
            sendToVerifyOTP(false);

        } else {
            FragmentManager fm = getSupportFragmentManager();
            NumberConfirmDFragment numberConfirmDFragment = NumberConfirmDFragment.newInstance("(+977) " + etPhoneNumber.getText().toString());
            numberConfirmDFragment.show(fm, "number_confirm_fragment");
        }

    }


    @Override
    public void authenticateNumber() {
        showProgressBar();
        presenter.authenticateNumber(etPhoneNumber.getText().toString(), schoolId);

    }


    @Override
    public void authenticated(boolean authorized) {
        if (authorized) {
            progressDFragment.dismissAllowingStateLoss();
            sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER, etPhoneNumber.getText().toString());
            sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING, true);
            sendToVerifyOTP(true);
        } else {
            progressDFragment.dismissAllowingStateLoss();
            showInvalidDialog(getString(R.string.invalid_no_provided));
        }
    }


    private void sendToVerifyOTP(Boolean restTimer) {

        if (restTimer) {
            long time = (System.currentTimeMillis() / (1000)) + 600;
            sharedPrefsHelper.saveValue(Constant.TIME_RESEND, time + "");
        }

        Intent i = new Intent(this, VerifyNumberActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    public void showProgressBar() {
        progressDFragment = ProgressDFragment.newInstance(getString(R.string.authenticating_your_number));
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Authenticate_Number").addToBackStack(null);
        ft.commitAllowingStateLoss();
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


    private void showInvalidDialog(String message) {
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance(message, getString(R.string.edit));
        numberInvalidDFragment.show(fm, "invalid_fragment");
    }

    @Override
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, RegisterActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, RegisterActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void retry() {
        authenticateNumber();
    }

}
