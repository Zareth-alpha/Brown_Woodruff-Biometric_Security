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
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private TextView textView;
    private EditText editText;
    private String current;
    private String newPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passphrase);

        editText = findViewById(R.id.editTextPassphrase);
        textView = findViewById(R.id.passActivityTitle);

        /*
        If there's nothing saved...
            create authentication phrase...
         */


        /*
        else authenticate
            then create authentication phrase...
         */
        Button submit = findViewById(R.id.buttonPhrase);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }


    //Save the text data entered into the editText line of the activity.
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, editText.getText().toString());

        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    //load the saved password. Not very secure.
    private String loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(TEXT, "");
    }

    //Change the textView based on whether the user is updating the password or
    //authenticating the password.
    private void updateViews() {
    }

    public Boolean authenticate() {
        Boolean authentication = false;
        String test = editText.getText().toString();
        if(test.equals(loadData())) {
            authentication = true;
        }
        return authentication;
    }
}
