package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.statement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.StatementAdapter;
import com.bihanitech.shikshyaprasasak.model.TitleWise;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.AnalyticsPresenter.TAG;

public class StatementActivity extends AppCompatActivity implements StatementView {

    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.cvFrom)
    ConstraintLayout cvFrom;

    @BindView(R.id.cvTo)
    ConstraintLayout cvTo;

    @BindView(R.id.tvDate)
    TextView tvDateFrom;

    @BindView(R.id.tvDateA)
    TextView tvDateTo;

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.rvStatement)
    RecyclerView rvStatement;

    @BindView(R.id.tvFrom)
    TextView tvFrom;

    @BindView(R.id.tvTo)
    TextView tvTo;

    StatementAdapter recyclerAdapter;
    Context context;
    StatementPresenter statementPresenter;

    SharedPrefsHelper sharedPrefsHelper;

    String from;
    String to;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        context = StatementActivity.this;
        dialog = new ProgressDialog(this);
        statementPresenter = new StatementPresenter(this);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                Log.d(TAG, "updateLabel: " + sdf.format(myCalendar.getTime()));
                from = sdf.format(myCalendar.getTime());
                tvDateFrom.setText(from);
            }

        };
        DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                Log.d(TAG, "updateLabel: " + sdf.format(myCalendar.getTime()));
                to = sdf.format(myCalendar.getTime());
                tvDateTo.setText(to);
                statementPresenter.populateStatementRecord(sharedPrefsHelper.getValue(Constant.TOKEN, ""), from, to);
            }

        };
        cvFrom.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                tvFrom.setVisibility(View.VISIBLE);
            }
        });

        cvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, dateTo, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                tvTo.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void populateData(List<TitleWise> response) {
        if (response.size() == 0) {
            this.tvError.setText("NO RECORD FOUND");
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvStatement.setLayoutManager(llm);
        rvStatement.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new StatementAdapter(response, this);
        rvStatement.setAdapter(recyclerAdapter);

    }

    @Override
    public void loadingData() {
        dialog.setMessage("Loading data");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

    }

    @OnClick(R.id.ivBack)
    void ivBackPressed() {
        onBackPressed();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showError() {
        tvError.setText("SOMETHING WENT WRONG PLEASE TRY AGIAN");
    }

    @Override
    public void sendToDetailActivity(TitleWise titleWise) {

    }
}
