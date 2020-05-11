package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity.subFragment.holidaysFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.calendarAdapter.HolidaysAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Holiday;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidaysFragment extends Fragment implements HolidaysView {


    HolidaysPresenter presenter;

    @BindView(R.id.rvHolidays)
    RecyclerView rvHolidays;
    int year;
    int month;


    DatabaseHelper databaseHelper;


    public static HolidaysFragment newInstance(int page, int year, int month) {
        HolidaysFragment fragment = new HolidaysFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Year", year);
        bundle.putInt("Month", month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays, container, false);
        ButterKnife.bind(this, view);
        // presenter = new HolidaysPresenter(this,new DbInternalRepo(getHelper()));

        year = getArguments().getInt("Year");
        month = getArguments().getInt("Month");

        presenter.fetchList(year, month);

        return view;
    }


    @Override
    public void populateList(List<Holiday> holidays) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvHolidays.setLayoutManager(llm);
        rvHolidays.setItemAnimator(new DefaultItemAnimator());

         /*   DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL);
            rvExamResults.addItemDecoration(dividerItemDecoration);
*/


        HolidaysAdapter recyclerAdapter = new HolidaysAdapter(holidays);
        rvHolidays.setAdapter(recyclerAdapter);
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
        if (databaseHelper == null) {
            OpenHelperManager.releaseHelper();
        }
        databaseHelper = null;
    }
}
