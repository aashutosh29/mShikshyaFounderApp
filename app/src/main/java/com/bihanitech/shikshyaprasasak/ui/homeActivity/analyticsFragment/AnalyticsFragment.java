package com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.curveGraph.CurveGraphConfig;
import com.bihanitech.shikshyaprasasak.curveGraph.CurveGraphView;
import com.bihanitech.shikshyaprasasak.curveGraph.models.GraphData;
import com.bihanitech.shikshyaprasasak.curveGraph.models.PointMap;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.ClassDueReport;
import com.bihanitech.shikshyaprasasak.model.EmployeeGenderWise;
import com.bihanitech.shikshyaprasasak.model.StudentAttendance;
import com.bihanitech.shikshyaprasasak.model.StudentGenderWise;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.AnalyticsPresenter.TAG;

public class AnalyticsFragment extends Fragment implements OnChartValueSelectedListener, AnalyticsView {
    public static final int[] FOUNDER_COLORS = {
            Color.rgb(8, 154, 214), Color.rgb(160, 17, 28)
    };
    public static final int[] FOUNDER_COLORS_NEW = {
            Color.rgb(160, 17, 28), Color.rgb(8, 154, 214)
    };
    final Calendar myCalendar = Calendar.getInstance();
    protected Typeface tfRegular;
    protected Typeface tfLight;
    protected SharedPrefsHelper sharedPrefsHelper;
    List<String> incomeVsDueClass = new ArrayList<>();
    int date = 0;

    int spanForIncomeVsDue;
    int maxValueForIncomeVsDue;
    String[] parties = new String[]{
            "ABS", "PRST", "Party C"
    };
    //pieChart data
    String female = "Female";
    String male = "Male";
    String totalMaleStaff = "0";
    String totalFemaleStaff = "0";
    String totalMaleStudent = "0";
    String totalFemaleStudent = "0";
    TextView tvToolbarTitle;
    String dateNow;
    //student attendance
    String absent = "Absent";
    String present = "Present";
    Toolbar toolbarNew;

    @BindView(R.id.chCircular2Mf)
    PieChart chCircular2Mf;

    @BindView(R.id.chCircular1Mf)
    PieChart chCircular1Mf;

    @BindView(R.id.cgv)
    CurveGraphView cgv;

    AnalyticsPresenter analyticsPresenter;

