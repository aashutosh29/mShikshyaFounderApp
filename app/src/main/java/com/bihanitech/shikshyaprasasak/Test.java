package com.bihanitech.shikshyaprasasak;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bihanitech.shikshyaprasasak.adapter.MyViewPagerAdapter;
import com.bihanitech.shikshyaprasasak.model.ScheduledVisit;
import com.bihanitech.shikshyaprasasak.utility.RajeshViewPager;

import java.util.List;

import butterknife.BindView;

public class Test extends AppCompatActivity {
    @BindView(R.id.rvScheduledVisit)
    RajeshViewPager rvScheduledVisit;

    MyViewPagerAdapter myViewPagerAdapter;
    @BindView(R.id.clSlider)
    ConstraintLayout clSlider;

    List<ScheduledVisit> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        clSlider.setVisibility(View.VISIBLE);


        myViewPagerAdapter = new MyViewPagerAdapter(getApplicationContext(), item);
        rvScheduledVisit.setAdapter(myViewPagerAdapter);
        rvScheduledVisit.setFadeEnabled(true);
        rvScheduledVisit.setFadeFactor(.5f);
        rvScheduledVisit.setAnimationEnabled(true);
    }
}
