package com.hansung.android.teamproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=100;
    public MonthCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    int year = MonthViewFragment.YEAR;
    int month = MonthViewFragment.MONTH;

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int count = (position + month - 1) / 12;

        return MonthCalendarFragment.newInstance(year+count, position + month - (count * 12));
    }


    @Override
    public int getItemCount () {
        return NUM_ITEMS;
    }

}
