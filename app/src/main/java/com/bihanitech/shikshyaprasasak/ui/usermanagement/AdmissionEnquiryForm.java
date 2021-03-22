package com.bihanitech.shikshyaprasasak.ui.usermanagement;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.BaseActivity;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdmissionEnquiryForm extends BaseActivity {

    @BindView(R.id.etName)
    AppCompatEditText etName;


    @BindView(R.id.etContact)
    AppCompatEditText etContact;

    @BindView(R.id.etAddress)
    AppCompatEditText etEmail;

    @BindView(R.id.etRegistrationClass)
    AppCompatEditText etSubject;

    @BindView(R.id.etDOB)
    AppCompatEditText etMessage;

    @BindDrawable(R.drawable.error_edit)
    Drawable errorEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_enquiry_form);
        ButterKnife.bind(this);
        listenForFields();
    }

    @SuppressLint("RestrictedApi")
    private void listenForFields() {

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etName.setSupportBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(),R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        etContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etContact.setSupportBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(),R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etEmail.setSupportBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(),R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etSubject.setSupportBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(),R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etMessage.setSupportBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(),R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @OnClick(R.id.btSubmit)
    public void submitClicked(){
        if(vlaidated()){

        }
    }


    @SuppressLint("RestrictedApi")
    private boolean vlaidated() {
        boolean valid1 = true;
        boolean valid2 = true;
        boolean valid3 = true;
        boolean valid4 = true;
        boolean valid5 = true;

        if(etName.getText().length()<=3){
            valid1 = false;
            etName.setSupportBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
        }
         if(etContact.getText().length()<=3){
                    valid2 = false;
                    etContact.setSupportBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                }
         /*if(etEmail.getText().length()<=3){
                    valid1 = false;
                    etEmail.setSupportBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                }*/
         if(etSubject.getText().length()<=3){
                    valid4 = false;
                    etSubject.setSupportBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                }
         if(etMessage.getText().length()<=3){
                    valid5 = false;
                    etMessage.setSupportBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                }




        return valid1 && valid2 && valid3 && valid4 && valid5;


    }
}
