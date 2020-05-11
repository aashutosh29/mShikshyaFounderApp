package com.bihanitech.shikshyaprasasak.adapter.calendarAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.HolidaysViewHolder> {

    private List<Holiday> examRoutineItemList;

    public HolidaysAdapter(List<Holiday> examRoutineItemList) {
        this.examRoutineItemList = examRoutineItemList;
    }


    @NonNull
    @Override
    public HolidaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam_routine, parent, false);
        HolidaysViewHolder examRoutineViewHolder = new HolidaysViewHolder(view);
        return examRoutineViewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull HolidaysViewHolder holder, int position) {
        holder.tvSubject.setText(examRoutineItemList.get(position).getDetail());
        holder.tvDate.setText(examRoutineItemList.get(position).getNepDay() + "");
    }

    @Override
    public int getItemCount() {
        return examRoutineItemList.size();
    }

    public static class HolidaysViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSubject)
        TextView tvSubject;

        @BindView(R.id.tvDate)
        TextView tvDate;

        public HolidaysViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
