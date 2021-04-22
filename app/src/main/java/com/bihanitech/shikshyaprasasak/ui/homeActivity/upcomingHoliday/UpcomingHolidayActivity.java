package com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @BindView(R.id.ivError)
    ImageView ivError;

    @BindView(R.id.tvErrorTitle)
    TextView tvErrorTitle;

    @BindView(R.id.tvErrorSubTitle)
    TextView tvErrorSubTitle;

    @BindView(R.id.btRetry)
    Button btRetry;

    @BindView(R.id.clError)
    ConstraintLayout clError;

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


        //shorting
        /*List<String> date = new ArrayList<>();
        for (int i = 0; i < holidayList.size(); i++) {
            date.add(holidayList.get(i).getStart());
        }
        date.sort(Comparator.naturalOrder());*/

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


    //handle Error

    @Override
    public void showNoDataFound() {
        clError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText("Oops!");
        tvErrorSubTitle.setText("No holiday Available");
        btRetry.setVisibility(View.INVISIBLE);


    }

    @Override
    public void showNetworkError() {
        clError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText("No internet available");
        tvErrorSubTitle.setText("Connect to the internet ");
        btRetry.setVisibility(View.VISIBLE);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHoliday();
            }
        });


    }

    @Override
    public void showServerError() {
        clError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText("Something went wrong");
        tvErrorSubTitle.setText("Please try again later");
        btRetry.setVisibility(View.VISIBLE);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHoliday();
            }
        });

    }
}
