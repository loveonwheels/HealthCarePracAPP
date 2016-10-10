package com.lovecareworks.healthcarepersonnel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lovecareworks.healthcarepersonnel.db.dbclasses.AppReqTra;

/**
 * Created by admin on 03-Sep-15.
 */
public class DBAppRequest {
    public static final String TABLE_APPREQ= "tbl_appreq";
    public static final String ROW_APPREQID= "row_appreqid";
    public static final String ROW_PATNAME = "row_patname";
    public static final String ROW_PATADD = "row_patadd";
    public static final String ROW_PATGEN = "row_patgen";
    public static final String ROW_PATAGE = "row_patage";
    public static final String ROW_APPDAT = "row_appdat";
    public static final String ROW_APPTIM = "row_apptim";
    public static final String ROW_APPDES = "row_appdes";
    public static final String ROW_SERDES = "row_serdes";
    public static final String ROW_ADDINFO = "row_addinfo";
    public static final String ROW_APPID = "row_appid";

    private DBHelper dbHelper;

    private SQLiteDatabase db;

    public DBAppRequest(Context mContext) {
        dbHelper = DBHelper.getInstance(mContext);
        db = dbHelper.getWritableDatabase();

    }

    public void setRequest(AppReqTra aTransfer) {
        ContentValues values = new ContentValues();

        values.put(ROW_PATNAME, aTransfer.getPatient_fullname());
        values.put(ROW_PATADD, aTransfer.getPatient_address());
        values.put(ROW_PATGEN, aTransfer.getPatient_gender());
        values.put(ROW_PATAGE, aTransfer.getPatient_age());
        values.put(ROW_APPDAT, aTransfer.getAppiontment_date());
        values.put(ROW_APPTIM, aTransfer.getAppiontment_time());
        values.put(ROW_APPDES, aTransfer.getAilmentdescription());
        values.put(ROW_SERDES, aTransfer.getServicedescription());
        values.put(ROW_ADDINFO, aTransfer.getAddiontionalinformation());
        values.put(ROW_APPID, aTransfer.getAppiontmentid());
        //why to replace?
        db.replace(TABLE_APPREQ, null, values);

    }

    public Cursor getRequests() {
        return db.rawQuery("SELECT * FROM " + TABLE_APPREQ, null);
    }

    public void clear() {
        db.execSQL("DELETE FROM "+ TABLE_APPREQ);
    }


}


