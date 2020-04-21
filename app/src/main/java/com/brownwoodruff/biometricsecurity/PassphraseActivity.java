/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/8/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This activity prompts the user for a passphrase. This will be compared to the saved
 * passphrase the user chooses.
 *
 * Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
 * and
 * Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
 *
 */
package com.brownwoodruff.biometricsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*NOTE: There are some repeat functions because I thought I could consolidate the functions from
CreatePassphraseActivity.java into this one class. I don't remember why, but I came to the conclusion
that I could not with the knowledge I currently hold of Java/Android programming.

copy, delete, or whatever.

This really is just to authenticate and tell the mainactivity that it's good.
 */

//Authentication: Let's just have these re-configure prompts authenticate the given prompt.
//e.g. new pattern authenticates previous pattern, new phrase authenticates previous phrase.

public class PassphraseActivity extends AppCompatActivity {

    Button passphraseYourButton;
    EditText passphraseYourInput;
    String passToAuthenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_passphrase);

        passphraseYourButton = findViewById(R.id.passphraseYourButton);
        passphraseYourInput = findViewById(R.id.passphraseYourInput);

        passphraseYourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                passToAuthenticate = passphraseYourInput.getText().toString();
                if (passToAuthenticate.equals(sharedPreferences.getString("secretPass", ""))){
                    Toast.makeText(PassphraseActivity.this, "Passphrase Entered Correctly", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PassphraseActivity.this, "Passphrase Incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }




    }









