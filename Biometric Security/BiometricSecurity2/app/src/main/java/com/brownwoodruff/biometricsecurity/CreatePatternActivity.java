/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/21/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This is the Activity that handles creating a pattern for the user to authenticate.
 *
 *
 * Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
 * and
 * Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
 * https://www.youtube.com/watch?v=BYYFelIdZsQ by Tihomir RAdeff
 * https://github.com/aritraroy/PatternLockView by Aritra Roy
 *
 */
package com.brownwoodruff.biometricsecurity;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class CreatePatternActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pattern);

        mPatternLockView = findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                SharedPreferences preferences = getSharedPreferences("PREPS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password", PatternLockUtils.patternToString(mPatternLockView, pattern));
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
