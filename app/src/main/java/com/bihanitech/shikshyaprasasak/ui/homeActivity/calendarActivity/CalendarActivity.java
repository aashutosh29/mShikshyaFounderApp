package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.calendarAdapter.CalendarDateAdapter;
import com.bihanitech.shikshyaprasasak.adapter.calendarAdapter.EventHolidaysAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.itemModels.CDayItem;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Calendar;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.DateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.LightDateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarActivity extends AppCompatActivity implements CalendarView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.tabSliding)
    TabLayout tabSliding;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.rvCalendar)
    RecyclerView rvCalendar;

    CalendarPresenter calendarPresenter;

    Model todayNepDate;

    Model earlierMonth;

    Model upcomingMonth;

    @BindView(R.id.titleNepali)
    TextView titleNepali;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.titleEnglish)
    TextView titleEnglish;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    @BindView(R.id.ivTwoline)
    ImageView ivTwoline;


    @BindView(R.id.btLeft)
    ImageView btLeft;

    @BindView(R.id.btRight)
    ImageView btRight;

    @BindView(R.id.ivmenu)
    ImageView ivMenu;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;


    FragmentManager fm;
    ProgressDFragment progressDFragment;

    SharedPrefsHelper sharedPrefsHelper;

    DatabaseHelper databaseHelper;
    EventHolidaysAdapter eventHolidaysAdapter;

 /*   @BindView(R.id.ivMenu)
    ImageView ivMenu;*/

    @BindView(R.id.clCalendar)
    ConstraintLayout clCalendar;

    Context context;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            calendarPresenter.downloadEventsHolidays(sharedPrefsHelper.getValue(Constant.TOKEN, ""), 1, true);
        }
    };


   /* @OnClick(R.id.ivMenu)
    public void ivMenuClicked() {
        Log.v("TAGESTO", "open menu here");
        PopupMenu popup = new PopupMenu(this, ivMenu);
        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.update_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new CalendarActivity.UpdateMenuItemClickListener());
        popup.show();

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ivMenu.setVisibility(View.GONE);
        ivProfile.setVisibility(View.GONE);
        ivTwoline.setVisibility(View.GONE);
        tvToolbarTitle.setText("Calendar");

        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        calendarPresenter = new CalendarPresenter(this, new DbInternalRepo(getHelper()));

        tabSliding.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));

        LightDateConverter dateConverter = new LightDateConverter();
        todayNepDate = dateConverter.getTodayNepaliDate();

        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,7);
        //rvCalendar.addItemDecoration(new GridSpacingItemDecoration(7, 50, false));

        GridLayoutManager manager = new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false);
        rvCalendar.setLayoutManager(manager);
        setUpCalendar();


        if (!sharedPrefsHelper.getValue(Constant.CALENDAR_DOWNLOADED, false)) {
            fm = getSupportFragmentManager();
            progressDFragment = ProgressDFragment.newInstance(getString(R.string.loading_calender_info));
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(progressDFragment, "Calendar").addToBackStack(null);
            ft.commitAllowingStateLoss();
            calendarPresenter.downloadEventsHolidays(sharedPrefsHelper.getValue(Constant.TOKEN, ""), 0, false);
        } else {
            if (!sharedPrefsHelper.getValue(Constant.CALENDAR_UPTO_DATE, false)) {
                fm = getSupportFragmentManager();
                progressDFragment = ProgressDFragment.newInstance(getString(R.string.loading_calender_info));
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(progressDFragment, "Calendar").addToBackStack(null);
                ft.commitAllowingStateLoss();
                calendarPresenter.downloadEventsHolidays(sharedPrefsHelper.getValue(Constant.TOKEN, ""), 1, false);
            } else {
                proceedToShowEventHolidayList();
            }

        }
    }

    @Override
    public void showSnackBar(int i) {
        progressDFragment.dismissAllowingStateLoss();
        proceedToShowEventHolidayList();

        String title;
        if (i == 1) {
            title = "Server Error !!!";

        } else {
            title = "Network Error !!!";

        }
        Snackbar snackbar = Snackbar.make(clCalendar, title, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                progressDFragment = ProgressDFragment.newInstance(getString(R.string.loading_calender_info));
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(progressDFragment, "Calendar").addToBackStack(null);
                ft.commitAllowingStateLoss();
                calendarPresenter.downloadEventsHolidays(sharedPrefsHelper.getValue(Constant.TOKEN, ""), 1, false);
            }
        });

        snackbar.setActionTextColor(Color.parseColor("#DCF5F5"));

        View snackbarView = snackbar.getView();
        TextView snackbarText = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarText.setTextColor(Color.YELLOW);
        snackbar.show();

    }

    @Override
    public void proceedToShowEventHolidayList() {
        setUpHolidayEventView();
    }

    @Override
    public void abort() {
        this.finish();

    }

    @Override
    public void showUnauthorized() {
        progressDFragment.dismissAllowingStateLoss();
        logout();
//        FragmentManager fm = getSupportFragmentManager();
//        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.UNAUTHORIZED_ERROR, CalendarActivity.class.getSimpleName());
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
//        ft.commitAllowingStateLoss();
    }

    @Override
    public void setCalendarInfoDownloaded() {
        progressDFragment.dismissAllowingStateLoss();
        sharedPrefsHelper.saveValue(Constant.CALENDAR_DOWNLOADED, true);
        sharedPrefsHelper.saveValue(Constant.CALENDAR_UPTO_DATE, true);
    }

    private void setUpCalendar() {
        todayNepDate.setMonth(todayNepDate.getMonth() + 1);
        tvDate.setText(todayNepDate.getYear() + ", " + getResources().getString(DateConverter.getNepaliMonthString(todayNepDate.getMonth() - 1)) + " " + todayNepDate.getDay());
        titleNepali.setText(Calendar.getNepaliMonth(todayNepDate.getMonth()) + " " + todayNepDate.getYear());
        titleEnglish.setText(Calendar.getEngMonthForNepMonth(todayNepDate.getMonth(), todayNepDate.getYear()).replace("\n", ""));
        int today = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        calendarPresenter.fetchAndPopulateCalendar(todayNepDate, today);
    }

    @OnClick(R.id.btLeft)
    public void btLeftClicked() {
        btRight.setVisibility(View.VISIBLE);

        if (earlierMonth == null) {
            earlierMonth = todayNepDate;
        }
        if (earlierMonth.getMonth() - 1 > 0) {
            earlierMonth.setMonth(earlierMonth.getMonth() - 1);
        } else {
            earlierMonth.setMonth(12);
            earlierMonth.setYear(earlierMonth.getYear() - 1);
        }
        if (earlierMonth.getYear() == 2070 && earlierMonth.getMonth() == 1) {
            btLeft.setVisibility(View.GONE);
        } else {
            btLeft.setVisibility(View.VISIBLE);
        }

        titleNepali.setText(Calendar.getNepaliMonth(earlierMonth.getMonth()) + " " + earlierMonth.getYear());
        titleEnglish.setText(Calendar.getEngMonthForNepMonth(earlierMonth.getMonth(), earlierMonth.getYear()).replace("\n", ""));

        //calendarPresenter.fetchAndPopulateCalendar(earlierMonth, -1);
        //setUpHolidayEventView();

    }

    @OnClick(R.id.btRight)
    public void btRightClicked() {
        btLeft.setVisibility(View.VISIBLE);
        if (upcomingMonth == null) {
            upcomingMonth = todayNepDate;
        }
        if (upcomingMonth.getMonth() + 1 <= 12) {
            upcomingMonth.setMonth(upcomingMonth.getMonth() + 1);
        } else {
            upcomingMonth.setMonth(1);
            upcomingMonth.setYear(upcomingMonth.getYear() + 1);
        }

        if (upcomingMonth.getYear() == 2080 && upcomingMonth.getMonth() == 12) {
            btRight.setVisibility(View.GONE);
        } else {
            btRight.setVisibility(View.VISIBLE);
        }
        titleNepali.setText(Calendar.getNepaliMonth(upcomingMonth.getMonth()) + " " + upcomingMonth.getYear());
        titleEnglish.setText(Calendar.getEngMonthForNepMonth(upcomingMonth.getMonth(), upcomingMonth.getYear()).replace("\n", ""));

        calendarPresenter.fetchAndPopulateCalendar(upcomingMonth, -1);
        //setUpHolidayEventView();
        // setUpHolidayEventView();
    }

    @Override
    public void showServerError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, CalendarActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismissAllowingStateLoss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, CalendarActivity.class.getSimpleName());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(networkErrorDFragment, "NetworkError").addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void retry() {
        progressDFragment = ProgressDFragment.newInstance(getString(R.string.loading_calender_info));
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(progressDFragment, "Calendar").addToBackStack(null);
        ft.commitAllowingStateLoss();
        calendarPresenter.downloadEventsHolidays(sharedPrefsHelper.getValue(Constant.TOKEN, ""), 0, false);

    }

    @Override
    public void populateCalendar(List<CDayItem> dayItems) {
        // Initialize a new instance of RecyclerView Adapter instance
        CalendarDateAdapter mAdapter = new CalendarDateAdapter(dayItems, todayNepDate);
        // Set the adapter for RecyclerView
        rvCalendar.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setUpHolidayEventView() {
        if (upcomingMonth == null) {
            upcomingMonth = todayNepDate;
        }
        String TAG = "Month year wala ";
        Log.v(TAG, "Year" + upcomingMonth.getYear());
        Log.v(TAG, "Month" + upcomingMonth.getMonth());
        eventHolidaysAdapter = new EventHolidaysAdapter(getSupportFragmentManager(), CalendarActivity.this, upcomingMonth.getYear(), upcomingMonth.getMonth());
        viewPager.setAdapter(eventHolidaysAdapter);
        tabSliding.setupWithViewPager(viewPager);
        tabSliding.getTabAt(0).setText(getString(R.string.holidays));
        tabSliding.getTabAt(1).setText(getString(R.string.events));
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
    public void onResume() {
        super.onResume();
        context = getApplicationContext();
        context.registerReceiver(mMessageReceiver, new IntentFilter("shikshya_notice"));
    }


    @Override
    public void onPause() {
        super.onPause();
        context = getApplicationContext();
        context.unregisterReceiver(mMessageReceiver);
    }

    public void logout() {
/*
        Toast.makeText(this, R.string.user_login_to_other_device,Toast.LENGTH_SHORT).show();DatabaseHelper databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        SQLiteDatabase db = databaseHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(SchoolInfo.class.getSimpleName(), null, null);
        db.delete(ContactsItem.class.getSimpleName(), null, null);
        db.delete(MessageSentItem.class.getSimpleName(), null, null);

        try {
            db.delete(StudentInfo.class.getSimpleName(), null, null);
        } catch (Exception e) {
        }
        db.delete(MetaRoutine.class.getSimpleName(), null, null);
        db.delete(RoutineSubjects.class.getSimpleName(), null, null);
        db.delete(MetaResult.class.getSimpleName(), null, null);
        db.delete(ResultMarks.class.getSimpleName(), null, null);
        db.delete(Event.class.getSimpleName(), null, null);
        db.delete(Holiday.class.getSimpleName(), null, null);
        db.delete(FeeLedger.class.getSimpleName(), null, null);
        db.delete(NoticeItem.class.getSimpleName(), null, null);
        db.delete(AttendanceInfo.class.getSimpleName(), null, null);
        db.delete(HomeworkInfo.class.getSimpleName(), null, null);
        db.delete(PendingPaymentItem.class.getSimpleName(), null, null);

        SharedPrefsHelper.getInstance(this).clear();

        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();*/
    }

    public class UpdateMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.nav_update) {
                fm = getSupportFragmentManager();
                progressDFragment = ProgressDFragment.newInstance(getString(R.string.loading_calender_info));
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(progressDFragment, "Calendar").addToBackStack(null);
                ft.commitAllowingStateLoss();
                calendarPresenter.downloadEventsHolidays(sharedPrefsHelper.getValue(Constant.TOKEN, ""), 1, false);
                return true;
            }
            return false;
        }
    }

}

