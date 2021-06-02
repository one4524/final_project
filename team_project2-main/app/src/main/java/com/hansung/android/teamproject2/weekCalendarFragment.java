package com.hansung.android.teamproject2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link weekCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class weekCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private int mParam1;
    private int mParam2;
    private int mParam3;
    private ArrayList<String> mParam4;


    int year, month;
    ArrayList<String> days;
    int lastday;
    int index;


    public weekCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *

     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @param param4 Parameter 4.
     * @return A new instance of fragment weekCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static weekCalendarFragment newInstance(int param1, int param2, int param3, ArrayList<String> param4) {
        weekCalendarFragment fragment = new weekCalendarFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putStringArrayList(ARG_PARAM4, param4);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MainActivity activity = (MainActivity) getActivity();


            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            mParam4 = getArguments().getStringArrayList(ARG_PARAM4);
            year = mParam1;
            month = mParam2;
            index = mParam3;
            days = mParam4;


            setdatelist();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {

            ((MainActivity) activity).setActionBarTitle(year + "년 " + month + "월");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View weekView = inflater.inflate(R.layout.fragment_week_calendar, container, false);

        GridView gridview_week_calendar = weekView.findViewById(R.id.can_calendar);

        ((TextView) weekView.findViewById(R.id.textView1)).setText(days.get(index++));
        ((TextView) weekView.findViewById(R.id.textView2)).setText(days.get(index++));
        ((TextView) weekView.findViewById(R.id.textView3)).setText(days.get(index++));
        ((TextView) weekView.findViewById(R.id.textView4)).setText(days.get(index++));
        ((TextView) weekView.findViewById(R.id.textView5)).setText(days.get(index++));
        ((TextView) weekView.findViewById(R.id.textView6)).setText(days.get(index++));
        ((TextView) weekView.findViewById(R.id.textView7)).setText(days.get(index++));


        //((TextView) weekView.findViewById(R.id.textView1)).setText(Integer.toString(position));
        weekGridViewAdapter weekGridViewAdapter = new weekGridViewAdapter(getContext(), R.layout.day_cell);
        gridview_week_calendar.setAdapter(weekGridViewAdapter);



        gridview_week_calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                TextView textView_day1, textView_day2, textView_day3, textView_day4, textView_day5, textView_day6, textView_day7;
                textView_day1 = weekView.findViewById(R.id.textView1);
                textView_day2 = weekView.findViewById(R.id.textView2);
                textView_day3 = weekView.findViewById(R.id.textView3);
                textView_day4 = weekView.findViewById(R.id.textView4);
                textView_day5 = weekView.findViewById(R.id.textView5);
                textView_day6 = weekView.findViewById(R.id.textView6);
                textView_day7 = weekView.findViewById(R.id.textView7);

                textView_day1.setBackgroundColor(Color.parseColor("#ffffff"));
                textView_day2.setBackgroundColor(Color.parseColor("#ffffff"));
                textView_day3.setBackgroundColor(Color.parseColor("#ffffff"));
                textView_day4.setBackgroundColor(Color.parseColor("#ffffff"));
                textView_day5.setBackgroundColor(Color.parseColor("#ffffff"));
                textView_day6.setBackgroundColor(Color.parseColor("#ffffff"));
                textView_day7.setBackgroundColor(Color.parseColor("#ffffff"));


                switch (position % 7){
                    case 0:
                        textView_day1.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    case 1:
                        textView_day2.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    case 2:
                        textView_day3.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    case 3:
                        textView_day4.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    case 4:
                        textView_day5.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    case 5:
                        textView_day6.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    case 6:
                        textView_day7.setBackgroundColor(Color.parseColor("#00ffff"));
                        break;
                    default:
                        textView_day1.setBackgroundColor(Color.parseColor("#00ffff"));

                }


                for(int i=0; i<168; i++){
                    gridview_week_calendar.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                }
                gridview_week_calendar.getChildAt(position).setBackgroundColor(Color.parseColor("#00ffff"));

                Toast.makeText(getContext(),
                        "position = " + (position),
                        Toast.LENGTH_SHORT).show();

            }
        });


        int height = weekView.getResources().getDisplayMetrics().heightPixels - 230;

        ((TextView)weekView.findViewById(R.id.time0)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time1)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time2)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time3)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time4)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time5)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time6)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time7)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time8)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time9)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time10)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time11)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time12)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time13)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time14)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time15)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time16)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time17)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time18)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time19)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time20)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time21)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time22)).setHeight(height/6 - 21);
        ((TextView)weekView.findViewById(R.id.time23)).setHeight(height/6 - 21);

        // Inflate the layout for this fragment
        return weekView;
    }

    public void setdatelist() {
        Calendar cal = Calendar.getInstance(); //Calendar 클래스를 이용해서 현재 월을 가져온다.


        cal.set(year, month - 1, 1); // 해당 월의 1일로 날짜를 세팅한다.
        // 1일의 요일을 알기 위해서이다.

        int front_empty_day = cal.get(Calendar.DAY_OF_WEEK) - 1; // 해당 월의 첫날의 요일을 알 수 있다.
        // 1일 앞의 공백의 갯수를 알기위해서이다.
        // 일요일(=1)부터 토요일(=7)까지 1~7로 표현됨.

        lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  //해당 월의 마지막 날짜를 알 수 있다.

        int end_empty_day = 42 - (front_empty_day + lastday);   //6x7의 모양을 유지하기위해 필요한 공백

        days = new ArrayList<String>(); //날짜 리스트 생성


        for (int i = 0; i < front_empty_day; i++) days.add(" "); //1일 앞의 공백
        for (int i = 1; i <= lastday; i++)
            days.add(String.valueOf(i));  //해당 월의 1일부터 마지막날까지 순서대로 넣음.
        if(days.size() > 35) {
            for (int i = 0; i < end_empty_day; i++) days.add(" ");   // 모양 유지 공백
        }
        else if(days.size() > 28){
            for (int i = 0; i < end_empty_day-7; i++) days.add(" ");   // 모양 유지 공백
        }
        else
            for (int i = 0; i < end_empty_day-14; i++) days.add(" ");

    }


}
