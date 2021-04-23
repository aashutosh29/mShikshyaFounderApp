package com.bihanitech.shikshyaprasasak.ui.homeActivity.noticeUploadActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.NoticeUploadAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.UploadNotice;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.addNoticeActivity.AddNoticeActivity;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.editNoitceActivity.EditNoticeActivity;
import com.bihanitech.shikshyaprasasak.utility.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeUploadActivity extends AppCompatActivity implements NoticeUploadView {
    NoticeUploadPresenter noticeUploadPresenter;
    @BindView(R.id.fabNoticeUpload)
    FloatingActionButton fabNoticeUpload;

    @BindView(R.id.rvNoticeUpload)
    RecyclerView rvNoticeUpload;

    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_upload);
        ButterKnife.bind(this);
        noticeUploadPresenter = new NoticeUploadPresenter(new MetaDatabaseRepo(getHelper()), this);
        noticeUploadPresenter.queryForData();
    }


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @OnClick(R.id.fabNoticeUpload)
    public void fabNoticeUpload() {
        Intent i = new Intent(NoticeUploadActivity.this, AddNoticeActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }

        databaseHelper = null;
    }

    @OnClick(R.id.ivBack)
    void ivBackOnClicked() {
        onBackPressed();
    }

    @Override
    public void populateUnpublishedNotice(List<UploadNotice> allUnpublishedNotice) {
        if (allUnpublishedNotice.size() == 0) {
            tvNoDataFound.setVisibility(View.VISIBLE);
        } else {
            tvNoDataFound.setVisibility(View.INVISIBLE);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvNoticeUpload.setLayoutManager(llm);
            NoticeUploadAdapter recyclerAdapter = new NoticeUploadAdapter(allUnpublishedNotice, this);
            rvNoticeUpload.setAdapter(recyclerAdapter);
        }
    }

    @Override
    public void onClickOnItem(UploadNotice uploadNotice) {
        Intent i = new Intent(NoticeUploadActivity.this, EditNoticeActivity.class);
        i.putExtra(Constant.UNPUBLISHED_ID, uploadNotice.getId() + "");
        i.putExtra(Constant.UNPUBLISHED_TITLE, uploadNotice.getTitle());
        i.putExtra(Constant.UNPUBLISHED_CONTENT, uploadNotice.getContent());
        i.putExtra(Constant.UNPUBLISHED_CATEGORY, uploadNotice.getCategory() + "");
        startActivity(i);
    }
}
