package com.bihanitech.shikshyaprasasak.ui.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.editNoitceActivity.EditNoticeActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessDFragment extends DialogFragment {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvLabel2)
    TextView tvLabele2;
    @BindView(R.id.ivNetwork)
    ImageView ivNetwork;
    @BindView(R.id.btAbort)
    Button btAbort;
    @BindView(R.id.btRetry)
    Button btRetry;
    @BindDrawable(R.drawable.ic_error_server)
    Drawable serverError;
    private Boolean isAddNotice;

    public static SuccessDFragment newInstance(boolean isAddNotice) {
        SuccessDFragment frag = new SuccessDFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constant.IS_ADD_NOTICE, isAddNotice);
        frag.setArguments(args);
        frag.setCancelable(false);

        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_success, container);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        isAddNotice = getArguments().getBoolean(Constant.IS_ADD_NOTICE);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setContentView(R.layout.dialog_fragment_network_error);


        return dialog;
    }


    @OnClick(R.id.btRetry)
    public void btRetryClicked() {
        if (isAddNotice) {
            ((AddNoticeActivity) getActivity()).back();
        } else {
            ((EditNoticeActivity) getActivity()).back();
        }
        getDialog().dismiss();
    }


}