package com.example.admin.timetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BroadCastReciverHelper extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String[] time = ((new SimpleDateFormat("EEEE-k-m")).format(new Date())).split("-");
        SharedPreferences spf=context.getSharedPreferences("buttons",Context.MODE_PRIVATE);
        SharedPreferences.Editor spe=spf.edit();
        int t=Integer.parseInt(time[1])*60+(Integer.parseInt(time[2])+5);
        if(t%5!=0)
            t=t-1;
        spe.putBoolean(time[0]+Integer.toString(t),false);
        spe.commit();
        SQLiteDatabase sdb = new sqlhelper(context).getReadableDatabase();
        Cursor c1 = sdb.rawQuery("select " + time[0] + " from tt where start = "+Integer.toString(t), null);
        c1.moveToNext();
        String subj[]=c1.getString(c1.getColumnIndex(time[0])).split("/");
        String subject=null;
        if(subj[0].equals("0")){
            Cursor subname = sdb.rawQuery("Select * from sub where code = '" +subj[1]  + "'", null);
            subname.moveToNext();
            subject = subname.getString(subname.getColumnIndex("name"));
        }
        else if(subj[0].equals("1"))
            subject="Lab";
        else if(subj[0].equals("2"))
            subject="Tutorial";
        else
            subject=subj[0];
        int c=0;
        while(c<4){
            Toast.makeText(context, subject + " starts in 5 mins", Toast.LENGTH_LONG).show();
        c++;
        }
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(3000);
        sdb.close();
    }
}
