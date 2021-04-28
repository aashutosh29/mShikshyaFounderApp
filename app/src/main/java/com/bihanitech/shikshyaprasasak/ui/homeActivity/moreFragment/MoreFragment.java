package com.bihanitech.shikshyaprasasak.ui.homeActivity.moreFragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.Classes;
import com.bihanitech.shikshyaprasasak.model.ExamName;
import com.bihanitech.shikshyaprasasak.model.Section;
import com.bihanitech.shikshyaprasasak.model.UploadNotice;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.analyticsFragment.statement.StatementActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity.NoticeUploadActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.searchActivity.SearchActivity;
import com.bihanitech.shikshyaprasasak.ui.schoolSelection.SchoolSelection;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.bihanitech.shikshyaprasasak.utility.sharedPreference.SharedPrefsHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFragment extends Fragment implements MoreView {


    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;
    @BindView(R.id.tvUSerContact)
    TextView tvSchoolAddress;
    @BindView(R.id.ivProfile)
    ImageView ivProfileImage;
    SharedPrefsHelper sharedPrefsHelper;
    TextView tvToolbarTitle;
    Toolbar toolbarNew;
    MorePresenter morePresenter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        morePresenter = new MorePresenter(this, new MetaDatabaseRepo(getHelper()));
        sharedPrefsHelper = SharedPrefsHelper.getInstance(getContext());
        initToolbar();
        initDetails();
        return view;
    }

    private void initDetails() {
        tvUserName.setText(sharedPrefsHelper.getValue(Constant.USER_NAME, ""));
        tvSchoolName.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_NAME, ""));
        tvSchoolAddress.setText(sharedPrefsHelper.getValue(Constant.SCHOOL_ADDRESS, ""));
        showSchoolLogo(sharedPrefsHelper.getValue(Constant.SCHOOL_LOGO, ""));

    }

    @OnClick(R.id.clUploadNotice)
    public void btUploadNoticeOnclick() {
        Intent intent = new Intent(getContext(), AddNoticeActivity.class);
        startActivity(intent);
    }

    private void initToolbar() {
        toolbarNew = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarNew);
        toolbarNew.setVisibility(View.VISIBLE);
        tvToolbarTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("More");
    }


    //here
    @OnClick(R.id.clStatement)
    void clStatementClicked() {
        Intent i = new Intent(getContext(), StatementActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.clSearchStudentProfile)
    void clSearchStudentProfile() {

        Intent i = new Intent(getContext(), SearchActivity.class);
        startActivity(i);

    }

    @OnClick(R.id.clUserLogOut)
    public void btLogoutClicked() {
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        SQLiteDatabase db = databaseHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Classes.class.getSimpleName(), null, null);
        db.delete(ExamName.class.getSimpleName(), null, null);
        db.delete(Section.class.getSimpleName(), null, null);
        db.delete(UploadNotice.class.getSimpleName(), null, null);
        SharedPrefsHelper.getInstance(getActivity()).clear();
        Intent il = new Intent(getActivity(), SchoolSelection.class);
        il.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(il);
    }

    private void showSchoolLogo(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivProfileImage);
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

    @OnClick(R.id.clSavedNotice)
    void btSavedNoticeClicked() {
        Intent intent = new Intent(getContext(), NoticeUploadActivity.class);
        startActivity(intent);
    }


}
