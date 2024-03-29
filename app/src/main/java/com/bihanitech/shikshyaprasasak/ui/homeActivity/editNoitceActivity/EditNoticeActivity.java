package com.bihanitech.shikshyaprasasak.ui.homeActivity.editNoitceActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.SuccessDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticePresenter;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeView;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity.NoticeUploadActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoticeActivity extends AppCompatActivity implements AddNoticeView {
    private static final String TAG = AddNoticeActivity.class.getSimpleName();
    final Calendar myCalendar = Calendar.getInstance();
    AddNoticePresenter addNoticePresenter;
    DatabaseHelper databaseHelper;
    String date;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btUploadNoticeLater)
    Button btUploadNoticeLater;
    Intent intent;

    NetworkErrorDFragment networkErrorDFragment;
    SuccessDFragment successDFragment;
    FragmentManager fm;
    Boolean click = false;

    ProgressDialog dialog;
    SharedPrefsHelper sharedPrefsHelper;
    Spinner spCategory;

    Context context;

    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etContentBody)
    EditText etContentBody;

    String title;
    String content;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit_noitce_activity);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        addNoticePresenter = new AddNoticePresenter(this, new MetaDatabaseRepo(getHelper()));
        dialog = new ProgressDialog(this);
        context = EditNoticeActivity.this;
        intent = getIntent();
        initToolbar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContent();
        loadSpinner();


    }

    private void setContent() {
        title = intent.getStringExtra(Constant.UNPUBLISHED_TITLE);
        content = intent.getStringExtra(Constant.UNPUBLISHED_CONTENT);
        id = Integer.parseInt(intent.getStringExtra(Constant.UNPUBLISHED_ID));
        etTitle.setText(title);
        etContentBody.setText(content);
    }


    private void initToolbar() {
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditNoticeActivity.this, HomeActivity.class);
                startActivity(intent);
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
    public void
    showSuccess() {
        dialog.dismiss();
        Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT);
        etTitle.setText("");
        etContentBody.setText("");
        addNoticePresenter.deleteLocally(Integer.parseInt(intent.getStringExtra(Constant.UNPUBLISHED_ID)));
        uploaded();
    }

    void uploaded() {
        FragmentManager fm = getSupportFragmentManager();
        successDFragment = SuccessDFragment.newInstance(false);
        successDFragment.show(fm, "Success");


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

    @OnClick(R.id.btUploadNoticeLater)
    void btUploadNoticeLaterClicked() {
        if (click) {
            Toast.makeText(this, "Already saved", Toast.LENGTH_SHORT).show();
        } else {
            if (etTitle.getText().toString().length() <= 3 && etContentBody.getText().toString().length() <= 3) {
                Toast.makeText(this, "please Enter full notice", Toast.LENGTH_SHORT).show();
            } else {
                addNoticePresenter.updateLocally(Integer.parseInt(intent.getStringExtra(Constant.UNPUBLISHED_ID)), etTitle.getText().toString(), etContentBody.getText().toString(), spCategory.getSelectedItemPosition() + 1);
                click = true;
                Intent i = new Intent(EditNoticeActivity.this, NoticeUploadActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }
    }

    @OnClick(R.id.btDelete)
    void btDeleteClicked() {
        addNoticePresenter.deleteLocally(id);
        Intent i = new Intent(EditNoticeActivity.this, NoticeUploadActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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
        addNoticePresenter.deleteLocally(id);
        //Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        dialog.setMessage("Loading data");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    @Override
    public void populateClassesAndSectionList(List<Classes> classesList, List<Section> sectionList) {

    }

    @Override
    public void showLoadingForStudent() {

    }

    @Override
    public void hideLoadingForStudent() {

    }

    @Override
    public void populateStudentList(List<Student> studentList, Boolean checked) {

    }


    @Override
    public void showError() {

    }

    @Override
    public void ifUnChecked(List<Student> students) {
    }

    @Override
    public void getListOfStudentToSendNotice(List<Student> students) {

    }

    @Override
    public void back() {
        Intent i = new Intent(EditNoticeActivity.this, NoticeUploadActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private void loadSpinner() {
        spCategory = findViewById(R.id.spCatagory);
        String[] items = new String[]{"Notice", "News", "Notice to teacher"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spCategory.setAdapter(adapter);
        spCategory.setSelection(Integer.parseInt(intent.getStringExtra(Constant.UNPUBLISHED_CATEGORY)) - 1);
    }

    private void addNotice() {

        if (click) {
            Toast.makeText(context, "Already Uploaded", Toast.LENGTH_SHORT).show();
        } else {
            if (etTitle.getText().toString().length() <= 3 && etContentBody.getText().toString().length() <= 3) {
                Toast.makeText(context, "please Enter full notice", Toast.LENGTH_SHORT).show();
            } else {
                addNoticePresenter.uploadNotice(false, sharedPrefsHelper.getValue(Constant.TOKEN, ""), "", etTitle.getText().toString(), etContentBody.getText().toString(), "", "", "2", null);
            }
        }
    }
}
