package com.bihanitech.shikshyaprasasak.ui.homeActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.StudentInfo;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.ProgressDFragment;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.UpdateDF;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.academicsFragment.AcademicsFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.AnalyticsFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.contactActivity.ContactActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.homeFragment.HomeFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment.MoreFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeActivity.NoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.webViewAcitivity.WebViewActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements HomeView {


    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btNavMain)
    BottomNavigationView btNavMain;


    @BindView(R.id.nvView)
    NavigationView nvDrawer;

    @BindView(R.id.ivTwoline)
    ImageView ivTwoline;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    TextView tvName;

    TextView tvSchool;


    SharedPrefsHelper sharedPrefsHelper;

    int iSwitch;
    @BindDrawable(R.drawable.ic_arrow_drop_down_black_24dp)
    Drawable ibDown;
    @BindDrawable(R.drawable.ic_arrow_drop_up_black_24dp)
    Drawable ibUp;
    HomePresenter homePresenter;
    List<StudentInfo> studentInfoList = new ArrayList<>();
    ProgressDFragment progressDFragment;
    FragmentManager fm;
    int retry;
    private ActionBarDrawerToggle drawerToggle;
    private DatabaseHelper databaseHelper;

    public static void openAppRating(Context context) {
        // you can also use BuildConfig.APPLICATION_ID
        String appId = context.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager()
                .queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName
                    .equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );

                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appId));
            context.startActivity(webIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);
        homePresenter = new HomePresenter(new MetaDatabaseRepo(getDatabaseHelper()), this);
        homePresenter.getStudentsList();
        fm = getSupportFragmentManager();
        FragmentManager manager = getSupportFragmentManager();
        btNavMain.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        manager.beginTransaction().add(R.id.flContent, new HomeFragment()).commit();
        setOriginalMenuView();

        setupDrawerContent(nvDrawer);
        btNavMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();

                switch (item.getItemId()) {
                    case R.id.action_home: {
                        manager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();

                    }
                    break;
                    case R.id.action_analytics: {
                        manager.beginTransaction().replace(R.id.flContent, new AnalyticsFragment()).commit();

                    }
                    break;
                    case R.id.action_academic: {
                        manager.beginTransaction().replace(R.id.flContent, new AcademicsFragment()).commit();
                    }
                    break;
                    case R.id.action_more: {
                        manager.beginTransaction().replace(R.id.flContent, new MoreFragment()).commit();

                    }
                    break;

                }


                return true;
            }
        });


        ivTwoline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(nvDrawer);
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

    private void setOriginalMenuView() {
        // ibSwitch.setImageDrawable(ibDown);
        iSwitch = 0;
        nvDrawer.getMenu().clear();
        nvDrawer.inflateMenu(R.menu.nav_menu);
    }


    private void setupDrawerContent(NavigationView navigationView) {

        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = HomeFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();

            if (fragment == null) {
                Log.v("TAGGGE", "I am null");
            } else {
                Log.v("TAGGGE", "I am not null");
            }
        } catch (Exception e) {
            Log.v("TAGGGE", e.getMessage());
            e.printStackTrace();

        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "HOME").commit();


        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });

    }

    public void selectDrawerItem(MenuItem menuItem) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        if (menuItem.getItemId() == R.id.nav_upload_notice) {
            menuItem.setChecked(false);
            startActivity(new Intent(this, AddNoticeActivity.class));
        } else if (menuItem.getItemId() == R.id.nav_notice) {
            menuItem.setChecked(false);
            startActivity(new Intent(this, NoticeActivity.class));
        } else if (menuItem.getItemId() == R.id.nav_web_view) {
            menuItem.setChecked(false);
            startActivity(new Intent(this, WebViewActivity.class));
        } else if (menuItem.getItemId() == R.id.nav_calendar) {
            menuItem.setChecked(false);
        } else if (menuItem.getItemId() == R.id.nav_homework) {
            menuItem.setChecked(false);

        } else if (menuItem.getItemId() == R.id.nav_contact) {
            menuItem.setChecked(false);
            Intent i = new Intent(this, ContactActivity.class);
            startActivity(i);

        } else if (menuItem.getItemId() == R.id.nav_marks_entry) {
            menuItem.setChecked(false);


        } else if (menuItem.getItemId() == R.id.nav_sync_subject) {
            menuItem.setChecked(false);
//            checkForSubjectUpdate();

        } else if (menuItem.getItemId() == R.id.nav_sync_students) {
            menuItem.setChecked(false);
//            checkForStudentsUpdate();
        } else {
            invalidateStudentInfo();


//            for (StudentInfo studentInfo : studentInfoList) {
//                if(menuItem.getItemId() == (100+studentInfo.getStId())){
//                    invalidateStudentInfo(studentInfo);
//                }
//            }
            if (menuItem.getItemId() == (100 + studentInfoList.get(0).getStId())) {

                //   invalidateStudentInfo(studentInfoList.get(0));

            } else if (menuItem.getItemId() == 102) {
                //    invalidateStudentInfo(2);
            }

            Fragment fragment = null;
            Class fragmentClass = null;

            fragmentClass = AnalyticsFragment.class;


            try {
                fragment = (Fragment) fragmentClass.newInstance();

                if (fragment == null) {
                    Log.v("TAGGGE", "I ma null");
                } else {
                    Log.v("TAGGGE", "I am not null");
                }
            } catch (Exception e) {
                Log.v("TAGGGE", e.getMessage());
                e.printStackTrace();

            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "HOME").commit();

        }
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.ivProfile)
    void ivProfileClicked() {
        Context mContex = HomeActivity.this;
        Intent mIntent = new Intent(mContex, AddNoticeActivity.class);
        startActivity(mIntent);
        finish();
    }


    private void invalidateStudentInfo() {
        tvName.setText(sharedPrefsHelper.getValue(Constant.TEACHER_NAME, ""));
        tvSchool.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        setOriginalMenuView();
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

        drawerToggle.onConfigurationChanged(newConfig);
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