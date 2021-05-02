package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.HomeNoticeAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.LightDateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.utility.MyApp.getContext;

public class UpcomingHolidayActivity extends AppCompatActivity implements UpcomingHolidayView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = UpcomingHolidayActivity.class.getSimpleName();
    UpcomingHolidayPresenter upcomingHolidayPresenter;
    SharedPrefsHelper sharedPrefsHelper;
    ProgressDFragment progressDFragment;
    @BindView(R.id.rvUpComingNotice)
    RecyclerView rvUpComingNotice;
    Calendar mCal;

    @BindView(R.id.slUpcomingHoliday)
    SwipeRefreshLayout slUpcomingHoliday;

    @BindView(R.id.ivError)
    ImageView ivError;

    @BindView(R.id.tvErrorTitle)
    TextView tvErrorTitle;

    @BindView(R.id.tvErrorSubTitle)
    TextView tvErrorSubTitle;

   /* @BindView(R.id.btRetry)
    Button btRetry;*/

    @BindView(R.id.clError)
    ConstraintLayout clError;

    @BindView(R.id.btLeft)
    ImageView btLeft;

    @BindView(R.id.btRight)
    ImageView btRight;

    @BindView(R.id.tvDate)
    TextView tvDate;
    int month;
    FragmentManager fm;
    Model todayNepaliDate;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        ButterKnife.bind(this);
        LightDateConverter dateConverter = new LightDateConverter();
        todayNepaliDate = dateConverter.getTodayNepaliDate();

        upcomingHolidayPresenter = new UpcomingHolidayPresenter(this, new MetaDatabaseRepo(getHelper()));
        slUpcomingHoliday.setOnRefreshListener(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        fm = getSupportFragmentManager();
        tvDate.setText(getMonth(todayNepaliDate.getMonth()) + " - " + todayNepaliDate.getYear());
        month = todayNepaliDate.getMonth();
        if (month <= 0)
            btLeft.setVisibility(View.GONE);

        else if (month >= 11)
            btRight.setVisibility(View.GONE);
        loadHoliday(todayNepaliDate.getYear() + "-" + todayNepaliDate.getMonth() + "-" + todayNepaliDate.getDay());
    }

    @Override
    public void populateHolidayList(List<Holiday> holidayList) {


        //shorting
        /*List<String> date = new ArrayList<>();
        for (int i = 0; i < holidayList.size(); i++) {
            date.add(holidayList.get(i).getStart());
        }
        date.sort(Comparator.naturalOrder());*/
        clError.setVisibility(View.GONE);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUpComingNotice.setLayoutManager(llm);
        rvUpComingNotice.setItemAnimator(new DefaultItemAnimator());
        HomeNoticeAdapter homeNoticeAdapter = new HomeNoticeAdapter(holidayList, this, true);
        rvUpComingNotice.setAdapter(homeNoticeAdapter);
    }

    @Override
    public void onRefresh() {
        loadHoliday(todayNepaliDate.getYear() + "-" + todayNepaliDate.getMonth() + "-" + todayNepaliDate.getDay());
        tvDate.setText(getMonth(todayNepaliDate.getMonth()) + " - " + todayNepaliDate.getYear());
    }

    @Override
    public void onComplete() {
        progressDFragment.dismiss();
    }


    private void loadHoliday(String sDate) {
        slUpcomingHoliday.setRefreshing(false);
        /*progressDFragment = ProgressDFragment.newInstance("Loading Holiday");
        progressDFragment.show(fm, "order");*/
        upcomingHolidayPresenter.getHoliday(sDate);
    }


    //handle Error

    @Override
    public void showNoDataFound() {
        clError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText("Oops!");
        tvErrorSubTitle.setText("No holiday Available");
        /* btRetry.setVisibility(View.INVISIBLE);*/


    }

    @Override
    public void showNetworkError() {
        clError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText("No internet available");
        tvErrorSubTitle.setText("Connect to the internet ");
       /* btRetry.setVisibility(View.VISIBLE);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadHoliday(sDate);
            }
        });*/


    }

    @OnClick(R.id.ivBack)
    void ivBackClicked() {
        onBackPressed();
    }

    @Override
    public void showServerError() {
        clError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText("Something went wrong");
        tvErrorSubTitle.setText("Please try again later");
        //btRetry.setVisibility(View.VISIBLE);
        /*btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // loadHoliday();
            }
        });*/

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

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }

        databaseHelper = null;
    }

    @OnClick(R.id.btLeft)
    public void btLeftClicked() {

        month = month - 1;
        if (month <= 0) {
            btLeft.setVisibility(View.GONE);
        } else {
            btRight.setVisibility(View.VISIBLE);
        }
        tvDate.setText(getMonth(month) + " - " + todayNepaliDate.getYear());
        upcomingHolidayPresenter.getHoliday(todayNepaliDate.getYear() + "-" + month + "-" + todayNepaliDate.getDay());

    }

    @OnClick(R.id.btRight)
    public void btRightClicked() {

        month = month + 1;
        if (month >= 11) {
            btRight.setVisibility(View.GONE);
        } else {
            btLeft.setVisibility(View.VISIBLE);
        }
        tvDate.setText(getMonth(month) + " - " + todayNepaliDate.getYear());
        upcomingHolidayPresenter.getHoliday(todayNepaliDate.getYear() + "-" + month + "-" + todayNepaliDate.getDay());
    }

    private String getMonthOfYear(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "n/a";

        }
    }

    public String getMonth(int month) {
        if (month == 0) {
            return getString(R.string.Baisakh);
        } else if (month == 1) {
            return getString(R.string.Jestha);
        } else if (month == 2) {
            return getString(R.string.Ashar);

        } else if (month == 3) {
            return getString(R.string.Shrawan);

        } else if (month == 4) {
            return getString(R.string.Bhadra);

        } else if (month == 5) {
            return getString(R.string.Ashoj);

        } else if (month == 6) {
            return getString(R.string.Kartik);

        } else if (month == 7) {
            return getString(R.string.Mangsir);

        } else if (month == 8) {
            return getString(R.string.Poush);

        } else if (month == 9) {
            return getString(R.string.Magh);

        } else if (month == 10) {
            return getString(R.string.Falgun);

        } else {
            return getString(R.string.Chaitra);
        }
    }
}
