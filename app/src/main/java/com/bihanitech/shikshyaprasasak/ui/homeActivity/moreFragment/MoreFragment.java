package com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;

import butterknife.ButterKnife;

public class MoreFragment extends Fragment {
    Toolbar toolbar;
    TextView tvToolbarTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tvToolbarTitle = getActivity().findViewById(R.id.tvToolbarTitle);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tvToolbarTitle.setText("More");
        return view;
    }


}
