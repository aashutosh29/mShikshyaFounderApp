package com.bihanitech.shikshyaprasasak.ui.homeActivity.accountDetailsActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvSn)
    TextView tvSn;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvParticulars)
    TextView tvParticulars;

    @BindView(R.id.tvAmount)
    TextView tvAmount;

    @BindView(R.id.tvVoucher)
    TextView tvVoucher;

    @BindView(R.id.tvVoucherTitle)
    TextView tvVoucherTitle;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarTitle.setText("Statement Detail");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        setUpLabels();

    }

    private void setUpLabels() {
        tvSn.setText("S.N : " + getIntent().getIntExtra(Constant.SNO, 0) + "");
        tvDate.setText(getIntent().getStringExtra(Constant.DATE));
        tvParticulars.setText(getIntent().getStringExtra(Constant.PARTICULARS));
        tvAmount.setText("" + getIntent().getStringExtra(Constant.AMOUNT));
        if (!getIntent().getStringExtra(Constant.VOUCHER_NO).equalsIgnoreCase("")) {
            tvVoucher.setVisibility(View.VISIBLE);
            tvVoucherTitle.setVisibility(View.VISIBLE);
            tvVoucher.setText("" + getIntent().getStringExtra(Constant.VOUCHER_NO));
        }
    }
}
