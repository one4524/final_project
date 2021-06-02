package com.hansung.android.teamproject2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link weekViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class weekViewFragment extends Fragment{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    int year, month, date;
    static int YEAR;
    static int MONTH;
    static int DATE;

    public weekViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment week_viewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static weekViewFragment newInstance(int param1, int param2) {
        weekViewFragment fragment = new weekViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        month += 1; //Calendar.MONTH는 0~11로 월을 나타내기 때문에 +1을 해준다.
        date = Calendar.getInstance().get(Calendar.DATE);

        YEAR = year;
        MONTH = month;
        DATE = date;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false);
        ViewPager2 vpPager = rootView.findViewById(R.id.vpPager2);
        vpPager.setOffscreenPageLimit(30);
        FragmentStateAdapter adapter = new weekCalendarAdapter(this);
        vpPager.setAdapter(adapter);
        // Attach the page change listener inside the activity


        // Inflate the layout for this fragment
        return rootView;
    }

}