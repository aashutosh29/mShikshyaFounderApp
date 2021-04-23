package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.UploadNotice;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity.NoticeUploadView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeUploadAdapter extends RecyclerView.Adapter<NoticeUploadAdapter.NoticeUploadViewHolder> {
    NoticeUploadView noticeUploadView;
    List<UploadNotice> uploadNotices;

    public NoticeUploadAdapter(List<UploadNotice> uploadNotices, NoticeUploadView noticeUploadView) {
        this.uploadNotices = uploadNotices;
        this.noticeUploadView = noticeUploadView;
    }


    @NonNull
    @Override
    public NoticeUploadAdapter.NoticeUploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_upload, parent, false);
        return new NoticeUploadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeUploadAdapter.NoticeUploadViewHolder holder, final int position) {
        String toWhom = "n-a";
        switch (uploadNotices.get(position).getCategory()) {
            case 1:
                toWhom = "All";
                break;
            case 2:
                toWhom = "News";
                break;
            case 3:
                toWhom = "Teacher";
                break;
            case 4:
                toWhom = "Parents";
                break;

        }
        holder.tvTitle.setText(uploadNotices.get(position).getTitle());
        holder.tvContent.setText(uploadNotices.get(position).getContent());
        holder.tvToWhom.setText(toWhom);
        holder.cvUnPublishedNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeUploadView.onClickOnItem(uploadNotices.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return uploadNotices.size();
    }

    public static class NoticeUploadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvToWhom)
        TextView tvToWhom;
        @BindView(R.id.cvUnpublishedNotice)
        CardView cvUnPublishedNotice;

        public NoticeUploadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
