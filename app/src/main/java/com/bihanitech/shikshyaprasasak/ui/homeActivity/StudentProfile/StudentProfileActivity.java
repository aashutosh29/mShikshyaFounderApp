package com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentProfileActivity extends AppCompatActivity implements StudentProfileView {

    @BindView(R.id.layoutSubject)
    View layoutSubject;

    @BindView(R.id.layoutSubjectSecond)
    View layoutSubjectSecond;

    @BindView(R.id.layoutSubjectThird)
    View layoutSubjectThird;

    @BindView(R.id.ivDownUPArrow)
    ImageView ivDownUpArrow;

    @BindView(R.id.ivDownUpArrowSecond)
    ImageView ivDownUpArrowSecond;

    @BindView(R.id.ivDownUPArrowThird)
    ImageView ivDownUpArrowThird;

    @BindView(R.id.tvProfileName)
    TextView tvProfileName;

    @BindView(R.id.tvStudentLocation)
    TextView tvStudentLocation;

    @BindView(R.id.tvStudentClass)
    TextView tvStudentClass;

    @BindView(R.id.tvFatherName)
    TextView tvFatherName;

    @BindView(R.id.tvMotherName)
    TextView tvMotherName;

    @BindView(R.id.tvGuardianName)
    TextView tvGuardianName;

    @BindView(R.id.tvContactNumber)
    TextView tvContactNumber;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    Boolean firstSubjectShowingFirst = true;

    Boolean secondSubjectShowingSecond = true;
    Boolean thirdSubjectShowingThird = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        ButterKnife.bind(this);
        getAllIntentAndSetRespectiveView();
    }

    @OnClick(R.id.ivBack)
    void ivBackClicked() {
        onBackPressed();
    }


    @OnClick(R.id.ivDownUPArrow)
    void ivDownUPArrowOnClick() {

          /* ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.clFirstTerm);
        ViewGroup.LayoutParams params = constraintLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            constraintLayout.setLayoutParams(params);*/

        if (firstSubjectShowingFirst) {
            layoutSubject.setVisibility(View.VISIBLE);
            firstSubjectShowingFirst = false;
            ivDownUpArrow.setImageResource(R.drawable.ic_up_arrow);
        } else {
            layoutSubject.setVisibility(View.GONE);
            firstSubjectShowingFirst = true;
            ivDownUpArrow.setImageResource(R.drawable.ic_down_arrow);

        }
    }

    @OnClick(R.id.ivDownUpArrowSecond)
    void setIvDownUpArrowSecondOnClick() {


        if (secondSubjectShowingSecond) {
            layoutSubjectSecond.setVisibility(View.VISIBLE);
            secondSubjectShowingSecond = false;
            ivDownUpArrowSecond.setImageResource(R.drawable.ic_up_arrow);
        } else {
            layoutSubjectSecond.setVisibility(View.GONE);
            secondSubjectShowingSecond = true;
            ivDownUpArrowSecond.setImageResource(R.drawable.ic_down_arrow);

        }

    }

    @OnClick(R.id.ivDownUPArrowThird)
    void setIvDownUpArrowThirdOnClick() {


        if (thirdSubjectShowingThird) {
            layoutSubjectThird.setVisibility(View.VISIBLE);
            thirdSubjectShowingThird = false;
            ivDownUpArrowThird.setImageResource(R.drawable.ic_up_arrow);
        } else {
            layoutSubjectThird.setVisibility(View.GONE);
            thirdSubjectShowingThird = true;
            ivDownUpArrowThird.setImageResource(R.drawable.ic_down_arrow);

        }

    }


    private void getAllIntentAndSetRespectiveView() {
        Intent i = getIntent();
        tvProfileName.setText(i.getStringExtra(Constant.STUDENT_NAME));
        tvStudentClass.setText(i.getStringExtra(Constant.STUDENT_CLASS));
        tvStudentLocation.setText(i.getStringExtra(Constant.STUDENT_ADDRESS));
        tvFatherName.setText(i.getStringExtra(Constant.STUDENT_FATHER_NAME));
        tvMotherName.setText(i.getStringExtra(Constant.STUDENT_MOTHER_NAME));
        tvGuardianName.setText(i.getStringExtra(Constant.STUDENT_GUARDIAN_NAME));
        tvContactNumber.setText(i.getStringExtra(Constant.STUDENT_CONTACT_NUMBER));


    }

}
