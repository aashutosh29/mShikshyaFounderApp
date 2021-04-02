package com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.curveGraph.CustomMarkerView;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity.SearchActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AcademicsFragment extends Fragment implements AcademicsView {
    public static final int[] FOUNDER_COLOR = {
            Color.rgb(8, 154, 214), Color.rgb(160, 17, 28)};

    protected Typeface tfRegular;
    TextView tvToolbarTitle;
    Toolbar toolbarNew;
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Nur", "Kg", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    @BindView(R.id.etStudentNameToSearch)
    Button etStudentNameToSearch;
    private BarChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academics, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        chart = view.findViewById(R.id.chart1);
        setAllStuffsBarGraph();
        startSearchActivity();
        return view;
    }

    private void startSearchActivity() {

        etStudentNameToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), SearchActivity.class);
                startActivity(in);
            }
        });
    }

    private void initToolbar() {
        toolbarNew = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarNew);
        toolbarNew.setVisibility(View.VISIBLE);
        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tvToolbarTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Academics");
    }

    private void setAllStuffsBarGraph() {

        Legend l = chart.getLegend();

        //initialize array
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "Passed";
        legendEntry.formColor = Color.rgb(8, 154, 214);

        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.label = "Failed";
        legendEntry1.formColor = Color.rgb(160, 17, 28);


        //index for the chart
        l.setEnabled(true);
        l.setFormSize(10f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(5f);
        l.setCustom(Arrays.asList(legendEntry, legendEntry1));


        //chart description
        chart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(12);
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //disable grid lines
        xAxis.setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);


        //data for graph
        ArrayList<BarEntry> values = new ArrayList<>();
       /* for (int i = 0; i < 12; i++) {
            float val = 8f;

        }*/
        values.add(new BarEntry(0, new float[]{12f, 26f}));
        values.add(new BarEntry(1, new float[]{17f, 32f}));
        values.add(new BarEntry(2, new float[]{19f, 41f}));
        values.add(new BarEntry(3, new float[]{16f, 21f}));
        values.add(new BarEntry(4, new float[]{21f, 36f}));
        values.add(new BarEntry(5, new float[]{16f, 25f}));
        values.add(new BarEntry(6, new float[]{18f, 14f}));
        values.add(new BarEntry(7, new float[]{14f, 21f}));
        values.add(new BarEntry(8, new float[]{17f, 12f}));
        values.add(new BarEntry(9, new float[]{14f, 13f}));
        values.add(new BarEntry(10, new float[]{10f, 16f}));
        BarDataSet set1;
        set1 = new BarDataSet(values, "Students");
        set1.setColors(FOUNDER_COLOR
        );
        set1.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        BarData data = new BarData(dataSets);
        chart.setData(data);
        data.setDrawValues(false);
        chart.getAxisRight().setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTypeface(tfRegular);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        //leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(90f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(5, 5, 5));
        // add a nice and smooth animation
        chart.animateY(1300);
        CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.graph_pointer);
        chart.setMarker(mv);

       /* chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Toast.makeText(getContext(), "you clicked" + e.toString(), Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(getContext(),chart);
                popupMenu.getMenuInflater().inflate(R.menu.);
            }

            @Override
            public void onNothingSelected() {

            }
        });*/


    }

    //    private void setAllStuffsBarGraph() {
