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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CreatePinActivity extends AppCompatActivity{
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        /*
        If there's nothing saved...
            create authentication pin...
         */


        /*
        else authenticate
            then create authentication pin...
         */
        Button submit = findViewById(R.id.pinButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, textView.getText().toString());

        editor.apply();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }
}
