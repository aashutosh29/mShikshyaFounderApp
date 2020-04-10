package com.bihanitech.shikshyaprasasak.ui.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import android.support.v4.app.DialogFragment;

/**
 * Created by dilip on 4/5/18.
 */

public class UpdateDF extends DialogFragment {

    private int errorType;
    private String errorSource;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvLabel2)
    TextView tvLabele2;

    @BindView(R.id.ivNetwork)
    ImageView ivNetwork;

    @BindView(R.id.btIgnore)
    Button btIgnore;

    @BindView(R.id.btUpdate)
    Button btUpdate;


    public static UpdateDF newInstance(int errorType, String source) {
        UpdateDF frag = new UpdateDF();
        Bundle args = new Bundle();
        args.putInt(Constant.ERROR_TYPE,errorType);
        args.putString(Constant.ERROR_SOURCE,source);
        frag.setArguments(args);
        if(source.equalsIgnoreCase(HomeActivity.class.getSimpleName())){
            frag.setCancelable(false);
        }
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_ps_update,container);
        ButterKnife.bind(this,view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorType = getArguments().getInt(Constant.ERROR_TYPE);
        errorSource = getArguments().getString(Constant.ERROR_SOURCE);
        if(errorType == 1){
            tvTitle.setText("Students Info Update");
            tvLabele2.setText("Please update to get your children's recent profile");
            btIgnore.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btIgnore)
    public void btAbortClicked(){
        getDialog().dismiss();
    }

    @OnClick(R.id.btUpdate)
    public void btRetryClicked(){
        if(errorSource.equalsIgnoreCase(HomeActivity.class.getSimpleName()))

        getDialog().dismiss();

    }


}
