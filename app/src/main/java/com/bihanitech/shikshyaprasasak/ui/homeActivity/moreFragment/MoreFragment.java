package com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFragment extends Fragment {

    TextView tvToolbarTitle;
    Toolbar toolbarNew;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        return view;
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


}
