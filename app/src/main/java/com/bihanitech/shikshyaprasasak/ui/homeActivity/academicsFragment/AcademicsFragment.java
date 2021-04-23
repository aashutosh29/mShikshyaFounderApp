package com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.curveGraph.CustomMarkerView;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.database.SharedPrefsHelper;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.acedamics.ClassData;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity.SearchActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity.token;
import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;


public class AcademicsFragment extends Fragment implements AcademicsView {
    public static final int[] FOUNDER_COLOR = {
            Color.rgb(160, 17, 28), Color.rgb(8, 154, 214), Color.rgb(25, 168, 12), Color.rgb(168, 152, 12), Color.rgb(30, 168, 12), Color.rgb(31, 140, 145)};
    protected Typeface tfRegular;
    String gradeAndDivision = "";
    List<String> classificationXAxisValue = new ArrayList<>();
    ArrayList<BarEntry> values = new ArrayList<>();
    //each gradeAndDivision should have list  gradeAndDivisionYAxisCount
    List<Float> gradeAndDivisionGroupStudentYAxisValue = new ArrayList<>();
    List<String> gradeAndDivisionGroupStudentGradeName = new ArrayList<>();

    TextView tvToolbarTitle;
    Toolbar toolbarNew;
    AcademicsPresenter academicsPresenter;
    @BindView(R.id.etStudentNameToSearch)
    Button etStudentNameToSearch;
    @BindView(R.id.spExam)
    Spinner spExam;
    SharedPrefsHelper sharedPrefsHelper;
    private DatabaseHelper databaseHelper;
    private BarChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academics, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        chart = view.findViewById(R.id.chart1);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        academicsPresenter = new AcademicsPresenter(new MetaDatabaseRepo(getHelper()), this);
        academicsPresenter.exams();
        setAllStuffsBarGraph();
        startSearchActivity();
        Log.d(TAG, "onCreateView: " + sharedPrefsHelper.getValue(Constant.TOKEN, ""));
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

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }

        databaseHelper = null;
    }

    private void setAllStuffsBarGraph() {

        Legend l = chart.getLegend();
        l.setEnabled(false);
        //l.setEnabled(false);

       /* //initialize array
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "Passed";
        legendEntry.formColor = Color.rgb(8, 154, 214);

        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.label = "Failed";
        legendEntry1.formColor = Color.rgb(160, 17, 28);*/

/*
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
        l.setCustom(Arrays.asList(legendEntry, legendEntry1));*/


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

        chart.setNoDataText("No Record found");
        //data for graph
       /* ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float val = 8f;

        }*/
        BarDataSet set1;
        set1 = new BarDataSet(values, "Students");
        set1.setColors(FOUNDER_COLOR
        );
        set1.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(classificationXAxisValue));

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

    @Override
    public void loadDataOnGraph(List<ClassData> data) {

        //values = null;
        for (int i = 0; i < data.size(); i++) {
            classificationXAxisValue.add(data.get(i).getClass_());
            for (int j = 0; j < data.get(i).getData().size(); j++) {
                gradeAndDivisionGroupStudentYAxisValue.add(data.get(i).getData().get(j).getCount().floatValue());
                gradeAndDivisionGroupStudentGradeName.add(data.get(i).getData().get(j).getGrade());
            }
            float[] list = new float[gradeAndDivisionGroupStudentYAxisValue.size()];
            for (int k = 0; k < gradeAndDivisionGroupStudentYAxisValue.size(); k++) {
                list[k] = gradeAndDivisionGroupStudentYAxisValue.get(k);
            }
            values.add(new BarEntry(i, list, gradeAndDivisionGroupStudentGradeName));
            Log.d(TAG, "loadDataOnGraph: " + gradeAndDivisionGroupStudentYAxisValue);
        }
        setAllStuffsBarGraph();
    }

    @Override
    public void getExams(List<ExamName> examList) {
        List<String> examName = new ArrayList<>();
        for (int i = 0; i < examList.size(); i++) {
            examName.add(examList.get(i).getExamName());
        }
        ArrayAdapter<String> arrayAdapterSection = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, examName);
        arrayAdapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spExam.setAdapter(arrayAdapterSection);
        spExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                academicsPresenter.getGraphData(token, examList.get(i).getExamID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void noDataAvailable() {
        classificationXAxisValue = new ArrayList<>();
        gradeAndDivisionGroupStudentYAxisValue = new ArrayList<>();
        //values.add(new BarEntry(0, 0,gradeAndDivisionGroupStudentGradeName));
        setAllStuffsBarGraph();

    }


}