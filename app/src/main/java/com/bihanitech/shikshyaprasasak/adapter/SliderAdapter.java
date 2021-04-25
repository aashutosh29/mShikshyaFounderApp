package com.bihanitech.shikshyaprasasak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.model.slider.EventSlider;
import com.bihanitech.shikshyaprasasak.ui.NewUserManagementSystem.LoginActivity.LoginView;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentView;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity.RegistrationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderAdapter extends RecyclerView.Adapter<com.bihanitech.shikshyaprasasak.adapter.SliderAdapter.SliderViewHolder> {

    List<EventSlider> eventSliders;
    // HomeFragmentView view;
    RegistrationView registrationView;
    LoginView loginView;

    HomeFragmentView homeFragmentView;

    Context context;
    int viewNo;


   /* public SliderAdapter(List<EventSlider> eventSliders, HomeFragmentView view, Context context) {
        this.eventSliders = eventSliders;
        this.view = view;
        this.context = context;
        viewNo = 1;
    }*/

    public SliderAdapter(List<EventSlider> eventSliders, RegistrationView view, Context context) {
        this.eventSliders = eventSliders;
        this.registrationView = view;
        this.context = context;
        viewNo = 1;
    }

    public SliderAdapter(List<EventSlider> eventSliders, LoginView view, Context context) {
        this.eventSliders = eventSliders;
        this.loginView = view;
        this.context = context;
        viewNo = 2;
    }

    public SliderAdapter(List<EventSlider> eventSliders, HomeFragmentView homeFragmentView, Context context) {
        this.eventSliders = eventSliders;
        this.homeFragmentView = homeFragmentView;
        this.context = context;
        viewNo = 3;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slidingimages_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder holder, final int position) {

        if (eventSliders.get(position).getImage().equalsIgnoreCase("no responce")) {
            holder.ivSlidigImage.setImageResource(R.drawable.shik_banner_20200553123753);
        } else {
            Glide.with(context)
                    .load(eventSliders.get(position).getImage())
                    .placeholder(R.drawable.shik_banner_20200553123753)
                    .error(R.drawable.shik_banner_20200553123753)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.ivSlidigImage);
        }


        holder.ivSlidigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewNo == 1) {
                    registrationView.sliderItemClicked(eventSliders.get(position));

                } else if (viewNo == 2) {
                    loginView.sliderItemClicked(eventSliders.get(position));


                } else if (viewNo == 3) {
                    homeFragmentView.sliderItemClicked(eventSliders.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventSliders.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivSlidigImage)
        ImageView ivSlidigImage;

        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
