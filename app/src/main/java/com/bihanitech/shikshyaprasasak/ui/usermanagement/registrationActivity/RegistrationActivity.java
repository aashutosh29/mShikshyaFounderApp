package com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberConfirmDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NumberInvalidDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.schoolSelection.SchoolSelection;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.VisitorFormActivity;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity.VerifyOtpActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView{

    @BindView(R.id.mbsCountry)
    MaterialBetterSpinner mbsCountry;

    @BindView(R.id.tvPhone)
    AppCompatEditText tvPhone;

    @BindView(R.id.tvCountryCode)
    TextView tvCountryCode;

    @BindView(R.id.btSignin)
    Button btSignin;

    String schoolId;

    RegistrationPresenter presenter;
    ProgressDFragment progressDFragment ;
    FragmentManager fm;

    SharedPrefsHelper sharedPrefsHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        presenter = new RegistrationPresenter(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        schoolId = sharedPrefsHelper.getValue(Constant.SCHOOL_ID,"");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new String[]{"Nepal"});
        mbsCountry.setAdapter(adapter);
        mbsCountry.setText("Nepal");
        btSignin.setTextColor(Color.GRAY);
        fm = getSupportFragmentManager();
        progressDFragment = ProgressDFragment.newInstance("Authenticating your number.");
        if(!sharedPrefsHelper.getValue(Constant.PHONE_NUMBER,"").equalsIgnoreCase("")){
            tvPhone.setText(sharedPrefsHelper.getValue(Constant.PHONE_NUMBER,""));
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
                if(tvPhone.getText().toString().length() >= 1){
                    btSignin.setEnabled(true);
                    btSignin.setTextColor(Color.WHITE);
                }else{
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
    public void btSigninClicked(){
        if(tvPhone.getText().toString().length()==10){
            confirmNumber();   
        }else if(tvPhone.getText().toString().length() < 10){
            showNumberTooShort();
        }else{
            showNumberTooLong();

        }
    }

    @Override
    public void showProgressBar() {

        progressDFragment.show(fm,"Authenticate_Number");
    }

    private void showNumberTooShort() {
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance("This phone number is too short for Nepal");
        numberInvalidDFragment.show(fm,"invalid_fragment");
    }

    private void showNumberTooLong(){
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance("This phone number is too long for Nepal");
        numberInvalidDFragment.show(fm,"invalid_fragment");

    }

    private void confirmNumber() {
        FragmentManager fm = getSupportFragmentManager();
        NumberConfirmDFragment numberConfirmDFragment = NumberConfirmDFragment.newInstance("("+tvCountryCode.getText().toString()+") "+tvPhone.getText().toString());
        numberConfirmDFragment.show(fm, "number_confirm_fragment");

    }

    @Override
    public void authenticated(boolean authorized) {
        if(authorized){
            progressDFragment.dismiss();
            sharedPrefsHelper.saveValue(Constant.PHONE_NUMBER,tvPhone.getText().toString());
            sharedPrefsHelper.saveValue(Constant.VERIFY_WAITING,true);
            sendToVerifyOTP(tvPhone.getText().toString());
        }else{
            progressDFragment.dismiss();
            showInvalidDialog("Number you provided is invalid. Please contact School administration if it continues.");
        }
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR,RegistrationActivity.class.getSimpleName());
        networkErrorDFragment.show(fm,"NetworkError");

    }

    @Override
    public void showServerError() {
        progressDFragment.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR,RegistrationActivity.class.getSimpleName());
        networkErrorDFragment.show(fm,"NetworkError");

    }

    @Override
    public void retry() {
        authenticateNumber();
    }

    private void sendToVerifyOTP(String s) {
        long time= (System.currentTimeMillis() / (1000) )+6000 ;
        sharedPrefsHelper.saveValue(Constant.TIME_RESEND,time+"");
        Intent i = new Intent(this, VerifyOtpActivity.class);
        startActivity(i);
        finish();
    }

    private void showInvalidDialog(String message) {
        FragmentManager fm = getSupportFragmentManager();
        NumberInvalidDFragment numberInvalidDFragment = NumberInvalidDFragment.newInstance(message);
        numberInvalidDFragment.show(fm,"invalid_fragment");

    }

    @Override
    public void showToast(String res) {
        Toast.makeText(this,"res :"+res, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btVisitor)
    public void visitorClicked(){
        Intent i = new Intent(this, VisitorFormActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, SchoolSelection.class);
        startActivity(i);
        finish();
    }

    public void authenticateNumber() {
        showProgressBar();
    //    Toast.makeText(this,"Phone :"+tvPhone.getText().toString()+" SID :"+schoolId,Toast.LENGTH_SHORT).show();
        presenter.authenticateNumber(tvPhone.getText().toString(),schoolId);
    }


}
