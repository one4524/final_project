package com.example.team_project_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    int year, month, date, time, hour_start, hour_end, min_start, min_end, key;

    int[] hour_start_list, hour_end_list, min_start_list, min_end_list;

    double Lat = 37.57600;
    double Lon = 126.97691;

    EditText addressText, memo;
    private DBHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        setTitle("일정 관리");

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
        TimePicker tp_start = (TimePicker) findViewById(R.id.start_time);
        TimePicker tp_end = (TimePicker) findViewById(R.id.end_time);

        tp_start.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour_start = hourOfDay;
                min_start = minute;
            }
        });

        tp_end.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour_end = hourOfDay;
                min_end = minute;
            }
        });


        // 액티비티 실행시 첫 시간
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tp_start.setHour(time);
            tp_start.setMinute(0);
            tp_end.setHour(time);
            tp_end.setMinute(59);
        } else {
            tp_start.setCurrentHour(time);
            tp_start.setCurrentMinute(0);
            tp_end.setCurrentHour(time);
            tp_end.setCurrentMinute(59);
        }

        //지도
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mDbHelper = new DBHelper(this);


        //다이얼로그 객체
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 데이터 베이스 버튼들
        hour_start_list = new int[50];
        hour_end_list = new int[50];
        min_start_list = new int[50];
        min_end_list = new int[50];

        // 저장 버튼 설정
        Button saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = mDbHelper.getMyScheduleBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date));

                int i = 0;

                if(cursor.getCount() == 0){
                    insertRecord();
                }
                else {
                    while (cursor.moveToNext()) {
                        hour_start_list[i] = cursor.getInt(3);
                        hour_end_list[i] = cursor.getInt(4);
                        min_start_list[i] = cursor.getInt(5);
                        min_end_list[i] = cursor.getInt(6);
                        i++;
                    }

                    for (i = 0; i < cursor.getCount(); i++) {
                        if (hour_end_list[i] < hour_start) {
                            key = 1;
                        } else if (hour_end < hour_start_list[i]) {
                            key = 1;
                        } else if (hour_start_list[i] == hour_end && min_start_list[i] > min_end) {
                            key = 1;
                        } else if (hour_end_list[i] == hour_start && min_end_list[i] < min_start) {
                            key = 1;
                        } else if (hour_start_list[i] == hour_start && hour_end_list[i] == hour_end && min_end_list[i] == min_end && min_start_list[i] == min_start) {
                            builder.setTitle("일정 중복").setMessage("내용을 업데이트 하시겠습니까?");

                            builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    updateRecord();
                                }
                            });

                            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            key = 0;
                            break;
                        }
                        else {
                            builder.setTitle("일정 겹침").setMessage("같은 시간에 다른 일정이 있습니다.");

                            builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            builder.setNegativeButton(null, null);

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            key = 0;
                            break;
                        }
                    }
                    if(key==1){
                        insertRecord();
                    }
                }
                key = 0;
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


        //삭제 버튼 설정
        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("일정 삭제").setMessage("진짜 삭제하시겠습니까?");

                builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deleteRecord();
                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    //일정 갱신
    private void updateRecord() {
        memo = findViewById(R.id.memo);
        addressText = findViewById(R.id.get_address);


        mDbHelper.updateScheduleBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date),
                Integer.toString(hour_start), Integer.toString(hour_end), Integer.toString(min_start), Integer.toString(min_end), addressText.getText().toString(), Double.toString(Lat), Double.toString(Lon), memo.getText().toString());
    }

    // 일정 삭제
    private void deleteRecord() {
        mDbHelper.deleteScheduleBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date),
                Integer.toString(hour_start), Integer.toString(hour_end), Integer.toString(min_start), Integer.toString(min_end));
    }

    // 일정 저장
    private void insertRecord() {
        memo = findViewById(R.id.memo);
        addressText = findViewById(R.id.get_address);

        mDbHelper.insertScheduleBySQL(Integer.toString(year), Integer.toString(month), Integer.toString(date),
                Integer.toString(hour_start), Integer.toString(hour_end), Integer.toString(min_start), Integer.toString(min_end), addressText.getText().toString(), Double.toString(Lat), Double.toString(Lon), memo.getText().toString());
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