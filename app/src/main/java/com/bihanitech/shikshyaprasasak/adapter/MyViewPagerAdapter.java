package com.bihanitech.shikshyaprasasak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.ScheduledVisit;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private final Context context;
    //    private MyObject myObject;
    private final List<ScheduledVisit> scheduledVisitList;

    public MyViewPagerAdapter(Context context, List<ScheduledVisit> scheduledVisits) {
        this.context = context;
        this.scheduledVisitList = scheduledVisits;
//        this.images = myObject.getImages();
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_slider_card, collection, false);

        TextView tvDealerName = layout.findViewById(R.id.tvDealerName);
        TextView tvPurpose = layout.findViewById(R.id.tvPurpose);
        TextView tvPostedOn = layout.findViewById(R.id.tvPostedOn);
        ImageView ivStatusImage = layout.findViewById(R.id.ivStatusImage);

        tvDealerName.setText(scheduledVisitList.get(position).getDealerName());
        tvPurpose.setText(scheduledVisitList.get(position).getPurpose());
        tvPostedOn.setText(scheduledVisitList.get(position).getCreatedAt().split(" ")[0]);
        if (scheduledVisitList.get(position).getIsVisit() == 1) {
            ivStatusImage.setImageResource(R.drawable.ic_visited);
        } else {
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return this.scheduledVisitList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
