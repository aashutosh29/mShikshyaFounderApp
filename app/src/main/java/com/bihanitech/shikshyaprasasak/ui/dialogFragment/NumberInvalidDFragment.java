package com.bihanitech.shikshyaprasasak.ui.dialogFragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dilip on 4/8/18.
 */

public class NumberInvalidDFragment extends DialogFragment {

    @BindView(R.id.tvLabel1)
    TextView tvLabel;

    public static NumberInvalidDFragment newInstance(String message, String btName) {
        NumberInvalidDFragment frag = new NumberInvalidDFragment();
        Bundle args = new Bundle();
        args.putString(Constant.PHONE_NUMBER, message);
        args.putString("btName", btName);
        frag.setArguments(args);
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_number_invalid,container);
        ButterKnife.bind(this,view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLabel.setText(Html.fromHtml(getArguments().getString(Constant.PHONE_NUMBER)));
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_number_invalid);


        return dialog;
    }


    @OnClick(R.id.btEdit)
    public void btEditClicked(){
        getDialog().dismiss();
    }





}
