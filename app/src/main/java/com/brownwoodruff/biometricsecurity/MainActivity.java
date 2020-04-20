/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/8/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This activity is the main activity for the Biometric_Security mobile app. This should
 * be in a standby state, unless a signal from a paired computer is prompting for authentication, to
 * which the user can then start the authentication process, or go to the toolbar on the upper-right
 * to open settings, about info, or send a report back to us.
 *
 * Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
 * and
 * Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
 *
 */
package com.brownwoodruff.biometricsecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH_FINGER = "switchFinger";
    public static final String SWITCH_PHRASE = "switchPhrase";
    public static final String SWITCH_FACE = "switchFace";
    public static final String SWITCH_PATTERN = "switchPattern";
    public static final String SWITCH_PIN = "switchPin";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

        //right now this is configured to happen when the button is pressed, but I figure we'll
        //need some sort of other "listener" like function with the bluetooth connection.
    public void authenticate(View view){
        //This gathers the shared Preferences for the settings page.
        //The boolean values for the authentication were removed since it was redundant to include
        // them when they were called only once.
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        //Instead of using 'finger' 'pin' etc. in the if statement (these strings were only called
        //right here, which adds an extra step. These if statements get the values straight from the
        //source.
        if (sharedPreferences.getBoolean(SWITCH_FINGER , false)) {
            Intent startFingerScan = new Intent(getApplicationContext(), FingerScanActivity.class);
            startActivity(startFingerScan);
        }
        if (sharedPreferences.getBoolean(SWITCH_PIN , false)) {
            Intent startPin = new Intent(getApplicationContext(), PinActivity.class);
            startActivity(startPin);
        }
        if (sharedPreferences.getBoolean(SWITCH_PHRASE , false)) {
            Intent startPassphrase = new Intent(getApplicationContext(), PassphraseActivity.class);
            startActivity(startPassphrase);
        }

        if (sharedPreferences.getBoolean(SWITCH_PATTERN , false)) {
            Intent startPattern = new Intent(getApplicationContext(), PatternActivity.class);
            startActivity(startPattern);
        }

        if (sharedPreferences.getBoolean(SWITCH_FACE , false)) {
           Intent startFace = new Intent(getApplicationContext(), FaceActivity.class);
            startActivity(startFace);

        }

    }


        //check if there are any saved configurations.
            //initial configurations
            //or load the first of up to 5 authentication prompts.
    //https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_about:
                //load about page.
                Intent startAbout = new Intent(getApplicationContext(), About_Page.class);
                startActivity(startAbout);
                return true;
            case R.id.menu_report:
                //Load report page.
                return true;
            case R.id.menu_settings:
                //Load settings pages.
                Intent startSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(startSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
