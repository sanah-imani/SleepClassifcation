package com.ff.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ff.sleep.R;

import java.util.ArrayList;
import java.util.List;

public class MySleeps extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] TT, DT, ID;
    List<List<String>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sleeps);
        recyclerView = findViewById(R.id.recview1);

        getSleepsData(Profile.USER_EMAIL_PHONE);

        mySleepsAdapter sleepsAdapter = new mySleepsAdapter(MySleeps.this, TT, DT, ID);
        recyclerView.setAdapter(sleepsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MySleeps.this));
    }

    private void getSleepsData(String email_phone){
        mData = new ArrayList<>();
        List<String> IDs = new readCSV(MySleeps.this, "User").userProfiles();
        ////


    }
}
