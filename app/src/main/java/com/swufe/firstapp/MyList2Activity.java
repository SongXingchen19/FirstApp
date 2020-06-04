package com.swufe.firstapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{

    private final String TAG = "mylist2";
    private ArrayList<HashMap<String,String>> listItems;  //存放文字，图片信息
    private SimpleAdapter listItemAdapter;  //适配器
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);

        //使用自定义适配器MyAdapter
        //MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item,listItems);
        //this.setListAdapter(myAdapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if (msg.what == 7);
                List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                listItemAdapter = new SimpleAdapter(MyList2Activity.this,list2,  //listItem数据源
                        R.layout.list_item,  //listItem的XML布局实现
                        new String[]{"ItemTitle","ItemDetail"},
                        new int[]{R.id.itemTitle,R.id.itemDetail}
                );
                setListAdapter(listItemAdapter);
            }

        };
        getListView().setOnClickListener((View.OnClickListener) this);
        getListView().setOnItemLongClickListener((AdapterView.OnItemLongClickListener) this);

    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String,String>>();  //分配内存
        for (int i=0;i<10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItenTitle","Rate:" + i);  //标题文字
            map.put("ItemDetail","detail" + i);  //详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素    当前上下文，数据源，布局，key，XML中id（主键）
        listItemAdapter = new SimpleAdapter(this,listItems,  //listItem数据源
                R.layout.list_item,  //listItem的XML布局实现
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );
    }


    //线程方法
    public void run(){
        //
        List<HashMap<String,String>> rateList = new ArrayList<HashMap<String,String>>();
        Document doc = null;
        try {
            Thread.sleep(1000);      //间隔时间
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");

            //从第几个table中获取数据
            Element table6 = tables.get(1);
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
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",val);
                rateList.add(map);
            }

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = rateList;
        handler.sendMessage(msg);
    }

    //处理第62行
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG, "onItemClick: parent:" + parent);
        Log.i(TAG, "onItemClick: View= " + view);
        Log.i(TAG, "onItemClick: position:" + position);
        Log.i(TAG, "onItemClick: id:" + id);

        //从ListView中获取选中数据
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr:" + titleStr);
        Log.i(TAG, "onItemClick: detailStr:" + detailStr);

        //从View中获取选中数据
        TextView title = view.findViewById(R.id.itemTitle);
        TextView detail = view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);


        //打开新的界面，传入参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按列表项position=" + position);
        //删除操作
        //构造对话框进行确认操作
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框事件处理");
                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        Log.i(TAG, "onItemLongClick: size=" + listItems.size());

        return true;
    }
}
