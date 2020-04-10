package com.bihanitech.shikshyaprasasak.adapter;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.itemModels.NoticeItem;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{

    List<NoticeItem> noticeItems;
    NoticeView noticeView;

    public NoticeAdapter(List<NoticeItem> noticeItems, NoticeView view){
        this.noticeItems = noticeItems;
        this.noticeView = view;
    }


    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,parent,false);

        NoticeViewHolder viewHolder = new NoticeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, final int position) {
        holder.tvTitle.setText(noticeItems.get(position).getTitle());
        holder.tvDate.setText(convertDate(noticeItems.get(position).getpDate()));
        holder.tvDetail.setText(noticeItems.get(position).getDetail());
        holder.cvNotice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                noticeView.sendToDetailView(noticeItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeItems.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cvNotice)
        ConstraintLayout cvNotice;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvDetail)
        TextView tvDetail;


        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public String convertDate(String sdate) {
/*

        String[] data = sdate.split(" ");
        String[] dat = data[0].split("-");
        String apple = dat[1] + "/" + dat[2] + "/" + dat[0];
        String newDate = apple + " " + data[1].substring(0, 8) + " " + data[1].substring(8);

*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date nDate = new Date();
        try {

            nDate = sdf.parse(sdate);
        } catch (ParseException e) {

        }

        Long newDateTimeLong = nDate.getTime();


        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                newDateTimeLong,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        return timeAgo.toString();


    }

}
