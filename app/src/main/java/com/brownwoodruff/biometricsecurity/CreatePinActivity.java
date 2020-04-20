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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//This is practically a repeat of the passphrase class, however, it's just numbers.
//While it's not very secure, lets not deal with input validation, unless we have more time.

public class CreatePinActivity extends AppCompatActivity{

    Button Submit;
    EditText PinInput;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        Submit = findViewById(R.id.pinButton);
        PinInput = findViewById(R.id.pinInput);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setPin = new Intent(CreatePinActivity.this, PinActivity.class);
                Intent mainPin = new Intent(CreatePinActivity.this, MainActivity.class);
                pin = PinInput.getText().toString();
                setPin.putExtra("pinValue", pin);
                mainPin.putExtra("pinValue", pin);
                startActivity(setPin);
                finish();

                }

            });
        }
    }