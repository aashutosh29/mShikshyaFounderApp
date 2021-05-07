package com.bihanitech.shikshyaprasasak.ui.dialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.incomeSummaryListActivity.IncomeSummaryListActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FilterDFragment extends DialogFragment {


    @BindView(R.id.tvFromDate)
    TextView tvFromDate;
    @BindView(R.id.tvToDate)
    TextView tvToDate;
    Date fromDate;
    DatabaseHelper databaseHelper;
    /* List<DealerInfo> dealerList;
     List<SubArea> subAreaList;*/
    private String type;
    private int mYear, mMonth, mDay;

    public static com.bihanitech.shikshyaprasasak.ui.dialogFragment.FilterDFragment newInstance(String type, String from, String to) {
        com.bihanitech.shikshyaprasasak.ui.dialogFragment.FilterDFragment frag = new com.bihanitech.shikshyaprasasak.ui.dialogFragment.FilterDFragment();
        Bundle args = new Bundle();
        args.putString(Constant.DIALOG_FROM, type);
        args.putString(Constant.FROM_DATE, from);
        args.putString(Constant.TO_DATE, to);


        frag.setArguments(args);

        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_filter, container);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        type = getArguments().getString(Constant.DIALOG_FROM);

        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);


        if (getArguments().getString(Constant.FROM_DATE).length() < 2) {
            tvFromDate.setText(strDate);

        } else {

            tvFromDate.setText(getArguments().getString(Constant.FROM_DATE));
        }
        if (getArguments().getString(Constant.TO_DATE).length() < 2) {
            tvToDate.setText(strDate);
        } else {
            tvToDate.setText(getArguments().getString(Constant.TO_DATE));
        }

        tvFromDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Calendar c = Calendar.getInstance();


                if (tvFromDate.getText().toString().length() > 2) {
                    String test = tvFromDate.getText().toString();
                    mYear = Integer.parseInt(tvFromDate.getText().toString().split("-")[0]);
                    mMonth = Integer.parseInt(tvFromDate.getText().toString().split("-")[1]) - 1;
                    mDay = Integer.parseInt(tvFromDate.getText().toString().split("-")[2]);
                } else {
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tvFromDate.setText(year + "-" + ((monthOfYear + 1) < 10 ? ("0" + (monthOfYear + 1)) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth));
                                try {
                                    fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(tvFromDate.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
                datePickerDialog.show();

                return false;
            }
        });


        tvToDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (fromDate != null) {
                    final Calendar c = Calendar.getInstance();
//                    c.setTime(fromDate);
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    tvToDate.setText(year + "-" + ((monthOfYear + 1) < 10 ? ("0" + (monthOfYear + 1)) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth));
                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(fromDate.getTime());
                }
                return false;
            }
        });


        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @OnClick(R.id.btCancel)
    public void btCancelClicked() {
        getDialog().dismiss();
    }

    @OnClick(R.id.btApply)
    public void btApplyClicked() {
        String fromDate = "";
        String toDate = "";


        if (!(tvFromDate.getText().toString().equalsIgnoreCase("Select a date"))) {
            fromDate = tvFromDate.getText().toString();
        }
        if (!(tvToDate.getText().toString().equalsIgnoreCase("Select a date"))) {
            toDate = tvToDate.getText().toString();
        }
        if (type.equalsIgnoreCase(Constant.INCOME_SUMMARY)) {
            ((IncomeSummaryListActivity) getActivity()).applyFilter(fromDate, toDate);
        } /*else if (type.equalsIgnoreCase("visit")) {
            ((VisitHistoryActivity) getActivity()).applyFilter(fromDate, toDate);
        } else {

            ((OrderHistoryActivity) getActivity()).applyFilter(fromDate, toDate);

        }*/

        getDialog().dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (databaseHelper != null)
            OpenHelperManager.releaseHelper();
        databaseHelper = null;

    }

    private DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null)
            databaseHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        return databaseHelper;
    }


}
