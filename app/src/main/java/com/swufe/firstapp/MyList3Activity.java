package com.swufe.firstapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyList3Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{

    private final String TAG = "mylist3";
    private ArrayList<HashMap<String,String>> listItems;  //存放文字，图片信息
    private SimpleAdapter listNoticeAdapter;  //适配器
    Handler handler;
    EditText inp3;
    private  String updateDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list3);

        //添加初始化方法和应用adapter
        initListView();
        this.setListAdapter(listNoticeAdapter);

        Thread t = new Thread(this);
        t.start();

        updateDate = sharedPreferences.getString("update_date","");

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd EEEE");
        final String todayStr = sdf.format(today);
        Date beginTime=sdf.parse(todayStr);
        Date endTime=sdf.parse(updateDate);

        //判断时间
        if (((endTime.getTime() - beginTime.getTime())/(24*60*60*1000))>=7){
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        }else {
            Log.i(TAG, "onCreate: 不需要更新");
        }

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 7) ;
                List<HashMap<String, String>> list3 = (List<HashMap<String, String>>) msg.obj;
                listNoticeAdapter = new SimpleAdapter(MyList3Activity.this, list3,  //listItem数据源
                        R.layout.result_list_item,  //listItem的XML布局实现
                        new String[]{"NoticeTitle", "NoticeLink"},
                        new int[]{R.id.NoticeTitle, R.id.NoticeLink}
                );
                setListAdapter(listNoticeAdapter);
            }

        };
        getListView().setOnClickListener((View.OnClickListener) this);



        //接收数据
        final String title3 = getIntent().getStringExtra("title");
        final String link3 = getIntent().getStringExtra("link");

        Log.i(TAG, "onCreate: title = " + title3);
        Log.i(TAG, "onCreate: link = " + link3);
        ((TextView)findViewById(R.id.title3)).setText(title3);
        inp3 = (EditText) findViewById(R.id.activity_notice_search);
        //监听器监听文本改变
        inp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextView show = MyList3Activity.this.findViewById(R.id.activity_notice_show);
                if (s != null){
                    //展示公告标题以及链接
                    show.setText("Title==>" + title3);
                    show.setText("Link==>" + link3);
                }else {
                    show.setText("");
                }
            }
        });
    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String,String>>();  //分配内存
        for (int i=0;i<10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("NoticeTitle","Title:" + i);  //标题文字
            map.put("NoticeLink","Link" + i);  //公告链接
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素    当前上下文，数据源，布局，key，XML中id（主键）
        listNoticeAdapter = new SimpleAdapter(this,listItems,  //listItem数据源
                R.layout.result_list_item,  //listItem的XML布局实现
                new String[]{"NoticeTitle","NoticeLink"},
                new int[]{R.id.NoticeTitle,R.id.NoticeLink}
        );
    }

    @Override
    public void run() {
        List<HashMap<String,String>> titleList = new ArrayList<HashMap<String,String>>();
        Document doc = null;

        try {
            Thread.sleep(100);      //间隔时间
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();     //获取网址
            Log.i(TAG,"run:" + doc.title());
            String textstr = String.valueOf(R.id.activity_notice_search);
            int a = textstr.indexOf();
            HashMap<String,String> map = new HashMap<String,String>();
            titleList.add(map);

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = titleList;
        handler.sendMessage(msg);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG, "onItemClick: parent:" + parent);
        Log.i(TAG, "onItemClick: View= " + view);
        Log.i(TAG, "onItemClick: position:" + position);
        Log.i(TAG, "onItemClick: id:" + id);
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("NoticeTitle");
        String linkStr = map.get("NoticeLink");
        Log.i(TAG, "onItemClick: titleStr:" + titleStr);
        Log.i(TAG, "onItemClick: linkStr:" + linkStr);

        TextView title = view.findViewById(R.id.NoticeTitle);
        TextView detail = view.findViewById(R.id.NoticeLink);
        String title3 = String.valueOf(title.getText());
        String link3 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title3);
        Log.i(TAG, "onItemClick: detail2=" + link3);


        //传入参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("link",Float.parseFloat(linkStr));


    }
}
