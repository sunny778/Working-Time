package com.sunny.sunny.workingtime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sunny on 15/11/2017.
 */

public class JobDBHelper extends SQLiteOpenHelper {

    //Details table name and columns names
    protected static final String TABLE_NAME = "details";
    protected static final String COL_ID = "id";
    protected static final String COL_NAME = "name";
    protected static final String COL_PER_HOUR = "per_hour";
    protected static final String COL_DISCOUNT_MONTHLY = "discount_monthly";
    protected static final String COL_INCREASE_MONTHLY = "increase_monthly";

    //Hours table name and columns name
    protected static final String TABLE_NAME2 = "work_hours";
    protected static final String COL_ID2 = "id";
    protected static final String COL_YEAR = "year";
    protected static final String COL_MONTH = "month";
    protected static final String COL_DAY = "day";
    protected static final String COL_STARTING_TIME = "starting_time";
    protected static final String COL_ENDING_TIME = "ending_time";
    protected static final String COL_TOTAL_HOURS = "total_hours";

    public JobDBHelper(Context context) {
        super(context, "jobs.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER)",
                TABLE_NAME, COL_ID, COL_NAME, COL_PER_HOUR, COL_DISCOUNT_MONTHLY, COL_INCREASE_MONTHLY);

        String sql2 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s REAL)"
                , TABLE_NAME2, COL_ID2, COL_YEAR, COL_MONTH, COL_DAY, COL_STARTING_TIME, COL_ENDING_TIME, COL_TOTAL_HOURS);

        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
