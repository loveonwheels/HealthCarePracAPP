package com.lovecareworks.healthcarepersonnel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mInstance = null;
    private static final String DATABASE_NAME = "hcpDB";
    private static final int DATABASE_VERSION = 1;

    public static DBHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL("CREATE TABLE IF NOT EXISTS " + DBAppRequest.TABLE_APPREQ + " ( " +
                DBAppRequest.ROW_APPREQID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                DBAppRequest.ROW_PATNAME + " TEXT NOT NULL, " +
                DBAppRequest.ROW_PATADD + " TEXT NOT NULL, " +
                DBAppRequest.ROW_PATGEN + " TEXT NOT NULL, " +
                DBAppRequest.ROW_PATAGE + " TEXT NOT NULL, " +
                DBAppRequest.ROW_APPDAT + " TEXT NOT NULL, " +
                DBAppRequest.ROW_APPTIM + " TEXT NOT NULL, " +
                DBAppRequest.ROW_APPDES + " TEXT NOT NULL, " +
                DBAppRequest.ROW_SERDES + " TEXT NOT NULL, " +
                DBAppRequest.ROW_ADDINFO + " TEXT NOT NULL, " +
                DBAppRequest.ROW_APPID + " INTEGER NOT NULL UNIQUE);");



    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
