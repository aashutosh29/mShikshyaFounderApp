package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.NoticeAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.noticeDetailAcitivity.NoticeDetailActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeActivity extends AppCompatActivity implements NoticeView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rvNotices)
    RecyclerView rvNotices;

    @BindView(R.id.clNoNotice)
    ConstraintLayout clNoNotice;

    NoticePresenter noticePresenter;
    SharedPrefsHelper sharedPrefsHelper;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    FragmentManager fm;
    ProgressDFragment progressDFragment;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        noticePresenter = new NoticePresenter(this, new DbInternalRepo(getDatabaseHelper()));
        swipeRefreshLayout.setOnRefreshListener(this);
        fm = getSupportFragmentManager();

        if (!sharedPrefsHelper.getValue(Constant.NOTICE_UPTO_DATE, false)) {
            setUpNoticeList();
        } else {
            noticePresenter.getNoticeFromDb();
        }

    }

    private void setUpNoticeList() {
        swipeRefreshLayout.setRefreshing(false);
        progressDFragment = ProgressDFragment.newInstance("Loading Notices");
        progressDFragment.show(fm, "Notices");
        noticePresenter.fetchNoticeList(sharedPrefsHelper.getValue(Constant.TOKEN, ""));

    }

    @Override
    public void proceedAfterDownload() {
        sharedPrefsHelper.saveValue(Constant.NOTICE_UPTO_DATE, true);
        noticePresenter.getNoticeFromDb();

    }

    @OnClick(R.id.ivBack)
    void ivBackPressed() {
        onBackPressed();
    }

    @Override
    public void populateList(List<NoticeItem> noticeItems) {
        if (progressDFragment != null)
            progressDFragment.dismiss();

        sharedPrefsHelper.saveValue(Constant.NOTICE_COUNT, noticeItems.size());

        if (noticeItems.size() == 0) {
            clNoNotice.setVisibility(View.VISIBLE);
            rvNotices.setVisibility(View.INVISIBLE);
        } else {
            clNoNotice.setVisibility(View.INVISIBLE);
            rvNotices.setVisibility(View.VISIBLE);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvNotices.setLayoutManager(llm);
            NoticeAdapter recyclerAdapter = new NoticeAdapter(noticeItems, this);
            rvNotices.setAdapter(recyclerAdapter);

        }

        if (noticeItems.size() > 0) {

            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE1, noticeItems.get(0).getTitle());
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL1, noticeItems.get(0).getDetail());
            sharedPrefsHelper.saveValue(Constant.P_DATE1, noticeItems.get(0).getpDate());
        }

        if (noticeItems.size() > 1) {
            sharedPrefsHelper.saveValue(Constant.NOTICE_TITLE2, noticeItems.get(1).getTitle());
            sharedPrefsHelper.saveValue(Constant.NOTICE_DETAIL2, noticeItems.get(1).getDetail());
            sharedPrefsHelper.saveValue(Constant.P_DATE2, noticeItems.get(1).getpDate());


        }
        //    Collections.reverse(noticeItems);

    }

    @Override
    public void sendToDetailView(NoticeItem noticeItem) {
        Intent i = new Intent(this, NoticeDetailActivity.class);
        i.putExtra(Constant.NOTICE_TITLE, noticeItem.getTitle());
        i.putExtra(Constant.NOTICE_DETAIL, noticeItem.getDetail());
        i.putExtra(Constant.NOTICE_DATE, noticeItem.getpDate());
        startActivity(i);
    }

    @Override
    public void showNetworkError() {
        progressDFragment.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR, NoticeActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "NetworkError");

    }

    @Override
    public void showServerError() {
        progressDFragment.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, NoticeActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "NetworkError");

    }

    private DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null)
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null)
            OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }

    @Override
    public void onRefresh() {
        setUpNoticeList();
    }

    public void retry() {
        setUpNoticeList();
    }
}
