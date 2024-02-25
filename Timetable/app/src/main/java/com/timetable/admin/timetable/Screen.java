package com.example.admin.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Screen1 extends AppCompatActivity {

    SharedPreferences spf;
    String[] time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        WebView wv=(WebView)findViewById(R.id.webview);
        wv.loadUrl("file:///android_asset/tmtab.html");
        wv.getSettings().setBuiltInZoomControls(true);
        spf=getSharedPreferences("check", Context.MODE_PRIVATE);
        time=((new SimpleDateFormat("EEEE-k-m")).format(new Date())).split("-");
        ((TextView)findViewById(R.id.wishes)).setText(wishes());
        TextView tv1=(TextView)findViewById(R.id.name);
        tv1.setSelected(true);
        tv1.setText(spf.getString("name",""));
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
}
