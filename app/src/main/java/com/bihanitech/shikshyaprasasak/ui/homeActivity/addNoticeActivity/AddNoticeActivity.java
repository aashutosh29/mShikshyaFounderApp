package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.ebolo.krichtexteditor.fragments.KRichEditorFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNoticeActivity extends AppCompatActivity implements AddNoticeView {
    final Calendar myCalendar = Calendar.getInstance();
    AddNoticePresenter addNoticePresenter;
    DatabaseHelper databaseHelper;
    EditText edittext;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etContentBody)
    EditText etContentBody;
    private KRichEditorFragment editorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        ButterKnife.bind(this);
        initToolbar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //   addNoticePresenter = new AddNoticePresenter(this, new MetaDatabaseRepo(getHelper()));
        //FragmentManager manager = getSupportFragmentManager();
        //manager.beginTransaction().add(R.id.flTextEditor, new TextEditorFragment()).commit();


        Spinner spCatagory = findViewById(R.id.spCatagory);

        String[] items = new String[]{"Notice", "News", "Notice to teacher", "Notice to class section"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spCatagory.setAdapter(adapter);
        edittext = findViewById(R.id.etExpire);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNoticeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void initToolbar() {
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

    private void updateLabel() {
        String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
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

    @OnClick(R.id.btUploadNotice)
    public void btUploadNoticeOnClicked() {
        etContentBody.getText();
        etTitle.getText();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showCantUpload() {

    }

    @Override
    public void showNetworkError() {

    }
}
