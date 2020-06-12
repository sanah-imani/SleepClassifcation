package com.ff.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ff.sleep.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity implements SensorEventListener, OnChartValueSelectedListener {

    TextView light, sound, motion, gyr, accel;
    String lightS, soundS, motionS, gyrS, accelS;
    String lightV, soundV, motionV, gyrV, accelV;

    public static float[] thresholds = {};
    public static float[] thresholdsV = {};

    private String date;

    BarChart barChart;
    RecyclerView recyclerView;
    String[] type_sense = {"Average Accelerometer Data", "Average Motion Detection Data", "Average Gyroscope Data", "Average Light Level Data", "Average Sound Level Data"};
    List<String> to_display = new ArrayList<>();
    private SleepRecording sr;
    List<String> timePoints;
    String SleepID;
    String[] labels;
    float[] yValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //get Important Data
        sr = new readCSV(Results.this, "My Sleeps").readSingleRecord(SleepID);
        getData();
        lightS = sr.getmLightaverage();
        accelS = sr.getmAccAverage();
        gyrS = sr.getmGyroAverage();
        motionS = sr.getmMotionAverage();
        soundS = sr.getmSoundAverage();
        lightV = sr.getmLightAV();
        soundV = sr.getmSoundAV();
        motionV = sr.getmMotionAV();
        gyrV = sr.getmGryoAV();
        accelV = sr.getmAccAV();

        light = findViewById(R.id.light);
        light.setText("Light Score: " + lightS + " (" + lightV + ")");
        sound = findViewById(R.id.sound);
        sound.setText("Sound Score: " + soundS + " (" + soundV + ")");
        motion = findViewById(R.id.motion);
        motion.setText("Motion Score: " + " (" + motionV + ")");
        gyr = findViewById(R.id.gyr);
        gyr.setText("Gyroscope Score: "+ gyrS + " (" + gyrV + ")");
        accel = findViewById(R.id.accel);
        accel.setText("Accelerometer Score: "+ accelS + " (" + accelV + ")");

        barChart = findViewById(R.id.bar_usage_stats);
        recyclerView = findViewById(R.id.recview2);

        //default show accelerometer avg
        to_display.add("Average Accelerometer Data");


        //recyclerView adapter
        final graphsAdapter gA = new graphsAdapter(Results.this, to_display, sr, timePoints);
        recyclerView.setAdapter(gA);
        recyclerView.setLayoutManager(new LinearLayoutManager(Results.this));

        ChipGroup chipGroup = findViewById(R.id.chip_group1);
        for (int i = 0; i < type_sense.length; i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.chip_row, chipGroup, false);
            final Chip chip = view.findViewById(R.id.loc_chip);
            chip.setText(type_sense[i]);
            chip.setClickable(true);
            chip.setCheckable(true);
            chipGroup.addView(chip);
            if(chip.getText().toString().equals("Average Accelerometer Data")){
                chip.setChecked(true);
            }
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(chip.isChecked()){
                        to_display.add(chip.getText().toString());
                        gA.updateList(to_display);


                    }
                    else{

                        to_display.remove(chip.getText().toString());
                        gA.updateList(to_display);

                    }

                }
            });
        }

        List<String> xValues = sr.getMpackageName();
        List<String> yVals = sr.getMuseTime();
        labels = new String[xValues.size()];
        yValues = new float[yVals.size()];
        for(int i = 0; i <xValues.size(); i++){
            labels[i] = xValues.get(i);
            yValues[i] = Float.parseFloat(yVals.get(i));


        }

        barChart = new myTrends(Results.this).setBarChart(labels,yValues,"App Usage During Sleep",barChart, "App Names");


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void getData(){
        Bundle bundle = getIntent().getExtras();


        if (bundle != null){
            SleepID = bundle.getString("Sleep ID");

        }

    }

    public void retHome(View view) {
        Intent intent = new Intent(Results.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
