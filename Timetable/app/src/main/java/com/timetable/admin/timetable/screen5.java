package com.example.admin.timetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class screen5 extends Activity {
    top t;
    int ti;
    SimpleDateFormat sdf;
    String time[],lec1[],lecfinal[],same="#00000000";
    SharedPreferences spf;
    SQLiteDatabase sdb;
    Fragment f;
    RelativeLayout rl;
    Cursor c,c1;
    int
    Blue=Color.parseColor("#000000"),
    Green=Color.parseColor("#000000"),
    Maroon=Color.parseColor("#00000000"),
    firstcolor=Color.parseColor(same),
    secondcolor=Color.parseColor(same),
    thirdcolor=Color.parseColor(same),
    fourthcolor=Color.parseColor(same);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);
        rl=findViewById(R.id.screen5);
        spf=getSharedPreferences("check", Context.MODE_PRIVATE);
        sdb=new sqlhelper(this).getReadableDatabase();
        time=((new SimpleDateFormat("EEEE-k-m")).format(new Date())).split("-");
        ((TextView)findViewById(R.id.wishes)).setText(wishes());
        TextView tv1=(TextView)findViewById(R.id.name);
        tv1.setSelected(true);
        tv1.setText(spf.getString("name",""));
        ti=(Integer.parseInt(time[1])*60)+Integer.parseInt(time[2]);
        readdata();
    }

    public void refresh(View v)
    {
        onCreate(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(null);
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
    void readdata()
    {
        if(time[0].equals("Saturday")||time[0].equals("Sunday")){
            textview(" College Remains Off Today".toUpperCase(),RelativeLayout.CENTER_VERTICAL,RelativeLayout.CENTER_HORIZONTAL);
        }
        else if(ti>930||ti<480)
            textview("College Timing Between 8:00 AM to 3:30 PM".toUpperCase(),RelativeLayout.CENTER_VERTICAL,RelativeLayout.CENTER_HORIZONTAL);
        else
        {
            c=sdb.rawQuery("select * from tt where start <="+ ti+" and end1 >= "+ti ,null);
            c.moveToNext();
            String currlec=c.getString(c.getColumnIndex(time[0]));
            if(currlec.equals("Break")) {
                textview("BREAK TIME NOW",0,RelativeLayout.CENTER_HORIZONTAL);
                textview("End in :- 0hr : ".toUpperCase()+Integer.toString(30-Integer.parseInt(time[2]))+"min ",RelativeLayout.CENTER_VERTICAL,RelativeLayout.CENTER_HORIZONTAL);

                //mainbody(false,"Break",null,"0hr : "+Integer.toString(30-(Integer.parseInt(time[2])))+"min");
            }

            else{
                lec1=currlec.split("/");
                lecfinal=lec1;
                if(lec1[0].equals("0")){
                    c1=sdb.rawQuery("select * from sub where code = '"+lec1[1]+"'",null);
                    c1.moveToNext();
                    String lec11=c1.getString(c1.getColumnIndex("name"));
                    String tn=c1.getString(c1.getColumnIndex("teacher"));
                    int temp=c.getInt(c.getColumnIndex("end1"))-ti;
                    String Endtime1=Integer.toString(temp/60);
                    String Endtime2=Integer.toString(temp%60);
                    mainbody(false,lec11,tn,Endtime1+"hr : "+Endtime2+"min");
                }
                else if(lec1[0].equals("1"))
                {
                    String Endtime1,Endtime2;
                    int temp;
                    if(lec1[1].equals("1")) {
                        temp = c.getInt(c.getColumnIndex("end1")) + 60-ti;
                    }
                    else
                        temp=c.getInt(c.getColumnIndex("end1"))-ti;
                    Endtime1=Integer.toString(temp/60);
                    Endtime2=Integer.toString(temp%60);
                    mainbody(true,"Lab",null,Endtime1+"hr : "+Endtime2+"min");
                }
                else
                {
                    int temp=c.getInt(c.getColumnIndex("end1"))-ti;
                    String Endtime1=Integer.toString(temp/60);
                    String Endtime2=Integer.toString(temp%60);
                    mainbody(true,"Tutorial",null,Endtime1+"hr : "+Endtime2+"min");
                }
            }
        }
    }
    int back=0;
    long backtime;
    @Override
    public void onBackPressed() {
        if(back==0) {
            backtime = System.currentTimeMillis();
            toaster("Press again to Exit ");
            back++;
        }
        else {
            if (System.currentTimeMillis() <= backtime + 2000) {
                super.onBackPressed();
            } else {
                back=0;
                onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sdb.close();
    }
    void toaster(String data)
    {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
    void textview(String text,int a,int b)
    {
        TextView tv=new TextView(screen5.this);
        tv.setText(text);
        tv.setTextSize(25);
        tv.setMaxLines(3);
        tv.setId(0);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setSelected(true);
        tv.setSingleLine(true);
        tv.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams p1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        p1.addRule(a);
        p1.addRule(b);
        if(a==0)
        {
            p1.addRule(RelativeLayout.BELOW,R.id.name);
        }
        rl.addView(tv,p1);
    }
    LinearLayout l2=null;
    void mainbody(boolean lab,String lecture1,String teachername,String Time)
    {

        p1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        p1.addRule(RelativeLayout.BELOW,R.id.wishes);

        l2 = viewprovider(lab, lecture1, teachername, Time);
        TextView tv311=newtextviewgenerator("",false,Maroon);
        TextView tv3=newtextviewgenerator("Ends in : "+Time,false,Green);
        l2.addView(tv311,lparamgernerator(true));
        l2.addView(tv3,lparamgernerator(true));
        l2.addView(newtextviewgenerator("",false,0),lparamgernerator(false));
        LinearLayout l3=new LinearLayout(this);
        l3.setOrientation(LinearLayout.HORIZONTAL);
        l3.addView(newtextviewgenerator("Next Lec : ",false,Blue),lparamgernerator(false));
        Cursor c3=null;
        if((lecfinal[0].equals("1"))&&(lecfinal[1].equals("1"))){
            int time=(c.getInt(c.getColumnIndex("end1")))+60;
            c3=sdb.rawQuery("select * from tt where start = "+Integer.toString(time),null);
        }
        else
        c3=sdb.rawQuery("select * from tt where start ="+Integer.toString(c.getInt(c.getColumnIndex("end1"))),null);
        if(c3.moveToNext())
        {
            int currtime=c3.getInt(c3.getColumnIndex("start"));
            if(currtime==720){
                c3=sdb.rawQuery("select * from tt where start = 750",null);
                c3.moveToNext();
            }
            String currlec[]=c3.getString(c3.getColumnIndex(time[0])).split("/");
            if(currlec[0].equals("0"))
            {
                Cursor cte=sdb.rawQuery("select * from sub where code ='"+currlec[1]+"'",null);
                cte.moveToNext();
                l3.addView(newtextviewgenerator(cte.getString(cte.getColumnIndex("name")),true,Blue),lparamgernerator(true));
                l2.addView(l3,lparamgernerator(true));
                l2.addView(eachgenerator(false,null,null,cte.getString(cte.getColumnIndex("teacher")),Maroon),lparamgernerator(true));
            }
            else if(currlec[0].equals("1"))
            {
                l3.addView(newtextviewgenerator("Lab",true,Blue),lparamgernerator(true));
                lec1=currlec;
                l2.addView(l3,lparamgernerator(true));
                l2.addView(llgenerator(),lparamgernerator(true));
            }
            else {
                l3.addView(newtextviewgenerator("Tutorial",true,Blue),lparamgernerator(true));
                lec1=currlec;
                l2.addView(l3,lparamgernerator(true));
                l2.addView(llgenerator(),lparamgernerator(true));
            }
        }
        else {
            l3.addView(newtextviewgenerator("College Time Over", true,Blue), lparamgernerator(true));
            l2.addView(l3,lparamgernerator(true));
        }
            //College timing
        rl.addView(l2,p1);
    }
    TextView textviewgenerator(String text,boolean b,int color)
    {
        TextView tv=new TextView(this);
        tv.setText(text);
        tv.setBackgroundColor(color);
        tv.setTextSize(16);
        tv.setTextColor(Color.WHITE);
        tv.setSingleLine(b);
        tv.setSelected(b);
        if(b)
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        return tv;
    }
    TextView newtextviewgenerator(String text,boolean b,int color)
    {
        TextView tv=new TextView(this);
        tv.setText(text);
        tv.setBackgroundColor(color);
        tv.setTextSize(20);
        tv.setTextColor(Color.WHITE);
        tv.setSingleLine(b);
        tv.setSelected(b);
        if(b)
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        return tv;
    }
    LinearLayout.LayoutParams lparamgernerator(boolean b)
    {
        LinearLayout.LayoutParams l1;
        if(b)
            l1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        else
            l1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return  l1;
    }
    LinearLayout llgenerator()
    {
        LinearLayout l1=new LinearLayout(this);
        l1.setOrientation(LinearLayout.VERTICAL);
        String Query="select * from sub where code =";
        for(int i=2;i<6;i++){
            Cursor ctemp=sdb.rawQuery(Query+"'"+lec1[i]+"'",null);
            ctemp.moveToNext();
            int color=0;
            switch (i) {
                case 2:
                    color = firstcolor;
                    break;
                case 3:
                    color = secondcolor;
                    break;
                case 4:
                    color = thirdcolor;
                    break;
                case 5:
                    color = fourthcolor;
                    break;
            }
        l1.addView(eachgenerator(true, "Batch "+Integer.toString(i-1)+" : ",ctemp.getString(ctemp.getColumnIndex("name")), null,color), lparamgernerator(true));
        l1.addView(eachgenerator(false, null, null, ctemp.getString(ctemp.getColumnIndex("teacher")),color),lparamgernerator(true));
    }
        return l1;
    }
    LinearLayout eachgenerator(boolean b,String batch,String sub,String teacher,int color)
    {
        LinearLayout l=l=new LinearLayout(this);;
        l.setOrientation(LinearLayout.HORIZONTAL);
        if(b)
        {
            l.addView(textviewgenerator(batch,false,color),lparamgernerator(false));
            l.addView(textviewgenerator(sub,true,color),lparamgernerator(true));
        }
        else
        {
            l.addView(textviewgenerator("By :"+teacher,false,color),lparamgernerator(true));
        }
        return l;
    }
    RelativeLayout.LayoutParams p1;
    LinearLayout viewprovider(boolean lab,String lecture1,String teachername,String Time)
    {
        LinearLayout l2=new LinearLayout(this);
        l2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        TextView tv0=newtextviewgenerator("Current Lec : ",false,Blue);
        TextView tv1=newtextviewgenerator(lecture1,true,Blue);
        ll.addView(tv0,lparamgernerator(false));
        ll.addView(tv1,lparamgernerator(true));
        l2.addView(ll);
        if(!lab) l2.addView(textviewgenerator("By : " + teachername, false, Maroon));
        else l2.addView(llgenerator(), lparamgernerator(true));
        return l2;
    }

    public void today(View view) {
        Intent i=new Intent(this,last.class);
        startActivity(i);
            }

    public void tmtab(View view) {
        Intent i=new Intent(this,timetable.class);
        startActivity(i);
    }

    public void news(View view) {
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cm.getActiveNetworkInfo();
        if(nf!=null)
        {
            startActivity(new Intent(this,notceboard.class));
        }
        else
        {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}



