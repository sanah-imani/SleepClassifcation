package com.covid19.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Results extends AppCompatActivity implements SensorEventListener {

    TextView light;
    TextView sound;
    TextView Motion;
    TextView gyr;
    TextView accel;


    private SensorManager sensorManager;
    Sensor lightSensor;
    Sensor motionDetect;
    Sensor gyroSense;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gyroSense = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        motionDetect = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);

        sensorManager.registerListener(Results.this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(Results.this, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(Results.this, gyroSense, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(Results.this, motionDetect, sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
