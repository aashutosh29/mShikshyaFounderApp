package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.incomeSummary.IncomeSummaryReport;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity.IncomeSummaryListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeSummaryAdapter extends RecyclerView.Adapter<IncomeSummaryAdapter.IncomeSummaryViewHolder> {
    List<IncomeSummaryReport> incomeSummaryReportList;
    IncomeSummaryListView incomeSummaryListView;

    public IncomeSummaryAdapter(List<IncomeSummaryReport> incomeSummaryReportList, IncomeSummaryListView incomeSummaryListView) {
        this.incomeSummaryReportList = incomeSummaryReportList;
        this.incomeSummaryListView = incomeSummaryListView;

    }

    @NonNull
    @Override
    public IncomeSummaryAdapter.IncomeSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income_summary, parent, false);

        return new IncomeSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeSummaryAdapter.IncomeSummaryViewHolder holder, final int position) {
        holder.tvSn.setText(String.valueOf(incomeSummaryReportList.get(position).getSn()));
        holder.tvDate.setText(incomeSummaryReportList.get(position).getDate());
        holder.tvAmount.setText("Rs." + incomeSummaryReportList.get(position).getAmount());
        holder.tvBalance.setText("Rs." + incomeSummaryReportList.get(position).getPreBalance());
        holder.tvParticulars.setText(incomeSummaryReportList.get(position).getNameOfStudent());
        holder.cvIncomeSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeSummaryListView.getIncomeSummaryActivity(incomeSummaryReportList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return incomeSummaryReportList.size();
    }

    public static class IncomeSummaryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvSn)
        TextView tvSn;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvParticulars)
        TextView tvParticulars;
        @BindView(R.id.tvBalance)
        TextView tvBalance;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.cvIncomeSummary)
        CardView cvIncomeSummary;


        public IncomeSummaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
