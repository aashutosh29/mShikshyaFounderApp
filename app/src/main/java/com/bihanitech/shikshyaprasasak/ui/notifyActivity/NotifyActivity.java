package com.bihanitech.shikshyaprasasak.ui.notifyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.NotifyAdapter;
import com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper;
import com.bihanitech.shikshyaprasasak.model.Notify;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifyActivity extends AppCompatActivity implements NotifyView {

    FragmentManager fm;
    SharedPrefsHelper sharedPrefsHelper;
    ProgressDFragment progressDFragment;
    LinearLayoutManager llm;
    Boolean init_Load = true;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;

    @BindView(R.id.rvNotifyList)
    RecyclerView rvNotifyList;

    @BindView(R.id.tvUnreadNotification)
    TextView tvUnreadNotification;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    @BindView(R.id.clEmptyList)
    ConstraintLayout clEmptyList;


    @BindView(R.id.spin_kit)
    SpinKitView spinKit;

    @BindView(R.id.ivBack)
    ImageView ivBack;


    @BindView(R.id.ivHome)
    ImageView ivHome;

    NotifyPresenter notifyPresenter;
    int currentPage = 0;
    int totalPages = 0;
    String listDate = "date";
    List<Notify> notifies = new ArrayList<>();
    private NotifyAdapter notifyAdapter;


    public NotifyActivity() {
        // Required empty public constructorn
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notify);
        ButterKnife.bind(this);

        fm = this.getSupportFragmentManager();
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        notifyPresenter = new NotifyPresenter(this);
        tvEmpty.setVisibility(View.GONE);
        rvNotifyList.setVisibility(View.VISIBLE);
        llm = new LinearLayoutManager(this);
        progressDFragment = ProgressDFragment.newInstance("Fetching Notification...");
        toolbar();
        doYourUpdate();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doYourUpdate();
                    }
                }
        );
        rvNotifyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = llm.getChildCount();
                totalItems = llm.getItemCount();
                scrollOutItems = llm.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    getData();
                }
            }
        });

    }


    private void doYourUpdate() {
        // TODO implement a refresh
        init_Load = true;
        notifyPresenter.fetchNoticeList(sharedPrefsHelper.getValue(Constant.USER_ID, 0), 1, sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        swipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }


    @Override
    public void Perlogout() {

    }

    @Override
    public void showServerError() {
        if (progressDFragment != null && progressDFragment.isAdded()) {
            progressDFragment.dismissAllowingStateLoss();

        }
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, NotifyActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "ServerError");

    }

    @Override
    public void showNetworkError() {

        if (progressDFragment != null && progressDFragment.isAdded()) {
            progressDFragment.dismissAllowingStateLoss();

        }
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETWORK_ERROR, NotifyActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "NetworkError");

    }

    @Override
    public void emptyNotices() {

        if (progressDFragment != null && progressDFragment.isAdded()) {
            progressDFragment.dismissAllowingStateLoss();

        }

        clEmptyList.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoading() {
        progressDFragment.show(fm, "fetch_notice_and_task");

    }

    @Override
    public void showNoticeList(List<Notify> response, Integer currentPage, Integer totalPages) {
        clEmptyList.setVisibility(View.GONE);
        this.currentPage = currentPage;
        this.totalPages = totalPages;

        if (init_Load == true) {
            if (progressDFragment != null && progressDFragment.isAdded()) {
                progressDFragment.dismissAllowingStateLoss();
            }
            if (response.size() == 0) {
                tvEmpty.setVisibility(View.VISIBLE);
                rvNotifyList.setVisibility(View.GONE);
            } else {
                notifies = response;
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rvNotifyList.setLayoutManager(llm);
            }

            //new code here
            notifyAdapter = new NotifyAdapter(notifies, this, R.layout.item_notify);
            rvNotifyList.setAdapter(notifyAdapter);
            rvNotifyList.setLayoutManager(llm);
            rvNotifyList.setItemAnimator(new DefaultItemAnimator());

        } else {
            notifies.addAll(response);
            notifyAdapter.notifyDataSetChanged();
            spinKit.setVisibility(View.GONE);

        }

    }

    @Override
    public void correspondingActivity(String flag) {
        Intent i;
        i = new Intent(NotifyActivity.this, HomeActivity.class);
        i.putExtra(Constant.NOTIFICATION_FLAG, flag);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }


    private void getData() {

        if (totalPages > currentPage) {
            spinKit.setVisibility(View.VISIBLE);
            init_Load = false;
            notifyPresenter.fetchNoticeList(sharedPrefsHelper.getValue(Constant.USER_ID, 0), currentPage + 1, sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        }
    }

    public void toolbar() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvToolbarTitle.setText("Notification");
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotifyActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

    }
}
