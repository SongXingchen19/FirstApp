package com.swufe.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG ="second" ;
    TextView score;
    TextView score2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        score=findViewById(R.id.score1);
        score2=findViewById(R.id.score2);
    }


    public void btnAdd1(View v) {
        if(v.getId()==R.id.button_4)
            showScore(1);
        else{
            showScore2(1);
        }
    }
    public void btnAdd2(View v) {
        if(v.getId()==R.id.button_5)
            showScore(2);
        else{
            showScore2(2);
        }
    }
    public void btnAdd3(View v) {
        if(v.getId()==R.id.button_6)
            showScore(3);
        else{
            showScore2(3);
        }
    }
    public void reset(View v) {
        score.setText("0");
        score2.setText("0");
    }
    public void showScore(int inc) {
        Log.i("main","inc="+inc);
        String oldScore=(String) score.getText();
        int newScore=Integer.parseInt(oldScore)+inc;
        score.setText(""+newScore);
    }
    public void showScore2(int inc) {
        Log.i("main","inc="+inc);
        String oldScore=(String) score2.getText();
        int newScore=Integer.parseInt(oldScore)+inc;
        score2.setText(""+newScore);
    }
}