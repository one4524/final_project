package com.example.team_project_final;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class weekCalendarAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=200;
    private item item;
    int year = weekViewFragment.YEAR;
    int month = weekViewFragment.MONTH;
    int index = 0;
    int lastday = 0;
    int prepage = 0;
    ArrayList<String> days;


    public weekCalendarAdapter(@NonNull Fragment fragment, Context context) {
        super(fragment);

        int w = context.getResources().getDisplayMetrics().widthPixels;
        setdatelist();
        item = (item) context;      //액티비티에 있는 item 인터페이스를 context로 가져오기
        item.setsize(days.size());
        item.setprepage(prepage);
        item.setitem(year, month, index, prepage);
    }

    @NonNull
    @Override
    public weekCalendarFragment createFragment(int position) {
        index = item.getindex();
        month = item.getmonth();
        year = item.getyear();
        prepage = item.getprepage();

        ///////////////////// 페이지 전환시 날짜 표시 코드 ////////////////////
        //페이지가 오른쪽으로 넘어갔을 때
        if (prepage < position) {
            index += 7;         //무조건 index를 7 더하라

            if(prepage + 1 == position){       //계속 오른쪽으로 넘길때
                if (index == item.getsize()) {
                    month += 1;
                    index = 0;

                }
                setdatelist();
                item.setsize(days.size());
            }
            else{           //왼쪽으로 2번이상 넘어갔다가 다시 오른쪽으로 계속 넘길때

                if(days.size() / 7 == 5){       // 격자 칸이 35개

                    if(index == 7){   // index가 0 (첫 주)일 때만 달이 바뀌지 않음
                        month -= 1;
                        index += 21;  // 4개의 페이지가 넘어가기 때문에 + 21(+7은 앞에서 이미 됨) 총 28
                    }
                    else{           // 두번째 주부터 달이 넘어가고 현재 월의 주에서 다음 달의 한 주 전으로 이동
                                    // 예를 들면 5월 2번째 주는 6월 1번째 주로 이동
                        index -= 14;    //(앞에서 이미 +7을 했기 때문에  - 14를 빼서 한 주 전으로 이동
                    }

                } else if(days.size() / 7 == 6){      // 격자 칸이 42개

                    if(index <= 14){
                        month -= 1;
                        index += 21;
                    }
                    else{
                        index -= 21;    //총 6개의 주가 있기 때문에 5개 주간이 있는 달력보다 -7을 더 한다.
                    }

                }else{                       // 격자 칸이 28개
                                    // 앞에서 이미 +7을 해주었기 때문에 -7를 해주는 것이다.
                    index -= 7;  //주간이 4개인 월은 무조건 달이 넘어가고 현재 보여주는 주간과 같은 다음달의 주간으로 이동

                }
                month += 1;
                setdatelist();
                item.setsize(days.size());

            }
            if (month > 12) {
                year += 1;
                month = 1;
            }


            setdatelist();
            item.setsize(days.size());

        }
        // 페이지를 왼쪽으로 넘길 때
        else if (prepage > position) {

            index -= 7;

            if(prepage - 1 == position){
                if(index == -7){
                    month -= 1;
                    setdatelist();
                    item.setsize(days.size());
                    index = item.getsize() - 7;
                }
            }
            else {
                month -= 1;

                if (index >= 21){
                    month += 1;
                    index -= 21;
                }else{
                    setdatelist();
                    item.setsize(days.size());

                    if (index == 14) {
                        index = days.size() - 7;

                    } else if (index == 7) {
                        index = days.size() - 14;

                    } else if (index == 0) {
                        index = days.size() - 21;

                    } else if (index == -7) {
                        index = days.size() - 28;

                    }
                }
            }

            if (month < 1) {
                year -= 1;
                month = 12;
                item.setpremonth(1);
            }

            setdatelist();
            item.setsize(days.size());

        }

        item.setprepage(position);
        item.setitem(year, month, index, position);
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