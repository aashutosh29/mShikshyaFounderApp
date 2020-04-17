package com.bihanitech.shikshyaprasasak.ui.homeActivity.analytics;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.cardview.widget.CardView;

import com.bihanitech.shikshyaprasasak.R;
import com.broooapps.graphview.CurveGraphConfig;
import com.broooapps.graphview.CurveGraphView;
import com.broooapps.graphview.models.GraphData;
import com.broooapps.graphview.models.PointMap;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
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

public class analyticsActivity extends Activity implements OnChartValueSelectedListener {

    public static final int[] FOUNDER_COLORS = {
            Color.rgb(21, 101, 192), Color.rgb(198, 40, 40),
    };
    protected final String[] parties = new String[]{
            "ABS", "PRST", "Party C"
    };
    protected Typeface tfRegular;
    protected Typeface tfLight;


    private BarChart chart;

    private PieChart circularChart;

    private CurveGraphView curveGraphView;
    private CardView cvIncomeVSDueBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_analytics);

        ScrollView sView = findViewById(R.id.svMain);
        // Hide the Scollbar
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        tfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");


        //Initializing Bar graph
        setAllStuffsBarGraph();
        //Initializing circular chart
        circularChart = findViewById(R.id.chCircular2);
        setAllStuffsPieChart();
        circularChart = findViewById(R.id.chCircular1);
        setAllStuffsPieChart();
        //Initializing Curve graph
        setAllStuffCurveGraph();
       /* cvIncomeVSDueBalance.findViewById(R.id.cvIncomeVSDueBalance);
        cvIncomeVSDueBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }


    private void setAllStuffsBarGraph() {

        chart = findViewById(R.id.chart1);

        chart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(10);

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
        for (int i = 0; i < 12; i++) {
            float val = 8f;
            values.add(new BarEntry(i, val));
        }

        BarDataSet set1;


        set1 = new BarDataSet(values, "Data Set");
        set1.setColors(FOUNDER_COLORS
        );
        set1.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        chart.setData(data);


        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);

    }

    private void setAllStuffsPieChart() {

        //circularChart = findViewById(R.id.chCircular2);
        circularChart.setUsePercentValues(false);
        circularChart.getDescription().setEnabled(false);
        circularChart.setExtraOffsets(5, 10, 5, 5);

        circularChart.setDragDecelerationFrictionCoef(0.95f);

        circularChart.setCenterTextTypeface(tfLight);
        circularChart.setCenterText(generateCenterSpannableText());

        circularChart.setDrawHoleEnabled(true);
        circularChart.setHoleColor(Color.WHITE);

        circularChart.setTransparentCircleColor(Color.WHITE);
        circularChart.setTransparentCircleAlpha(110);

        circularChart.setHoleRadius(70f);
        circularChart.setTransparentCircleRadius(61f);

        circularChart.setDrawCenterText(true);

        circularChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        circularChart.setRotationEnabled(true);
        circularChart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        circularChart.setOnChartValueSelectedListener(this);

        circularChart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

       /*
       //forIndex
       Legend l = circularChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);*/

        // entry label styling
        circularChart.setEntryLabelColor(Color.WHITE);
        //  circularChart.setEntryLabelTypeface(tfRegular);
        circularChart.setEntryLabelTextSize(12f);
        setData(10, 5);
    }

    private void setAllStuffCurveGraph() {
        curveGraphView = findViewById(R.id.cgv);

        curveGraphView.configure(
                new CurveGraphConfig.Builder(this)
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


        final GraphData gd = GraphData.builder(this)
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


        final GraphData gd1 = GraphData.builder(this)
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


        final GraphData gd2 = GraphData.builder(this)
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

        SpannableString s = new SpannableString("FounderUI");
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