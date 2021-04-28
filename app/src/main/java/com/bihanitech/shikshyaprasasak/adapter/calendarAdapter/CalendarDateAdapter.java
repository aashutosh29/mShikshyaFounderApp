package com.bihanitech.shikshyaprasasak.adapter.calendarAdapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.itemModels.CDayItem;
import com.bihanitech.shikshyaprasasak.utility.NepCalendar.Model;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarDateAdapter extends RecyclerView.Adapter<CalendarDateAdapter.CalendarDateViewHolder> {


    Model todayNepDate;
    private final List<CDayItem> days;

    public CalendarDateAdapter(List<CDayItem> days, Model todayNepDate) {
        this.days = days;
        this.todayNepDate = todayNepDate;
    }

    @NonNull
    @Override
    public CalendarDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        CalendarDateViewHolder calendarDateViewHolder = new CalendarDateViewHolder(view);
        return calendarDateViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarDateViewHolder holder, int position) {
        String eDate = "";
        String nDate = "";
        eDate = days.get(position).getEnglishDate() == 0 ? "" : days.get(position).getEnglishDate() + "";
        nDate = days.get(position).getNepDate() == 0 ? "" : days.get(position).getNepDate() + "";
        holder.tvEDate.setText(eDate);
        holder.tvNDate.setText(nDate);

        if (days.get(position).getCurrentDate() == 1) {
            holder.clDate.setBackgroundColor(Color.parseColor("#fafafa"));
            holder.tvEDate.setTextColor(Color.parseColor("#036C99"));
            holder.tvNDate.setTextColor(Color.parseColor("#036C99"));

        } else {
            holder.clDate.setBackgroundColor(Color.parseColor("#036C99"));
            if (days.get(position).getDay() == 7) {
                holder.tvEDate.setTextColor(Color.RED);
                holder.tvNDate.setTextColor(Color.RED);
            } else {
                holder.tvEDate.setTextColor(Color.WHITE);
                holder.tvNDate.setTextColor(Color.parseColor("#fafafa"));
            }
        }


        if (days.get(position).getDay() == 7) {
            holder.tvEDate.setTextColor(Color.RED);
            holder.tvNDate.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class CalendarDateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvEDate)
        TextView tvEDate;

        @BindView(R.id.tvNDate)
        TextView tvNDate;

        @BindView(R.id.clDate)
        ConstraintLayout clDate;

        public CalendarDateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
