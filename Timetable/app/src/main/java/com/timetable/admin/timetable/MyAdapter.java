package com.example.admin.timetable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class MyAdapter extends BaseAdapter {
    newsfeed sc;
    String[] str;
    int size;
    public MyAdapter(newsfeed sc,String[] str,int size)
    {
        this.sc=sc;
        this.str=str;
        this.size=size;
    }
    @Override
    public int getCount() {
        return size;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf=LayoutInflater.from(sc);
        View v = inf.inflate(R.layout.item,null);
        TextView tv1=(TextView)v.findViewById(R.id.textView6);
        TextView tv2=(TextView)v.findViewById(R.id.textView7);
        Log.i("tag",str[i]);
        String[] temp=str[i].split(Pattern.quote("$"));
        Log.i("tag",temp[0]);
        Log.i("tag",temp[1]);
        tv1.setText(temp[0]);
        tv2.setText(temp[1]);
        return v;
    }
}

