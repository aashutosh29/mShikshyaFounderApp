package com.bihanitech.shikshyaprasasak.ui.homeActivity.dailyIncomeListActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bihanitech.shikshyaprasasak.R;

import butterknife.ButterKnife;

public class DailyIncomeListActivity extends AppCompatActivity implements DailyIncomeListView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_income_list);
        ButterKnife.bind(this);
    }
}
