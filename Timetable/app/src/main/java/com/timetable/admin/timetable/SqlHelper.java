package com.example.admin.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqlHelper extends SQLiteOpenHelper {
    sqlhelper(Context ct)
    {
        super(ct,"Timetable",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists tt(start int,end1 int,Monday varchar,Tuesday varchar,Wednesday varchar,Thursday varchar" +
                ",Friday varchar)");
        sqLiteDatabase.execSQL("create table if not exists sub(code varchar,name varchar,teacher varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