//
//        chart.getLegend().setEnabled(false);
//
//
//        chart.getDescription().setEnabled(false);
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        chart.setMaxVisibleValueCount(12);
//
//        // scaling can now only be done on x- and y-axis separately
//        chart.setPinchZoom(false);
//
//        chart.setDrawBarShadow(false);
//        chart.setDrawGridBackground(false);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        xAxis.setDrawGridLines(false);
//
//        chart.getAxisLeft().setDrawGridLines(false);
//        CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.graph_pointer);
//        chart.setMarker(mv);
//
//        //trying to
//
//
//        //data for graph
//        ArrayList<BarEntry> values = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            float val = 8f;
//
//        }
//
//
//        values.add(new BarEntry(1, 60));
//        values.add(new BarEntry(2, 55));
//        values.add(new BarEntry(3, 65));
//        values.add(new BarEntry(4, 55));
//        values.add(new BarEntry(5, 50));
//        values.add(new BarEntry(6, 50));
//        values.add(new BarEntry(7, 55));
//        values.add(new BarEntry(8, 60));
//        values.add(new BarEntry(9, 65));
//        values.add(new BarEntry(10, 60));
//
//
//        BarDataSet set1;
//
//
//        set1 = new BarDataSet(values, "Data Set");
//        set1.setColors(FOUNDER_COLOR
//        );
//        set1.setDrawValues(true);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set1);
//        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
//
//        BarData data = new BarData(dataSets);
//        chart.setData(data);
//        data.setDrawValues(false);
//        chart.getAxisRight().setEnabled(false);
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setTypeface(tfRegular);
//        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//        //leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(90f);
//        leftAxis.setYOffset(-9f);
//        leftAxis.setTextColor(Color.rgb(5, 5, 5));
//
//
//        // add a nice and smooth animation
//        chart.animateY(1300);
//
//        Legend l = chart.getLegend();
//
//        //initialize array
//        LegendEntry legendEntry = new LegendEntry();
//        legendEntry.label = "Boys";
//        legendEntry.formColor = Color.rgb(8, 154, 214);
//
//        LegendEntry legendEntry1 = new LegendEntry();
//        legendEntry1.label = "Girls";
//        legendEntry1.formColor = Color.rgb(160, 17, 28);
//
//
//        //index for the chart
//        l.setEnabled(true);
//        l.setFormSize(10f);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setTextSize(12f);
//        l.setTextColor(Color.BLACK);
//        l.setXEntrySpace(5f);
//        l.setYEntrySpace(5f);
//        l.setCustom(Arrays.asList(legendEntry, legendEntry1));
//
//
//        //chart description
//        chart.getDescription().setEnabled(false);
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        chart.setMaxVisibleValueCount(12);
//
//        // scaling can now only be done on x- and y-axis separately
//        chart.setPinchZoom(false);
//
//        chart.setDrawBarShadow(false);
//        chart.setDrawGridBackground(false);
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        //disable grid lines
//        xAxis.setDrawGridLines(false);
//        chart.getAxisLeft().setDrawGridLines(false);
//        chart.getAxisRight().setLabelCount(12, true);
//
//
//        //data for graph
//        ArrayList<BarEntry> values = new ArrayList<>();
//       /* for (int i = 0; i < 12; i++) {
//            float val = 8f;
//
//        }*/
//        values.add(new BarEntry(0, new float[]{12f, 26f}));
//        values.add(new BarEntry(1, new float[]{17f, 32f}));
//        values.add(new BarEntry(2, new float[]{19f, 41f}));
//        values.add(new BarEntry(3, new float[]{16f, 21f}));
//        values.add(new BarEntry(4, new float[]{21f, 36f}));
//        values.add(new BarEntry(5, new float[]{35f, 10f}));
//        values.add(new BarEntry(6, new float[]{18f, 14f}));
//        values.add(new BarEntry(7, new float[]{14f, 21f}));
//        values.add(new BarEntry(8, new float[]{17f, 12f}));
//        values.add(new BarEntry(9, new float[]{14f, 13f}));
//        values.add(new BarEntry(10, new float[]{10f, 16f}));
//        BarDataSet set1;
//        set1 = new BarDataSet(values, "Students");
//        set1.setColors(FOUNDER_COLOR
//        );
//        set1.setDrawValues(true);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set1);
//        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
//
//        BarData data = new BarData(dataSets);
//        chart.setData(data);
//        data.setDrawValues(false);
//        chart.getAxisRight().setEnabled(false);
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setTypeface(tfRegular);
//        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//        //leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(90f);
//        leftAxis.setYOffset(-9f);
//        leftAxis.setTextColor(Color.rgb(5, 5, 5));
//        // add a nice and smooth animation
//        chart.animateY(1300);
//        CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.graph_pointer);
//        chart.setMarker(mv);
//
//        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//
//                Toast.makeText(getContext(), "you clicked" + e.toString(), Toast.LENGTH_SHORT).show();
//                PopupMenu popupMenu = new PopupMenu(getContext(),chart);
//                popupMenu.getMenuInflater().inflate(R.menu.);
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//
//    }
}