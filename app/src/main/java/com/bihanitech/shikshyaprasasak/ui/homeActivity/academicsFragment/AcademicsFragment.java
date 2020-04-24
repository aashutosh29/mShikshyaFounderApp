package com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;

import butterknife.ButterKnife;

public class AcademicsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}