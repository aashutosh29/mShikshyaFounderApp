package com.bihanitech.shikshyaprasasak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.account.Account;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile.StudentProfileView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    StudentProfileView studentProfileView;
    List<Account> statementsList;

    public AccountAdapter(List<Account> statementsList, StudentProfileView studentProfileView) {
        this.statementsList = statementsList;
        this.studentProfileView = studentProfileView;
    }

    @NonNull
    @Override
    public AccountAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statement, parent, false);
        AccountAdapter.AccountViewHolder accountViewHolder = new AccountAdapter.AccountViewHolder(view);

        return accountViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.AccountViewHolder holder, final int position) {
        holder.tvSn.setText(String.valueOf(statementsList.get(position).getVchrno()));
        holder.tvDate.setText(statementsList.get(position).getTransactionDate());
        holder.tvParticulars.setText(statementsList.get(position).getParticular());
        holder.tvAmount.setText(statementsList.get(position).getDr());
        holder.tvBalance.setText(statementsList.get(position).getCr());

    }

    @Override
    public int getItemCount() {
        return statementsList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSn)
        TextView tvSn;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvParticulars)
        TextView tvParticulars;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvBalance)
        TextView tvBalance;


        public AccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
