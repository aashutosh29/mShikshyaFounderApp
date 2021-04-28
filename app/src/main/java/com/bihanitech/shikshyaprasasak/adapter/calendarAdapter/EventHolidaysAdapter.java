package com.bihanitech.shikshyaprasasak.adapter.calendarAdapter;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity.subFragment.eventsFragment.EventFragment;
import com.bihanitech.shikshyaprasasak.ui.homeActivity.calendarActivity.subFragment.holidaysFragment.HolidaysFragment;

public class EventHolidaysAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 2;
    int nepYear = 0;
    int nepMonth = 0;
    private final String[] tabTitles = new String[]{"Holidays", "Events"};
    private final Context context;

    public EventHolidaysAdapter(FragmentManager fm, Context context, int nepYear, int nepMonth) {
        super(fm);
        this.context = context;
        this.nepYear = nepYear;
        this.nepMonth = nepMonth;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HolidaysFragment.newInstance(0, nepYear, nepMonth);
            case 1:
                return EventFragment.newInstance(1, nepYear, nepMonth);
            default:
                return HolidaysFragment.newInstance(0, nepYear, nepMonth);

        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


}
