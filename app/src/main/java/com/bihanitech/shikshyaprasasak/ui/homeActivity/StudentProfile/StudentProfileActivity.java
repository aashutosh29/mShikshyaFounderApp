package com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.AccountAdapter;
import com.bihanitech.shikshyaprasasak.adapter.ExamAdapter;
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

    @BindView(R.id.rvExamResult)
    RecyclerView rvExamResult;


    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;


    SharedPrefsHelper sharedPrefsHelper;
    Boolean firstSubjectShowingFirst = true;
    String regNo;
    String studentClassID;

    SubjectListAdapter subjectListAdapter;
    AccountAdapter recyclerAdapter;
    ExamAdapter examAdapter;
    ProgressDialog dialog;

    StudentProfilePresenter studentProfilePresenter;

    List<Statements> statementsList = new ArrayList<>();
    List<Subject> subjectList = new ArrayList<>();


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
        initSubject();

    }


    @OnClick(R.id.ivBack)
    void ivBackClicked() {
        onBackPressed();
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
        dialog.dismiss();
        if (a == 1) {

            tvNoDataFound.setText("Something went wrong");
            tvNoDataFound.setVisibility(View.VISIBLE);
        }

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

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvExamResult.setLayoutManager(llm);
        rvExamResult.setItemAnimator(new DefaultItemAnimator());
        examAdapter = new ExamAdapter(results, this);
        rvExamResult.setAdapter(examAdapter);

    }

    @Override
    public void ivDownUpArrowClicked(Result result, ConstraintLayout clSubjects, ImageView ivDownUPArrow, RecyclerView rvSubjects) {
        if (firstSubjectShowingFirst) {
            clSubjects.setVisibility(View.VISIBLE);
            firstSubjectShowingFirst = false;
            ivDownUPArrow.setImageResource(R.drawable.ic_up_arrow);
        } else {
            clSubjects.setVisibility(View.GONE);
            firstSubjectShowingFirst = true;
            ivDownUPArrow.setImageResource(R.drawable.ic_down_arrow);

        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSubjects.setLayoutManager(llm);
        rvSubjects.setItemAnimator(new DefaultItemAnimator());
        subjectListAdapter = new SubjectListAdapter(result.getMarks(), this);
        rvSubjects.setAdapter(subjectListAdapter);

    }

    @Override
    public void populateAccountDetails(List<Account> accountList) {
        tvNoDataFound.setVisibility(View.GONE);
        if (accountList.size() == 0) {
            tvNoDataFound.setText("No data found");
            tvNoDataFound.setVisibility(View.VISIBLE);
        }
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
