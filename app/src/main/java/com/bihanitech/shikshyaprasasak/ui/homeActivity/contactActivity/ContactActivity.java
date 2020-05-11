package com.bihanitech.shikshyaprasasak.ui.homeActivity.contactActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.ContactAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.itemModels.ContactsItem;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity implements ContactView{


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ivmenu)
    ImageView ivMenu;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;

    @BindView(R.id.ivTwoline)
    ImageView ivTwoline;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    ContactPresenter contactPresenter;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ivMenu.setVisibility(View.GONE);
        ivProfile.setVisibility(View.GONE);
        ivTwoline.setVisibility(View.GONE);
        tvToolbarTitle.setText("Contact");

        contactPresenter = new ContactPresenter(this,new MetaDatabaseRepo(getHelper()));
//      dummy ho talako
//       contactPresenter.
        contactPresenter.convertAllDateToNepali();
        setUpList();
    }

    private void setUpList() {
       contactPresenter.fetchContacts();

    }

    @Override
    public void populateList(List<ContactsItem> contactsItemList) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvContacts.setLayoutManager(llm);
        rvContacts.setItemAnimator(new DefaultItemAnimator());
        ContactAdapter recyclerAdapter = new ContactAdapter(contactsItemList,this);
        rvContacts.setAdapter(recyclerAdapter);
    }


    @Override
    public void proceedToCall(String contacts) {
        if(contacts != null){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contacts));
            startActivity(intent);
        }
    }

    private DatabaseHelper getHelper(){
        if(databaseHelper == null){
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(databaseHelper == null){
            OpenHelperManager.releaseHelper();
        }
        databaseHelper = null;
    }
}
