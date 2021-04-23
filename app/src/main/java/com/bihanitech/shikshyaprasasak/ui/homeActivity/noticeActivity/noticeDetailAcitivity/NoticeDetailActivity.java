package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.noticeDetailAcitivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvDetail)
    TextView tvDetail;

    @BindView(R.id.tvDate)
    TextView tvDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);


        setUpNoticeDetail();
    }

    @OnClick(R.id.ivBack)
    void ivBackPressed() {
        onBackPressed();
    }

    private void setUpNoticeDetail() {
        tvTitle.setText(Html.fromHtml(getIntent().getStringExtra(Constant.NOTICE_TITLE)));
        tvDetail.setText(Html.fromHtml(getIntent().getStringExtra(Constant.NOTICE_DETAIL)));
        tvDate.setText(getIntent().getStringExtra(Constant.NOTICE_DATE));
    }


}
