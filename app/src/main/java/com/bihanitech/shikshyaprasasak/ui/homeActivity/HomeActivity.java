package com.bihanitech.shikshyaprasasak.ui.homeActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.UpdateDF;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment.AcademicsFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.AnalyticsFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment.MoreFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.notifyActivity.NotifyActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements HomeView {


    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.bnNavigation)
    BottomNavigationView bnNavigation;
    @BindView(R.id.ivNewCircle)
    ImageView ivNewCircle;
    @BindDrawable(R.drawable.ic_arrow_drop_down_black_24dp)
    Drawable ibDown;
    @BindDrawable(R.drawable.ic_arrow_drop_up_black_24dp)
    Drawable ibUp;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;


    TextView tvName;
    TextView tvSchool;
    SharedPrefsHelper sharedPrefsHelper;
    HomePresenter homePresenter;
    FragmentManager fm;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        Log.d(TAG, "onCreate: " + sharedPrefsHelper.getValue(Constant.TOKEN, ""));
        homePresenter = new HomePresenter(new MetaDatabaseRepo(getDatabaseHelper()), this);
        homePresenter.getStudentsList();
        fm = getSupportFragmentManager();
        loadFragment(new HomeFragment());
        setUpBottomNavigation();
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, NoticeActivity.class);
                startActivity(i);
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void setUpBottomNavigation() {
        bnNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        loadFragment(new HomeFragment());
                        return true;


                    case R.id.action_analytics:
                        loadFragment(new AnalyticsFragment());
                        return true;


                    case R.id.action_academic:
                        loadFragment(new AcademicsFragment());
                        return true;
                    case R.id.action_more:
                        loadFragment(new MoreFragment());
                        return true;

                }


                return false;
            }
        });

    }

    private void checkForStudentOrAppUpdate() {
        if (!sharedPrefsHelper.getValue(Constant.APPLICATION_UPTO_DATE, true)) {
            FragmentManager fm = getSupportFragmentManager();
            UpdateDF networkErrorDFragment = UpdateDF.newInstance(Constant.SERVER_ERROR, HomeActivity.class.getSimpleName());
            networkErrorDFragment.show(fm, "NetworkError");

            //show update dialog
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getStringExtra(Constant.NOTIFICATION_ACTION) != null) {
            Log.v(TAG, intent.getStringExtra(Constant.NOTIFICATION_ACTION));
        } else {
            Log.v(TAG, "I have no intent");
        }
    }

    @OnClick(R.id.ivNotification)
    public void cvNotificationClicked() {
        sharedPrefsHelper.saveValue(Constant.NOTIFICATION_COUNT, 0);
        ivNewCircle.setVisibility(View.GONE);
        Intent i = new Intent(HomeActivity.this, NotifyActivity.class);
        startActivity(i);
    }


    private void invalidateStudentInfo() {
        tvName.setText(sharedPrefsHelper.getValue(Constant.TEACHER_NAME, ""));
        tvSchool.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        //setOriginalMenuView();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles

        //drawerToggle.onConfigurationChanged(newConfig);
    }


    private DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null)
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }

        databaseHelper = null;
    }


    private void showAppUpdateDialog() {
        checkForStudentOrAppUpdate();
    }


}