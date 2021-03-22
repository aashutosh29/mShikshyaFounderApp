package com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.SetupPasswordActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.LoginActivity.LoginActivity;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupPasswordActivity extends AppCompatActivity implements SetupPasswordView {

    SetupPasswordPresenter presenter;
    SharedPrefsHelper sharedPrefsHelper;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.productOf)
    TextView productOf;
    Boolean validInputs = true;
    @BindView(R.id.btConfirm)
    Button btConfirm;
    ProgressDFragment progressDFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password_v2);
        ButterKnife.bind(this);
        presenter = new SetupPasswordPresenter(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        fm = getSupportFragmentManager();

        productOf.setText(Html.fromHtml(getString(R.string.product_of)));
        tvName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        String imageUrl = sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, "");
        btConfirm.setEnabled(false);
        btConfirm.setTextColor(Color.LTGRAY);
        showSchoolLogo(imageUrl);
        InputListener(etConfirmPassword);
        InputListener(etNewPassword);

    }

    private void InputListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().length() == 0) {
                    editText.setBackgroundResource(R.drawable.custom_edittext_background_error);
                    editText.setError("This input Field Can't be empty");
                } else {
                    editText.setBackgroundResource(R.drawable.custom_edittext_background);
                    editText.setError(null);
                }
                if (editText.getText().toString().length() >= 6) {
                    if (etNewPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
                        btConfirm.setEnabled(true);
                        btConfirm.setTextColor(Color.WHITE);
                    } else {
                        btConfirm.setEnabled(false);
                        btConfirm.setTextColor(Color.LTGRAY);
                    }
                } else {
                    btConfirm.setEnabled(false);
                    btConfirm.setTextColor(Color.LTGRAY);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btConfirm)
    public void onViewClicked() {
        validateInputs();
        if (validInputs) {
            presenter.setPassword(etNewPassword.getText().toString(), sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""), sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""));
        }
    }

    private void validateInputs() {
        if (etNewPassword.getText().toString().length() < 6) {
            validInputs = false;
            etNewPassword.setError("Password should contains at list 6 characters ");
            etNewPassword.setBackgroundResource(R.drawable.custom_edittext_background_error);
        }
        if (etConfirmPassword.getText().toString().length() < 6) {
            validInputs = false;
            etConfirmPassword.setError("Password should contains at list 6 characters ");
            etConfirmPassword.setBackgroundResource(R.drawable.custom_edittext_background_error);

        }
        if (!etNewPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
            validInputs = false;
            etConfirmPassword.setError("Confirm password does't match to Password");
            etConfirmPassword.setBackgroundResource(R.drawable.custom_edittext_background_error);

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

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, SetupPasswordActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, SetupPasswordActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showProgressBar() {
        progressDFragment = ProgressDFragment.newInstance("Creating a new Password");
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Authenticate_Number").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void sendTOLogin() {
        progressDFragment.dismissAllowingStateLoss();
        Intent i = new Intent(this, LoginActivity.class);
        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        //FragmentManager fm = getSupportFragmentManager();
        //NumberConfirmDFragment numberConfirmDFragment = NumberConfirmDFragment.newInstance("login");
        //numberConfirmDFragment.show(fm, "invalid_fragment");

    }

    @Override
    public void invalidUser() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance("Your Number is not registered please contact to Your School", getString(R.string.ok));
        numberInvalidDFragment.show(fm, "invalid_fragment");

    }

    @Override
    public void gotoLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void retry() {
        validateInputs();
        if (validInputs) {
            presenter.setPassword(etNewPassword.getText().toString(), sharedPrefsHelper.getValue(Constant.PHONE_NUMBER, ""), sharedPrefsHelper.getValue(Constant.SCHOOL_ID, ""));
        }
    }
}
