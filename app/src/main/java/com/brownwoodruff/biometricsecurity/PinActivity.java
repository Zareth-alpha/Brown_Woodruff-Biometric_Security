/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/8/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: Like the PassphraseActivity.java, this is going to ask for a pin from the user and
 * compare it to whatever the user has set it to before moving on in the authentication process.
 *
 *
 * Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
 * and
 * Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
 *
 */
package com.brownwoodruff.biometricsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Just like the passphrase, I split this into "Create" and just the activity itself.
//So this one needs to authenticate the pin.

public class PinActivity extends AppCompatActivity {

    int pinText;

    EditText pinInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pinInput = findViewById(R.id.pinInput);

        Button pinButton = findViewById(R.id.pinButton);
        pinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinText = Integer.valueOf(pinText);

                finish();
            }
        });
    }
}
