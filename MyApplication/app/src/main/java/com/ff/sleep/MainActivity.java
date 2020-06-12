package com.ff.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ff.sleep.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button b1,b2;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        b1 = findViewById(R.id.start);
        b2 = findViewById(R.id.mysleeps);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, inBackground.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MySleeps.class);
                startActivity(intent);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SleepsFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFrag = null;
            switch (menuItem.getItemId()){
                case R.id.aboutUs:
                    selectedFrag = new AboutSleepFragment();
                    break;
                case R.id.profile:
                    selectedFrag = new ProfileFragment();
                    break;
                case R.id.sleep:
                    selectedFrag = new SleepsFragment();
                    break;
                case R.id.settings:
                    selectedFrag = new SettingsFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,selectedFrag).commit();
            return true;
        }
    };
}
