package com.hansung.android.teamproject2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class weekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=200;
    int year = weekViewFragment.YEAR;
    int month = weekViewFragment.MONTH;
    int date = weekViewFragment.DATE;
    int index = 0;
    int prePosition = 0;
    int lastday;

    ArrayList<String> days;


    public weekCalendarAdapter(@NonNull Fragment fragment) {
        super(fragment);

        setdatelist();
    }

    @NonNull
    @Override
    public weekCalendarFragment createFragment(int position) {

        if(position > prePosition){
            index += 7;
            if(index == days.size() || (index < days.size() && (days.get(index) != " " ? Integer.parseInt(days.get(index)) : 0)>lastday)){

                month += 1;
                index = 0;
                if(month>12){

                    year += 1;
                    month = 1;
                }
                setdatelist();
            }
        }else if(position < prePosition){
            index -= 7;
            if(index == 0){
                month -= 1;
                index = days.size() - 7;
                if(month < 1){
                    year -= 1;
                    month = 12;
                }
            }
        }

        if(index < 0 ){
            index = 0;
        }

        prePosition = position;
        return weekCalendarFragment.newInstance(year, month, index, days);
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

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}