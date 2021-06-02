package com.example.team_project_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity implements OnMapReadyCallback {

    int year, month, date, time, hour_start, hour_end, min_start, min_end;

    private item item;

    double Lat = 37.57600;
    double Lon = 126.97691;

    EditText addressText, memo;
    TimePicker tp_start, tp_end;

    private DBHelper mDbHelper;

    int year_a, month_a, day_a, hour_a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        Intent intent = getIntent();
        intent.putExtra("key", 0);
        setResult(RESULT_OK,intent);

        year = intent.getIntExtra("year", 2021);
        month = intent.getIntExtra("month", 1);
        date = intent.getIntExtra("date", 1);
        time = intent.getIntExtra("time", 8);

        // 제목에 날짜 설정
        TextView  title = (TextView ) findViewById(R.id.title_time);
        title.setText(year + "년 " + month + "월 " + date + "일 " + time + "시");


        // 일정 시간 설정
        tp_start = (TimePicker) findViewById(R.id.start_time);
        tp_end = (TimePicker) findViewById(R.id.end_time);


        // 액티비티 실행시 첫 시간
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tp_start.setHour(time);
            tp_start.setMinute(0);
            tp_end.setHour(time + 1);
            tp_end.setMinute(0);
        } else {
            tp_start.setCurrentHour(time);
            tp_start.setCurrentMinute(0);
            tp_end.setCurrentHour(time + 1);
            tp_end.setCurrentMinute(0);
        }

        //지도
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mDbHelper = new DBHelper(this);


        // 데이터 베이스 버튼들

        // 저장 버튼 설정
        Button saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour_start = tp_start.getHour();
                    min_start = tp_start.getMinute();
                } else {
                    hour_start = tp_start.getCurrentHour();
                    min_start = tp_start.getCurrentMinute();
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour_end = tp_end.getHour();
                    min_end = tp_end.getMinute();
                } else {
                    hour_end = tp_end.getCurrentHour();
                    min_end = tp_end.getCurrentMinute();
                }

                String sql = String.format (
                        "Select * FROM %s WHERE %s = %s and %s = %s and %s = %s and %s = %s",
                        ScheduleContract.schedule.TABLE_NAME,
                        ScheduleContract.schedule.KEY_YEAR, year,
                        ScheduleContract.schedule.KEY_MONTH, month,
                        ScheduleContract.schedule.KEY_DAY, date,
                        ScheduleContract.schedule.KEY_TIME_START, hour_start
                );

                Cursor cursor = mDbHelper.getReadableDatabase().rawQuery(sql, null);


                while (cursor.moveToNext()) {
                    year_a = cursor.getInt(0);
                    month_a = cursor.getInt(1);
                    day_a = cursor.getInt(2);
                    hour_a = cursor.getInt(3);
                }

                if(year == year_a){
                    if(month == month_a){
                        if(date == day_a){
                            if(hour_start == hour_a){
                                updateRecord();
                            }else
                                insertRecord();
                        }else
                            insertRecord();
                    }else
                        insertRecord();
                }else
                    insertRecord();


                //deleteRecord();
                //insertRecord();

                viewAllToTextView();
            }
        });

        //취소 버튼 설정
        Button cancelBtn = findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //다이얼로그 객체
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //삭제 버튼 설정
        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("일정 삭제").setMessage("진짜 삭제하시겠습니까?");

                builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        insertRecord();
                        deleteRecord();
                        viewAllToTextView();
                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        viewAllToTextView();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    //일정 갱신
    private void updateRecord() {

        tp_start = (TimePicker) findViewById(R.id.start_time);
        tp_end = (TimePicker) findViewById(R.id.end_time);
        memo = findViewById(R.id.memo);
        addressText = findViewById(R.id.get_address);


        mDbHelper.updateUserBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date),
                Integer.toString(hour_start), Integer.toString(hour_end), Integer.toString(min_start), Integer.toString(min_end), addressText.getText().toString(), Double.toString(Lat), Double.toString(Lon), memo.getText().toString());
    }

    // 일정 삭제
    private void deleteRecord() {
        mDbHelper.deleteUserBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date),
                Integer.toString(hour_start));
    }

    // 일정 저장
    private void insertRecord() {
        tp_start = (TimePicker) findViewById(R.id.start_time);
        tp_end = (TimePicker) findViewById(R.id.end_time);
        memo = findViewById(R.id.memo);
        addressText = findViewById(R.id.get_address);

        mDbHelper.insertUserBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date),
                Integer.toString(hour_start), Integer.toString(hour_end), Integer.toString(min_start), Integer.toString(min_end), addressText.getText().toString(), Double.toString(Lat), Double.toString(Lon), memo.getText().toString());
   }


    //SQL 작동 테스트
    private void viewAllToTextView() {
        TextView title = (TextView) findViewById(R.id.title_time);

        Cursor cursor = mDbHelper.getAllUsersBySQL();

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getInt(0)+" \t");
            buffer.append(cursor.getString(1)+" \t");
            buffer.append(cursor.getString(2)+"\n");
        }
        //title.setText(buffer);
    }


    //지도 설정
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        final String[] input = new String[1];

        //처음 위치
        LatLng location = new LatLng(Lat, Lon);
        googleMap.addMarker(new MarkerOptions().position(location).title("start"));
        // move the camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));


        addressText = findViewById(R.id.get_address);
        Button findBtn = findViewById(R.id.find_address);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input[0] = addressText.getText().toString();

                try {
                    Geocoder geocoder = new Geocoder(ScheduleActivity.this, Locale.KOREA);
                    List<Address> addresses = geocoder.getFromLocationName(input[0],10);
                    if (addresses.size() > 0) {
                        Address bestResult = (Address) addresses.get(0);

                        Lat = bestResult.getLatitude();
                        Lon = bestResult.getLongitude();
                    }
                } catch (IOException e) {
                    Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                    return;
                }
                LatLng location = new LatLng(Lat, Lon);
                googleMap.addMarker(new MarkerOptions().position(location).title("destiny"));
                // move the camera
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            }
        });

    }
}