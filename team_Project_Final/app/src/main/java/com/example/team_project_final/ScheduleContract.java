package com.example.team_project_final;

import android.provider.BaseColumns;

public final class ScheduleContract {
    public static final String DB_NAME="schedule.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT NOT NULL";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ScheduleContract() {}

    /* Inner class that defines the table contents */
    public static class schedule implements BaseColumns {
        public static final String TABLE_NAME="Schedule";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_MONTH = "Month";
        public static final String KEY_DAY = "Day";
        public static final String KEY_TIME_START = "Time_start";
        public static final String KEY_TIME_END = "Time_end";
        public static final String KEY_MIN_START = "Min_start";
        public static final String KEY_MIN_END = "Min_end";
        public static final String KEY_ADDRESS = "Address";
        public static final String KEY_LATITUDE = "Latitude";
        public static final String KEY_LONGITUDE = "Longitude";
        public static final String KEY_MEMO = "Memo";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_YEAR + " TEXT, " +
                KEY_MONTH + " TEXT, " +
                KEY_DAY + " TEXT, " +
                KEY_TIME_START + " TEXT, " +
                KEY_TIME_END + " TEXT, " +
                KEY_MIN_START + " TEXT, " +
                KEY_MIN_END + " TEXT, " +
                KEY_ADDRESS + " TEXT, " +
                KEY_LATITUDE + " TEXT, " +
                KEY_LONGITUDE + " TEXT, " +
                KEY_MEMO +  " TEXT )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}