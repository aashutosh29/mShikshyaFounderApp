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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.curveGraph.models.CustomMarkerView;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.acedamics.ClassData;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity.SearchActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
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
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragmentPresenter.TAG;


public class AcademicsFragment extends Fragment implements AcademicsView {
    public static final int[] FOUNDER_COLOR = {
            Color.rgb(160, 17, 28), Color.rgb(8, 154, 214), Color.rgb(25, 168, 12), Color.rgb(168, 152, 12), Color.rgb(30, 168, 12), Color.rgb(31, 140, 145)};
    protected Typeface tfRegular;
    String gradeAndDivision = "";
    List<String> classificationXAxisValue;
    ArrayList<BarEntry> values = new ArrayList<>();
    //each gradeAndDivision should have list  gradeAndDivisionYAxisCount
    List<Float> gradeAndDivisionGroupStudentYAxisValue;
    List<String> gradeAndDivisionGroupStudentGradeName;

    TextView tvToolbarTitle;
    Toolbar toolbarNew;
    AcademicsPresenter academicsPresenter;
    @BindView(R.id.etStudentNameToSearch)
    Button etStudentNameToSearch;
    @BindView(R.id.spExam)
    Spinner spExam;

    @BindView(R.id.clEmpty)
    ConstraintLayout clEmpty;

    @BindView(R.id.chart1)
    BarChart chart1;

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.ivError)
    ImageView ivError;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @BindView(R.id.tvErrorTitle)
    TextView tvErrorTitle;

    @BindView(R.id.btRefresh)
    TextView btRefresh;

    SharedPrefsHelper sharedPrefsHelper;
    String examId;
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


    }

    @Override
    public void loadDataOnGraph(List<ClassData> data) {
        chart1.setVisibility(View.VISIBLE);
        clEmpty.setVisibility(View.GONE);
        loadingPanel.setVisibility(View.GONE);
        classificationXAxisValue = new ArrayList<>();

        //values = null;
        for (int i = 0; i < data.size(); i++) {
            classificationXAxisValue.add(data.get(i).getClass_());
            gradeAndDivisionGroupStudentYAxisValue = new ArrayList<>();
            gradeAndDivisionGroupStudentGradeName = new ArrayList<>();
            for (int j = 0; j < data.get(i).getData().size(); j++) {
                gradeAndDivisionGroupStudentYAxisValue.add(data.get(i).getData().get(j).getCount().floatValue());
                gradeAndDivisionGroupStudentGradeName.add(data.get(i).getData().get(j).getGrade() + "@" + data.get(i).getData().get(j).getCount().floatValue());
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
                examId = examList.get(i).getExamID();
                academicsPresenter.getGraphData(sharedPrefsHelper.getValue(Constant.TOKEN, ""), examId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.btRefresh)
    void btRefreshClicked() {
        academicsPresenter.getGraphData(sharedPrefsHelper.getValue(Constant.TOKEN, ""), examId);


    }

    @Override
    public void noDataAvailable() {
        loadingPanel.setVisibility(View.GONE);
        chart1.setVisibility(View.INVISIBLE);
        clEmpty.setVisibility(View.VISIBLE);
        tvError.setText("No data found. Please select another terminal.");
        tvErrorTitle.setVisibility(View.GONE);
        btRefresh.setVisibility(View.GONE);
        ivError.setImageResource(R.drawable.ic_no_data);

    }

    @Override
    public void error(boolean isNetworkError) {
        loadingPanel.setVisibility(View.GONE);
        chart1.setVisibility(View.INVISIBLE);
        clEmpty.setVisibility(View.VISIBLE);
        btRefresh.setVisibility(View.VISIBLE);

        ivError.setImageResource(R.drawable.ic_no_connection);
        tvErrorTitle.setVisibility(View.VISIBLE);
        if (isNetworkError) {
            tvErrorTitle.setText("No Internet Connection");
            tvError.setText("There is problem in Server. Please refresh later");

        } else {
            tvError.setText("Check your connection, then refresh the page.");
            tvErrorTitle.setText("Problem in  Server");
        }
    }

    @Override
    public void showLoading() {
        loadingPanel.setVisibility(View.VISIBLE);
        chart1.setVisibility(View.INVISIBLE);
        clEmpty.setVisibility(View.INVISIBLE);
    }


}