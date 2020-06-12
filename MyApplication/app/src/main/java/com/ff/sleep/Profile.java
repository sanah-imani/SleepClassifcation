package com.ff.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChartView;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.ff.sleep.R;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class Profile extends AppCompatActivity {
    String[] sleep_qual = {"Poor", "Average", "Good", "Excellent"};
    String[] sleep_time_pie = {"Met sleep time goal", "Less than sleep goal time"};
    double[] perc_qual;
    double[] perc_time;
    private List<String> profile_tokens;
    public static String USER_NAME;
    public static String USER_EMAIL_PHONE;
    public static String SLEEP_TARGET;

    AnyChartView chart1, chart2;
    BarChart barChart1, barChart2, barChart3, barChart4;
    float[] sleep_time_bar, light_bar, motion_bar, sound_bar;
    CheckBox check1;
    Button edit, sync;
    Boolean editing = false;
    TextInputEditText age, sleep_time, weight, height;
    TextView device_name;
    ImageView profile_pic;
    RadioButton rb1, rb2;
    CheckBox vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        chart1 = findViewById(R.id.sleep_qual);
        chart2 = findViewById(R.id.sleep_time_pie);
        barChart1 = findViewById(R.id.sleep_time_bar);
        barChart2 = findViewById(R.id.light_var_bar);
        barChart3 = findViewById(R.id.motion_var_bar);
        barChart4 = findViewById(R.id.sound_var_bar);
        check1 = findViewById(R.id.check1);
        edit = findViewById(R.id.editprofile);
        age = findViewById(R.id.age);
        sleep_time = findViewById(R.id.sleep_time);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        profile_pic = findViewById(R.id.profile);
        rb1 = findViewById(R.id.metric);
        rb2 = findViewById(R.id.imperial);
        device_name = findViewById(R.id.sync);
        vib = findViewById(R.id.check1);
        getUser();
        fillProfile();
        profile_pic.setEnabled(false);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        age.setEnabled(false);
        sleep_time.setEnabled(false);
        weight.setEnabled(false);
        height.setEnabled(false);
        vib.setEnabled(false);

        Pie pie1 = new myTrends(Profile.this).setUpPie(sleep_qual, perc_qual);
        chart1.setChart(pie1);
        pie1.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        if (pie1 == null){
            Toast.makeText(Profile.this, "No sleep quality pie chart could be generated", Toast.LENGTH_SHORT).show();
        }
        Pie pie2 = new myTrends(Profile.this).setUpPie(sleep_time_pie, perc_time);
        chart1.setChart(pie2);
        pie2.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        if (pie2 == null){
            Toast.makeText(Profile.this, "No sleep time goal chart could be generated", Toast.LENGTH_SHORT).show();
        }

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        barChart1 = new myTrends(Profile.this).setBarChart(days,sleep_time_bar, "Sleep time during this week", barChart1, "Week days");
        barChart2 = new myTrends(Profile.this).setBarChart(days,light_bar, "Sleep light variation during this week", barChart2, "Week days");
        barChart3 = new myTrends(Profile.this).setBarChart(days,motion_bar, "Sleep motion variation during this week", barChart3, "Week days");
        barChart4 = new myTrends(Profile.this).setBarChart(days, sound_bar, "Sleep sound variation during this week", barChart4, "Week days");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editing){
                    Boolean val = checkValidation();
                    if (val){
                        Toast.makeText(Profile.this, "Saved Successfully", Toast.LENGTH_LONG).show();
                        edit.setText("Edit");
                        editing = false;
                        profile_pic.setEnabled(false);
                        rb1.setEnabled(false);
                        rb2.setEnabled(false);
                        age.setEnabled(false);
                        sleep_time.setEnabled(false);
                        weight.setEnabled(false);
                        height.setEnabled(false);
                        vib.setEnabled(false);
                    }
                    else{
                        Toast.makeText(Profile.this, "Did not save", Toast.LENGTH_LONG).show();

                    }

                }
                else{
                    edit.setText("Save");
                    profile_pic.setEnabled(true);
                    rb1.setEnabled(true);
                    rb2.setEnabled(true);
                    editing = true;
                    age.setEnabled(true);
                    sleep_time.setEnabled(true);
                    weight.setEnabled(true);
                    height.setEnabled(true);
                    vib.setEnabled(true);

                }

            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb2.isChecked() && rb1.isChecked()){
                    rb2.setChecked(false);
                }

            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb1.isChecked() && rb2.isChecked()){
                    rb1.setChecked(false);
                }

            }
        });

    }

    public boolean checkValidation(){
        if(!TextUtils.isDigitsOnly(age.getText()) || !TextUtils.isDigitsOnly(sleep_time.getText()) || !TextUtils.isDigitsOnly(height.getText()) || !TextUtils.isDigitsOnly(weight.getText())){
            Toast.makeText(Profile.this, "Numeric values to be entered", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(Integer.parseInt(age.getText().toString()) <= 0 || Integer.parseInt(sleep_time.getText().toString()) <= 0 || Integer.parseInt(height.getText().toString()) <= 0 || Integer.parseInt(weight.getText().toString()) <= 0){
            Toast.makeText(Profile.this, "Negative values are not accepted", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!rb1.isChecked() && !rb2.isChecked()){
            Toast.makeText(Profile.this, "You need to select a measuring scale", Toast.LENGTH_LONG).show();
            return false;

        }
        else{

        }

        return true;
    }

    public void getUser(){

        List<String> userTokens = new readCSV(Profile.this, "User").userProfiles();
        if (userTokens != null){
            USER_NAME = userTokens.get(0);
            USER_EMAIL_PHONE = userTokens.get(1);
        }
    }

    public void fillProfile(){
        profile_tokens = new readCSV(Profile.this, "Profile").readProfiles(USER_NAME, USER_EMAIL_PHONE);
        age.setText(profile_tokens.get(3));
        device_name.setText("Frontier X " + profile_tokens.get(4));
        sleep_time.setText(profile_tokens.get(4));
        if(profile_tokens.get(5).equalsIgnoreCase("Imperial")){
            rb2.setChecked(true);

        }
        else if(profile_tokens.get(5).equalsIgnoreCase("Metric")){
            rb1.setChecked(true);
        }
        SLEEP_TARGET = profile_tokens.get(6);
        weight.setText(profile_tokens.get(7));
        height.setText(profile_tokens.get(8));
    }

}
