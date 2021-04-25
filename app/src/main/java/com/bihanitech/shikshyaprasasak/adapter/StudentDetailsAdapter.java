package com.bihanitech.shikshyaprasasak.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;

public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.StudentDetailsViewHolder> {
    AddNoticeView addNoticeView;
    List<Student> students;

    public StudentDetailsAdapter(List<Student> students, AddNoticeView addNoticeView) {
        this.students = students;
        this.addNoticeView = addNoticeView;
    }


    @NonNull
    @Override
    public StudentDetailsAdapter.StudentDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_check, parent, false);
        return new StudentDetailsAdapter.StudentDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentDetailsAdapter.StudentDetailsViewHolder holder, final int position) {
        holder.tvName.setText(students.get(position).getName());
        holder.tvFatherName.setText(students.get(position).getFathername());
        holder.cbStudent.setChecked(students.get(position).getCheckStatus());
        holder.cbStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    students.get(position).setCheckStatus(true);
                } else {
                    addNoticeView.ifUnChecked(students);
                    students.get(position).setCheckStatus(false);

                }
                Log.d(TAG, "onCheckedChanged: " + students.get(position).getCheckStatus());

                addNoticeView.getListOfStudentToSendNotice(students);
            }
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentDetailsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.clStudent)
        ConstraintLayout clStudent;
        @BindView(R.id.cbStudent)
        CheckBox cbStudent;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvFatherName)
        TextView tvFatherName;


        public StudentDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
