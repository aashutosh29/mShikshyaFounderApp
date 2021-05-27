package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.TitleWise;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.statement.StatementView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.StatementViewHolder> {
    StatementView statementView;
    List<TitleWise> titleWises;

    public StatementAdapter(List<TitleWise> titleWises, StatementView statementView) {
        this.titleWises = titleWises;
        this.statementView = statementView;
    }


    @NonNull
    @Override
    public StatementAdapter.StatementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_wise, parent, false);
        return new StatementAdapter.StatementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatementAdapter.StatementViewHolder holder, final int position) {
        holder.tvParticulars.setText(titleWises.get(position).getParticular());
        holder.tvBalance.setText(titleWises.get(position).getTotalpaid() + "");

        holder.cvStatmentItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                statementView.sendToDetailActivity(titleWises.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return titleWises.size();
    }

    public static class StatementViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvParticulars)
        TextView tvParticulars;
        @BindView(R.id.tvBalance)
        TextView tvBalance;
        @BindView(R.id.cvStatementItem)
        ConstraintLayout cvStatmentItem;

        public StatementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
