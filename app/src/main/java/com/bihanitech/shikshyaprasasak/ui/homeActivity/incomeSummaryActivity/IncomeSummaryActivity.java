package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncomeSummaryActivity extends AppCompatActivity implements IncomeSummaryView {

    @BindView(R.id.tvSn)
    TextView tvSn;

    @BindView(R.id.tvParticulars)
    TextView tvParticulars;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvClassInformation)
    TextView tvClassInformation;

    @BindView(R.id.tvAmount)
    TextView tvAmount;

    @BindView(R.id.tvVoucher)
    TextView tvVoucher;

    @BindView(R.id.tvPenalty)
    TextView tvPenalty;

    @BindView(R.id.tvDiscount)
    TextView tvDiscount;

    @BindView(R.id.tvPreBalance)
    TextView tvPreBalance;

    @BindView(R.id.tvSubtotal)
    TextView tvSubtotal;

    @BindView(R.id.tvPaid)
    TextView tvPaid;

    @BindView(R.id.tvDues)
    TextView tvDues;

    @BindView(R.id.tvUser)
    TextView tvUser;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;


    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_summary_v2);
        ButterKnife.bind(this);
        intent = getIntent();
        getIntentData();
    }

    private void getIntentData() {
        tvSn.setText(intent.getStringExtra(Constant.S_N));
        tvParticulars.setText(intent.getStringExtra(Constant.NAME_OF_STUDENT));
        tvDate.setText(intent.getStringExtra(Constant.DATE));
        tvClassInformation.setText(intent.getStringExtra(Constant.ClASS_INFORMATION));
        tvAmount.setText(intent.getStringExtra(Constant.AMOUNT));
        tvVoucher.setText(intent.getStringExtra(Constant.REC_NO));
        tvPenalty.setText(intent.getStringExtra(Constant.PENALTY));
        tvDiscount.setText(intent.getStringExtra(Constant.DISCOUNT));
        tvPreBalance.setText(intent.getStringExtra(Constant.PRE_BALANCE));
        tvSubtotal.setText(intent.getStringExtra(Constant.SUB_TOTAL));
        tvPaid.setText(intent.getStringExtra(Constant.PAID));
        tvDues.setText(intent.getStringExtra(Constant.DUES));
        tvTime.setText(intent.getStringExtra(Constant.TIME));
        tvUser.setText(intent.getStringExtra(Constant.USER));
        tvToolbarTitle.setText(intent.getStringExtra(Constant.TITLE));


    }

    @OnClick(R.id.ivBack)
    void ivBackClicked() {
        onBackPressed();
    }
}
