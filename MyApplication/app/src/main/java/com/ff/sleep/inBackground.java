package com.ff.sleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.anychart.core.Chart;
import com.ff.sleep.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class inBackground extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = "inBackground";
    private String SleepID;
    private String date;
    private String time;
    private String score;

    private Chronometer chronometer;
    Boolean running;
    long pauseOffset;
    Button b1, b2, b3, b4;
    RecyclerView recyclerView;
    private LineChart lineChart1, lineChart2, lineChart3, lineChart4, lineChart5;

    List<String> alerts = new ArrayList<>();
    List<String> ts = new ArrayList<>();

    sensorRecording sr;
    String hh, mm, ss;
    List<Float> l1, m1, g1, a1;
    List<Float> l2, m2, g2, a2;
    List<Double> s1;
    List<Double> s2;

    private SensorManager sensorManager;
    Sensor lightSensor;
    Sensor motionDetect;
    Sensor gyroSense;
    Sensor accelerometer;
    String target;
    ImageView target_pic;


    //app usage tracking;
    private UsageStatsManager mUsageStatsManager;
    private boolean StopRec = false;
    private List<String> mpackageName, museTime;
    private HashMap<String, Long> startPackages;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_background);
        chronometer = findViewById(R.id.chrono1);
        b1 = findViewById(R.id.pause);
        b2 = findViewById(R.id.stop);
        b3 = findViewById(R.id.delete);
        b4 = findViewById(R.id.analyse);
        recyclerView = findViewById(R.id.recview1);
        target_pic = findViewById(R.id.target_reached);
        target_pic.setImageDrawable(null);
        lineChart1 = findViewById(R.id.chart1);
        lineChart2 = findViewById(R.id.chart2);
        lineChart3 = findViewById(R.id.chart3);
        lineChart4 = findViewById(R.id.chart4);
        lineChart5 = findViewById(R.id.chart5);
        setDateandTime();
        mUsageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);

        target = Profile.SLEEP_TARGET + ":00:00";

        final alertAdapter nots = new alertAdapter(inBackground.this, alerts, ts);
        recyclerView.setAdapter(nots);
        recyclerView.setLayoutManager(new LinearLayoutManager(inBackground.this));

        //sensor data plotting

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gyroSense = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        motionDetect = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);

        sr = new sensorRecording(inBackground.this, lightSensor,motionDetect,gyroSense,accelerometer, sensorManager);

        //sensorData charts
        LineChart[] chartsColl = {lineChart1,lineChart2,lineChart3, lineChart4, lineChart5};
        for(LineChart chart: chartsColl){
            chart.getDescription().setEnabled(true);
            chart.getDescription().setText("Real-time data");
            chart.setTouchEnabled(false);
            chart.setDragEnabled(false);
            chart.setScaleEnabled(true);
            chart.setDrawGridBackground(false);
            chart.setPinchZoom(true);
            chart.setBackgroundColor(Color.DKGRAY);

            LineData lineData = new LineData();
            lineData.setValueTextColor(Color.WHITE);
            lineData.setValueTextSize(10f);
            chart.setData(lineData);

            chart = sr.startPlot(chart);

        }


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

                checkAlerts();

                if("12:00:00".equals(cArg.getText())){
                    alerts.add("No more data can be recorded after 12 hour recordings");
                    ts.add(cArg.getText().toString());
                    nots.updateList(alerts,ts);
                    nots.notifyDataSetChanged();
                    StopRec = true;
                }
                if(target.equals(cArg.getText())){
                    cArg.setTextColor(Color.GREEN);
                    target_pic.setImageResource(R.drawable.target);
                }

                final Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -1);

                List<UsageStats> queryUsageStats = mUsageStatsManager
                        .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(),
                                System.currentTimeMillis());
                for (int i = 0; i < queryUsageStats.size(); i ++){
                    if(!queryUsageStats.get(i).getPackageName().equals(getPackageName())){
                        alerts.add(queryUsageStats.get(i).getPackageName() + " was used");
                        long last_time = queryUsageStats.get(i).getLastTimeStamp();
                        ts.add(String.valueOf(last_time));

                    }
                }
                String cTime = cArg.getText().toString();
                if(StopRec == false && cTime.substring(cTime.indexOf(":")+1, cTime.length()).equals("00:00")){
                    createAveragesV(sr.getL1(),sr.getM1(),sr.getG1(),sr.getA1(),s1);
                    createAverages(sr.getL2(),sr.getM2(),sr.getG2(),sr.getA2(),s2);
                }
            }
        });

        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
        running = true;
    }

    public void stopChrono(View view) throws IOException {
        chronometer.setBase(SystemClock.elapsedRealtime());
        sr.Pause();
    }

    public void pauseChrono(View view) {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            sr.Pause();
        }
        //CHECK THIS
        sr.Resume();
    }

    public void delete(View view) {
        chronometer.stop();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete? All data will be deleted.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete? All data will be deleted.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void createAverages(List<Float> lData, List<Float> mData, List<Float> gData, List<Float> aData, List<Double> sData){
        //averages

        l2.add(calcAvgS(lData));
        m2.add(calcAvgS(mData));
        g2.add(calcAvgS(gData));
        a2.add(calcAvgS(aData));
        s2.add(calcAvgSound(sData));

    }
    public float calcAvgS(List<Float> data){
        float favg = 0f;
        for (Float f: data){

            favg += f;

        }

        favg = (favg/data.size());

        return favg;
    }

    public double calcAvgSound(List<Double> data){
        double favg = 0f;
        for (double f: data){

            favg += f;

        }

        favg = (favg/data.size());

        return favg;
    }

    public void createAveragesV(List<Float> lData, List<Float> mData, List<Float> gData, List<Float> aData, List<Double> sData){
        //averages

        l1.add(calcAvgS(lData));
        m1.add(calcAvgS(mData));
        g1.add(calcAvgS(gData));
        a1.add(calcAvgS(aData));
        s1.add(calcAvgSound(sData));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void saveSleepData() throws IOException {
        genSleepID();
        new WriteCSV("user", null, inBackground.this, null).changeUserData(SleepID);

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

        String[] tokens = df.format(dateobj).split("\\s+");

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(),
                        System.currentTimeMillis());

        for (int i = 0; i < queryUsageStats.size(); i ++){
            if(!queryUsageStats.get(i).getPackageName().equals(getPackageName())){
                mpackageName.add(queryUsageStats.get(i).getPackageName());
                long duration = queryUsageStats.get(i).getTotalTimeInForeground();
                if(startPackages.containsKey(queryUsageStats.get(i).getPackageName())){
                    long begTime = startPackages.get(queryUsageStats.get(i).getPackageName());
                    museTime.add(String.valueOf(duration-begTime));
                }
                else{
                    museTime.add(String.valueOf(duration));
                }


            }
        }



        SleepRecording sleepData = new SleepRecording(SleepID, date, (time + " - " + tokens[1]), score, chronometer.getText().toString(), String.valueOf(calcAvgS(a2)), String.valueOf(calcAvgS(m2)), String.valueOf(calcAvgS(g2)), String.valueOf(calcAvgS(l2)),
                String.valueOf(calcAvgSound(s2)), String.valueOf(calcAvgS(a1)), String.valueOf(calcAvgS(m1)), String.valueOf(calcAvgS(g1)), String.valueOf(calcAvgS(l1)),
                String.valueOf(calcAvgSound(s1)), a2,m2,g2,l2,s2,a1,m1,g1,l1,s1,mpackageName, museTime);

        ArrayList mdata = new ArrayList();
        mdata.add(sleepData);

        //make the data list;
        try {
            new WriteCSV("sleep_recordings", SleepID, inBackground.this, mdata).addSleepRecording(sleepData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genSleepID(){
        Random rand = new Random();

        Boolean match = false;

        List<List<String>> sleeps = new readCSV(inBackground.this, "My Sleeps").readRecord();
        int count = 0;
        while (match == true || (match == false && count == 0)){
            match = false;
            final int testID = rand.nextInt(100000000);


            for(List<String> l1: sleeps){
                for (String id: l1){
                    if (id.equals(String.valueOf(testID))){
                        match = true;
                        SleepID = "";
                    }
                    else{
                        if(match == false){

                            SleepID = String.valueOf(testID);

                        }


                    }
                }
            }
            count++;

        }



    }

    public void setDateandTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

        String[] tokens = df.format(dateobj).split("\\s+");

        if(tokens.length >= 2){
            date = tokens[0];
            time = tokens[1];
        }
    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,gyroSense,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,motionDetect,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this);
        super.onDestroy();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void checkAlerts(){
        //TO-DO
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void analyse(View view) throws IOException {
        saveSleepData();
        Intent i = new Intent(inBackground.this,Results.class);
        i.putExtra("Sleep ID", SleepID);
        startActivity(i);
    }
}
