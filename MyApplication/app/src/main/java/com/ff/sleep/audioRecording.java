package com.ff.sleep;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;

public class audioRecording extends AppCompatActivity {
    final int REQUEST_PERMISSION_CODE = 1000;

    MediaRecorder mRecorder;
    Boolean running1;
    Context myCon;
    List<Double> amplitudes;



    public audioRecording(Context c1) throws IOException {
        myCon = c1;
    }

    public interface Data{
        void averageDecibalLevel(int decibal);
        void averageDecibalvariation(int variation);
        void startSuccessfully(boolean start);
    }

    public void start(){
        if(checkPermissionFromDevice()){

            record();

        }
        else{

            requestPermission();

        }

    }

    public void record(){

        setUpMediaRecorder();
        try{
            mRecorder.prepare();
            mRecorder.start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void setUpMediaRecorder(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile("/dev/null");
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions((Activity) myCon, new String[]{
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(myCon, "Microphone permission granted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(myCon, "Microphone permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    private boolean checkPermissionFromDevice(){
        int record_audio_result = ContextCompat.checkSelfPermission(myCon,Manifest.permission.RECORD_AUDIO);
        return record_audio_result == PackageManager.PERMISSION_GRANTED;
    }



    public void pauseMicrophone(){

    }

    public void stopMicrophone(){
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

    }

    public void getAmplitude() {
        if (mRecorder != null)
            amplitudes.add((double) mRecorder.getMaxAmplitude());
        else
            amplitudes.add(0.00);

    }

    public int calculateRMSLevel(byte[] audioData)
    {
        long lSum = 0;
        for(int i=0; i < audioData.length; i++)
            lSum = lSum + audioData[i];

        double dAvg = lSum / audioData.length;
        double sumMeanSquare = 0d;

        for(int j=0; j < audioData.length; j++)
            sumMeanSquare += Math.pow(audioData[j] - dAvg, 2d);

        double averageMeanSquare = sumMeanSquare / audioData.length;

        return (int)(Math.pow(averageMeanSquare,0.5d) + 0.5);
    }
}
