/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/27/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This activity prompts the user for a new passphrase.
 *
 * Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
 * and
 * Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
 *
 */
package com.brownwoodruff.biometricsecurity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Please note: I do not know why Android wanted to create different API versions. I've been trying
//to only modify the v26 layout, because that's what comes up in my virtual tests.

//Authentication: Let's just have these re-configure prompts authenticate the given prompt.
//e.g. new pattern authenticates previous pattern, new phrase authenticates previous phrase.
public class CreatePassphraseActivity extends AppCompatActivity {

    Button Submit;
    EditText PassInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passphrase);

        Submit = findViewById(R.id.passphraseButton);
        PassInput = findViewById(R.id.passphraseInput);
        final String SHARED_PREFS = "sharedPrefs";

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).edit();
                editor.putString("secretPass", PassInput.getText().toString());
                editor.apply();
                Toast.makeText(CreatePassphraseActivity.this, "Saved Passphrase", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
