package com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity.subFragment.eventsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bihanitech.shikshyaprasasak.R;
import com.bihanitech.shikshyaprasasak.adapter.calendarAdapter.EventsAdapter;
import com.bihanitech.shikshyaprasasak.database.DatabaseHelper;
import com.bihanitech.shikshyaprasasak.model.eventHolidays.Event;
import com.bihanitech.shikshyaprasasak.repositories.DbInternalRepo;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventFragment extends Fragment implements EventsView {

    EventsPresenter presenter;

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;
    int year;
    int month;


    DatabaseHelper databaseHelper;


    public static EventFragment newInstance(int page, int year, int month) {
        EventFragment fragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Year", year);
        bundle.putInt("Month", month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, view);
        presenter = new EventsPresenter(this, new DbInternalRepo(getHelper()));
        year = getArguments().getInt("Year");
        month = getArguments().getInt("Month");
        presenter.fetchList(year, month);

        return view;
    }


    @Override
    public void populateList(List<Event> examNameItems) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvEvents.setLayoutManager(llm);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        EventsAdapter recyclerAdapter = new EventsAdapter(examNameItems);
        rvEvents.setAdapter(recyclerAdapter);
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
