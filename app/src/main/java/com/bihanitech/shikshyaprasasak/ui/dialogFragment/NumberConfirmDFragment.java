package com.bihanitech.shikshyaprasasak.ui.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity.RegistrationActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dilip on 4/4/18.
 */

public class NumberConfirmDFragment extends DialogFragment {

    @BindView(R.id.tvPhoneNo)
    TextView tvPhoneNo;

    public static NumberConfirmDFragment newInstance(String number) {
        NumberConfirmDFragment frag = new NumberConfirmDFragment();
        Bundle args = new Bundle();
        args.putString(Constant.PHONE_NUMBER, number);
        frag.setArguments(args);
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_number_confirm,container);
        ButterKnife.bind(this,view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvPhoneNo.setText(getArguments().getString(Constant.PHONE_NUMBER));
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fragment_number_confirm);


        return dialog;
    }


    @OnClick(R.id.btEdit)
    public void btEditClicked(){
        getDialog().dismiss();
    }


    @OnClick(R.id.btOk)
    public void btOkClicked(){
        ((RegistrationActivity)getActivity()).authenticateNumber();
        getDialog().dismiss();
    }


}
