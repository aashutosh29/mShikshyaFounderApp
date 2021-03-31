package com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFragment extends Fragment implements MoreView {


    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;

    @BindView(R.id.tvSchoolAddress)
    TextView tvSchoolAddress;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    SharedPrefsHelper sharedPrefsHelper;
    TextView tvToolbarTitle;
    Toolbar toolbarNew;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        initToolbar();
        initDetails();
        return view;
    }

    private void initDetails() {
        tvUserName.setText(sharedPrefsHelper.getValue(Constant.USER_NAME, ""));
        tvSchoolName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvSchoolAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        showSchoolLogo(sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, ""));

    }

    @OnClick(R.id.btUploadNotice)
    public void btUploadNoticeOnclick() {
        Intent intent = new Intent(getContext(), AddNoticeActivity.class);
        startActivity(intent);


    }

    private void initToolbar() {
        toolbarNew = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarNew);
        toolbarNew.setVisibility(View.VISIBLE);
        tvToolbarTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("More");
    }

    private void showSchoolLogo(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivProfileImage);
    }


}
