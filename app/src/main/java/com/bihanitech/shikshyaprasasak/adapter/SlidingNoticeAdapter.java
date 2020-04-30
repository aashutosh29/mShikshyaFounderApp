package com.bihanitech.shikshyaprasasak.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bihanitech.shikshyaprasasak.R;

import java.util.ArrayList;

public class SlidingNoticeAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    private ArrayList<String> NAMES;

    public SlidingNoticeAdapter(Context context, ArrayList<String> NAMES) {
        this.context = context;
        this.NAMES = NAMES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return NAMES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View textLayout = inflater.inflate(R.layout.notification_layout, view, false);

        assert textLayout != null;
        final TextView tvMainTitle = textLayout
                .findViewById(R.id.tvMainTitle);


        tvMainTitle.setText(NAMES.get(position));

        view.addView(textLayout, 0);

        return textLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
