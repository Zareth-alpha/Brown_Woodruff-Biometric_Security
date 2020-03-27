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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.brownwoodruff.biometricsecurity.CreatePatternActivity;
import com.brownwoodruff.biometricsecurity.InputPatternActivity;
import com.brownwoodruff.biometricsecurity.R;

public class PatternActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        String password = preferences.getString("password", "0");
        if (!(password.equals("0"))) {
            /*
            Intent intent = new Intent(getApplicationContext(), CreatePatternActivity.class);
            startActivity(intent);
            finish();
             */
            Intent intent = new Intent(getApplicationContext(), InputPatternActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "No pattern saved.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
