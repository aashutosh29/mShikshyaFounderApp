package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.noticeDetailAcitivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvDetail)
    TextView tvDetail;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setUpNoticeDetail();
    }

    private void setUpNoticeDetail() {
        tvTitle.setText(getIntent().getStringExtra(Constant.NOTICE_TITLE));
        tvDetail.setText(getIntent().getStringExtra(Constant.NOTICE_DETAIL));
        tvDate.setText(getIntent().getStringExtra(Constant.NOTICE_DATE));
    }


}