    PointMap pointMapTotalCharge;
    PointMap pointMapTotalDue;
    PointMap pointMapTotalPaid;
    @BindView(R.id.cvMoreAndUpdatedA)
    ConstraintLayout cvMoreAndUpdatedA;
    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvTotalChargedNum)
    TextView tvTotalChargedNum;

    @BindView(R.id.tvTotalPaidNum)
    TextView tvTotalPaidNum;

    @BindView(R.id.tvTotalDUeNum)
    TextView tvTotalDUeNum;

    @BindView(R.id.viewStatement)
    Button viewStatement;

    //loading pannels

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @BindView(R.id.loadingPanelTSASR)
    RelativeLayout loadingPanelTSASR;

    @BindView(R.id.loadingPanelIVDB)
    RelativeLayout loadingPanelIVDB;


    //Attendance Report
    @BindView(R.id.clErrorAR)
    ConstraintLayout clErrorAR;
    @BindView(R.id.tvErrorTitleAR)
    TextView tvErrorTitleAR;
    @BindView(R.id.tvNetworkErrorAR)
    TextView tvNetworkError;


    //Income Vs Due Balance

    @BindView(R.id.clErrorIVDB)
    ConstraintLayout clErrorIVDB;
    @BindView(R.id.tvErrorTitleIVDB)
    TextView tvErrorTitleIVDB;
    @BindView(R.id.tvErrorSubTitleIVDB)
    TextView tvErrorSubTitleIVDB;
    @BindView(R.id.btRetryIVDB)
    Button btRetryIVDB;
    @BindView(R.id.tvUpdatedDate)
    TextView tvUpdatedDateIVDB;
    @BindView(R.id.clAmount)
    ConstraintLayout clAmount;


    //Total student and staff report error layout
    @BindView(R.id.clErrorTSASR)
    ConstraintLayout clErrorTSASR;
    @BindView(R.id.tvErrorTitleTSASR)
    TextView tvErrorTitleTSASR;
    @BindView(R.id.tvErrorSubTitleTSASR)
    TextView tvErrorSubTitleTSASR;
    @BindView(R.id.btRetryTSASR)
    Button btRetryTSASR;
    @BindView(R.id.tvUpdatedDateTSASR)
    TextView tvUpdatedDateTSASR;


    @BindView(R.id.chCircular1)
    PieChart chCircular1;
    String pattern = "yyyy-MM-dd";
    private PieChart circularChartStudentGender;
    private PieChart circularChartStaffGender;
    private PieChart pieChartStaff;
    private PieChart pieChartStudent;
    private CurveGraphView curveGraphView;
    private DatabaseHelper databaseHelper;

    public AnalyticsFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        analyticsPresenter = new AnalyticsPresenter(this, new MetaDatabaseRepo(getHelper()));
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        sharedPrefsHelper.saveValue(Constant.STUDENT_PRESENT, "0");
        sharedPrefsHelper.saveValue(Constant.STUDENT_ABSENT, "0");
        analyticsPresenter.getGenderWiseStaffAndStudent(sharedPrefsHelper.getValue(Constant.TOKEN, ""), false);
        ScrollView sView = view.findViewById(R.id.svMain);
        // Hide the Scrollbar
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        //fonts
        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");


        //Initializing circular chart
        pieChartStaff = view.findViewById(R.id.chCircular2);
        pieChartStudent = view.findViewById(R.id.chCircular1);
        setAllStuffsPieChartStaff();

        //setAllStuffsPieChartStudent();
        circularChartStudentGender = view.findViewById(R.id.chCircular1Mf);
        setAllStuffsPieChartStudentGender();
        circularChartStaffGender = view.findViewById(R.id.chCircular2Mf);
        setAllStuffsPieChartStaffGender();
        //dateNow = java.time.LocalDate.now().toString();
        //dateNow= Instant.now().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        dateNow = simpleDateFormat.format(new Date());
        Log.d(TAG, "onCreateView: " + dateNow);

        analyticsPresenter.getAttendanceReport(sharedPrefsHelper.getValue(Constant.TOKEN, ""), dateNow);
        tvDate.setText(dateNow);


        //Initializing Curve graph
        curveGraphView = view.findViewById(R.id.cgv);
        setAllStuffCurveGraph();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        cvMoreAndUpdatedA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    private void initToolbar() {
        toolbarNew = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarNew);
        toolbarNew.setVisibility(View.VISIBLE);
        tvToolbarTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Analytics");
       /* viewStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), StatementActivity.class);
                startActivity(i);
            }
        });*/
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Log.d(TAG, "updateLabel: " + sdf.format(myCalendar.getTime()));

        analyticsPresenter.getAttendanceReport(sharedPrefsHelper.getValue(Constant.TOKEN, ""), sdf.format(myCalendar.getTime()));
        //edittext.setText(sdf.format(myCalendar.getTime()));
    }


    private void setAllStuffsPieChartStudent() {

        pieChartStudent.setUsePercentValues(false);
        pieChartStudent.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText("Student");
        description.setPosition(310, 640);
        description.setTextSize(16f);


        description.setTextColor(Color.parseColor("#036C99"));
        pieChartStudent.setDescription(description);


        pieChartStudent.getLegend().setEnabled(false);
        pieChartStudent.setExtraOffsets(1, -10, 1, -10);


        pieChartStudent.setDragDecelerationFrictionCoef(0.95f);

        pieChartStudent.setCenterTextTypeface(tfLight);
        pieChartStudent.setCenterText(generateStudentAttendance());

        pieChartStudent.setDrawHoleEnabled(true);
        pieChartStudent.setHoleColor(Color.parseColor("#F2F0F7"));

        pieChartStudent.setTransparentCircleColor(Color.parseColor("#F2F0F7"));
        pieChartStudent.setTransparentCircleAlpha(110);

        pieChartStudent.setHoleRadius(72f);
        pieChartStudent.setTransparentCircleRadius(72f);


        pieChartStudent.setDrawCenterText(true);

        pieChartStudent.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChartStudent.setRotationEnabled(true);
        pieChartStudent.setHighlightPerTapEnabled(true);

        // add a selection listener
        pieChartStudent.setOnChartValueSelectedListener(this);
        pieChartStudent.animateY(1400, Easing.EaseInOutQuad);
        // entry label styling
        pieChartStudent.setEntryLabelColor(Color.WHITE);
        //  circularChart.setEntryLabelTypeface(tfRegular);
        pieChartStudent.setEntryLabelTextSize(0f);
        ArrayList<PieEntry> entries = new ArrayList<>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        int range = 5;

       /* for (int i = 0; i < 2; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties[i % parties.length]));
        }*/


        entries.add(new PieEntry(Integer.parseInt(sharedPrefsHelper.getValue(Constant.STUDENT_ABSENT, "0")), (float) 2));
        entries.add(new PieEntry(Integer.parseInt(sharedPrefsHelper.getValue(Constant.STUDENT_PRESENT, "0")), (float) 2));

        PieDataSet dataSet = new PieDataSet(entries, "Student Attendance");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : FOUNDER_COLORS_NEW)
            colors.add(c);


        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChartStudent));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        pieChartStudent.setData(data);

        // undo all highlights
        pieChartStudent.highlightValues(null);

        pieChartStudent.invalidate();
    }

    private void setAllStuffsPieChartStaff() {

        pieChartStaff.setUsePercentValues(false);
        pieChartStaff.getDescription().setEnabled(true);


        Description description = new Description();
        description.setText("Staff");
        description.setPosition(310, 640);
        description.setTextSize(16f);
        description.setTextColor(Color.parseColor("#036C99"));
        pieChartStaff.setDescription(description);


        pieChartStaff.getLegend().setEnabled(false);
        pieChartStaff.setExtraOffsets(1, -10, 1, -10);


        pieChartStaff.setDragDecelerationFrictionCoef(0.95f);

        pieChartStaff.setCenterTextTypeface(tfLight);
        pieChartStaff.setCenterText(generateCenterSpannableText());

        pieChartStaff.setDrawHoleEnabled(true);
        pieChartStaff.setHoleColor(Color.parseColor("#F2F0F7"));

        pieChartStaff.setTransparentCircleColor(Color.parseColor("#F2F0F7"));
        pieChartStaff.setTransparentCircleAlpha(110);

        pieChartStaff.setHoleRadius(72f);
        pieChartStaff.setTransparentCircleRadius(72f);
        pieChartStaff.setDrawCenterText(true);

        pieChartStaff.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChartStaff.setRotationEnabled(true);
        pieChartStaff.setHighlightPerTapEnabled(true);

        // add a selection listener
        pieChartStaff.setOnChartValueSelectedListener(this);
        pieChartStaff.animateY(1400, Easing.EaseInOutQuad);

        // entry label styling
        pieChartStaff.setEntryLabelColor(Color.WHITE);
        pieChartStaff.setEntryLabelTextSize(0f);
        ArrayList<PieEntry> entries = new ArrayList<>();
        pieChartStaff.setCenterText("NO RECORD FOUND");
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        int range = 5;

        for (int i = 0; i < 2; i++) {
            entries.add(new PieEntry((float) (range),
                    parties[i % parties.length]));
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

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChartStaff));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        pieChartStaff.setData(data);

        // undo all highlights
        pieChartStaff.highlightValues(null);

        pieChartStaff.invalidate();
    }

    private void setAllStuffsPieChartStaffGender() {

        circularChartStaffGender.setUsePercentValues(false);
        circularChartStaffGender.getDescription().setEnabled(true);

        Description description = new Description();
        description.setText("Staff");
        description.setPosition(310, 640);
        description.setTextSize(16f);
        description.setTextColor(Color.parseColor("#036C99"));
        circularChartStaffGender.setDescription(description);

        circularChartStaffGender.getLegend().setEnabled(false);
        circularChartStaffGender.setExtraOffsets(1, -10, 1, -10);


        circularChartStaffGender.setDragDecelerationFrictionCoef(0.95f);

        circularChartStaffGender.setCenterTextTypeface(tfLight);
        circularChartStaffGender.setCenterText(generateStaffGenderSpannableText());

        circularChartStaffGender.setDrawHoleEnabled(true);
        circularChartStaffGender.setHoleColor(Color.parseColor("#F2F0F7"));

        circularChartStaffGender.setTransparentCircleColor(Color.parseColor("#F2F0F7"));
        circularChartStaffGender.setTransparentCircleAlpha(110);

        circularChartStaffGender.setHoleRadius(72f);
        circularChartStaffGender.setTransparentCircleRadius(72f);


        circularChartStaffGender.setDrawCenterText(true);

        circularChartStaffGender.setRotationAngle(0);
        // enable rotation of the chart by touch
        circularChartStaffGender.setRotationEnabled(true);
        circularChartStaffGender.setHighlightPerTapEnabled(true);
        // add a selection listener
        circularChartStaffGender.setOnChartValueSelectedListener(this);

        circularChartStaffGender.animateY(1400, Easing.EaseInOutQuad);


        // entry label styling
        circularChartStaffGender.setEntryLabelColor(Color.WHITE);
        circularChartStaffGender.setEntryLabelTextSize(0f);
        ArrayList<PieEntry> entries = new ArrayList<>();
        //entries.add(new PieEntry(5f,3), new PieEntry(5,9));
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        int range = 5;
        String[] staffGender = new String[]{
                male, female
        };
        List<String> staffGenderData = new ArrayList<>();
        staffGenderData.add(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_MALE_DATA, ""));
        staffGenderData.add(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_FEMALE_DATA, ""));

       /* for (int i = 0; i < staffGender.length; i++) {

            entries.add(new PieEntry((float) (range),
                    staffGender[i % staffGender.length]));
        }*/


        entries.add(new PieEntry(Integer.parseInt(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_MALE_DATA, "")), (float) staffGender.length));
        entries.add(new PieEntry(Integer.parseInt(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_FEMALE_DATA, "")), (float) staffGender.length));

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

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(circularChartStaffGender));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        circularChartStaffGender.setData(data);

        // undo all highlights
        circularChartStaffGender.highlightValues(null);

        circularChartStaffGender.invalidate();
    }

    private void setAllStuffsPieChartStudentGender() {
        circularChartStudentGender.setUsePercentValues(false);
        circularChartStudentGender.getDescription().setEnabled(true);


        Description description = new Description();
        description.setText("Student");
        description.setPosition(310, 640);
        description.setTextSize(16f);
        description.setTextColor(Color.parseColor("#036C99"));
        circularChartStudentGender.setDescription(description);


        circularChartStudentGender.getLegend().setEnabled(false);
        circularChartStudentGender.setExtraOffsets(1, -10, 1, -10);


        circularChartStudentGender.setDragDecelerationFrictionCoef(0.95f);

        circularChartStudentGender.setCenterTextTypeface(tfLight);
        circularChartStudentGender.setCenterText(generateStudentGenderSpannableText());

        circularChartStudentGender.setDrawHoleEnabled(true);
        circularChartStudentGender.setHoleColor(Color.parseColor("#F2F0F7"));

        circularChartStudentGender.setTransparentCircleColor(Color.parseColor("#F2F0F7"));
        circularChartStudentGender.setTransparentCircleAlpha(110);

        circularChartStudentGender.setHoleRadius(72f);
        circularChartStudentGender.setTransparentCircleRadius(72f);


        circularChartStudentGender.setDrawCenterText(true);

        circularChartStudentGender.setRotationAngle(0);
        // enable rotation of the chart by touch
        circularChartStudentGender.setRotationEnabled(true);
        circularChartStudentGender.setHighlightPerTapEnabled(true);

        // add a selection listener
        circularChartStudentGender.setOnChartValueSelectedListener(this);

        circularChartStudentGender.animateY(1400, Easing.EaseInOutQuad);

        // entry label styling
        circularChartStudentGender.setEntryLabelColor(Color.WHITE);
        circularChartStudentGender.setEntryLabelTextSize(0f);

        int count = 10;
        int range = 5;

        //testing


        ArrayList<PieEntry> entries = new ArrayList<>();
        //entries.add(new PieEntry(5f,3), new PieEntry(5,9));


       /* for (int i = 0; i < 2; i++) {

            entries.add(new PieEntry((float) (range),
                    parties[i % parties.length]));
        }*/
        String[] studentGender = new String[]{
                male, female
        };
        List<String> studentGenderData = new ArrayList<>();
        studentGenderData.add(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_MALE_DATA, ""));
        studentGenderData.add(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_FEMALE_DATA, ""));


        entries.add(new PieEntry(Integer.parseInt(sharedPrefsHelper.getValue(Constant.STUDENT_TOTAL_MALE_DATA, "")), (float) studentGender.length));
        entries.add(new PieEntry(Integer.parseInt(sharedPrefsHelper.getValue(Constant.STUDENT_TOTAL_FEMALE_DATA, "")), (float) studentGender.length));


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

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(circularChartStudentGender));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        circularChartStudentGender.setData(data);

        // undo all highlights
        circularChartStudentGender.highlightValues(null);

        circularChartStudentGender.invalidate();


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

