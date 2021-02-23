package com.bihanitech.shikshyaprasasak.utility;
/*
 *
 * Copyright (C) 2018 Krishna Kumar Sharma
 *
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class RajeshViewPager extends ViewPager implements ViewPager.PageTransformer {
    public static final String TAG = "KKViewPager";
    private float MAX_SCALE = 0.0f;
    private int mPageMargin;
    private boolean animationEnabled = true;
    private boolean fadeEnabled = false;
    private float fadeFactor = 0.5f;


    public RajeshViewPager(Context context) {
        this(context, null);
    }

    public RajeshViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // clipping should be off on the pager for its children so that they can scale out of bounds.
        setClipChildren(true);
        setClipToPadding(false);
        // to avoid fade effect at the end of the page
        setOverScrollMode(2);
        setPageTransformer(false, this);
        setOffscreenPageLimit(3);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        mPageMargin = dp2px(context.getResources(), (int) (dpWidth / 9.5));
        setPadding(mPageMargin, mPageMargin / 5, mPageMargin, mPageMargin / 5);
    }

    public int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

    public void setAnimationEnabled(boolean enable) {
        this.animationEnabled = enable;
    }

    public void setFadeEnabled(boolean fadeEnabled) {
        this.fadeEnabled = fadeEnabled;
    }

    public void setFadeFactor(float fadeFactor) {
        this.fadeFactor = fadeFactor;
    }

    @Override
    public void setPageMargin(int marginPixels) {
        mPageMargin = marginPixels;
        setPadding(mPageMargin, 0, mPageMargin, 0);
    }

    @Override
    public void transformPage(View page, float position) {
        if (mPageMargin <= 0 || !animationEnabled)
            return;
        page.setPadding(mPageMargin / 3, mPageMargin / 5, mPageMargin / 3, mPageMargin / 5);

        if (MAX_SCALE == 0.0f && position > 0.0f && position < 1.0f) {
            MAX_SCALE = position;
        }
        position = position - MAX_SCALE;
        float absolutePosition = Math.abs(position);
        if (position <= -1.0f || position >= 1.0f) {
            if (fadeEnabled)
                page.setAlpha(fadeFactor);
            // Page is not visible -- stop any running animations

        } else if (position == 0.0f) {
            // Page is selected -- reset any views if necessary
            page.setScaleX((1 + MAX_SCALE));
            page.setScaleY((1 + MAX_SCALE));
            page.setAlpha(1);
        } else {
            page.setScaleX(1 + MAX_SCALE * (1 - absolutePosition));
            page.setScaleY(1 + MAX_SCALE * (1 - absolutePosition));
            if (fadeEnabled)
                page.setAlpha(Math.max(fadeFactor, 1 - absolutePosition));
        }
    }
}
