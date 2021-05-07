package com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bihanitech.shikshyaprasasak.R;

public class IncomeSummaryActivity extends AppCompatActivity implements IncomeSummaryView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_summary);
    }
}
