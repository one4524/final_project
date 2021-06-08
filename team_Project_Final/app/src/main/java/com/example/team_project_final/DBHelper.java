package com.example.team_project_final;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, ScheduleContract.DB_NAME, null, ScheduleContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(ScheduleContract.schedule.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(ScheduleContract.schedule.DELETE_TABLE);
        onCreate(db);
    }

    public void insertScheduleBySQL(String title, String year, String month, String day, String Time_start, String Time_end, String min_start, String min_end, String address, String lat, String lon, String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s' )",
                    ScheduleContract.schedule.TABLE_NAME,
                    ScheduleContract.schedule.KEY_TITLE,
                    ScheduleContract.schedule.KEY_YEAR,
                    ScheduleContract.schedule.KEY_MONTH,
                    ScheduleContract.schedule.KEY_DAY,
                    ScheduleContract.schedule.KEY_TIME_START,
                    ScheduleContract.schedule.KEY_TIME_END,
                    ScheduleContract.schedule.KEY_MIN_START,
                    ScheduleContract.schedule.KEY_MIN_END,
                    ScheduleContract.schedule.KEY_ADDRESS,
                    ScheduleContract.schedule.KEY_LATITUDE,
                    ScheduleContract.schedule.KEY_LONGITUDE,
                    ScheduleContract.schedule.KEY_MEMO,
                    title, year, month, day, Time_start,
                    Time_end, min_start, min_end, address, lat, lon, memo
            );

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getAllScheduleBySQL() {
        String sql = "Select * FROM " + ScheduleContract.schedule.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getMyScheduleBySQL(String year, String month, String day) {
        String sql = String.format (
                "Select * FROM %s WHERE %s = %s and %s = %s and %s = %s",
                ScheduleContract.schedule.TABLE_NAME,
                ScheduleContract.schedule.KEY_YEAR, year,
                ScheduleContract.schedule.KEY_MONTH, month,
                ScheduleContract.schedule.KEY_DAY, day
        );
        return getReadableDatabase().rawQuery(sql,null);

    }

    public void deleteScheduleBySQL(String year, String month, String day, String Time_start, String Time_end, String min_start, String min_end) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s and %s = %s and %s = %s and %s = %s and %s = %s and %s = %s and %s = %s",
                    ScheduleContract.schedule.TABLE_NAME,
                    ScheduleContract.schedule.KEY_YEAR, year,
                    ScheduleContract.schedule.KEY_MONTH, month,
                    ScheduleContract.schedule.KEY_DAY, day,
                    ScheduleContract.schedule.KEY_TIME_START, Time_start,
                    ScheduleContract.schedule.KEY_TIME_END, Time_end,
                    ScheduleContract.schedule.KEY_MIN_START, min_start,
                    ScheduleContract.schedule.KEY_MIN_END, min_end
                    );
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

    public void updateScheduleBySQL(String title, String year, String month, String day, String Time_start, String Time_end, String min_start, String min_end, String address, String lat, String lon, String memo) {
        try {
            String sql = String.format (
                    "UPDATE %s SET %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s' WHERE %s = %s and %s = %s and %s = %s and %s = %s and %s = %s  and %s = %s and %s = %s",
                    ScheduleContract.schedule.TABLE_NAME,
                    ScheduleContract.schedule.KEY_TITLE, title,
                    ScheduleContract.schedule.KEY_YEAR, year,
                    ScheduleContract.schedule.KEY_MONTH, month,
                    ScheduleContract.schedule.KEY_DAY, day,
                    ScheduleContract.schedule.KEY_TIME_START, Time_start,
                    ScheduleContract.schedule.KEY_TIME_END, Time_end,
                    ScheduleContract.schedule.KEY_MIN_START, min_start,
                    ScheduleContract.schedule.KEY_MIN_END, min_end,
                    ScheduleContract.schedule.KEY_ADDRESS, address,
                    ScheduleContract.schedule.KEY_LATITUDE, lat,
                    ScheduleContract.schedule.KEY_LONGITUDE, lon,
                    ScheduleContract.schedule.KEY_MEMO, memo,
                    ScheduleContract.schedule.KEY_YEAR, year,
                    ScheduleContract.schedule.KEY_MONTH, month,
                    ScheduleContract.schedule.KEY_DAY, day,
                    ScheduleContract.schedule.KEY_TIME_START, Time_start,
                    ScheduleContract.schedule.KEY_TIME_END, Time_end,
                    ScheduleContract.schedule.KEY_MIN_START, min_start,
                    ScheduleContract.schedule.KEY_MIN_END, min_end) ;
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in updating recodes");
        }
    }

}