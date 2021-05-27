package com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.SearchAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.SearchProfile;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile.StudentProfileActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements SearchView {

    private static final String TAG = SearchActivity.class.getSimpleName();
    List<SearchProfile> searchProfiles = new ArrayList<>();
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;

    SharedPrefsHelper sharedPrefsHelper;
    @BindView(R.id.spClass)
    Spinner spClass;

    @BindView(R.id.spSection)
    Spinner spSection;

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.ivError)
    ImageView ivError;

    @BindView(R.id.btRefresh)
    Button btRefresh;

    @BindView(R.id.tvErrorTitle)
    TextView tvErrorTitle;

    @BindView(R.id.clEmpty)
    ConstraintLayout clEmpty;

    @BindView(R.id.etSearch)
    EditText etSearch;


    SearchPresenter searchPresenter;
    SearchAdapter recyclerAdapter;
    ProgressDialog dialog;
    String grade = "";
    String section = "";
    private DatabaseHelper databaseHelper;

    @Override
    protected void onStart() {
        super.onStart();
        etSearch.requestFocus();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        searchPresenter = new SearchPresenter(new MetaDatabaseRepo(getHelper()), this);
        searchPresenter.getSpinnerData();
        dialog = new ProgressDialog(this);
        searchPresenter.getStudents(grade, section, sharedPrefsHelper.getValue(Constant.TOKEN, ""));
    }


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    private void filter(String text, List<Student> studentListNew) {
        List<Student> filteredStudentList = new ArrayList<>();

        Log.d(TAG, "filter: " + studentListNew.size());
        for (Student students : studentListNew) {
            if (students.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredStudentList.add(students);
            }
        }
        //here
        if (filteredStudentList.size() == 0) {
            clEmpty.setVisibility(View.VISIBLE);
            tvError.setText("No data found. Please try another name.");
            tvErrorTitle.setVisibility(View.GONE);
            btRefresh.setVisibility(View.INVISIBLE);
            ivError.setImageResource(R.drawable.ic_no_data);
        } else clEmpty.setVisibility(View.GONE);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(llm);
        rvSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new SearchAdapter(filteredStudentList, this, this);
        rvSearch.setAdapter(recyclerAdapter);
    }

    @OnClick(R.id.ivBack)
    void ivBackOnClicked() {
        onBackPressed();
    }


    @Override
    public void getClickedStudentDetails(Student searchProfile) {
        Intent i = new Intent(SearchActivity.this, StudentProfileActivity.class);
        i.putExtra(Constant.STUDENT_NAME, searchProfile.getName());
        i.putExtra(Constant.STUDENT_ADDRESS, searchProfile.getAddress());
        i.putExtra(Constant.STUDENT_CLASS, searchProfile.getClass_().getClassname());
        i.putExtra(Constant.STUDENT_FATHER_NAME, searchProfile.getFathername());
        i.putExtra(Constant.STUDENT_MOTHER_NAME, searchProfile.getMothername());
        i.putExtra(Constant.STUDENT_GUARDIAN_NAME, searchProfile.getGurdainname());
        i.putExtra(Constant.STUDENT_CONTACT_NUMBER, searchProfile.getContact());
        i.putExtra(Constant.STUDENT_CLASS_ID, searchProfile.getClass_().getSid());
        i.putExtra(Constant.REGNO, searchProfile.getRegno());
        startActivity(i);
    }

    @Override
    public void populateClassesAndSectionList(List<Classes> classesList, List<Section> sectionList) {

        List<String> className = new ArrayList<>();
        List<String> sectionName = new ArrayList<>();
        for (int i = 0; i < classesList.size(); i++) {
            className.add(classesList.get(i).getGradeId());
        }
        for (int i = 0; i < sectionList.size(); i++) {
            sectionName.add(sectionList.get(i).getClass_());
        }

        ArrayAdapter<String> arrayAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, className);
        arrayAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClass.setAdapter(arrayAdapterClass);

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                grade = classesList.get(position).getGrade();
                section = sectionList.get(0).getClassID();
                //searchPresenter.getStudents(grade, section, sharedPrefsHelper.getValue(Constant.TOKEN, ""));

                ArrayAdapter<String> arrayAdapterSection = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, sectionName);
                arrayAdapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSection.setAdapter(arrayAdapterSection);
                spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        section = sectionList.get(position).getClassID();
                        //searchPresenter.getStudents(grade, section, sharedPrefsHelper.getValue(Constant.TOKEN, ""));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    @Override
    public void populateStudentList(List<Student> studentList) {
        if (studentList.size() == 0) {
            clEmpty.setVisibility(View.VISIBLE);
            tvError.setText("No records are available");
            tvErrorTitle.setVisibility(View.GONE);
            btRefresh.setVisibility(View.INVISIBLE);
            ivError.setImageResource(R.drawable.ic_no_data);

        } else {
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvSearch.setLayoutManager(llm);
            rvSearch.setItemAnimator(new DefaultItemAnimator());
            recyclerAdapter = new SearchAdapter(studentList, this, this);
            rvSearch.setAdapter(recyclerAdapter);


            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    filter(editable.toString(), studentList);
                }
            });
        }
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
    public void showError(Boolean isNetworkError) {
        dialog.dismiss();
        clEmpty.setVisibility(View.VISIBLE);
        btRefresh.setVisibility(View.INVISIBLE);
        ivError.setImageResource(R.drawable.ic_no_connection);
        tvErrorTitle.setVisibility(View.VISIBLE);
        if (isNetworkError) {
            tvError.setText("There is problem in Server. Please try again later");

        } else
            tvError.setText("Check your connection, then refresh the page.");

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);

    }
}
