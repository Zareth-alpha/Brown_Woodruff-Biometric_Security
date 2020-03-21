package com.brownwoodruff.biometricsecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

public class InputPatternActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pattern);

        SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        password = preferences.getString("password", "0");

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if(password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                    Intent intent = new Intent(getApplicationContext(), ProgramActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(InputPatternActivity.this, "Wrong pattern", Toast.LENGTH_SHORT).show();
                    mPatternLockView.clearPattern();
                }

            }

            @Override
            public void onCleared() {

            }
        });
    }
}
