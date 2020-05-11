package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.curveGraph.CurveGraphConfig;
import com.bihanitech.shikshyaprasasak.curveGraph.CurveGraphView;
import com.bihanitech.shikshyaprasasak.curveGraph.models.GraphData;
import com.bihanitech.shikshyaprasasak.curveGraph.models.PointMap;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalyticsFragment extends Fragment implements OnChartValueSelectedListener {

    /* @BindView(R.id.toolbar)
     Toolbar toolbar;*/
    Toolbar toolbar;
    TextView tvToolbarTitle;
    /*@BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;*/

    public static final int[] FOUNDER_COLORS = {
            Color.rgb(8, 154, 214), Color.rgb(160, 17, 28),
    };
    public static final int[] FOUNDER_COLOR = {
            Color.rgb(8, 154, 214)};
    protected final String[] parties = new String[]{
            "ABS", "PRST", "Party C"
    };
    protected Typeface tfRegular;
    protected Typeface tfLight;
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Nur", "Kg", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    private BarChart chart;

    private PieChart circularChart;

    private CurveGraphView curveGraphView;

    public AnalyticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        /* ButterKnife.bind(this, view);*/

        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        //setContentView(R.layout.fragment_analytics);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tvToolbarTitle = getActivity().findViewById(R.id.tvToolbarTitle);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tvToolbarTitle.setText("Analytics");

        ScrollView sView = view.findViewById(R.id.svMain);
        // Hide the Scollbar
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");


        //Initializing Bar graph
        chart = view.findViewById(R.id.chart1);
        setAllStuffsBarGraph();
        //Initializing circular chart
        circularChart = view.findViewById(R.id.chCircular2);
        setAllStuffsPieChart();
        circularChart = view.findViewById(R.id.chCircular1);
        setAllStuffsPieChart();
        //Initializing Curve graph
        curveGraphView = view.findViewById(R.id.cgv);
        setAllStuffCurveGraph();
        return view;
    }


    private void setAllStuffsBarGraph() {


        chart.getLegend().setEnabled(false);

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

        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);


        //data for graph
        ArrayList<BarEntry> values = new ArrayList<>();
       /* for (int i = 0; i < 12; i++) {
            float val = 8f;

        }*/
        values.add(new BarEntry(0, 65));
        values.add(new BarEntry(1, 60));
        values.add(new BarEntry(2, 55));
        values.add(new BarEntry(3, 65));
        values.add(new BarEntry(4, 55));
        values.add(new BarEntry(5, 50));
        values.add(new BarEntry(6, 50));
        values.add(new BarEntry(7, 55));
        values.add(new BarEntry(8, 60));
        values.add(new BarEntry(9, 65));
        values.add(new BarEntry(10, 60));


        BarDataSet set1;


        set1 = new BarDataSet(values, "Data Set");
        set1.setColors(FOUNDER_COLOR
        );
        set1.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

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


    }

    private void setAllStuffsPieChart() {

        //circularChart = findViewById(R.id.chCircular2);
        circularChart.setUsePercentValues(false);
        circularChart.getDescription().setEnabled(true);


        Description description = new Description();
        description.setText("Student");
        description.setPosition(350, 700);
        description.setTextSize(16f);
        description.setTextColor(Color.parseColor("#036C99"));
        circularChart.setDescription(description);


        circularChart.getLegend().setEnabled(false);
        circularChart.setExtraOffsets(1, -10, 1, -10);


        circularChart.setDragDecelerationFrictionCoef(0.95f);

        circularChart.setCenterTextTypeface(tfLight);
        circularChart.setCenterText(generateCenterSpannableText());

        circularChart.setDrawHoleEnabled(true);
        circularChart.setHoleColor(Color.parseColor("#F2F0F7"));

        circularChart.setTransparentCircleColor(Color.parseColor("#F2F0F7"));
        circularChart.setTransparentCircleAlpha(110);

        circularChart.setHoleRadius(72f);
        circularChart.setTransparentCircleRadius(72f);


        circularChart.setDrawCenterText(true);

        circularChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        circularChart.setRotationEnabled(true);
        circularChart.setHighlightPerTapEnabled(true);
        //    circularChart.setExtraOffsets(-2,-12,-2,-12);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        circularChart.setOnChartValueSelectedListener(this);

        circularChart.animateY(1400, Easing.EaseInOutQuad);


        //forIndex



                /*
                 Legend l = circularChart.getLegend();
                 setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);*/


        // entry label styling
        circularChart.setEntryLabelColor(Color.WHITE);
        //  circularChart.setEntryLabelTypeface(tfRegular);
        circularChart.setEntryLabelTextSize(0f);
        setData(10, 5);
    }

    private void setAllStuffCurveGraph() {


        curveGraphView.configure(
                new CurveGraphConfig.Builder(getContext())
                        .setAxisColor(R.color.FounderBlue)                                            // Set number of values to be displayed in X ax
                        .setGuidelineCount(0)                                                   // Set number of background guidelines to be shown.
                        .setGuidelineColor(R.color.Red)                                       // Set color of the visible guidelines.
                        .setNoDataMsg(" No Data ")                                              // Message when no data is provided to the view.
                        .setxAxisScaleTextColor(R.color.Black)                                  // Set X axis scale text color.
                        .setyAxisScaleTextColor(R.color.Black)                                  // Set Y axis scale text color
                        .setAnimationDuration(2000)                                             // Set Animation Duration
                        .build()
        );


        PointMap pointMap = new PointMap();
        pointMap.addPoint(1, 10);
        pointMap.addPoint(3, 40);
        pointMap.addPoint(4, 25);
        pointMap.addPoint(5, 60);
        pointMap.addPoint(6, 50);
        pointMap.addPoint(7, 45);
        pointMap.addPoint(8, 5);
        pointMap.addPoint(9, 70);
        pointMap.addPoint(10, 75);
        pointMap.addPoint(11, 100);


        final GraphData gd = GraphData.builder(getContext())
                .setPointMap(pointMap)
                .setGraphStroke(R.color.FounderRed)
                /*.setGraphGradient(R.color.gradientStartColor2, R.color.gradientEndColor2)*/
                .animateLine(true)
                .setPointColor(R.color.FounderBlue)
                .setPointRadius(5)
                .setStraightLine(true)
                .build();


        PointMap pm1 = new PointMap();
        pm1.addPoint(1, 20);
        pm1.addPoint(2, 25);
        pm1.addPoint(3, 60);
        pm1.addPoint(4, 160);
        pm1.addPoint(5, 125);
        pm1.addPoint(6, 200);
        pm1.addPoint(7, 150);
        pm1.addPoint(8, 220);
        pm1.addPoint(9, 250);
        pm1.addPoint(10, 230);
        pm1.addPoint(11, 350);


        final GraphData gd1 = GraphData.builder(getContext())
                .setPointMap(pm1)
                .setGraphStroke(R.color.FounderGreen)
                /*.setGraphGradient(R.color.gradientStartColor2, R.color.gradientEndColor2)*/
                .animateLine(true)
                .setPointColor(R.color.Red)
                .setPointRadius(5)
                .setStraightLine(true)
                .build();


        PointMap pm2 = new PointMap();
        pm2.addPoint(1, 30);
        pm2.addPoint(2, 40);
        pm2.addPoint(3, 90);
        pm2.addPoint(4, 200);
        pm2.addPoint(5, 250);
        pm2.addPoint(6, 350);
        pm2.addPoint(7, 300);
        pm2.addPoint(8, 340);
        pm2.addPoint(9, 400);
        pm2.addPoint(10, 380);
        pm2.addPoint(11, 550);


        final GraphData gd2 = GraphData.builder(getContext())
                .setPointMap(pm2)
                .setGraphStroke(R.color.FounderBlue)
                /*.setGraphGradient(R.color.gradientStartColor2, R.color.gradientEndColor2)*/
                .animateLine(true)
                .setPointColor(R.color.Red)
                .setPointRadius(5)
                .setStraightLine(true)
                .build();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curveGraphView.setData(11, 600, gd, gd1, gd2);
            }
        }, 3000);
    }


    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        //entries.add(new PieEntry(5f,3), new PieEntry(5,9));
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        for (int i = 0; i < 2; i++) {
            /*   entries.add(new PieEntry(3,parties));*/


            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties[i % parties.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Student Attendance");


        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : FOUNDER_COLORS)
            colors.add(c);

 /*       for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
*/
        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(circularChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        circularChart.setData(data);

        // undo all highlights
        circularChart.highlightValues(null);

        circularChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {
        String absentNum = "16";

        SpannableString s = new SpannableString("16 \n Absent \n 576 \n Total");
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");

    }

}