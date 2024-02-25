package com.example.admin.timetable;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class IntentServiceHelper extends IntentService {

    public IntentServiceHelper() {
        super("IntentServiceHelper");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        data(480,540,"1/1/cs-6006/cs-6003/cs-6004/cs-6002","1/1/cs-6002/cs-6006/cs-6003/cs-6004","1/1/cs-6004/cs-6002/cs-6006/cs-6003","1/1/cs-6003/cs-6004/cs-6002/cs-6006","0/cs-6003");
        data(540,600,"1/2/cs-6006/cs-6003/cs-6004/cs-6002","1/2/cs-6002/cs-6006/cs-6003/cs-6004","1/2/cs-6004/cs-6002/cs-6006/cs-6003","1/2/cs-6003/cs-6004/cs-6002/cs-6006","0/lib") ;
        data(600,660,"0/cs-6002","0/cs-6001","2/0/cs-6003/cs-6003/cs-6001/cs-6001","0/cs-6003","0/cs-6002");
        data(660,720,"0/cs-6003","0/cs-6007","0/cs-6004","0/cs-6001","0/cs-6004");
        data(720,750,"Break","Break","Break","Break","Break");
        data(750,810,"0/cs-6005","2/0/cs-6004/cs-6004/cs-6002/cs-6002","2/0/cs-6005/cs-6005/lib/lib","2/0/cs-6007/cs-6007/cs-6004/cs-6004","2/0/lib/lib/cs-6005/cs-6005");
        data(810,870,"0/cs-6004","0/cs-6005","0/cs-6001","0/cs-6008","0/cs-6005");
        data(870,930,"2/0/cs-6001/cs-6001/cs-6003/cs-6003","0/cs-6007","0/cs-6002","0/cs-6008","0/self");

        data1("cs-6001","ACA","Mrs. Rupali Bhartiya");
        data1("cs-6002","PPL","Ms.Neha Agarwal");
        data1("cs-6003","SEPM","Ms. Rupali Dave");
        data1("cs-6004","Computer Networking","Mrs. Priti Shukla");
        data1("cs-6005","Elective-II","Ms. Neha Agarwal");
        data1("cs-6006","Minor Project","Mrs. Archana Choubey");
        data1("cs-6007","Creativity & Enterprunership Development","Ms. Neha Agarawal");
        data1("cs-6008","Startup / Industrial Lectures","Ms. Ritu Patidar");
        data1("lib","Library","Yourself");
        data1("sports","Sports","Yourself");
        data1("self","Self Study","Yourself");
        data1("nolec","No Lecture Alloted","    -    ");
        Toast.makeText(ttService.this,"Inserted sucessfully",Toast.LENGTH_LONG).show();
        stopSelf();
        }
        void data(int start,int end1,String mon,String tue,String wed,String thu,String fri)
        {
            sqlhelper s=new sqlhelper(this);
            SQLiteDatabase sdb=s.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("start",start);
            cv.put("end1",end1);
            cv.put("Monday",mon);
            cv.put("Tuesday",tue);
            cv.put("Wednesday",wed);
            cv.put("Thursday",thu);
            cv.put("Friday",fri);
            sdb.insert("tt",null,cv);
            sdb.close();
            s.close();
        }
        void data1(String code,String name,String teacher)
        {
            sqlhelper s=new sqlhelper(this);
            SQLiteDatabase sdb=s.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("code",code);
            cv.put("name",name);
            cv.put("teacher",teacher);
            sdb.insert("sub",null,cv);
            sdb.close();
            s.close();
        }
}
