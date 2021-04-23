package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import android.annotation.SuppressLint;
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
import com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity.NoticeUploadActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity.token;

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
    Button btUploadNoticeLater;

    NetworkErrorDFragment networkErrorDFragment;
    FragmentManager fm;
    Boolean click = false;


    SharedPrefsHelper sharedPrefsHelper;
    Spinner spCategory;

    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etContentBody)
    EditText etContentBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        addNoticePresenter = new AddNoticePresenter(this, new MetaDatabaseRepo(getHelper()));
        initToolbar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadSpinner();
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
                Intent intent = new Intent(AddNoticeActivity.this, HomeActivity.class);
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
    public void showSuccess() {
        Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT);
        etTitle.setText("");
        etContentBody.setText("");
        loadSpinner();
    }

    @Override
    public void showCantUpload() {
        FragmentManager fm = getSupportFragmentManager();
        networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, "");
        networkErrorDFragment.show(fm, "ServerError");
    }

    @Override
    public void showNetworkError() {
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
    }

    private void addNotice() {

        addNoticePresenter.uploadNotice(false, token, "", etTitle.getText().toString(), etContentBody.getText().toString(), date, "", String.valueOf(spCategory.getSelectedItemPosition() + 1));
    }
}
