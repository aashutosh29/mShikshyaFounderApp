package com.bihanitech.shikshyaprasasak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.ui.schoolSelection.SchoolSelectionView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dilip on 3/6/18.
 */

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.SchoolListViewHolder>{

        private final List<SchoolInfo> schoolList;

        int itemLayout ;

        SchoolSelectionView selectionView;

        Context context;

        public SchoolListAdapter(Context context, List<SchoolInfo> schoolList, int itemLayout, SchoolSelectionView selectionView) {
            this.schoolList = schoolList;
            this.itemLayout = itemLayout;
            this.selectionView = selectionView;
            this.context = context;
        }

        @Override
        public SchoolListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);

            return new SchoolListViewHolder(view);

        }

        @Override
        public void onBindViewHolder(SchoolListViewHolder holder, final int position) {

            holder.tvAddress.setText(schoolList.get(position).getAddress());
            holder.tvName.setText(schoolList.get(position).getName());


            Glide.with(context)
                    .load(schoolList.get(position).getLogo())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_school_black_24dp)
                            .fitCenter()
                            .error(R.drawable.book_res))
                    .into(holder.ivLogo);

            holder.cvSchools.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    selectionView.sendToRegistration(schoolList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return schoolList.size();
        }

        public static class SchoolListViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.tvName)
            TextView tvName;

            @BindView(R.id.tvAddress)
            TextView tvAddress;

            @BindView(R.id.ivLogo)
            ImageView ivLogo;

            @BindView(R.id.cvSchools)
            CardView cvSchools;


            public SchoolListViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
             //   changeFonts();
            }


            }
        }




