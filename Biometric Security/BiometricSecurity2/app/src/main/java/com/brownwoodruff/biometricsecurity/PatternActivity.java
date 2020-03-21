/*
https://www.youtube.com/watch?v=BYYFelIdZsQ

 */
package com.brownwoodruff.biometricsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.brownwoodruff.biometricsecurity.CreatePatternActivity;
import com.brownwoodruff.biometricsecurity.R;

public class PatternActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("PREPS", 0);
                String password = preferences.getString("password", "0");
                if(password.equals("0")){
                    Intent intent = new Intent(getApplicationContext(), CreatePatternActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), InputPatternActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
