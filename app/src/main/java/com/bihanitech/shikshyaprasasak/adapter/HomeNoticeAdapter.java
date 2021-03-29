package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.Notice;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeNoticeAdapter extends RecyclerView.Adapter<HomeNoticeAdapter.HomeNoticeViewHolder> {
    HomeFragmentView homeFragmentView;
    List<Notice> noticeList;

    public HomeNoticeAdapter(List<Notice> noticeList, HomeFragmentView homeFragmentView) {
        this.noticeList = noticeList;
        this.homeFragmentView = homeFragmentView;
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
        holder.tvTitle1s.setText(noticeList.get(position).getTitle());
        holder.tvDetail1s.setText(noticeList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class HomeNoticeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMonthEvent)
        TextView tvMonthEvent;
        @BindView(R.id.tvDateEvent)
        TextView tvDateEvent;
        @BindView(R.id.tvTitle1s)
        TextView tvTitle1s;
        @BindView(R.id.tvDetail1s)
        TextView tvDetail1s;


        public HomeNoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
