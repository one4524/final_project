package com.example.team_project_final;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    int year, month;
    private item item;
    ArrayList<Item> items = new ArrayList<>();
    DBHelper dbHelper;
    MonthGridViewAdapter monthGridViewAdapter;


    public MonthCalendarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MonthCalendarFragment newInstance(int year, int month) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle(year+"년 "+month+"월");

            item.setyear(year);
            item.setmonth(month);
            item.setpremonth(month);
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            year = mParam1;
            month = mParam2;
        }

        item = (item) getActivity();
        dbHelper = new DBHelper(getContext());

        setdatelist_month();
    }

    class Item{
        String day;
        ArrayList<String> titles = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View monthView = inflater.inflate(R.layout.fragment_month_calendar, container, false);

        final GridView gridview_month_calendar = monthView.findViewById(R.id.month_calendar);

        monthGridViewAdapter = new MonthGridViewAdapter(getContext(), R.layout.day_cell, items);
        gridview_month_calendar.setAdapter(monthGridViewAdapter);


        gridview_month_calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                for(int i=0; i<42; i++){
                    gridview_month_calendar.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                }


                if(items.get(position).day != " ") {
                    Toast.makeText(getContext(),
                            year +"."+ month+"."+ (items.get(position).day)+ "일",
                            Toast.LENGTH_SHORT).show();
                    gridview_month_calendar.getChildAt(position).setBackgroundColor(Color.parseColor("#00ffff"));

                    item.setdate(Integer.parseInt(items.get(position).day));
                    item.settime(8);
                    item.setkey(1);
                }else{
                    item.setdate(0);
                }

                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle(year+"."+month+"."+items.get(position).day);

                CharSequence[] cs = items.get(position).titles.toArray(new CharSequence[items.get(position).titles.size()]);

                dlg.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO:페이지 이동
                    }
                });
                dlg.show();

            }

        });

        return monthView;
    }


    public void setdatelist_month() {
        Calendar cal = Calendar.getInstance(); //Calendar 클래스를 이용해서 현재 월을 가져온다.

        cal.set(year, month - 1, 1); // 해당 월의 1일로 날짜를 세팅한다.
        // 1일의 요일을 알기 위해서이다.

        int front_empty_day = cal.get(Calendar.DAY_OF_WEEK) - 1; // 해당 월의 첫날의 요일을 알 수 있다.
        // 1일 앞의 공백의 갯수를 알기위해서이다.
        // 일요일(=1)부터 토요일(=7)까지 1~7로 표현됨.

        int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  //해당 월의 마지막 날짜를 알 수 있다.

        int end_empty_day = 42 - (front_empty_day + lastday);   //6x7의 모양을 유지하기위해 필요한 공백

        //days = new ArrayList<String>(); //날짜 리스트 생성


        for (int i = 0; i < front_empty_day; i++) {
            Item item = new Item();
            item.day = " "; //1일 앞의 공백
            items.add(item);
        }
        for (int i = 1; i <= lastday; i++) {
            Item item = new Item();
            item.day = Integer.toString(i);  //해당 월의 1일부터 마지막날까지 순서대로 넣음.
            Cursor cursor = dbHelper.getMyScheduleBySQL(Integer.toString(year), Integer.toString(month), item.day);
            while(cursor != null && cursor.moveToNext()){
                item.titles.add(cursor.getString(0));
            }
            items.add(item);
        }
        for (int i = 0; i < end_empty_day; i++) {
            Item item = new Item();
            item.day = " "; // 모양 유지 공백
            items.add(item);
        }


    }
}