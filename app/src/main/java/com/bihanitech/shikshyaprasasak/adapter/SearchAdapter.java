package com.bihanitech.shikshyaprasasak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity.SearchView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private final List<Student> searchProfiles;
    SearchView searchView;
    Context context;

    public SearchAdapter(List<Student> searchProfiles, SearchView searchView, Context context) {
        this.searchProfiles = searchProfiles;
        this.searchView = searchView;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        SearchAdapter.SearchViewHolder searchViewHolder = new SearchAdapter.SearchViewHolder(view);

        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, final int position) {
        holder.tvNameOfStudent.setText(searchProfiles.get(position).getName());
        holder.tvAddressOfSearch.setText(searchProfiles.get(position).getAddress());
        //holder.tvClassAndSection.setText(searchProfiles.get(position).getSection().getClassname());
        Glide.with(context)
                .load(searchProfiles.get(position).getPhoto())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.ivProfileImageSearch);
        holder.clParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.getClickedStudentDetails(searchProfiles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchProfiles.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNameOfStudent)
        TextView tvNameOfStudent;

        @BindView(R.id.tvClassAndSection)
        TextView tvClassAndSection;

        @BindView(R.id.tvAddressOfSearch)
        TextView tvAddressOfSearch;

        @BindView(R.id.ivProfileImageSearch)
        ImageView ivProfileImageSearch;

        @BindView(R.id.clParentLayout)
        ConstraintLayout clParentLayout;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
