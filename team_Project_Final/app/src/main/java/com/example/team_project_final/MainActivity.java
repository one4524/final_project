package com.example.team_project_final;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements item{

    int year, month, index, page, size, prepage, date, time = 8;

    private static final int FIRST_ACTIVITY_REQUEST_CODE = 0;

    int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_container, new MonthViewFragment());
        fragmentTransaction.commit();


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_btn);

        //플로팅 버튼
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class); //그룹 만들기 화면으로 연결

                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("date", date);
                intent.putExtra("time", time);

                if(key == 1) {
                    startActivityForResult(intent, FIRST_ACTIVITY_REQUEST_CODE); //액티비티 열기
                }
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 결과를 반환하는 액티비티가 FIRST_ACTIVITY_REQUEST_CODE 요청코드로 시작된 경우가 아니거나
        // 결과 데이터가 빈 경우라면, 메소드 수행을 바로 반환함.
        if (requestCode != FIRST_ACTIVITY_REQUEST_CODE || data == null)
            return;
        key = data.getIntExtra("key", 0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month_view:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new MonthViewFragment());
                fragmentTransaction.commit();
                return true;
            case R.id.week_view:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new weekViewFragment());
                fragmentTransaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //https://hashcode.co.kr/questions/8676/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-fragment%EB%A1%9C-%EC%9D%B4%EB%8F%99%EC%8B%9C-settitle%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void setitem(int year, int month, int index, int page) {
        this.year = year;
        this.month = month;
        this.index = index;
        this.page = page;
    }

    @Override
    public void setindex(int index) {
        this.index = index;
    }

    @Override
    public void setyear(int year) {
        this.year = year;
    }

    @Override
    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public void setpage(int page) {
        this.page = page;
    }

    @Override
    public void setsize(int size) {
        this.size = size;
    }

    @Override
    public void setdate(int date) {
        this.date = date;
    }

    @Override
    public void settime(int time) {
        this.time = time;
    }

    @Override
    public void setprepage(int prepage) {
        this.prepage = prepage;
    }

    @Override
    public void setkey(int key) {
        this.key = key;
    }

    @Override
    public int getyear() {
        return year;
    }

    @Override
    public int getmonth() {
        return month;
    }

    @Override
    public int getindex() {
        return index;
    }

    @Override
    public int getpage() {
        return page;
    }

    @Override
    public int getsize() {
        return size;
    }

    @Override
    public int getprepage() {
        return prepage;
    }

    @Override
    public int getdate() {
        return date;
    }

    @Override
    public int gettime() {
        return time;
    }

    @Override
    public int getkey() {
        return key;
    }
}