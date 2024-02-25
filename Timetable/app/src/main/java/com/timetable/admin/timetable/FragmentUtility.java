package com.example.admin.timetable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentUtility extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v= inflater.inflate(R.layout.top,container,false);
        return v;
    }
    TextView tv;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv=((TextView)getView().findViewById(R.id.textView2));
    }
    String t;
    public void updateText(String text)
    {
        tv.setText(text);
    }
}
