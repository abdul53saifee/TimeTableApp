package com.example.admin.timetable;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLClientInfoException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AllSubjectViewScreen extends Activity {

    int cout=0, blue=Color.parseColor("#000000");
    SharedPreferences spfbut;
    RelativeLayout rllast;
    SharedPreferences spf;
    SQLiteDatabase sdb;
    String time[];
    int Green=Color.parseColor("#006400");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_last);
            rllast = findViewById(R.id.rllast1);
            spf = getSharedPreferences("check", Context.MODE_PRIVATE);
            spfbut = getSharedPreferences("buttons", Context.MODE_PRIVATE);
            sdb = new sqlhelper(this).getReadableDatabase();
            time = ((new SimpleDateFormat("EEEE-k-m")).format(new Date())).split("-");
            ((TextView) findViewById(R.id.wishes)).setText(wishes());
            TextView tv1 = (TextView) findViewById(R.id.name);
            tv1.setSelected(true);
            tv1.setText(spf.getString("name", ""));
            cout = 0;
            mainBody();
        }
    void back(View v)
    {
        onBackPressed();
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

    void mainBody() {
        if (time[0].equals("Saturday") || time[0].equals("Sunday")) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            TextView tvf = tvgenerator(25, "College Remains Off Today");
            rllast.addView(tvf, lp);
        } else {
            LinearLayout l1 = new LinearLayout(this);
            l1.setOrientation(LinearLayout.VERTICAL);
            Cursor c = sdb.rawQuery("select start," + time[0] + " from tt", null);
            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView tvtop = tvgenerator(20, "Todays View Day :- " + time[0]);
            tvtop.setBackgroundColor(Color.parseColor("#ff000000"));
            TextView tvtop1 = tvgenerator(20, "");
            tvtop1.setId(View.generateViewId());
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            l1.addView(tvtop, lp2);
            l1.setId(View.generateViewId());
            l1.addView(tvtop1, lp2);
            lp1.addRule(RelativeLayout.BELOW, R.id.wishes);
            int i = 1;
            TableLayout tl = new TableLayout(this);
            RelativeLayout.LayoutParams rltable = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rllast.addView(l1, lp1);
            tl.setBackgroundColor(blue);
            rltable.addRule(RelativeLayout.BELOW, l1.getId());
            while (c.moveToNext()) {
                final int temp = cout;
                String[] lec = c.getString(c.getColumnIndex(time[0])).split("/");
                int start = c.getInt(c.getColumnIndex("start"));
                final int stdtime = c.getInt(c.getColumnIndex("start"));
                if (!lec[0].equals("Break")) {
                    if (lec[1].equals("2"))
                        continue;
                }
                TableRow tr = new TableRow(this);
                LinearLayout lintab = new LinearLayout(this);
                lintab.setOrientation(LinearLayout.HORIZONTAL);
                TableRow.LayoutParams trlp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                Button b = new Button(this);
                b.setTextColor(Color.parseColor("#ffffff"));
                b.setGravity(Gravity.LEFT);
                b.setBackgroundColor(blue);
                int sdk= Build.VERSION.SDK_INT;
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int w=0;
                if(sdk>=Build.VERSION_CODES.M)
                w = (int) (dm.widthPixels * 0.85);
                else
                    w = (int) (dm.widthPixels * 0.75);
                b.setWidth(w);
                final Switch b1 = new Switch(this);
                if (spfbut.getBoolean(time[0] + start, false))
                    b1.setChecked(true);
                TableLayout.LayoutParams tllp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                if (lec[0].equals("0")) {
                    Cursor subname = sdb.rawQuery("Select * from sub where code = '" + lec[1] + "'", null);
                    subname.moveToNext();
                    final String subname1 = subname.getString(subname.getColumnIndex("name"));
                    final String teachername1 = subname.getString(subname.getColumnIndex("teacher"));
                    b.setText("Lec " + i + " : " + subname1);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(last.this, abcd.class);
                            i.putExtra("lab", false);
                            i.putExtra("subname", subname1);
                            i.putExtra("break", false);
                            i.putExtra("teachername", teachername1);
                            i.putExtra("time", stdtime);
                            startActivity(i);
                        }
                    });
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startalert(b1, stdtime, subname1, temp);
                        }
                    });
                } else if (lec[0].equals("1")) {

                    b.setText("Lec " + i + " : Lab");
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(last.this, abcd.class);
                            i.putExtra("lab", true);
                            i.putExtra("tutorial", false);
                            i.putExtra("time", stdtime);
                            i.putExtra("day", time[0]);
                            startActivity(i);
                        }
                    });
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startalert(b1, stdtime, "Lab", temp);
                        }
                    });
                } else if (lec[0].equals("Break")) {
                    i--;
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(last.this, abcd.class);
                            i.putExtra("break", true);
                            i.putExtra("lab", false);
                            i.putExtra("tutorial", false);
                            i.putExtra("time", stdtime);
                            startActivity(i);
                        }
                    });
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startalert(b1, stdtime, "Break Time", temp);
                        }
                    });
                    b.setText("Break Time");
                } else {
                    b.setText("Lec " + i + " : Tutorial");
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(last.this, abcd.class);
                            i.putExtra("lab", true);
                            i.putExtra("tutorial", true);
                            i.putExtra("time", stdtime);
                            i.putExtra("day", time[0]);
                            startActivity(i);
                        }
                    });
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startalert(b1, stdtime, "Tutorial", temp);
                        }
                    });
                }
                lintab.addView(b);
                lintab.addView(b1);
                tr.addView(lintab, trlp1);
                tl.addView(tr, tllp);
                i++;
                cout++;
            }
            rllast.addView(tl, rltable);
        }
    }
    void dynamic(boolean batches,String batch1,String batch2,String batch3,String batch4,String Time)
    {
        Intent i=new Intent(this,abcd.class);
        i.putExtra("batches",batches);

    }
    TextView tvgenerator(float size,String text)
    {
        TextView tv=new TextView(this);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTextSize(size);
        tv.setText(text);
        return tv;
    }
    public void startalert(Switch s,int time,String sub,int num)
    {
            SharedPreferences.Editor spe= spfbut.edit();
            int start=time;
            time=time-5;
            long t=time*60*1000;
            String[] str=((new SimpleDateFormat("EEEE-k-m")).format(new Date())).split("-");
            int hr=Integer.parseInt(str[1]),min=Integer.parseInt(str[2]);
            hr=(hr*60+min);
            long t1=hr*60000;
            if(t1>t)
            {
                Toast.makeText(this, "Subject Time Over", Toast.LENGTH_SHORT).show();
                if(s.isChecked())
                s.setChecked(false);
                else
                    s.setChecked(true);
            }
            else {
                spe.putBoolean(str[0]+start,true);
                spe.commit();
                Intent i=new Intent(this,brs.class);
                i.putExtra("num",num);
                if (s.isChecked()) {
                    i.putExtra("bool",true);
                    i.putExtra("t",t);
                    i.putExtra("t1",t1);
                    Toast.makeText(this, "Vibration set for "+sub, Toast.LENGTH_SHORT).show();
                }
                else {
                    i.putExtra("bool",false);
                    Toast.makeText(this, "Vibration Set Off", Toast.LENGTH_SHORT).show();
                    spe.putBoolean(str[0]+start,false);
                    spe.commit();
                }
                startService(i);
            }
    }
}
