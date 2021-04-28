package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.ClickableViewPager;
import com.bihanitech.shikshyaprasasak.adapter.HomeNoticeAdapter;
import com.bihanitech.shikshyaprasasak.adapter.SliderAdapter;
import com.bihanitech.shikshyaprasasak.adapter.SlidingNoticeAdapter;
import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.noticeDetailAcitivity.NoticeDetailActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday.UpcomingHolidayActivity;
import com.bihanitech.shikshyaprasasak.ui.webViewAcitivity.WebViewActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.LightDateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class HomeFragment extends Fragment implements HomeFragmentView, SwipeRefreshLayout.OnRefreshListener {
    private static final int currentPage = 0;
    private static final int NUM_PAGES = 0;
    private static int currentNoticePage = 0;
    private static int NUM_NOTICE_PAGE = 0;
    SliderAdapter sliderAdapter;
    ProgressDFragment progressDFragment;
    HomeFragmentPresenter homePresenter;
    @BindView(R.id.vpAdvertise)
    ClickableViewPager vpAdvertise;
    @BindView(R.id.vpNotice)
    ClickableViewPager vpNotice;
    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;
    @BindView(R.id.ivSchoolIcon)
    ImageView ivSchoolIcon;
    @BindView(R.id.tvSchoolAddress)
    TextView tvSchoolAddress;
    @BindView(R.id.cpIndicator)
    CirclePageIndicator indicator;
    @BindView(R.id.cpNotice)
    CirclePageIndicator cpNotice;
    @Nullable
    @BindView(R.id.tvMainTitle)
    TextView tvTitle1;
    @BindView(R.id.tvTodayMonth)
    TextView tvTodayMonth;
    @BindView(R.id.tvTodayDate)
    TextView tvTodayDate;
    @Nullable
    @BindView(R.id.tvDetail1)
    TextView tvDetail1;
    @Nullable
    @BindView(R.id.cvNotice1)
    ConstraintLayout cvNotice1;
    @Nullable
    @BindView(R.id.btMore)
    TextView btMore;
    @BindView(R.id.clEmpty)
    ConstraintLayout clEmpty;
    @BindView(R.id.clNoNotice)
    ConstraintLayout clNoNotice;
    @BindView(R.id.rvUpComingNotice)
    RecyclerView rvUpComingNotice;
    @BindView(R.id.ivLoadingNotices)
    ImageView ivLoadingNotices;
    @BindView(R.id.pv2Slider)
    ViewPager2 pv2Slider;
    @BindView(R.id.cvSlider)
    CardView cvSlider;
    @BindView(R.id.srlHome)
    SwipeRefreshLayout srlHome;
    @BindView(R.id.clCardSlider)
    ConstraintLayout clCardSlider;
    FragmentManager fm;
    SharedPrefsHelper sharedPrefsHelper;
    Toolbar toolbarNew;
    TextView tvToolbarTitle;
    Model todayNepaliDate;
    String schoolId;
    Timer timer;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        initToolbar();
        srlHome.setOnRefreshListener(this);
        schoolId = sharedPrefsHelper.getValue(Constant.SCHOOL_ID, "");
        homePresenter = new HomeFragmentPresenter(this, getContext());
        homePresenter.fetchSliderList(schoolId);
        tvSchoolName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvSchoolAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        checkFirst();
        Log.d(TAG, "onCreateView: " + sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        timer = new Timer();
        LightDateConverter dateConverter = new LightDateConverter();
        todayNepaliDate = dateConverter.getTodayNepaliDate();
        tvTodayDate.setText(todayNepaliDate.getDay() + "");
        int a = (todayNepaliDate.getMonth());
        tvTodayMonth.setText(getMonth(a));
        fm = getFragmentManager();
        String imageURl = sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, "");
        showSchoolLogo(imageURl);
        homePresenter.getNotices(sharedPrefsHelper.getValue(Constant.TOKEN, ""), false);
        return view;
    }

    private void checkFirst() {

        if (!sharedPrefsHelper.getValue(Constant.FIRST_LOAD_HOME, false)) {
            homePresenter.getHoliday(sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        } else {
            homePresenter.getLocallySavedUpComingNotice();
        }


    }

    private void initToolbar() {
        toolbarNew = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarNew);
        toolbarNew.setVisibility(View.VISIBLE);
        tvToolbarTitle = Objects.requireNonNull(getActivity().findViewById(R.id.tvToolbarTitle));
        tvToolbarTitle.setText("Shikshya Executive");

    }

    @OnClick(R.id.clMore)
    public void clMoreClicked() {
        Intent i = new Intent(getContext(), UpcomingHolidayActivity.class);
        startActivity(i);
    }

    private void showSchoolLogo(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivSchoolIcon);
    }


    @OnClick(R.id.btMore)
    public void btMoreClicked() {
        sendToNoticeList();
    }

    private void sendToNoticeList() {
        startActivity(new Intent(getActivity(), NoticeActivity.class));
    }

    @Override
    public void populateNotice(List<Notice> noticeList) {

        vpNotice.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // your code
                Intent i = new Intent(getActivity(), NoticeDetailActivity.class);
                i.putExtra(Constant.NOTICE_TITLE, noticeList.get(position).getTitle());
                i.putExtra(Constant.NOTICE_DATE, noticeList.get(position).getDate());
                i.putExtra(Constant.NOTICE_DETAIL, noticeList.get(position).getContent());
                startActivity(i);

            }
        });

        vpNotice.setAdapter(new SlidingNoticeAdapter(getContext(), noticeList));
        cpNotice.setViewPager(vpNotice);

        //set Circle indicator radius
        final float densityNotice = getResources().getDisplayMetrics().density;
        cpNotice.setRadius(3 * densityNotice);
        NUM_NOTICE_PAGE = 5;
        cpNotice.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentNoticePage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

    @Override
    public void noNoticeAvailable() {
        clEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void dataSynced() {
        ivLoadingNotices.setVisibility(View.GONE);
    }

    @Override
    public void populateHolidayList(List<Holiday> holidayList, Boolean isFirst) {

        sharedPrefsHelper.saveValue(Constant.FIRST_LOAD_HOME, isFirst);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUpComingNotice.setLayoutManager(llm);
        rvUpComingNotice.setItemAnimator(new DefaultItemAnimator());
        HomeNoticeAdapter homeNoticeAdapter = new HomeNoticeAdapter(holidayList, this);
        rvUpComingNotice.setAdapter(homeNoticeAdapter);
    }

    @Override
    public void hideLoading() {
        Objects.requireNonNull(getView()).findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    public void onComplete() {
        progressDFragment.dismiss();
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

    @Override
    public void onRefresh() {
        loadHome(true);
    }

    private void loadHome(boolean refresh) {
        srlHome.setRefreshing(false);
        progressDFragment = ProgressDFragment.newInstance("Loading data");
        progressDFragment.show(fm, "data");
        if (refresh) {
            homePresenter.getNotices(sharedPrefsHelper.getValue(Constant.TOKEN, ""), true);
        }

    }


    public void slideTheSlider() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pv2Slider.post(new Runnable() {

                    @Override
                    public void run() {
                        pv2Slider.setCurrentItem((pv2Slider.getCurrentItem() + 1) % sliderAdapter.getItemCount());
                    }
                });
            }
        };
        timer.schedule(timerTask, 6000, 8000);


    }

    @Override
    public void populateSliderList(List<EventSlider> response) {
        if (response.size() >= 1) {

            cvSlider.setVisibility(View.VISIBLE);
            double cvHight = Resources.getSystem().getDisplayMetrics().widthPixels / 2.5;
            clCardSlider.setMaxHeight((int) cvHight);
            clCardSlider.setMinHeight((int) cvHight);

            List<EventSlider> eventSliders = response;
            sliderAdapter = new SliderAdapter(eventSliders, this, getContext());
            pv2Slider.setAdapter(sliderAdapter);

            slideTheSlider();

        } else {
            cvSlider.setVisibility(View.VISIBLE);

            double cvHight = cvSlider.getWidth() / 2.6;

            clCardSlider.setMaxHeight((int) cvHight);
            clCardSlider.setMinHeight((int) cvHight);


            List<EventSlider> eventSliders = new ArrayList<>();
            EventSlider es = new EventSlider();
            es.setImage("no responce");
            eventSliders.add(es);

            SliderAdapter sliderAdapter = new SliderAdapter(eventSliders, this, getContext());
            pv2Slider.setAdapter(sliderAdapter);
        }


    }

    @Override
    public void sliderItemClicked(EventSlider eventSlider) {
        if (eventSlider.getUrl() != null) {
            if (eventSlider.getUrl().length() > 4) {
                if (timer != null) {
                    timer.cancel();
                }
                Intent i = new Intent(getContext(), WebViewActivity.class);
                i.putExtra("url", eventSlider.getUrl());
                startActivity(i);
            }
        }
    }
}
