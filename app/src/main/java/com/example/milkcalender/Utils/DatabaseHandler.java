package com.example.milkcalender.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context){
        super(context, "MilkCalender.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE MilkCalender (date VARCHAR(10) PRIMARY KEY, volume REAL)";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS MilkCalender");
        onCreate(db);
    }

    public void addVolume(String date, float volume) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("volume", volume);

        db.insert("MilkCalender", null, values);
    }

    public void updateVolume(String date, float volume){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE MilkCalender SET volume="+volume+" WHERE date="+date);
    }

    public boolean CheckIsDataAlreadyPresent(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT volume FROM MilkCalender WHERE date = " + date;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
