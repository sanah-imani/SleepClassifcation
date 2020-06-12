package com.ff.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.ff.sleep.R;

public class AboutSleep extends AppCompatActivity implements View.OnClickListener {

    String[] urls = {"https://www.medicalnewstoday.com/articles/325353", "https://www.health.harvard.edu/press_releases/importance_of_sleep_and_health", "https://www.healthline.com/nutrition/ways-to-fall-asleep"};
    CardView a1, a2, a3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_sleep);

        a1 = findViewById(R.id.article1);
        a2 = findViewById(R.id.article2);
        a3 = findViewById(R.id.article3);

        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        Intent i;
        switch (v.getId()) {
            case R.id.article1:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[0]));
                startActivity(i);
                break;

            case R.id.article2:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[1]));
                startActivity(i);
                break;
            case R.id.article3:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[2]));
                startActivity(i);
                break;

            default:
                break;


        }

    }
}