/*

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
*/


        final GraphData gd = GraphData.builder(getContext())
                .setPointMap(pointMapTotalPaid)
                .setGraphStroke(R.color.FounderRed)
                /*.setGraphGradient(R.color.gradientStartColor2, R.color.gradientEndColor2)*/
                .animateLine(true)
                .setPointColor(R.color.FounderBlue)
                .setPointRadius(5)
                .setStraightLine(true)
                .build();

/*
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
        pm1.addPoint(11, 350);*/


        final GraphData gd1 = GraphData.builder(getContext())
                .setPointMap(pointMapTotalDue)
                .setGraphStroke(R.color.FounderGreen)
                /*.setGraphGradient(R.color.gradientStartColor2, R.color.gradientEndColor2)*/
                .animateLine(true)
                .setPointColor(R.color.Red)
                .setPointRadius(5)
                .setStraightLine(true)
                .build();

/*
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
        pm2.addPoint(11, 550);*/


        final GraphData gd2 = GraphData.builder(getContext())
                .setPointMap(pointMapTotalCharge)
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
                curveGraphView.setData(spanForIncomeVsDue, maxValueForIncomeVsDue, gd, gd1, gd2);
            }
        }, 10);
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("16\nAbsent\n576\nTotal");
        s.setSpan(new ForegroundColorSpan(Color.rgb(160, 17, 28)), 0, 2, 0);
        s.setSpan(new RelativeSizeSpan(2f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        s.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), 3, 10, 1);
        s.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 3, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        s.setSpan(new ForegroundColorSpan(Color.rgb(8, 154, 214)), 10, 13, 2);
        s.setSpan(new RelativeSizeSpan(2f), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        s.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), 14, 19, 3);
        s.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 14, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    private SpannableString generateStudentGenderSpannableText() {
        SpannableString ss = new SpannableString(sharedPrefsHelper.getValue(Constant.STUDENT_TOTAL_FEMALE_DATA, "") + "\n" + female + "\n" + sharedPrefsHelper.getValue(Constant.STUDENT_TOTAL_MALE_DATA, "") + "\n" + male);
        int lengthFemaleTotal = sharedPrefsHelper.getValue(Constant.STUDENT_TOTAL_FEMALE_DATA, "").length();
        int lengthFemale = 6;
        int lengthMaleTotal = sharedPrefsHelper.getValue(Constant.STUDENT_TOTAL_MALE_DATA, "").length();
        int lengthMale = 4;


        ss.setSpan(new ForegroundColorSpan(Color.rgb(160, 17, 28)), 0, lengthFemaleTotal, 0);
        ss.setSpan(new RelativeSizeSpan(2f), 0, lengthFemaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, lengthFemaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), lengthFemaleTotal + 1, lengthFemaleTotal + lengthFemale + 1, 1);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthFemaleTotal + 1, lengthFemaleTotal + lengthFemale + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.rgb(8, 154, 214)), lengthFemaleTotal + lengthFemale + 2, lengthFemaleTotal + lengthFemale + 2 + lengthMaleTotal, 2);
        ss.setSpan(new RelativeSizeSpan(2f), lengthFemaleTotal + lengthFemale + 2, lengthFemaleTotal + lengthFemale + 2 + lengthMaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthFemaleTotal + lengthFemale + 2, lengthFemaleTotal + lengthFemale + 2 + lengthMaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        ss.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3, lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3 + lengthMale, 3);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3, lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3 + lengthMale, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }


    private SpannableString generateStaffGenderSpannableText() {
        SpannableString ss = new SpannableString(sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_FEMALE_DATA, "") + "\n" + female + "\n" + sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_MALE_DATA, "") + "\n" + male);
        int lengthFemaleTotal = sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_FEMALE_DATA, "").length();
        int lengthFemale = 6;
        int lengthMaleTotal = sharedPrefsHelper.getValue(Constant.STAFF_TOTAL_MALE_DATA, "").length();
        int lengthMale = 4;


        ss.setSpan(new ForegroundColorSpan(Color.rgb(160, 17, 28)), 0, lengthFemaleTotal, 0);
        ss.setSpan(new RelativeSizeSpan(2f), 0, lengthFemaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, lengthFemaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), lengthFemaleTotal + 1, lengthFemaleTotal + lengthFemale + 1, 1);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthFemaleTotal + 1, lengthFemaleTotal + lengthFemale + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.rgb(8, 154, 214)), lengthFemaleTotal + lengthFemale + 2, lengthFemaleTotal + lengthFemale + 2 + lengthMaleTotal, 2);
        ss.setSpan(new RelativeSizeSpan(2f), lengthFemaleTotal + lengthFemale + 2, lengthFemaleTotal + lengthFemale + 2 + lengthMaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthFemaleTotal + lengthFemale + 2, lengthFemaleTotal + lengthFemale + 2 + lengthMaleTotal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        ss.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3, lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3 + lengthMale, 3);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3, lengthFemaleTotal + lengthFemale + lengthMaleTotal + 3 + lengthMale, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }


    private SpannableString generateStudentAttendance() {
        SpannableString ss = new SpannableString(sharedPrefsHelper.getValue(Constant.STUDENT_ABSENT, "") + "\n" + absent + "\n" + sharedPrefsHelper.getValue(Constant.STUDENT_PRESENT, "") + "\n" + present);
        int lengthTotalAbsent = sharedPrefsHelper.getValue(Constant.STUDENT_ABSENT, "").length();
        int lengthAbsent = 6;
        int lengthTotalPresent = sharedPrefsHelper.getValue(Constant.STUDENT_PRESENT, "").length();
        int lengthPresent = 4;


        ss.setSpan(new ForegroundColorSpan(Color.rgb(160, 17, 28)), 0, lengthTotalAbsent, 0);
        ss.setSpan(new RelativeSizeSpan(2f), 0, lengthTotalAbsent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, lengthTotalAbsent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), lengthTotalAbsent + 1, lengthTotalAbsent + lengthAbsent + 1, 1);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthTotalAbsent + 1, lengthTotalAbsent + lengthAbsent + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(new ForegroundColorSpan(Color.rgb(8, 154, 214)), lengthTotalAbsent + lengthAbsent + 2, lengthTotalAbsent + lengthAbsent + 2 + lengthTotalPresent, 2);
        ss.setSpan(new RelativeSizeSpan(2f), lengthTotalAbsent + lengthAbsent + 2, lengthTotalAbsent + lengthAbsent + 2 + lengthTotalPresent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthTotalAbsent + lengthAbsent + 2, lengthTotalAbsent + lengthAbsent + 2 + lengthTotalPresent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        ss.setSpan(new ForegroundColorSpan(Color.rgb(74, 66, 66)), lengthTotalAbsent + lengthAbsent + lengthTotalPresent + 3, lengthTotalAbsent + lengthAbsent + lengthTotalPresent + 3 + lengthPresent, 3);
        ss.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), lengthTotalAbsent + lengthAbsent + lengthTotalPresent + 3, lengthTotalAbsent + lengthAbsent + lengthTotalPresent + 3 + lengthPresent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
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

    @Override
    public void showLoading() {
    }

    @Override
    public void populateEmployeeGenderWise(List<EmployeeGenderWise> employeeGenderWises) {
        List<Integer> genderTypeListStaff = new ArrayList<>();
        for (int i = 0; i < employeeGenderWises.size(); i++) {
            genderTypeListStaff.add(employeeGenderWises.get(i).getGENDER());
        }

        for (int j = 0; j < genderTypeListStaff.size(); j++) {
            if (genderTypeListStaff.get(j) == 0) {
                totalFemaleStaff = employeeGenderWises.get(j).getTotal().toString();
            } else if (genderTypeListStaff.get(j) == 1) {
                totalMaleStaff = employeeGenderWises.get(j).getTotal().toString();
            }
        }
        if (employeeGenderWises.size() == 0) {
            sharedPrefsHelper.saveValue(Constant.STUDENT_TOTAL_FEMALE_DATA, "0");
            sharedPrefsHelper.saveValue(Constant.STUDENT_TOTAL_MALE_DATA, "0");
        } else {
            sharedPrefsHelper.saveValue(Constant.STAFF_TOTAL_FEMALE_DATA, totalFemaleStaff);
            sharedPrefsHelper.saveValue(Constant.STAFF_TOTAL_MALE_DATA, totalMaleStaff);
        }
        setAllStuffsPieChartStaffGender();
    }

    @Override
    public void populateStudentGenderWise(List<StudentGenderWise> studentGenderWises) {

        List<Integer> genderTypeListStudent = new ArrayList<>();
        for (int i = 0; i < studentGenderWises.size(); i++) {
            genderTypeListStudent.add(studentGenderWises.get(i).getGENDER());
        }

        for (int j = 0; j < genderTypeListStudent.size(); j++) {
            if (genderTypeListStudent.get(j) == 0) {
                totalFemaleStudent = studentGenderWises.get(j).getTotal().toString();
            } else if (genderTypeListStudent.get(j) == 1) {
                totalMaleStudent = studentGenderWises.get(j).getTotal().toString();
            }
        }
        if (studentGenderWises.size() == 0) {
            sharedPrefsHelper.saveValue(Constant.STUDENT_TOTAL_FEMALE_DATA, "0");
            sharedPrefsHelper.saveValue(Constant.STUDENT_TOTAL_MALE_DATA, "0");
        } else {
            sharedPrefsHelper.saveValue(Constant.STUDENT_TOTAL_FEMALE_DATA, totalFemaleStudent);
            sharedPrefsHelper.saveValue(Constant.STUDENT_TOTAL_MALE_DATA, totalMaleStudent);
        }

        setAllStuffsPieChartStudentGender();
    }

    @Override
    public void populateStudentAttendance(StudentAttendance response) {
        if (response.getStatus().equals("unavailable")) {
            clErrorAR.setVisibility(View.VISIBLE);
            tvNetworkError.setVisibility(View.VISIBLE);
            tvNetworkError.setText("NO DATA FOUND. PLEASE SELECT ANOTHER DATE");
            chCircular1.setVisibility(View.INVISIBLE);
        } else {
            sharedPrefsHelper.saveValue(Constant.STUDENT_PRESENT, response.getPresentCount().toString());
            sharedPrefsHelper.saveValue(Constant.STUDENT_ABSENT, response.getAbsentCount().toString());
        }
        setAllStuffsPieChartStudent();

    }

    @OnClick(R.id.ivRightArrow)
    public void ivRightArrowClicked() {
        date++;
    }

    @OnClick(R.id.ivLeftArrow)
    public void ivLeftArrowClicked() {
        date--;

    }


    @Override
    public void populateIncomeVsDueBalance(List<ClassDueReport> response) {

        spanForIncomeVsDue = response.size();
        pointMapTotalCharge = new PointMap();
        pointMapTotalDue = new PointMap();
        pointMapTotalPaid = new PointMap();
        int[] list = new int[50];
        int compare;
        int totalCharge = 0;
        int totalPaid = 0;
        int totalDue = 0;
        for (int i = 0; i < response.size(); i++) {
            totalCharge = response.get(i).getTotalCharge() + totalCharge;
            totalPaid = response.get(i).getTotalPaid() + totalPaid;
            totalDue = response.get(i).getTotalDue() + totalDue;
            list[i] = response.get(i).getTotalCharge();
            incomeVsDueClass.add(response.get(i).getClass_());
            pointMapTotalCharge.addPoint(i, response.get(i).getTotalCharge());
            pointMapTotalDue.addPoint(i, response.get(i).getTotalDue());
            pointMapTotalPaid.addPoint(i, response.get(i).getTotalPaid());

        }
        for (int i = 0; i < list.length; i++) {
            for (int j = i + 1; j < list.length; j++) {
                if (list[i] > list[j]) {
                    compare = list[i];
                    list[i] = list[j];
                    list[j] = compare;
                }
            }

        }
        maxValueForIncomeVsDue = list[list.length - 1];

        Log.d(TAG, "maxvalue: " + maxValueForIncomeVsDue);


        tvTotalChargedNum.setText(totalCharge + "");
        tvTotalPaidNum.setText(totalPaid + "");
        tvTotalDUeNum.setText(totalDue + "");
        setAllStuffCurveGraph();


    }

    @Override
    public void onSuccessAR(String newDate) {
        loadingPanel.setVisibility(View.GONE);
        tvNetworkError.setVisibility(View.GONE);
        chCircular1.setVisibility(View.VISIBLE);
        tvDate.setText(newDate);
    }

    @Override
    public void onSuccessIVDB() {
        tvUpdatedDateIVDB.setText("Synced on " + dateNow);
        clErrorIVDB.setVisibility(View.GONE);
        cgv.setVisibility(View.VISIBLE);
        clAmount.setVisibility(View.VISIBLE);
        loadingPanelIVDB.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessTSASR() {
        tvUpdatedDateTSASR.setText("Synced on " + dateNow);
        clErrorTSASR.setVisibility(View.GONE);
        chCircular1Mf.setVisibility(View.VISIBLE);
        chCircular2Mf.setVisibility(View.VISIBLE);
        loadingPanelTSASR.setVisibility(View.GONE);
    }

    @Override
    public void LoadingScreenOnAttendance() {
        clErrorAR.setVisibility(View.GONE);
        chCircular1.setVisibility(View.INVISIBLE);
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingScreenOnIVDB() {
        clErrorIVDB.setVisibility(View.GONE);
        cgv.setVisibility(View.INVISIBLE);
        clAmount.setVisibility(View.INVISIBLE);
        loadingPanelIVDB.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingScreenOnTSASR() {
        clErrorTSASR.setVisibility(View.GONE);
        chCircular1Mf.setVisibility(View.INVISIBLE);
        chCircular2Mf.setVisibility(View.INVISIBLE);
        loadingPanelTSASR.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetworkErrorOnAttendance(String type) {

        clErrorAR.setVisibility(View.VISIBLE);
        loadingPanel.setVisibility(View.GONE);
        chCircular1.setVisibility(View.INVISIBLE);
        if (type.equals("server"))
            tvNetworkError.setText("THERE iS PROBLEM IN SERVER");

        else if (type.equals("network"))
            tvNetworkError.setText("NO INTERNET AVAILABLE!");
    }

    @Override
    public void showNetworkErrorOnIVDB(String type) {
        clErrorIVDB.setVisibility(View.VISIBLE);
        loadingPanelIVDB.setVisibility(View.GONE);
        cgv.setVisibility(View.INVISIBLE);
        clAmount.setVisibility(View.VISIBLE);
        if (type.equals("server"))
            tvErrorTitleIVDB.setText("THERE iS PROBLEM IN SERVER");

        else if (type.equals("network"))
            tvErrorSubTitleIVDB.setText("NO INTERNET AVAILABLE!");

    }

    @OnClick({R.id.btRetryIVDB, R.id.cvMoreAndUpdatedIVDB})
    void btRetryIVDBClicked() {
        analyticsPresenter.getIncomeVsDueBalance(sharedPrefsHelper.getValue(Constant.TOKEN, ""));
    }

    @OnClick({R.id.btRetryTSASR, R.id.cvMoreAndUpdatedTSASR})
    void btRetryTSASR() {
        analyticsPresenter.getGenderWiseStaffAndStudent(sharedPrefsHelper.getValue(Constant.TOKEN, ""), true);
    }

    @Override
    public void showNetworkErrorOnTSASR(String type) {
        clErrorTSASR.setVisibility(View.VISIBLE);
        loadingPanelTSASR.setVisibility(View.GONE);
        chCircular1Mf.setVisibility(View.INVISIBLE);
        chCircular2Mf.setVisibility(View.INVISIBLE);
        if (type.equals("server"))
            tvErrorTitleTSASR.setText("THERE iS PROBLEM IN SERVER");
        else if (type.equals("network"))
            tvErrorTitleTSASR.setText("NO INTERNET AVAILABLE!");
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
}