package com.swufe.firstapp;

import android.app.ListActivity;
import android.os.Bundle;

public class RateListActivity extends ListActivity {
    String date[] = {"one","two","three"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);        一定记得注释掉！！
    }
}
