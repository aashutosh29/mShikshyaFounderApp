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
import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryList;
import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryReport;
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

    //variables
    boolean isScrolling = false;
    boolean init_load = true;
    int currentPage = 0;
    int totalPages = 0;
    int currentItems, totalItems, scrollOutItems;
    String fromDate = "";
    String toDate = "";
    String listDate = "date";


    //list
    List<IncomeSummaryReport> incomeSummaryList = new ArrayList<>();


    //classes
    SharedPrefsHelper sharedPrefsHelper;
    IncomeSummaryListPresenter incomeSummaryListPresenter;
    LinearLayoutManager llm;
    FragmentManager fm;
    ProgressDFragment progressDFragment;
    IncomeSummaryAdapter incomeSummaryAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_summary_list);
        ButterKnife.bind(this);
        llm = new LinearLayoutManager(this);
        fm = getSupportFragmentManager();
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
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
                incomeSummaryList = response.getData();
                tvEmpty.setVisibility(View.GONE);
                this.currentPage = response.getPageno();

                //here
                totalPages = response.getTotalpages();
                spinKit.setVisibility(View.GONE);
                incomeSummaryList = response.getData();
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rvList.setLayoutManager(llm);
                rvList.setItemAnimator(new DefaultItemAnimator());
                incomeSummaryAdapter = new IncomeSummaryAdapter(incomeSummaryList, this);
                rvList.setAdapter(incomeSummaryAdapter);
                init_load = false;
            }
        } else {
            incomeSummaryList.addAll(response.getData());
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
        incomeSummaryList = new ArrayList<com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryReport>();
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
    public void getIncomeSummaryActivity(IncomeSummaryReport incomeSummaryReport) {
        Intent intent = new Intent(IncomeSummaryListActivity.this, IncomeSummaryActivity.class);
        intent.putExtra(Constant.S_N, incomeSummaryReport.getSn());
        intent.putExtra(Constant.DATE, incomeSummaryReport.getDate());
        intent.putExtra(Constant.NAME_OF_STUDENT, incomeSummaryReport.getNameOfStudent());
        intent.putExtra(Constant.ClASS_INFORMATION, incomeSummaryReport.getClassInformation());
        intent.putExtra(Constant.AMOUNT, incomeSummaryReport.getAmount());
        intent.putExtra(Constant.PENALTY, incomeSummaryReport.getPenalty());
        intent.putExtra(Constant.DISCOUNT, incomeSummaryReport.getDiscount());
        intent.putExtra(Constant.PRE_BALANCE, incomeSummaryReport.getPreBalance());
        intent.putExtra(Constant.SUB_TOTAL, incomeSummaryReport.getSubBalance());
        intent.putExtra(Constant.PAID, incomeSummaryReport.getPaid());
        intent.putExtra(Constant.DUES, incomeSummaryReport.getDues());
        intent.putExtra(Constant.USER, incomeSummaryReport.getUser());
        intent.putExtra(Constant.TIME, incomeSummaryReport.getTime());
        intent.putExtra(Constant.REC_NO, incomeSummaryReport.getRecordNo());
        startActivity(intent);


    }

}
