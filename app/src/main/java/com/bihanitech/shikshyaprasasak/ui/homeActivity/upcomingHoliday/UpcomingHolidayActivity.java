package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.HomeNoticeAdapter;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bihanitech.shikshyaprasasak.utility.MyApp.getContext;

public class UpcomingHolidayActivity extends AppCompatActivity implements UpcomingHolidayView {

    UpcomingHolidayPresenter upcomingHolidayPresenter;
    SharedPrefsHelper sharedPrefsHelper;
    @BindView(R.id.rvUpComingNotice)
    RecyclerView rvUpComingNotice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        ButterKnife.bind(this);
        upcomingHolidayPresenter = new UpcomingHolidayPresenter(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        upcomingHolidayPresenter.getHoliday(sharedPrefsHelper.getValue(Constant.TOKEN, ""));
    }

    @Override
    public void populateHolidayList(List<Holiday> holidayList) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUpComingNotice.setLayoutManager(llm);
        rvUpComingNotice.setItemAnimator(new DefaultItemAnimator());
        HomeNoticeAdapter homeNoticeAdapter = new HomeNoticeAdapter(holidayList, this, true);
        rvUpComingNotice.setAdapter(homeNoticeAdapter);
    }
}
