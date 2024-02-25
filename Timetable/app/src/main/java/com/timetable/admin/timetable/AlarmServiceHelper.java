package com.example.admin.timetable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmServiceHelper extends Service {
    public AlarmServiceHelper() {
    }
    Intent inten[]=new Intent[7];
    PendingIntent pendinginten[] =new PendingIntent[7];

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Boolean b=intent.getBooleanExtra("bool",false);
        int num=intent.getIntExtra("num",10);
        long t=intent.getLongExtra("t",1),t1=intent.getLongExtra("t1",1);
        inten[num]= new Intent(this, br.class);
        pendinginten[num] = PendingIntent.getBroadcast(this.getApplicationContext(), num, inten[num], 0);
        if(b)
            activate(t,t1,num);
        else
            deactivate(num);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "lets see hahaha", Toast.LENGTH_SHORT).show();
        return null;
    }
    void activate(long t,long t1,int num)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + t - t1,pendinginten[num]);
    }
    void deactivate(int num)
    {
        ((AlarmManager) getSystemService(ALARM_SERVICE)).cancel(pendinginten[num]);
    }
}
