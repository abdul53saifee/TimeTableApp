package com.example.admin.timetable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SharedPreferences spf;
    SharedPreferences.Editor se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra("Exit",false))finish();
        setContentView(R.layout.activity_main);
        spf=getSharedPreferences("check", Context.MODE_PRIVATE);
        int checker = spf.getInt("first", 0);
        if(checker==0){
            setContentView(R.layout.screen1);
            se=spf.edit();
            se.putInt("first",1);
            se.commit();
            Intent i=new Intent(this,ttService.class);
            startService(i);
            new CountDownTimer(5000,1000){
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    screen2(null);
                }
            }.start();
        }else{
            redirect();
        }
}
    public void screen2(View v)
    {
        setContentView(R.layout.screen3);
    }
    public void name(View v)
    {
        EditText et=(EditText) findViewById(R.id.et);
        if(et.getText().toString().length()==0) {
            Toast.makeText(this, "Plz Enter Valid Name", Toast.LENGTH_SHORT).show();
            screen2(null);
        }
        else{
        se.putString("name",et.getText().toString()+"\n");
        se.commit();
        redirect();}
    }
    public void redirect()
    {
        Intent i1=new Intent(this,screen5.class);
        startActivity(i1);
        finish();
    }

}