package com.bihanitech.shikshyaprasasak.ui.dialogFragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bihanitech.shikshyaprasasak.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortBottomSheetF extends BottomSheetDialogFragment {


    @BindView(R.id.ivName)
    ImageView ivName;

    @BindView(R.id.ivDate)
    ImageView ivDate;



    @BindView(R.id.tvName)
    TextView tvName;


    @BindView(R.id.tvDate)
    TextView tvDate;


    @BindView(R.id.clName)
    ConstraintLayout clName;

    @BindView(R.id.clDate)
    ConstraintLayout clDate;



    @BindView(R.id.sort_bottom_sheet_cl_main)
    ConstraintLayout clSortBottomSheet;


    String option;
    boolean isAsc;

    @BindDrawable(R.drawable.ic_arrow_upward_blue_24dp)
    Drawable upDrawable;

    @BindDrawable(R.drawable.ic_arrow_downward_blue_24dp)
    Drawable downDrawable;

    @BindDrawable(R.drawable.sort_bg_drawable)
    Drawable sortBg;

    public SortBottomSheetF() {

    }

    public static SortBottomSheetF newInstance(String option, boolean isAsc) {
        SortBottomSheetF frag = new SortBottomSheetF();
        Bundle args = new Bundle();
        args.putString("Option", option);
        args.putBoolean("isAsc", isAsc);
        frag.setArguments(args);
        return frag;
    }


    public interface SortBottomSheetFListener {
        void sortOptionSelected(String option);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_frag_sort_bottom_sheet, container, false);
        ButterKnife.bind(this,rootView);
        option = getArguments().getString("Option");
        isAsc = getArguments().getBoolean("isAsc");
        setUpSelectedView(option);

        return rootView;
    }

    // highlight the previously selected option
    private void setUpSelectedView(String option) {
        switch (option) {
            case "Student Name":
                clName.setBackground(sortBg);
                tvName.setTextColor(Color.parseColor("#FF03A9F4"));
                setUpArrow(ivName);
                ivName.setVisibility(View.VISIBLE);
                break;
            case "Roll No":
                clDate.setBackground(sortBg);
                setUpArrow(ivDate);
                tvDate.setTextColor(Color.parseColor("#FF03A9F4"));
                ivDate.setVisibility(View.VISIBLE);

        }
    }

    private void setUpArrow(ImageView v) {
        if (isAsc)
            v.setImageDrawable(upDrawable);
        else
            v.setImageDrawable(downDrawable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // sorting option selected
    @OnClick({R.id.clName, R.id.clDate})
    public void optionSelected(View v) {
        SortBottomSheetFListener listener = (SortBottomSheetFListener) getActivity();

        switch (v.getId()) {
            case R.id.clName:
                listener.sortOptionSelected("Student Name");
                break;
            case R.id.clDate:
                listener.sortOptionSelected("Roll No");
                break;

        }

        getDialog().dismiss();

    }

}