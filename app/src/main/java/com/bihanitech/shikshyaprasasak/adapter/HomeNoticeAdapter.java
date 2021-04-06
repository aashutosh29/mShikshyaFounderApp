package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.holiday.Holiday;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentView;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.upcomingHoliday.UpcomingHolidayView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeNoticeAdapter extends RecyclerView.Adapter<HomeNoticeAdapter.HomeNoticeViewHolder> {
    HomeFragmentView homeFragmentView;
    UpcomingHolidayView upcomingHolidayView;
    List<Holiday> holidayList;
    boolean isActivity = false;

    public HomeNoticeAdapter(List<Holiday> holidayList, HomeFragmentView homeFragmentView) {
        this.holidayList = holidayList;
        this.homeFragmentView = homeFragmentView;
    }

    public HomeNoticeAdapter(List<Holiday> holidayList, UpcomingHolidayView upcomingHolidayView, boolean isActivity) {
        this.holidayList = holidayList;
        this.upcomingHolidayView = upcomingHolidayView;
        this.isActivity = isActivity;
    }


    @NonNull
    @Override
    public HomeNoticeAdapter.HomeNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_notice, parent, false);
        HomeNoticeAdapter.HomeNoticeViewHolder HomeNoticeViewHolder = new HomeNoticeAdapter.HomeNoticeViewHolder(view);

        return HomeNoticeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNoticeAdapter.HomeNoticeViewHolder holder, final int position) {
        //holder.tvMonthEvent.setText(String.valueOf());
        //holder.tvDateEvent.setText();
        holder.tvTitle1s.setText(holidayList.get(position).getTitle());
        holder.tvStart.setText(holidayList.get(position).getStart());
        holder.tvEnd.setText(holidayList.get(position).getEnd());
        //holder.tvDetail1s.setText(Html.fromHtml(noticeList.get(position).getContent()));

    }

    @Override
    public int getItemCount() {
        if (isActivity)
            return holidayList.size();
        else
            return 3;
    }

    public static class HomeNoticeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvStart)
        TextView tvStart;
        @BindView(R.id.tvEnd)
        TextView tvEnd;
        @BindView(R.id.tvTitle1s)
        TextView tvTitle1s;


        public HomeNoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
