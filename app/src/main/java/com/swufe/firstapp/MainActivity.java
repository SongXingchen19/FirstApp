package com.swufe.firstapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //add something....
          TextView out = (TextView) findViewById(R.id.Showtext);
          EditText inp = (EditText) findViewById(R.id.InpText);


        Button btn = (Button)findViewById(R.id.btn);

        //btn.setOnClickListener(new View.OnClickListener() {  //创建新的对象进行监听
           // @Override
            //public void onClick(View v) {
                //Log.i("click","onClick1");
                //String str = inp.getText().toString();
                //int num = Integer.valueOf(str).intValue();
                //out.setText("The temperature is:" + num*1.8+32);
            //}
        //});
        btn.setOnClickListener(this);  //直接使用MainActivity对象监听
    }

    @Override
    public void onClick(View v) {
        Log.i("click","onClick2");

            //TextView tv = (TextView) findViewById(R.id.Showtext);

            //EditText inp = (EditText) findViewById(R.id.InpText);
        String str = inp.getText().toString();
        int num = Integer.valueOf(str).intValue();
        out.setText("The temperature is:" + num*1.8+32);
    }
}
