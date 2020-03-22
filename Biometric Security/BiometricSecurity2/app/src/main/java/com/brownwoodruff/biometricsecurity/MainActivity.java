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
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        }

    public void authenticate(View view){
        //check settings for which authentication methods are required.
        boolean face = false;
        boolean finger = false;
        boolean pattern = false;
        boolean pin = false;
        boolean passphrase = false;

        if (!finger) {
            Intent startFingerScan = new Intent(getApplicationContext(), FingerScanActivity.class);
            startActivity(startFingerScan);
        }
        if (!pin) {
            Intent startPin = new Intent(getApplicationContext(), PinActivity.class);
            startActivity(startPin);
        }
        if (!passphrase) {
            Intent startPassphrase = new Intent(getApplicationContext(), PassphraseActivity.class);
            startActivity(startPassphrase);
        }
        /*
        if (!pattern) {
            Intent startPattern = new Intent(getApplicationContext(), PatternActivity.class);
            startActivity(startPattern);
        }
        if (!face) {
            Intent startFace = new Intent(getApplicationContext(), FaceActivity.class);
            startActivity(startFace);
        }
         */
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
