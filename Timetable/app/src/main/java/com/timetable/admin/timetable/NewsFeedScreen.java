package com.example.admin.timetable;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class NewsFeedScreen extends AppCompatActivity {
    ListView lv;
    String data[]=new String[15];
    int temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        lv=(ListView)findViewById(R.id.lview);
        new asy().execute();
    }
    class asy extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            upload();
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                URL url=new URL("http://customurl/test.html");
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.setConnectTimeout(10000);
                con.setReadTimeout(10000);
                System.out.println(con.getResponseCode());
                BufferedReader br=new BufferedReader(new InputStreamReader (new BufferedInputStream(con.getInputStream())));
                int i=0;
                while((data[i]=br.readLine())!=null)
                {
                    i++;
                }
                temp=i;
                con.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void upload()
    {
        lv.setAdapter(new myadapter(newsfeed.this,data,temp));
    }
}
