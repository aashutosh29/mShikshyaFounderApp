package com.bihanitech.shikshyaprasasak.ui.schoolSelection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.SchoolListAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.SchoolInfo;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.registrationActivity.RegistrationActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SchoolSelection extends AppCompatActivity implements SchoolSelectionView{

    @BindView(R.id.rvSchools)
    RecyclerView rvSchools;

    DatabaseHelper databaseHelper;
    SchoolSelectionPresenter schoolSelectionPresenter;
    SharedPrefsHelper sharedPrefsHelper;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.ivClear)
    ImageView ivClear;

    List<SchoolInfo> schoolInfoList ;
    List<String> schoolNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_selection);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        schoolInfoList = new ArrayList<>();
        schoolNames = new ArrayList<>();
        schoolSelectionPresenter = new SchoolSelectionPresenter(this,new MetaDatabaseRepo(getDatabaseHelper()));
        schoolSelectionPresenter.setUpSchoolList();
        listenForSearch();

    }

    @Override
    public void setUpRecyclerView(List<SchoolInfo> schoolInfo) {
        if(schoolInfoList.size()==0)
            schoolInfoList = schoolInfo;

        rvSchools.setAdapter(new SchoolListAdapter(this,schoolInfo,R.layout.item_school,this));
        //rvSchools.setLayoutManager(new GridLayoutManager(this,3));
        rvSchools.setLayoutManager(new LinearLayoutManager(this));

    }




    @Override
    public void sendToRegistration(SchoolInfo schoolInfo) {
        sharedPrefsHelper.saveValue(Constant.SCHOOL_NAME,schoolInfo.getName());
        sharedPrefsHelper.saveValue(Constant.SCHOOL_ADDRESS,schoolInfo.getAddress());
        sharedPrefsHelper.saveValue(Constant.SCHOOL_ID,schoolInfo.getSchoolid());
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }

    /* @Override
    public Bitmap getBitmapFile(String schoolid) {
        Bitmap bMap = null;
        FileInputStream inputStream = null;
           // bMap = BitmapFactory.decodeFile(getExternalFilesDir(null) + File.separator+ " " + schoolid + ".jpg");
        try {
            inputStream = this.openFileInput(schoolid+".png");
            bMap = BitmapFactory.decodeStream(this.openFileInput(schoolid+".png"));
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bMap;
    }*/

    private DatabaseHelper getDatabaseHelper(){
        if(databaseHelper==null){
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(databaseHelper!=null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private void listenForSearch(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==0){
                    ivClear.setVisibility(View.GONE);
                    hideKeyboard(SchoolSelection.this);
                }else{
                    searchForSchool(charSequence.toString());
                    ivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchForSchool(String searchKey) {

        List<SchoolInfo> schoolInfos = new ArrayList<>();
        for (SchoolInfo schoolInfo : schoolInfoList) {
            String schoolNameLS = schoolInfo.getName().toLowerCase();
            if(schoolNameLS.contains(searchKey.toLowerCase())){
                schoolInfos.add(schoolInfo);
            }
        }

        rvSchools.setAdapter(new SchoolListAdapter(this,schoolInfos,R.layout.item_school,this));
        //rvSchools.setLayoutManager(new GridLayoutManager(this,3));
        rvSchools.setLayoutManager(new LinearLayoutManager(this));


    }



    @OnClick(R.id.ivClear)
    public void ivClearClicked(){
        etSearch.setText("");
        ivClear.setVisibility(View.GONE);
        rvSchools.setAdapter(new SchoolListAdapter(this,schoolInfoList,R.layout.item_school,this));
        //rvSchools.setLayoutManager(new GridLayoutManager(this,3));
        rvSchools.setLayoutManager(new LinearLayoutManager(this));
    }


    public void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
