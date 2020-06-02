package com.covid19.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.ArrayList;
import java.util.List;

public class inBackground extends AppCompatActivity {

    private Chronometer chronometer;
    Boolean running;
    long pauseOffset;
    Button b1, b2;
    RecyclerView recyclerView;

    List<String> alerts = new ArrayList<>();
    List<String> ts = new ArrayList<>();


    String hh, mm, ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_background);
        chronometer = findViewById(R.id.chrono1);
        b1 = findViewById(R.id.pause);
        b2 = findViewById(R.id.stop);
        recyclerView = findViewById(R.id.recview1);

        alertAdapter nots = new alertAdapter(inBackground.this, alerts, ts);
        recyclerView.setAdapter(nots);
        recyclerView.setLayoutManager(new LinearLayoutManager(inBackground.this));

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                hh = h < 10 ? "0"+h: h+"";
                mm = m < 10 ? "0"+m: m+"";
                ss = s < 10 ? "0"+s: s+"";
                cArg.setText(hh+":"+mm+":"+ss);
            }
        });

        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
        running = true;

    }

    public void stopChrono(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        Intent intent = new Intent(inBackground.this, Results.class);
        intent.putExtra("Time Elapsed", hh+":"+mm+":"+ss);
        startActivity(intent);
    }

    public void pauseChrono(View view) {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
}
