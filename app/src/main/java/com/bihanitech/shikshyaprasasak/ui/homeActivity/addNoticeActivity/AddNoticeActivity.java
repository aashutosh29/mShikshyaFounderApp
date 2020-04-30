package com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.textEditorFragment.TextEditorFragment;
import com.ebolo.krichtexteditor.fragments.KRichEditorFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNoticeActivity extends AppCompatActivity implements AddNoticeView {
    final Calendar myCalendar = Calendar.getInstance();
    AddNoticePresenter addNoticePresenter;
    DatabaseHelper databaseHelper;
    EditText edittext;
    private KRichEditorFragment editorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //   addNoticePresenter = new AddNoticePresenter(this, new MetaDatabaseRepo(getHelper()));
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.flTextEditor, new TextEditorFragment()).commit();


        Spinner spCatagory = findViewById(R.id.spCatagory);

        String[] items = new String[]{"Notice", "News", "Notice to teacher", "Notice to class section"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spCatagory.setAdapter(adapter);
        edittext = findViewById(R.id.etPushishDate);
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
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
}
