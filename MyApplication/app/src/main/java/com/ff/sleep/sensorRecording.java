package com.ff.sleep;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

public class sensorRecording implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor lightSensor;
    Sensor motionDetect;
    Sensor gyroSense;
    Sensor accelerometer;
    Context myCon;
    private boolean plotData = true;
    private Thread thread;

    LineChart c1, c2, c3, c4, c5;

    float light, motion, gyro, accel;
    List<Float> l1, m1, g1, a1;
    List<Float> l2, m2, g2, a2;

    public sensorRecording(Context c1, Sensor light, Sensor motion, Sensor gyro, Sensor accel, SensorManager manager){
        myCon = c1;
        sensorManager = manager;
        lightSensor = light;
        motionDetect = motion;
        gyroSense = gyro;
        accelerometer = accel;

        sensorManager.registerListener((SensorEventListener) myCon, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) myCon, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) myCon, gyroSense, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) myCon, motionDetect, sensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            light = event.values[0];

            if(l1.size() > 1){
                l1.add((new Float(light)/l1.get(l1.size()-1)));

            }
            else{
                l1.add(1f);
            }
            l2.add(new Float(light));

        }
        else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accel = event.values[0];
            if(a1.size() >= 1){
                a1.add((new Float(accel)/a1.get(a1.size()-1)));

            }
            else{
                a1.add(1f);
            }
            a2.add(new Float(accel));

        }
        else if(event.sensor.getType() == Sensor.TYPE_MOTION_DETECT) {
            motion = event.values[0];
            if(m1.size() > 1){
                m1.add((new Float(motion)/m1.get(m1.size()-1)));

            }
            else{
                m1.add(new Float(motion));
            }
            m2.add(1f);

        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            gyro = event.values[0];

            if(g1.size() > 1){
                g1.add((new Float(gyro)/g1.get(g1.size()-1)));

            }
            else{
                g1.add(1f);
            }
            g2.add(new Float(gyro));

        }
        else{

        }


        if(plotData){
            if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
                addEntry(event, c1);
                plotData = false;
            }
            else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                addEntry(event, c2);
                plotData = false;
            }
            else if(event.sensor.getType() == Sensor.TYPE_MOTION_DETECT){
                addEntry(event, c3);
                plotData = false;
            }
            else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                addEntry(event, c4);
                plotData = false;
            }
            else{

            }
        }


    }

    private void addEntry(SensorEvent event, LineChart mChart){
        LineData data = mChart.getData();
        if(data != null){
            ILineDataSet set = data.getDataSetByIndex(0);

            if(set == null){
                set = createSet();
                data.addDataSet(set);

            }

            data.addEntry(new Entry(set.getEntryCount(), event.values[0]+5),0);
            data.notifyDataChanged();
            mChart.setMaxVisibleValueCount(200);
            mChart.moveViewToX(data.getEntryCount());

        }

    }

    private LineDataSet createSet(){
        LineDataSet set = new LineDataSet(null, "Real-time Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setCubicIntensity(0.2f);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setColor(Color.MAGENTA);
        return set;
    }

    public LineChart startPlot(LineChart chart){
        if(thread != null){
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    plotData = true;
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        return chart;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void Pause(){
        sensorManager.unregisterListener((SensorEventListener) myCon);

    }

    public void Resume(){
        sensorManager.registerListener((SensorEventListener) myCon, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) myCon, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) myCon, gyroSense, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) myCon, motionDetect, sensorManager.SENSOR_DELAY_NORMAL);


    }

    public List<Float> getL1(){

        return l1;

    }
    public List<Float> getM1(){

        return m1;

    }
    public List<Float> getG1(){

        return g1;

    }
    public List<Float> getA1(){

        return a1;

    }

    public List<Float> getA2(){

        return a2;

    }
    public List<Float> getM2(){

        return m2;

    }
    public List<Float> getL2(){

        return l2;

    }
    public List<Float> getG2(){

        return g2;

    }


}
