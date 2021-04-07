package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.HomeNoticeAdapter;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bihanitech.shikshyaprasasak.utility.MyApp.getContext;

public class UpcomingHolidayActivity extends AppCompatActivity implements UpcomingHolidayView, SwipeRefreshLayout.OnRefreshListener {

    UpcomingHolidayPresenter upcomingHolidayPresenter;
    SharedPrefsHelper sharedPrefsHelper;
    ProgressDFragment progressDFragment;
    @BindView(R.id.rvUpComingNotice)
    RecyclerView rvUpComingNotice;

    @BindView(R.id.slUpcomingHoliday)
    SwipeRefreshLayout slUpcomingHoliday;

    FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        ButterKnife.bind(this);
        upcomingHolidayPresenter = new UpcomingHolidayPresenter(this);
        slUpcomingHoliday.setOnRefreshListener(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        fm = getSupportFragmentManager();
        loadHoliday();
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

    @Override
    public void onRefresh() {
        loadHoliday();

    }

    @Override
    public void onComplete() {
        progressDFragment.dismiss();
    }

    private void loadHoliday() {
        slUpcomingHoliday.setRefreshing(false);
        progressDFragment = ProgressDFragment.newInstance("Loading Holiday");
        progressDFragment.show(fm, "order");
        upcomingHolidayPresenter.getHoliday(sharedPrefsHelper.getValue(Constant.TOKEN, ""));
    }
}
