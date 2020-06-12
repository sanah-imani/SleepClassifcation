package com.ff.sleep;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class readCSV {

    private String myPath;
    private List<String> tokens;
    List<List<String>> mdata;
    private Context myCon;

    public readCSV(Context c1, String label){
        myPath = label;
        myCon = c1;
    }

    public List<List<String>> readRecord(){
        InputStream is = null;
        mdata = new ArrayList<>();

        if(myPath.equalsIgnoreCase("My Sleeps")){
            is = myCon.getResources().openRawResource(R.raw.sleep_recordings);

        }
        else {
            Toast.makeText(myCon, "Wrong file accessed", Toast.LENGTH_LONG).show();

        }

        if (is != null){
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";

            try {
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if (count > 0){

                        String[] tokens = line.split((",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                        for (int i = 0; i < tokens.length; i++) {
                            tokens[i] = tokens[i].replaceAll("\"", "");
                        }

                        List<String> singRec = new ArrayList<>();

                        for (int i = 0; i < (tokens.length); i++){
                            singRec.add(i,tokens[i]);

                        }
                        mdata.add(singRec);

                    }

                    count++;

                }
            } catch (IOException e) {
                Log.wtf("MyActivity", "Error reading data file on line" + line, e);
                e.printStackTrace();
            }

        }

        return mdata;
    }

    public List<String> readProfiles(String username, String email_phone){
        InputStream is = null;

        if(myPath.equalsIgnoreCase("Profile")){
            is = myCon.getResources().openRawResource(R.raw.Profile);

        }
        else {
            Toast.makeText(myCon, "Wrong file accessed", Toast.LENGTH_LONG).show();

        }

        if (is != null){
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";
            tokens = new ArrayList<>();

            try {
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if(count>0){

                        String[] alltokens = line.split((",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                        for (int i = 0; i < alltokens.length; i++) {
                            alltokens[i] = alltokens[i].replaceAll("\"", "");
                        }
                        if(alltokens[0].equals(username) && alltokens[1].equals(email_phone)){
                            for (int i = 0; i < (alltokens.length); i++){
                                tokens.add(i,alltokens[i]);
                            }

                        }

                    }
                    count++;


                }
            } catch (IOException e) {
                Log.wtf("MyActivity", "Error reading data file on line" + line, e);
                e.printStackTrace();
            }

        }
        return tokens;
    }

    public List<String> userProfiles(){
        tokens.clear();
        InputStream is = null;

        if(myPath.equalsIgnoreCase("User")){
            is = myCon.getResources().openRawResource(R.raw.User);

        }
        else {
            Toast.makeText(myCon, "Wrong file accessed", Toast.LENGTH_LONG).show();

        }

        if (is != null){
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";
            tokens = new ArrayList<>();

            try {
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if (count > 0){
                        String[] alltokens = line.split((",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                        for (int i = 0; i < alltokens.length; i++) {
                            alltokens[i] = alltokens[i].replaceAll("\"", "");
                        }
                        for (String token: alltokens){

                            tokens.add(token);

                        }
                    }
                    count++;
                }
            } catch (IOException e) {
                Log.wtf("MyActivity", "Error reading data file on line" + line, e);
                e.printStackTrace();
            }

        }
        return tokens;

    }


    public SleepRecording readSingleRecord(String SleepID){
        InputStream is = null;
        SleepRecording sr = null;

        if(myPath.equalsIgnoreCase("My Sleeps")){
            is = myCon.getResources().openRawResource(R.raw.sleep_recordings);

        }
        else {
            Toast.makeText(myCon, "Wrong file accessed", Toast.LENGTH_LONG).show();

        }

        if (is != null){
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = "";
            List<List<Float>> recSensor = new ArrayList();
            List<List<Double>> recSound = new ArrayList();
            List<String> packNames = new ArrayList<>();
            List<String> timeUse = new ArrayList<>();
            String[] tokens = null;

            try {
                int count  = 0;
                while ((line = br.readLine()) != null) {
                    if (count > 0){

                        tokens = line.split((",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                        for (int i = 0; i < tokens.length; i++) {
                            tokens[i] = tokens[i].replaceAll("\"", "");
                        }

                        for (int i = 15; i < tokens.length; i++) {

                            if (i == 19 || i == 24) {
                                List<Double> sound = new ArrayList<>();
                                String[] sub = tokens[i].split("\\s+");
                                for (String s : sub) {
                                    sound.add(Double.parseDouble(s));
                                }
                                recSound.add(sound);

                            }

                            else if(i == tokens.length-2){
                                String[] sub = tokens[i].split("\\s+");
                                for (String s : sub) {
                                    packNames.add(s);
                                }
                            }

                            else if(i == tokens.length-1){
                                String[] sub = tokens[i].split("\\s+");
                                for (String s : sub) {
                                    timeUse.add(s);
                                }
                            }

                            else {

                                List<Float> sensors = new ArrayList<>();
                                String[] sub = tokens[i].split("\\s+");
                                for (String s : sub) {
                                    sensors.add(Float.parseFloat(s));
                                }
                                recSensor.add(sensors);
                            }



                        }

                    }

                }//set error checking
                sr = new SleepRecording(tokens[0],tokens[1],tokens[2],tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9], tokens[10], tokens[11], tokens[12], tokens[13], tokens[14], recSensor.get(0),
                        recSensor.get(1), recSensor.get(2),recSensor.get(3),recSound.get(0),recSensor.get(4),recSensor.get(5),recSensor.get(6),recSensor.get(7), recSound.get(1),packNames,timeUse);
            } catch (IOException e) {
                Log.wtf("MyActivity", "Error reading data file on line" + line, e);
                e.printStackTrace();
            }

        }

        return sr;
    }

}
