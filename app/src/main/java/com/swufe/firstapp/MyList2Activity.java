package com.swufe.firstapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyList2Activity extends ListActivity {

    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;  //存放文字，图片信息
    private SimpleAdapter listItemAdapter;  //适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);
    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String,String>>();  //分配内存
        for (int i=0;i<10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItenTitle","Rate:" + i);  //标题文字
            map.put("ItemDetail","detail" + i);  //详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素    当前创建文，数据源，布局，key，XML中id（主键）
        listItemAdapter = new SimpleAdapter(this,listItems,  //listItem数据源
                R.layout.list_item,  //listItem的XML布局实现
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );
    }
}
