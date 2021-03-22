package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.Subject;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile.StudentProfileView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectViewHolder> {
    StudentProfileView studentProfileView;
    List<Subject> subjectList;

    public SubjectListAdapter(List<Subject> subjectList, StudentProfileView studentProfileView) {
        this.subjectList = subjectList;
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
        holder.tvNameOfSubject.setText(String.valueOf(subjectList.get(position).getSubjectName()));
        holder.tvScore.setText(subjectList.get(position).getScore());


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
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
