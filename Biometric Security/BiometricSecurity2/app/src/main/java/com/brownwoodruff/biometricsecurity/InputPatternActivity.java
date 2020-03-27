/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/21/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This is the Activity that handles validating a pattern for the user to authenticate.
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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

//NOTE: This is mostly done. I don't think I know any way that you would need to modify this.
//Authentication: Let's just have these re-configure prompts authenticate the given prompt.
//e.g. new pattern authenticates previous pattern, new phrase authenticates previous phrase.
public class InputPatternActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pattern);

        SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        password = preferences.getString("password", "0");

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
                if(password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                    //Intent intent = new Intent(getApplicationContext(), PatternActivity.class);
                    //startActivity(intent);
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
