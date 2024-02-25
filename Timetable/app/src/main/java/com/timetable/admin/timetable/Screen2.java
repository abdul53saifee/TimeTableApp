package com.example.admin.timetable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Screen2 extends Activity {

    TextView tv;
    Intent i;
    int Green= Color.parseColor("#00006400");
    SQLiteDatabase sdb;
    SharedPreferences spf;
    String time[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcd);
       tv=findViewById(R.id.textView5);
       tv.setBackgroundColor(Green);
       tv.setTextSize(20);
        i=getIntent();
        sdb=new sqlhelper(this).getReadableDatabase();
        spf=getSharedPreferences("check", Context.MODE_PRIVATE);
        time=((new SimpleDateFormat("EEEE-k-m")).format(new Date())).split("-");
        ((TextView)findViewById(R.id.wishes)).setText(wishes());
        TextView tv1=(TextView)findViewById(R.id.name);
        tv1.setSelected(true);
        tv1.setText(spf.getString("name",""));
        last();
    }
    String wishes()
    {
        if(Integer.parseInt(time[1])>=5&&Integer.parseInt(time[1])<=11)
            return "Good Morning : ";
        else if(Integer.parseInt(time[1])>11&&Integer.parseInt(time[1])<=17)
            return "Good Afternoon : ";
        else if(Integer.parseInt(time[1])>17&&Integer.parseInt(time[1])<=20)
            return "Good Evening : ";
        else
            return "Good Night : ";
    }
    void last()
    {
        if(i.getBooleanExtra("lab",false))
        {
            int start=i.getIntExtra("time",0);
            String day=i.getStringExtra("day");
            Cursor c=sdb.rawQuery("select "+day+" from tt where start = "+start,null);
            c.moveToNext();
            String ans[]=c.getString(c.getColumnIndex(day)).split("/");
            String tvcontents=null;
            if(i.getBooleanExtra("tutorial",false))
            {
                tvcontents="Lec : Tutorial";
            }
            else
            {
                tvcontents="Lec : Lab";
            }
            int time1=start/60,time2=start%60;
            String time,time2f="00";
            if(!(time2==0))time2f=Integer.toString(time2);
            if(time1<12)
                time=time1+":"+time2f+"AM";
            else if(time1==12)
                time=time1+":"+time2f+"PM";
            else
                time=(time1-12)+":"+time2f+"PM";
            tvcontents=tvcontents+"\n\nBatch 1 : "+gen(ans[2])[0]+"\nBy: "+gen(ans[2])[1]+"\n\nBatch 2 : "+gen(ans[3])[0]+"\nBy: "+gen(ans[3])[1]+"\n\nBatch 3 : "+gen(ans[4])[0]+"\nBy: "+gen(ans[4])[1]+"\n\nBatch 4 : "+gen(ans[5])[0]+"\nBy: "+gen(ans[5])[1]+"\n\nStarts in "+time;
            tv.setText(tvcontents);
        }
        else
        {

            if(i.getBooleanExtra("break",false))
            {
                tv.setText("Break Time \nStarts at 12:00 am");
            }
            else
            {
                int time1 =i.getIntExtra("time",0)/60;
                int time2=i.getIntExtra("time",0)%60;
                String time,time2f="00";
                if(!(time2==0))time2f=Integer.toString(time2);
                if(time1<12)
                    time=time1+":"+time2f+"AM";
                else if(time1==12)
                    time=time1+":"+time2f+"PM";
                else
                    time=(time1-12)+":"+time2f+"PM";
                tv.setText("Lec : "+i.getStringExtra("subname")+"\nBy : "+i.getStringExtra("teachername")+"\nStarts at "+time);
            }
        }
    }
    String[] gen(String code)
    {
        Cursor c=sdb.rawQuery("select * from sub where code = '"+code+"'",null);
        c.moveToNext();
        String[] sub=new String[2];
        sub[0]=c.getString(c.getColumnIndex("name"));
        sub[1]=c.getString(c.getColumnIndex("teacher"));
        return sub;
    }
    void back(View v)
    {
        onBackPressed();
    }
}
