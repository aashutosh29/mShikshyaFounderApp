package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.StudentDetailsAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper;
import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.SuccessDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity.NoticeUploadActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity.token;
import static com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticePresenter.studentList1;

public class AddNoticeActivity extends AppCompatActivity implements AddNoticeView {
    private static final String TAG = AddNoticeActivity.class.getSimpleName();
    final Calendar myCalendar = Calendar.getInstance();
    AddNoticePresenter addNoticePresenter;
    DatabaseHelper databaseHelper;
    EditText edittext;
    String date;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btUploadNoticeLater)
    ImageView btUploadNoticeLater;
    ProgressDialog dialog;
    @BindView(R.id.clClassSectionStudent)
    ConstraintLayout clClassSectionStudent;
    NetworkErrorDFragment networkErrorDFragment;
    SuccessDFragment successDFragment;
    FragmentManager fm;
    Boolean click = false;
    @BindView(R.id.spClass)
    Spinner spClass;
    @BindView(R.id.tvAllChecked)
    TextView tvAllChecked;

    @BindView(R.id.btSubmitNotice)
    Button btSubmitNotice;


    Boolean isHide = true;

    @BindView(R.id.spSection)
    Spinner spSection;
    SharedPrefsHelper sharedPrefsHelper;
    Spinner spCategory;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;


    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etContentBody)
    EditText etContentBody;

    @BindView(R.id.clSelectStudent)
    ConstraintLayout clSelectStudent;

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.rvStudents)
    RecyclerView rvStudents;

    @BindView(R.id.tvHideShow)
    TextView tvHideShow;

    StudentDetailsAdapter recyclerAdapter;

    Context context;
    String grade = "";
    String section = "";

    @BindView(R.id.cbAll)
    CheckBox cbAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        addNoticePresenter = new AddNoticePresenter(this, new MetaDatabaseRepo(getHelper()));
        context = AddNoticeActivity.this;
        dialog = new ProgressDialog(this);
        initToolbar();
        loadSpinner();


    }

    public void itemClicked(View v) {
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            populateStudentList(studentList1, true);
            tvAllChecked.setText("All students are selected");

        } else if (!checkBox.isChecked()) {
            populateStudentList(studentList1, false);
            tvAllChecked.setText("");
        }

        btSubmitNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = studentList1.size();
                List<Student> studentArrayList = new ArrayList<>();
                String studentId = "";
                for (int i = 0; i < j; i++) {
                    if (studentList1.get(i).getCheckStatus()) {
                        studentArrayList.add(studentList1.get(i));
                    }
                }
                int k = studentArrayList.size();
                String finalJson = "";
                for (int i = 0; i < k; i++) {
                    studentId = studentId + "\"" + studentArrayList.get(i).getRegno() + "\",";
                }
                if (studentId.length() > 0) {
                    studentId = studentId.substring(0, (studentId.length() - 1));
                }
                finalJson = "[" + studentId + "]";

                if (click) {
                    Toast.makeText(context, "Already Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    if (etTitle.getText().toString().length() <= 3 && etContentBody.getText().toString().length() <= 3) {
                        Toast.makeText(context, "please Enter full notice", Toast.LENGTH_SHORT).show();
                    } else if (studentArrayList.size() == 0) {
                        Toast.makeText(context, "Please Select Student", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "onClick: non-filtered" + studentList1.size());
                        Log.d(TAG, "onClick: filtered" + studentArrayList.size());
                        addNoticePresenter.uploadNotice(false, token, grade, etTitle.getText().toString(), etContentBody.getText().toString(), "", section, String.valueOf(spCategory.getSelectedItemPosition() + 1), finalJson);
                        click = true;
                    }
                }
            }
        });
    }


    private void initToolbar() {
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        if (databaseHelper == null) {
            OpenHelperManager.releaseHelper();
        }
        databaseHelper = null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btSubmitNotice)
    void btSubmitNoticeClicked() {
        addNotice();
    }

    @SuppressLint("ShowToast")
    @Override
    public void showSuccess() {
        dialog.dismiss();
        Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT);
        etTitle.setText("");
        etContentBody.setText("");
        Uploaded();
    }

    @Override
    public void showCantUpload() {
        dialog.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, "");
        networkErrorDFragment.show(fm, "ServerError");
    }

    @Override
    public void showNetworkError() {
        dialog.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, "");
        networkErrorDFragment.show(fm, "NetworkError");
    }

    void Uploaded() {
        FragmentManager fm = getSupportFragmentManager();
        successDFragment = SuccessDFragment.newInstance();
        successDFragment.show(fm, "Success");


    }

    @OnClick(R.id.btUploadNoticeLater)
    void btUploadNoticeLaterClicked() {
        if (click) {
            Toast.makeText(this, "Already saved", Toast.LENGTH_SHORT).show();
        } else {
            if (etTitle.getText().toString().length() <= 3 && etContentBody.getText().toString().length() <= 3) {
                Toast.makeText(this, "please Enter full notice", Toast.LENGTH_SHORT).show();
            } else {
                addNoticePresenter.saveLocally(etTitle.getText().toString(), etContentBody.getText().toString(), spCategory.getSelectedItemPosition() + 1);
                click = true;
                Intent i = new Intent(AddNoticeActivity.this, NoticeUploadActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }
    }

    @Override
    public void retry() {
        addNotice();
    }

    @Override
    public void savedLocally() {
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveNoticeLocally() {
        btUploadNoticeLaterClicked();
    }

    @Override
    public void deletedLocally() {

    }

    private void loadSpinner() {
        spCategory = findViewById(R.id.spCatagory);
        String[] items = new String[]{"Notice", "News", "Notice to teacher", "Notice to class section"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spCategory.setAdapter(adapter);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Notice to class section")) {
                    clClassSectionStudent.setVisibility(View.VISIBLE);
                    btUploadNoticeLater.setVisibility(View.GONE);
                    addNoticePresenter.getSpinnerData();
                    // do your stuff
                } else {
                    clClassSectionStudent.setVisibility(View.GONE);
                    btUploadNoticeLater.setVisibility(View.VISIBLE);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void addNotice() {
        if (click) {
            Toast.makeText(context, "Already Uploaded", Toast.LENGTH_SHORT).show();
        } else {
            if (etTitle.getText().toString().length() <= 3 && etContentBody.getText().toString().length() <= 3) {
                Toast.makeText(context, "please Enter full notice", Toast.LENGTH_SHORT).show();
            } else {

                addNoticePresenter.uploadNotice(false, token, "", etTitle.getText().toString(), etContentBody.getText().toString(), date, "", String.valueOf(spCategory.getSelectedItemPosition() + 1), "[]");
            }
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
    public void populateClassesAndSectionList
            (List<Classes> classesList, List<Section> sectionList) {

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
                clSelectStudent.setVisibility(View.VISIBLE);
                //addNoticePresenter.getStudents(grade, section, token);
                ArrayAdapter<String> arrayAdapterSection = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, sectionName);
                arrayAdapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSection.setAdapter(arrayAdapterSection);
                spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        section = sectionList.get(position).getClassID();
                        addNoticePresenter.getStudents(grade, section, token);
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
    public void showLoadingForStudent() {
        tvError.setVisibility(View.GONE);
        loadingPanel.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoadingForStudent() {
        loadingPanel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void populateStudentList(List<Student> studentList, Boolean checked) {
        if (studentList.size() == 0) {
            tvError.setVisibility(View.VISIBLE);
            tvAllChecked.setVisibility(View.GONE);
            tvError.setText("NO RECORD FOUND");
        } else {
            int j = studentList.size();
            for (int i = 0; i < j; i++) {
                studentList.get(i).setCheckStatus(false);
            }
            if (checked) {
                for (int i = 0; i < j; i++) {
                    studentList.get(i).setCheckStatus(true);
                }
            }
            if (!checked) {
                for (int i = 0; i < j; i++) {
                    studentList.get(i).setCheckStatus(false);
                }
            }
            tvError.setVisibility(View.GONE);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvStudents.setLayoutManager(llm);
            rvStudents.setItemAnimator(new DefaultItemAnimator());
            recyclerAdapter = new StudentDetailsAdapter(studentList, this);
            rvStudents.setAdapter(recyclerAdapter);
        }
    }

    @OnClick(R.id.tvHideShow)
    void tvHideShowClicked() {
        if (isHide) {
            rvStudents.setVisibility(View.GONE);
            tvHideShow.setText("Show");
            tvHideShow.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow_white, 0);
            isHide = false;

        } else {
            rvStudents.setVisibility(View.VISIBLE);
            tvAllChecked.setVisibility(View.GONE);
            tvHideShow.setText("Hide");
            tvHideShow.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow_white, 0);
            isHide = true;
        }

    }

    @Override
    public void showError() {
        tvAllChecked.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText("Something Went Wrong");
        loadingPanel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void ifUnChecked(List<Student> students) {
       /* List<Student> CheckedStudent = new ArrayList<>();
        int j = students.size();

        for (int i = 0; i < j; i++) {
            if (students.get(i).getCheckStatus()) {
                CheckedStudent.add(students.get(i));
            }
        }
        int k = CheckedStudent.size();

        switch (k) {
            case 0:
                tvAllChecked.setText("No student is Selected.");
                break;
            case 1:
                tvAllChecked.setText("1 Student is Selected.");
                break;
            default:
                tvAllChecked.setText(k + " Students are Selected.");
        }*/
        cbAll.setChecked(false);
    }

    @Override
    public void getListOfStudentToSendNotice(List<Student> students) {
        btSubmitNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = students.size();
                List<Student> studentArrayList = new ArrayList<>();
                String studentId = "";
                for (int i = 0; i < j; i++) {
                    if (students.get(i).getCheckStatus()) {
                        studentArrayList.add(students.get(i));
                    }
                }
                int k = studentArrayList.size();
                String finalJson = "";
                for (int i = 0; i < k; i++) {
                    studentId = studentId + "\"" + studentArrayList.get(i).getRegno() + "\",";
                }
                if (studentId.length() > 0) {
                    studentId = studentId.substring(0, (studentId.length() - 1));
                }
                finalJson = "[" + studentId + "]";

                if (click) {
                    Toast.makeText(context, "Already Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    if (etTitle.getText().toString().length() <= 3 && etContentBody.getText().toString().length() <= 3) {
                        Toast.makeText(context, "please Enter full notice", Toast.LENGTH_SHORT).show();
                    } else if (studentArrayList.size() == 0) {
                        Toast.makeText(context, "Please Select Student", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "onClick: non-filtered" + students.size());
                        Log.d(TAG, "onClick: filtered" + studentArrayList.size());
                        addNoticePresenter.uploadNotice(false, token, grade, etTitle.getText().toString(), etContentBody.getText().toString(), "", section, String.valueOf(spCategory.getSelectedItemPosition() + 1), finalJson);
                        click = true;
                    }
                }

            }
        });
    }

    @Override
    public void back() {
        onBackPressed();
        deletedLocally();
    }
}
