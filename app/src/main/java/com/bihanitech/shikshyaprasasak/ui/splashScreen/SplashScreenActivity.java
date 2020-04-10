package com.bihanitech.shikshyaprasasak.ui.splashScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.dialogFragment.NetworkErrorDFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.HomeActivity;
import com.bihanitech.shikshyaprasasak.ui.schoolSelection.SchoolSelection;
import com.bihanitech.shikshyaprasasak.ui.usermanagement.verifyOtpActivity.VerifyOtpActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView{

    SplashScreenPresenter splashScreenPresenter;
    SharedPrefsHelper sharedPrefsHelper;
    DatabaseHelper databaseHelper;

    @BindView(R.id.tvLoading)
    TextView tvLoading;

    @BindView(R.id.pbProgress)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        splashScreenPresenter = new SplashScreenPresenter(this,new MetaDatabaseRepo(getHelper()));
        sharedPrefsHelper = SharedPrefsHelper.getInstance(this);

        if(sharedPrefsHelper.getValue(Constant.CURRRENT_VERSION,0)!=4){
            sharedPrefsHelper.clear();
            sharedPrefsHelper.saveValue(Constant.CURRRENT_VERSION,4);
        }
        if(sharedPrefsHelper.getValue(Constant.LOGGED_IN,false)){
            Intent i = new Intent(this, HomeActivity.class);
            if(getIntent()!=null){
                if(getIntent().getStringExtra(Constant.NOTIFICATION_ACTION)!=null){
                    String notAction = getIntent().getStringExtra(Constant.NOTIFICATION_ACTION);
                    i.putExtra(Constant.NOTIFICATION_ACTION,notAction);
                }else{
                    final String TAG = SplashScreenActivity.class.getSimpleName();
                    Log.v(TAG," I have no intent data");
                }
            }
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        }else if (sharedPrefsHelper.getValue(Constant.VERIFY_WAITING, false)) {
            sendtoVerify();
        }else {
            if (sharedPrefsHelper.getValue(Constant.FIRST_RUN, true)) {

                splashScreenPresenter.loadMetaSchool();

            } else {
                sendToHome();

            }
        }
    }

    private void sendtoVerify() {
        Intent i = new Intent(this, VerifyOtpActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void sendToHome() {
        Intent i = new Intent(this, SchoolSelection.class);
        startActivity(i);
        finish();
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    public void showNetworkError() {
        tvLoading.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.NETOWRK_ERROR,SplashScreenActivity.class.getSimpleName());
        networkErrorDFragment.show(fm,"NetworkError");
    }

    @Override
    public FileOutputStream getSchoolLogoFile(String schoolTag) {
        try {
            return this.openFileOutput(schoolTag+".png", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setMetaDataDownloaded() {
        sharedPrefsHelper.saveValue(Constant.FIRST_RUN,false);
       // splashScreenPresenter.loadLogo();
    }

    @Override
    public void setLogoDownloaded() {
        sharedPrefsHelper.saveValue(Constant.LOGO_NOT_DOWNLOADED,false);

    }

    @Override
    public void showProgressBar() {
        tvLoading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void retry() {
        splashScreenPresenter.loadMetaSchool();
    }

    @Override
    public void abort() {
        finish();
    }

    @Override
    public void showServerError() {
        tvLoading.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        NetworkErrorDFragment networkErrorDFragment = NetworkErrorDFragment.newInstance(Constant.SERVER_ERROR,SplashScreenActivity.class.getSimpleName());
        networkErrorDFragment.show(fm,"NetworkError");
    }
}
