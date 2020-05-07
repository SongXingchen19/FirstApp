package com.swufe.firstapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    private final String TAG = "rate";
    String data[] = {"wait..."};
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);        一定记得注释掉！！
        List<String> list1 = new ArrayList<String>();
        for (int i=1;i<100;i++){
            list1.add("list" + i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        setListAdapter(adapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
            }
        };
    }

    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程中
        List<String> retList = new ArrayList<String>();
        Document doc = null;
        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            //doc = Jsoup.parse(html);
            Log.i(TAG,"run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");
            int i = 1;
        /*for (Element table : tables){
            Log.i(TAG, "run: table["+i+"]=" + table);
            i++;
        }*/

            //从第几个table中获取数据
            Element table6 = tables.get(5);
            //Log.i(TAG, "run: table6=" + table6);

            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for (int I=0;I<tds.size();I+=6) {
                Element td1 = tds.get(I);
                Element td2 = tds.get(I+5);
                Log.i(TAG, "run: " + td1.text() + "==>" + td2.text());
                String str1 = td1.text();
                String val = td2.text();

                Log.i(TAG, "run: "+ str1 + "==>" + val);
                retList.add(str1 + "==>" + val);
            }


        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
