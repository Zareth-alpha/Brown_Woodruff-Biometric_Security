/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/21/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This is a useless activity at this time.
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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//NOTE: I was following the tutorial, and changed it up a little bit. This is a useless activity,
//but I don't want to deal with cleanup yet.
public class ProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
    }
}
