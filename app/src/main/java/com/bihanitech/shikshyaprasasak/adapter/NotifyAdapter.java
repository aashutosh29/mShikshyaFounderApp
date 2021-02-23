package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.Notify;
import com.bihanitech.shikshyaprasasak.ui.notifyActivity.NotifyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aashutosh rimal on 31/01/21.
 */

public class NotifyAdapter extends RecyclerView.Adapter<com.bihanitech.shikshyaprasasak.adapter.NotifyAdapter.NotifyViewHolder> {

    private final List<Notify> notifyList;
    private final NotifyView notifyView;
    int itemLayout;

    public NotifyAdapter(List<Notify> notifyList, NotifyView notifyView, int itemLayout) {
        this.notifyList = notifyList;
        this.notifyView = notifyView;
        this.itemLayout = itemLayout;
    }

    @Override
    public NotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        return new NotifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotifyViewHolder holder, final int position) {
        holder.tvTitleNotify.setText(notifyList.get(position).getTitle());
        holder.tvBodyNotification.setText(notifyList.get(position).getNotice());
        holder.tvDate.setText(notifyList.get(position).getDate());
        holder.clParentOfNotify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                notifyView.correspondingActivity(notifyList.get(position).getFlag());


            }
        });
    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    public static class NotifyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitleNotify)
        TextView tvTitleNotify;
        @BindView(R.id.tvBodyNotification)
        TextView tvBodyNotification;
        @BindView(R.id.clParentOfNotify)
        ConstraintLayout clParentOfNotify;
        @BindView(R.id.tvDate)
        TextView tvDate;


        public NotifyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
