package com.swufe.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {

    private final String TAG = "rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;

    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showOut);
    }

    public void onClick(View btn){
        Log.i(TAG,"onClick:");
        String str = rmb.getText().toString();
        Log.i(TAG,"onClick:get str=" + str);

        float r = 0;
        if (str.length()>0){
            r = Float.parseFloat(str);
        }else {
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG,"onClick:r=" + r);

        float val = 0;
        if (btn.getId()==R.id.btn_dollar){
            show.setText(String.format("%.2f",r*dollarRate));

        }else if (btn.getId()==R.id.btn_euro){
            show.setText(String.format("%.2f",r*euroRate));

        }else {
            show.setText(String.format("%.2f",r*wonRate));

        }
    }

    public void openOne(View btn){
        //Intent main = new Intent(this,MainActivity.class);
        //Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:87092173"));
        //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:87092173"));
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jd.com"));
        //startActivity(intent);
        //startActivity(main);
        //finish();

        Intent config = new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);

        Log.i(TAG,"openOne:dollar_rate_key=" + dollarRate);
        Log.i(TAG,"openOne:euro_rate_key=" + euroRate);
        Log.i(TAG,"openOne:won_rate_key=" + wonRate);

        startActivity(config);
    }
}
