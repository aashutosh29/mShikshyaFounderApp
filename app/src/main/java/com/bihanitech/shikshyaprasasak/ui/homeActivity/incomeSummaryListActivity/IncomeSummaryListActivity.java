package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.IncomeSummaryAdapter;
import com.bihanitech.shikshyaprasasak.model.incomeSummary.Datum;
import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryList;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.FilterDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryActivity.IncomeSummaryActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.SpinKitView;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncomeSummaryListActivity extends AppCompatActivity implements IncomeSummaryListView {

    //viewBinding
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rvList)
    RecyclerView rvList;

    @BindView(R.id.spin_kit)
    SpinKitView spinKit;

    @BindView(R.id.clFilter)
    ConstraintLayout clFilter;

    @BindView(R.id.tvFilter)
    TextView tvFilter;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    //variables
    boolean isScrolling = false;
    boolean init_load = true;
    int currentPage = 0;
    int totalPages = 0;
    int currentItems, totalItems, scrollOutItems;
    String fromDate = "";
    String toDate = "";
    String listDate = "date";
    String title;


    //list
    List<Datum> datum = new ArrayList<>();


    //classes
    SharedPrefsHelper sharedPrefsHelper;
    IncomeSummaryListPresenter incomeSummaryListPresenter;
    LinearLayoutManager llm;
    FragmentManager fm;
    ProgressDFragment progressDFragment;
    IncomeSummaryAdapter incomeSummaryAdapter;
    Intent intent;
    String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_summary_list);
        ButterKnife.bind(this);
        intent = getIntent();
        title = intent.getStringExtra(Constant.TITLE);
        llm = new LinearLayoutManager(this);
        fm = getSupportFragmentManager();
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        token = sharedPrefsHelper.getValue(Constant.TOKEN, "");
        incomeSummaryListPresenter = new IncomeSummaryListPresenter(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doYourUpdate();
            }
        });
        FirstLoad();
        listenScroll();

    }

    private void FirstLoad() {
        tvToolbarTitle.setText(title);
        incomeSummaryListPresenter.fetchIncomeSummaryList(sharedPrefsHelper.getValue(Constant.TOKEN, ""), fromDate, toDate, 1);

    }


    @OnClick(R.id.ivFilter)
    void ivFilterClicked() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDFragment filterDFragment = FilterDFragment.newInstance(Constant.INCOME_SUMMARY, fromDate, toDate);
        filterDFragment.show(fm, Constant.INCOME_SUMMARY);
    }

    @Override
    public void applyFilter(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        if (fromDate.length() > 2 && toDate.length() > 2) {

            clFilter.setVisibility(View.VISIBLE);
            tvFilter.setText(fromDate + " - " + toDate);
        } else {
            clFilter.setVisibility(View.GONE);
        }
        incomeSummaryListPresenter.fetchIncomeSummaryList(sharedPrefsHelper.getValue(Constant.TOKEN, ""), fromDate, toDate, 1);
    }

    @Override
    public void populateIncomeSummaryList(IncomeSummaryList response) {

        if (progressDFragment != null && progressDFragment.isAdded()) {
            progressDFragment.dismissAllowingStateLoss();
        }
        if (response.getPageno() == 1) {
            init_load = true;
            listDate = "";
        }
        this.currentPage = response.getPageno();

        if (init_load) {
            if (response.getData().size() == 0) {
                tvEmpty.setVisibility(View.VISIBLE);
                rvList.setVisibility(View.GONE);

            } else {
                datum = response.getData();
                tvEmpty.setVisibility(View.GONE);
                this.currentPage = response.getPageno();

                //here
                totalPages = response.getTotalpages();
                spinKit.setVisibility(View.GONE);
                datum = response.getData();
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rvList.setLayoutManager(llm);
                rvList.setItemAnimator(new DefaultItemAnimator());
                incomeSummaryAdapter = new IncomeSummaryAdapter(datum, this);
                rvList.setAdapter(incomeSummaryAdapter);
                init_load = false;
            }
        } else {
            datum.addAll(response.getData());
            incomeSummaryAdapter.notifyDataSetChanged();
            spinKit.setVisibility(View.GONE);

        }

    }

    @OnClick(R.id.ivCancelFilter)
    public void clearFilterClicked() {
        clFilter.setVisibility(View.GONE);
        fromDate = "";
        toDate = "";
        FirstLoad();
    }


    private void doYourUpdate() {
        listDate = "date";
        init_load = true;
        datum = new ArrayList<Datum>();
        currentPage = 1;
        incomeSummaryListPresenter.fetchIncomeSummaryList(sharedPrefsHelper.getValue(Constant.TOKEN, ""), fromDate, toDate, currentPage);
        swipeRefreshLayout.setRefreshing(false);
    }

    void listenScroll() {
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void getData() {
        if (totalPages > currentPage) {
            spinKit.setVisibility(View.VISIBLE);
            init_load = false;
            incomeSummaryListPresenter.fetchIncomeSummaryList(sharedPrefsHelper.getValue(Constant.TOKEN, ""), fromDate, toDate, currentPage + 1);
        }
    }

    @OnClick(R.id.ivBack)
    void ivBackPressed() {
        onBackPressed();
    }

    @Override
    public void showLoading() {
        progressDFragment = ProgressDFragment.newInstance("Collecting Data...");
        progressDFragment.show(fm, "Authenticate_credential");
    }

    @Override
    public void showServerError() {
        if (progressDFragment != null) {
            progressDFragment.dismissAllowingStateLoss();
        }
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR, HomeActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "ServerError");
    }

    @Override
    public void showNetworkError() {
        if (progressDFragment != null) {
            progressDFragment.dismissAllowingStateLoss();
        }
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETWORK_ERROR, HomeActivity.class.getSimpleName());
        networkErrorDFragment.show(fm, "NetworkError");
    }

    @Override
    public void getIncomeSummaryActivity(Datum datum) {
        Intent intent = new Intent(IncomeSummaryListActivity.this, IncomeSummaryActivity.class);
        intent.putExtra(Constant.TITLE, title);
        intent.putExtra(Constant.S_N, datum.getHeadingid());
        intent.putExtra(Constant.DATE, datum.getTrantime());
        intent.putExtra(Constant.NAME_OF_STUDENT, datum.getParticular());
        intent.putExtra(Constant.ClASS_INFORMATION, "n-a");
        intent.putExtra(Constant.AMOUNT, datum.getCramt());
        intent.putExtra(Constant.PENALTY, "n-a");
        intent.putExtra(Constant.DISCOUNT, "n-a");
        intent.putExtra(Constant.PRE_BALANCE, "n-a");
        intent.putExtra(Constant.SUB_TOTAL, "n-a");
        intent.putExtra(Constant.PAID, "n-a");
        intent.putExtra(Constant.DUES, "n-a");
        intent.putExtra(Constant.USER, datum.getTranuser());
        intent.putExtra(Constant.TIME, datum.getTrantime());
        intent.putExtra(Constant.REC_NO, datum.getRegno());
        startActivity(intent);


    }

}
