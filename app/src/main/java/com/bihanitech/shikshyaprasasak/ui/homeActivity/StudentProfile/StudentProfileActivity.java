package com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.AccountAdapter;
import com.bihanitech.shikshyaprasasak.adapter.SubjectListAdapter;
import com.bihanitech.shikshyaprasasak.model.Statements;
import com.bihanitech.shikshyaprasasak.model.StudentInformation;
import com.bihanitech.shikshyaprasasak.model.Subject;
import com.bihanitech.shikshyaprasasak.model.account.Account;
import com.bihanitech.shikshyaprasasak.model.examResult.Result;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.rvStatements)
    RecyclerView rvStatements;

    @BindView(R.id.rvSubjects)
    RecyclerView rvSubjects;

    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.tvGrade)
    TextView tvGrade;

    @BindView(R.id.tvRemarks)
    TextView tvRemarks;

    @BindView(R.id.tvGPA)
    TextView tvGPA;

    @BindView(R.id.tvGrade2ndTerm)
    TextView tvGrade2ndTerm;

    @BindView(R.id.tvRemarks2ndTerm)
    TextView tvRemarks2ndTerm;

    @BindView(R.id.tvGPA2ndTerm)
    TextView tvGPA2ndTerm;

    @BindView(R.id.tvGrade3rdTerm)
    TextView tvGrade3rdTerm;

    @BindView(R.id.tvRemarks3rdTerm)
    TextView tvRemarks3rdTerm;

    @BindView(R.id.tvGPA3rdTerm)
    TextView tvGPA3rdTerm;

    SharedPrefsHelper sharedPrefsHelper;

    String regNo;
    String studentClassID;

    SubjectListAdapter subjectListAdapter;
    AccountAdapter recyclerAdapter;
    ProgressDialog dialog;

    StudentProfilePresenter studentProfilePresenter;

    List<Statements> statementsList = new ArrayList<>();
    List<Subject> subjectList = new ArrayList<>();

    Boolean firstSubjectShowingFirst = true;

    Boolean secondSubjectShowingSecond = true;
    Boolean thirdSubjectShowingThird = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        studentProfilePresenter = new StudentProfilePresenter(this);
        dialog = new ProgressDialog(this);
        getAllIntentAndSetRespectiveView();
        fetchAccountData();
        initDataOfAccount();
        //initRecyclerViewStatements();
        initSubject();
        initSubjectRecyclerView();

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
        regNo = i.getStringExtra(Constant.REGNO);
        studentClassID = i.getStringExtra(Constant.STUDENT_CLASS_ID);


    }

   /* private void initRecyclerViewStatements() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvStatements.setLayoutManager(llm);
        rvStatements.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new AccountAdapter(statementsList, this);
        rvStatements.setAdapter(recyclerAdapter);
    }*/

    private void initSubject() {
        Subject subject = new Subject("Maths", "A");
        subjectList.add(subject);
        subject = new Subject("Science", "A+");
        subjectList.add(subject);
        subject = new Subject("Social", "B+");
        subjectList.add(subject);
    }

    private void initDataOfAccount() {
        Statements statement = new Statements(2, "2021-02-23", "Account paid From Receipt No:", "Rs. 5900.0", "Rs. 2560.0");
        statementsList.add(statement);
        statement = new Statements(1, "2021-02-23", "Opening Balance", "Rs. 3340.0", "Rs. 3340.0");
        statementsList.add(statement);
    }

    private void initSubjectRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSubjects.setLayoutManager(llm);
        rvSubjects.setItemAnimator(new DefaultItemAnimator());
        subjectListAdapter = new SubjectListAdapter(subjectList, this);
        rvSubjects.setAdapter(subjectListAdapter);
    }


    @Override
    public void showLoading() {

        dialog.setMessage("Loading data");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showError(int a) {
        if (a == 2) {
            dialog.dismiss();
        }
        tvNoDataFound.setText("Something went wrong");
        tvNoDataFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void populateStudentInformation(StudentInformation studentInformation) {
        Glide.with(this)
                .load(studentInformation.getStpphoto())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivProfileImage);

    }

    @Override
    public void populateExamResult(List<Result> results) {
        tvGrade.setText(results.get(0).getStddivgrade());
        tvGPA.setText(results.get(0).getStgpa());
        tvRemarks.setText(results.get(0).getStremakrs());

    }

    @Override
    public void populateAccountDetails(List<Account> accountList) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvStatements.setLayoutManager(llm);
        rvStatements.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new AccountAdapter(accountList, this);
        rvStatements.setAdapter(recyclerAdapter);


    }

    private void fetchAccountData() {
        studentProfilePresenter.getAccountDetails(regNo, sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        studentProfilePresenter.getExamDetails(regNo, studentClassID, sharedPrefsHelper.getValue(Constant.TOKEN, ""));
    }
}