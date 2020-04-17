package com.swufe.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        //获取SP里保存的内容
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate = sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate = sharedPreferences.getFloat("won_rate",0.0f);
        Log.i(TAG,"onCreate: sp dollarRate" + dollarRate);
        Log.i(TAG,"onCreate: sp euroRate" + euroRate);
        Log.i(TAG,"onCreate: sp wonRate" + wonRate);

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

        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openOne:dollar_rate_key=" + dollarRate);
        Log.i(TAG, "openOne:euro_rate_key=" + euroRate);
        Log.i(TAG, "openOne:won_rate_key=" + wonRate);

        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_set){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if (requestCode==1 && resultCode==2){

            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar=",0.1f);
            euroRate = bundle.getFloat("key_euro=",0.1f);
            wonRate = bundle.getFloat("key_won=",0.1f);
            Log.i(TAG,"onActivityResult:dollarRate=" + dollarRate);
            Log.i(TAG,"onActivityResult:euroRate=" + euroRate);
            Log.i(TAG,"onActivityResult:wonRate=" + wonRate);

            //将新设置的汇率写到sp中
            SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.commit();
            Log.i(TAG,"onActivityResult已保存到sharedPreferences")；
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}
