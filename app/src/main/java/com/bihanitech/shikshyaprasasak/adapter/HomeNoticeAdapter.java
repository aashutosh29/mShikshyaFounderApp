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

        String[] dateParts = holidayList.get(position).getStartNepaliDate().split("-");
        String[] datePartsEnd = holidayList.get(position).getEndNepaliDate().split("-");

        String year = dateParts[0];
        String yearEnd = datePartsEnd[0];
        String sMonth;
        String sMonthEnd;
        int month = Integer.parseInt(dateParts[1].trim());
        int monthEnd = Integer.parseInt(datePartsEnd[1].trim());

        String day = dateParts[2];
        String dayEnd = datePartsEnd[2];
        if (month == 0) {
            sMonth = "Baisakh";
        } else if (month == 1) {
            sMonth = "Jestha)";
        } else if (month == 2) {
            sMonth = "Ashar";

        } else if (month == 3) {
            sMonth = "Shrawan";

        } else if (month == 4) {
            sMonth = "Bhadra";

        } else if (month == 5) {
            sMonth = "Ashoj";

        } else if (month == 6) {
            sMonth = "Kartik";

        } else if (month == 7) {
            sMonth = "Mangsir";

        } else if (month == 8) {
            sMonth = "Poush";

        } else if (month == 9) {
            sMonth = "Magh";

        } else if (month == 10) {
            sMonth = "Falgun";

        } else {
            sMonth = "Chaitra";
        }

        if (monthEnd == 0) {
            sMonthEnd = "Baisakh";
        } else if (monthEnd == 1) {
            sMonthEnd = "Jestha)";
        } else if (monthEnd == 2) {
            sMonthEnd = "Ashar";

        } else if (monthEnd == 3) {
            sMonthEnd = "Shrawan";

        } else if (monthEnd == 4) {
            sMonthEnd = "Bhadra";

        } else if (monthEnd == 5) {
            sMonthEnd = "Ashoj";

        } else if (monthEnd == 6) {
            sMonthEnd = "Kartik";

        } else if (monthEnd == 7) {
            sMonthEnd = "Mangsir";

        } else if (monthEnd == 8) {
            sMonthEnd = "Poush";

        } else if (monthEnd == 9) {
            sMonthEnd = "Magh";

        } else if (monthEnd == 10) {
            sMonthEnd = "Falgun";

        } else {
            sMonthEnd = "Chaitra";
        }


        holder.tvTitle1s.setText(holidayList.get(position).getTitle());
        holder.tvStart.setText(sMonth + " " + day + ", " + year);
        holder.tvEnd.setText(sMonthEnd + " " + dayEnd + ", " + year);
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
