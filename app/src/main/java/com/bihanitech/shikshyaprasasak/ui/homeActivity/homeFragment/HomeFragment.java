package com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.ClickableViewPager;
import com.bihanitech.shikshyaprasasak.adapter.HomeNoticeAdapter;
import com.bihanitech.shikshyaprasasak.adapter.SlidingImageAdapter;
import com.bihanitech.shikshyaprasasak.adapter.SlidingNoticeAdapter;
import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.noticeDetailAcitivity.NoticeDetailActivity;
import com.bihanitech.shikshyaprasasak.ui.webViewAcitivity.WebViewActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.LightDateConverter;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class HomeFragment extends Fragment implements HomeFragmentView {

    private static final Integer[] IMAGES = {R.drawable.img_slider, R.drawable.img_slider_two, R.drawable.img_slider_one, R.drawable.img_slider};
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static int currentNoticePage = 0;
    private static int NUM_NOTICE_PAGE = 0;
    private final ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    HomeFragmentPresenter homePresenter;
    String URl;
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
    SharedPrefsHelper sharedPrefsHelper;
    String orgNotice1 = "", orgNotice2 = "";
    Toolbar toolbarNew;
    TextView tvToolbarTitle;
    Model todayNepaliDate;


    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        initToolbar();
        //String schoolName = sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, "");
        tvSchoolName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));

        Log.d(TAG, "onCreateView: " + sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        //Nepali date converter
        LightDateConverter dateConverter = new LightDateConverter();
        todayNepaliDate = dateConverter.getTodayNepaliDate();
        tvTodayDate.setText(todayNepaliDate.getDay() + "");
        int a = (todayNepaliDate.getMonth());
        tvTodayMonth.setText(getMonth(a));
        //Satrting  Presenter
        homePresenter = new HomeFragmentPresenter(this);
        homePresenter.getNotices(sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        String imageURl = sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, "");
        // showSchoolLogo(imageURl);
        //setUpNoticeList();
        vpAdvertise.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0)
                    URl = "https://www.facebook.com/";
                else if (position == 1)
                    URl = "https://www.youtube.com/";

                else
                    URl = "https://www.google.com/";
                // your code
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtra("url", URl);
                startActivity(i);

            }
        });

        vpNotice.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // your code
                Intent i = new Intent(getActivity(), NoticeActivity.class);
                startActivity(i);

            }
        });

        init();
        return view;
    }

    private void initToolbar() {
        toolbarNew = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarNew);
        toolbarNew.setVisibility(View.VISIBLE);
        tvToolbarTitle = Objects.requireNonNull(getActivity().findViewById(R.id.tvToolbarTitle));
        tvToolbarTitle.setText("Shikshya Executive");

    }

    private void showSchoolLogo(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivSchoolIcon);
    }


    private void init() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);
        vpAdvertise.setAdapter(new SlidingImageAdapter(getContext(), ImagesArray));


        indicator.setViewPager(vpAdvertise);


        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(3 * density);


        NUM_PAGES = IMAGES.length;


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                vpAdvertise.setCurrentItem(currentPage++, true);
                /*if (currentNoticePage == NUM_NOTICE_PAGE) {
                    currentNoticePage = 0;
                }
                vpNotice.setCurrentItem(currentPage++, true);*/

            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void setUpNoticeList() {
        int count = sharedPrefsHelper.getValue(Constant.NOTICE_COUNT, 0);
        if (count == 0) {
            cvNotice1.setVisibility(View.VISIBLE);
            //cvNotice2.setVisibility(View.VISIBLE);
            //vBar.setVisibility(View.GONE);
            btMore.setVisibility(View.VISIBLE);
            clNoNotice.setVisibility(View.GONE);
        } else {

            clNoNotice.setVisibility(View.GONE);
            btMore.setVisibility(View.VISIBLE);
            if (count > 0) {
                cvNotice1.setVisibility(View.VISIBLE);
                tvTitle1.setText(sharedPrefsHelper.getValue(Constant.NOTICE_TITLE1, ""));
                String noticeDetail1 = sharedPrefsHelper.getValue(Constant.NOTICE_DETAIL1, "");
                orgNotice1 = noticeDetail1;
                if (noticeDetail1.length() >= 80) {
                    noticeDetail1 = noticeDetail1.substring(0, 80) + "...";
                }
                tvDetail1.setText(noticeDetail1);

                //tvDate1.setText(sharedPrefsHelper.getValue(Constant.P_DATE1,""));
                // tvDate1.setText(convertDate(sharedPrefsHelper.getValue(Constant.P_DATE1,"")));
            }

            if (count > 1) {
                //vBar.setVisibility(View.VISIBLE);
                //cvNotice2.setVisibility(View.VISIBLE);
                //tvTitle2.setText(sharedPrefsHelper.getValue(Constant.NOTICE_TITLE2,""));
                String noticeDetail2 = sharedPrefsHelper.getValue(Constant.NOTICE_DETAIL2, "");
                orgNotice2 = noticeDetail2;
                if (noticeDetail2.length() >= 80) {
                    noticeDetail2 = noticeDetail2.substring(0, 80) + "...";
                }
                // tvDetail2.setText(noticeDetail2);

                /*                tvDate2.setText(sharedPrefsHelper.getValue(Constant.P_DATE2,""));
                 */
                //tvDate2.setText(convertDate(sharedPrefsHelper.getValue(Constant.P_DATE2,"")));

            }

            if (count == 1) {

            }
        }

    }


    @OnClick(R.id.btMore)
    public void btMoreClicked() {
        sendToNoticeList();
    }


    private void sendToNoticeList() {
        startActivity(new Intent(getActivity(), NoticeActivity.class));
    }


    private void sendToNoticeDetail(int i) {
        Intent intent = new Intent(getActivity(), NoticeDetailActivity.class);

        if (i == 1) {
            intent.putExtra(Constant.NOTICE_TITLE, tvTitle1.getText().toString());
            intent.putExtra(Constant.NOTICE_DETAIL, orgNotice1);
            //intent.putExtra(Constant.NOTICE_DATE, tvDate1.getText().toString());
        } else {
            // intent.putExtra(Constant.NOTICE_TITLE, tvTitle2.getText().toString());
            intent.putExtra(Constant.NOTICE_DETAIL, orgNotice2);
            //intent.putExtra(Constant.NOTICE_DATE, tvDate2.getText().toString());

        }

        startActivity(intent);
    }


    public String convertDate(String sdate) {
/*

        String[] data = sdate.split(" ");
        String[] dat = data[0].split("-");
        String apple = dat[1] + "/" + dat[2] + "/" + dat[0];
        String newDate = apple + " " + data[1].substring(0, 8) + " " + data[1].substring(8);

*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date nDate = new Date();
        try {

            nDate = sdf.parse(sdate);
        } catch (ParseException e) {

        }

        Long newDateTimeLong = nDate.getTime();


        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                newDateTimeLong,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        return timeAgo.toString();


    }


    @Override
    public void populateNotice(List<Notice> noticeList) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUpComingNotice.setLayoutManager(llm);
        rvUpComingNotice.setItemAnimator(new DefaultItemAnimator());
        HomeNoticeAdapter homeNoticeAdapter = new HomeNoticeAdapter(noticeList, this);
        rvUpComingNotice.setAdapter(homeNoticeAdapter);


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
