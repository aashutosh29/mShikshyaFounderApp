package com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.SearchAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.SearchProfile;
import com.bihanitech.shikshyaprasasak.model.student.Student;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.StudentProfile.StudentProfileActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements SearchView {

    List<SearchProfile> searchProfiles = new ArrayList<>();
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;
    @BindView(R.id.etStudentNameToSearch)
    EditText etStudentNameToSearch;

    SharedPrefsHelper sharedPrefsHelper;
    @BindView(R.id.spClass)
    Spinner spClass;

    @BindView(R.id.spSection)
    Spinner spSection;

    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;

    SearchPresenter searchPresenter;
    SearchAdapter recyclerAdapter;
    ProgressDialog dialog;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onStart() {
        super.onStart();
        etStudentNameToSearch.requestFocus();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        searchPresenter = new SearchPresenter(new MetaDatabaseRepo(getHelper()), this);
        searchPresenter.getSpinnerData();
        populateStudentDetails();
        initSpinner();
        //searchPresenter.getStudents("L037", "L052", sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        dialog = new ProgressDialog(this);
        //initRecyclerView();
        makeASearch();


    }


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    private void makeASearch() {

       /* etStudentNameToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                *//*if (editable.toString().trim().equals(""))
                    rvSearch.setVisibility(View.INVISIBLE);*//*
                filter(editable.toString());
                //rvSearch.setVisibility(View.VISIBLE);
            }
        });*/
    }

    @OnClick(R.id.ivBack)
    void ivBackOnClicked() {
        onBackPressed();
    }

  /*  private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(llm);
        rvSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new SearchAdapter(searchProfiles, this);
        rvSearch.setAdapter(recyclerAdapter);
    }*/

  /*  private void filter(String text) {
        List<SearchProfile> filteredSearchProfileList = new ArrayList<>();
        for (SearchProfile sP : searchProfiles) {
            if (sP.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredSearchProfileList.add(sP);

            }
        }
       *//* if (filteredSearchProfileList.size() == 0)
            rvSearch.setVisibility(View.INVISIBLE);
        else {*//*

        rvSearch.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(llm);
        rvSearch.setItemAnimator(new DefaultItemAnimator());
        SearchAdapter recyclerAdapter = new SearchAdapter(filteredSearchProfileList, this);
        rvSearch.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }*/


    private void populateStudentDetails() {
        SearchProfile searchProfile = new SearchProfile("Rajesh Adhikari", "Fulbari", "6 (C)", R.drawable.img_profile_1, "Ram Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Nida Liu", "Cecilia Chapman", "6 (A)", R.drawable.img_profile_1, "Aadesh Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Lester Foskey", "Iris Watson", "7 (B)", R.drawable.img_profile_2, "Aadi Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Kenda Graig", "Celeste Slater", "8 (C)", R.drawable.img_profile_3, "Aadarsh Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Shirlee Resendiz", "Theodore Lowe", "9 (A)", R.drawable.img_profile_4, "Aadinath Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Shari Vivier", "Calista Wise", "10 (B)", R.drawable.img_profile_5, "Aadishankar Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Jolynn Kumar", "Kyla Olsen", "9 (C)", R.drawable.img_profile_6, "Aahlaad Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Cyndi Branning", "Forrest Ray", "8 (A)", R.drawable.img_profile_1, "Aakaash Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Suanne Cash", "Hiroko Potter", "1 (B)", R.drawable.img_profile_2, "Aakanksha Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Liliana Votaw", "Nyssa Vazquez", "2 (C)", R.drawable.img_profile_3, "Aakar Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Savanna Crabb", "Lawrence Moreno", "3 (A)", R.drawable.img_profile_4, "Aakarshan Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Larry Lepine", "Ina Moran", "4 (B)", R.drawable.img_profile_5, "Alaap Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Marcelo Mock", "Aaron Hawkins", "5 (C)", R.drawable.img_profile_6, "Aamod Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Delia Grabill", "Hedy Greene", "6 (A)", R.drawable.img_profile_1, "Aashish Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Lavenia Schafer", "Melvin Porter", "7 (B)", R.drawable.img_profile_2, "Aastik Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Katelyn Iliff", "Keefe Sellers", "8 (C)", R.drawable.img_profile_3, "Aatish Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Rita Mulvey", "Joan Romero", "9 (A)", R.drawable.img_profile_4, "Abhay Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Cammie Parvin", "Fulbari", "10 (B)", R.drawable.img_profile_5, "Abhayananda Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Malcom Goggins", "Fulbari", "1 (C)", R.drawable.img_profile_6, "Abhichandra Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Nida Liu", "Fulbari", "2 (A)", R.drawable.img_profile_1, "Abhidi Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Lester Foskey", "Fulbari", "3 (B)", R.drawable.img_profile_2, "Abhijat Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Kenda Graig", "Fulbari", "4 (C)", R.drawable.img_profile_3, "Abhik Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Shirlee Resendiz", "Fulbari", "5 (A)", R.drawable.img_profile_4, "Abhimand Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Shari Vivier", "Fulbari", "6 (B)", R.drawable.img_profile_5, "Abhimani Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Jolynn Kumar", "Fulbari", "7 (C)", R.drawable.img_profile_6, "Aarav Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Cyndi Branning", "Fulbari", "8 (A)", R.drawable.img_profile_1, "Arbindra Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Suanne Cash", "Fulbari", "9 (B)", R.drawable.img_profile_2, "Arbindra Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Liliana Votaw", "Fulbari", "10 (C)", R.drawable.img_profile_3, "Arbindra Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Savanna Crabb", "Fulbari", "1 (A)", R.drawable.img_profile_4, "Aajad Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Larry Lepine", "Fulbari", " 2(B)", R.drawable.img_profile_5, "Arit Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Marcelo Mock", "Fulbari", "3 (C)", R.drawable.img_profile_6, "Ashok Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Delia Grabill", "Fulbari", "4 (A)", R.drawable.img_profile_1, "Aryan Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Lavenia Schafer", "Fulbari", "5 (B)", R.drawable.img_profile_2, "Abisesh Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Katelyn Iliff", "Fulbari", "6 (A)", R.drawable.img_profile_3, "Anjan Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Rita Mulvey", "Fulbari", "7 (C)", R.drawable.img_profile_4, "Anjan Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Cammie Parvin", "Fulbari", "8 (B)", R.drawable.img_profile_5, "Anurag Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Malcom Goggins", "Fulbari", "9 (C)", R.drawable.img_profile_6, "Aahrav Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);
        searchProfile = new SearchProfile("Rajesh Adhikari", "Fulbari", "2 (A)", R.drawable.img_profile_1, "Aviyan Adhikari", "Sita Adhikari", "Shyam Adhikari", "9860848450");
        searchProfiles.add(searchProfile);


    }


    @Override
    public void getClickedStudentDetails(Student searchProfile) {
        Intent i = new Intent(SearchActivity.this, StudentProfileActivity.class);
        i.putExtra(Constant.STUDENT_NAME, searchProfile.getName());
        i.putExtra(Constant.STUDENT_ADDRESS, searchProfile.getAddress());
        i.putExtra(Constant.STUDENT_CLASS, searchProfile.getClass_().getClassname());
        i.putExtra(Constant.STUDENT_FATHER_NAME, searchProfile.getFathername());
        i.putExtra(Constant.STUDENT_MOTHER_NAME, searchProfile.getMothername());
        i.putExtra(Constant.STUDENT_GUARDIAN_NAME, searchProfile.getGurdainname());
        i.putExtra(Constant.STUDENT_CONTACT_NUMBER, searchProfile.getContact());
        i.putExtra(Constant.REGNO, searchProfile.getRegno());
        startActivity(i);


    }

    @Override
    public void populateClasses(List<String> className) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, className);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClass.setAdapter(arrayAdapter);

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                searchPresenter.getStudents(parentView.getItemAtPosition(position).toString(), "", sharedPrefsHelper.getValue(Constant.TOKEN, ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    public void populateStudentList(List<Student> studentList) {
        if (studentList.size() == 0) {
            tvNoDataFound.setVisibility(View.VISIBLE);

        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(llm);
        rvSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new SearchAdapter(studentList, this, this);
        rvSearch.setAdapter(recyclerAdapter);
    }

    @Override
    public void showLoading() {

        dialog.setMessage("Loading data");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showError() {
        dialog.dismiss();
        tvNoDataFound.setText("Something went wrong");
        tvNoDataFound.setVisibility(View.VISIBLE);
    }

    private void initSpinner() {
    }
}
