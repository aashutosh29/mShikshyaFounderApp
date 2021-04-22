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
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.splashScreen.SplashScreenActivity;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity.RegistrationActivity;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity.VerifyOtpActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dilip on 4/5/18.
 */

public class NetworkErrorDFragment extends DialogFragment {

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
    private int errorType;
    private String errorSource;

    public static NetworkErrorDFragment newInstance(int errorType, String source) {
        NetworkErrorDFragment frag = new NetworkErrorDFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.ERROR_TYPE, errorType);
        args.putString(Constant.ERROR_SOURCE, source);
        frag.setArguments(args);
        if (source.equalsIgnoreCase(SplashScreenActivity.class.getSimpleName())
                || source.equalsIgnoreCase(NoticeActivity.class.getSimpleName())) {
            frag.setCancelable(false);
        }
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_network_error, container);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorType = getArguments().getInt(Constant.ERROR_TYPE);
        errorSource = getArguments().getString(Constant.ERROR_SOURCE);
        if (errorType == 1) {
            tvTitle.setText("Some things happens");
            tvLabele2.setText("Please retry or report to school if it continues");
            ivNetwork.setImageDrawable(serverError);
        } else if (errorType == 2) {
            tvTitle.setText("Some things happens");
            tvLabele2.setText("No internet connection please connect to internet");
            ivNetwork.setImageDrawable(serverError);
        }

        if (errorSource.equalsIgnoreCase(SplashScreenActivity.class.getSimpleName())
                || errorSource.equalsIgnoreCase(NoticeActivity.class.getSimpleName())) {
            btAbort.setVisibility(View.VISIBLE);
        }


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

    @OnClick(R.id.btAbort)
    public void btAbortClicked() {
        if (errorSource.equalsIgnoreCase(SplashScreenActivity.class.getSimpleName())) {

            ((SplashScreenActivity) getActivity()).abort();
        } else {
            getActivity().onBackPressed();
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.btRetry)
    public void btRetryClicked() {
        if (errorSource.equalsIgnoreCase(SplashScreenActivity.class.getSimpleName())) {
            ((SplashScreenActivity) getActivity()).retry();
        } else if (errorSource.equalsIgnoreCase(RegistrationActivity.class.getSimpleName())) {
            ((RegistrationActivity) getActivity()).retry();
        } else if (errorSource.equalsIgnoreCase(VerifyOtpActivity.class.getSimpleName())) {
            ((VerifyOtpActivity) getActivity()).retry();
        } else if (errorSource.equalsIgnoreCase(NoticeActivity.class.getSimpleName())) {
            ((NoticeActivity) getActivity()).retry();
        } else if (errorSource.equalsIgnoreCase(AddNoticeActivity.class.getSimpleName())) {
            ((AddNoticeActivity) getActivity()).retry();

        }
        getDialog().dismiss();

    }


}
