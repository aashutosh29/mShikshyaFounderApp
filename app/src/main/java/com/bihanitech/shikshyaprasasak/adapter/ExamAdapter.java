package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.examResult.Result;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile.StudentProfileView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {
    StudentProfileView studentProfileView;
    List<Result> results;

    public ExamAdapter(List<Result> results, StudentProfileView studentProfileView) {
        this.results = results;
        this.studentProfileView = studentProfileView;
    }

    @NonNull
    @Override
    public ExamAdapter.ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam_results, parent, false);
        ExamAdapter.ExamViewHolder examViewHolder = new ExamAdapter.ExamViewHolder(view);

        return examViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAdapter.ExamViewHolder holder, final int position) {
        holder.tvResultTitle.setText(results.get(position).getStdexam() + "(" + results.get(position).getExamyear() + ")");
        holder.tvGrade.setText(results.get(position).getStddivgrade());
        holder.tvGPA.setText(results.get(position).getStgpa());
        holder.tvRemarks.setText(results.get(position).getStremakrs());
        holder.ivDownUPArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentProfileView.ivDownUpArrowClicked(results.get(position), holder.clSubjects, holder.ivDownUPArrow, holder.rvSubjects);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvResultTitle)
        TextView tvResultTitle;
        @BindView(R.id.tvGrade)
        TextView tvGrade;
        @BindView(R.id.tvGPA)
        TextView tvGPA;
        @BindView(R.id.tvRemarks)
        TextView tvRemarks;
        @BindView(R.id.rvSubjects)
        RecyclerView rvSubjects;
        @BindView(R.id.ivDownUPArrow)
        ImageView ivDownUPArrow;
        @BindView(R.id.clSubjects)
        ConstraintLayout clSubjects;


        public ExamViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
