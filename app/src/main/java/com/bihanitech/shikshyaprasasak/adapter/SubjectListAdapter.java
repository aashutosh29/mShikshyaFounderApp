package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.examResult.Mark;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile.StudentProfileView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectViewHolder> {
    StudentProfileView studentProfileView;
    List<Mark> markList;

    public SubjectListAdapter(List<Mark> subjectList, StudentProfileView studentProfileView) {
        this.markList = subjectList;
        this.studentProfileView = studentProfileView;
    }

    @NonNull
    @Override
    public SubjectListAdapter.SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam_result_subject, parent, false);
        SubjectListAdapter.SubjectViewHolder subjectViewHolder = new SubjectListAdapter.SubjectViewHolder(view);

        return subjectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListAdapter.SubjectViewHolder holder, final int position) {


        holder.tvNameOfSubject.setText(String.valueOf(markList.get(position).getSubname()));
        holder.tvScore.setText(markList.get(position).getStgrade());


    }

    @Override
    public int getItemCount() {
        return markList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNameOfSubject)
        TextView tvNameOfSubject;
        @BindView(R.id.tvScore)
        TextView tvScore;


        public SubjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
