/*AUTHORS: Jonathan Brown & Khalil Woodruff
* DATE: 3/8/2020
* PROJECT: Brown_Woodruff-Biometric_Security
* API: 29
* DESCRIPTION: This activity holds the configuration settings for the android-authentication-security
* application that a user can set for how paranoi- I mean - how secure they wish to make their
* authentication process from their mobile device.
*
*
* Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
* and
* Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
* Coding in Flow (https://codinginflow.com/tutorials/android/sharedpreferences)
*
 */
package com.brownwoodruff.biometricsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlertDialog;

public class SettingsActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH_LINUX = "switchLinux";
    public static final String SWITCH_KEE = "switchKee";
    public static final String SWITCH_FIRE = "switchFire";
    public static final String SWITCH_FINGER = "switchFinger";
    public static final String SWITCH_PHRASE = "switchPhrase";
    public static final String SWITCH_FACE = "switchFace";
    public static final String SWITCH_PATTERN = "switchPattern";
    public static final String SWITCH_PIN = "switchPin";

    private Switch keeSwitch;
    private Switch fireSwitch;
    private Switch linuxSwitch;
    private Switch fingerSwitch;
    private Switch phraseSwitch;
    private Switch faceSwitch;
    private Switch patternSwitch;
    private Switch pinSwitch;

    private Button pinButton2;
    private Button passPhraseButton;
    private Button patternButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //This button needs to be here to leech off the onCreate Bundle above
        pinButton2 = findViewById(R.id.pinButton);
        pinButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPinActivity();
            }
        });

        findViewById(R.id.passphraseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPassPhraseActivity();
            }
        });

        findViewById(R.id.patternButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatternActivity();
            }
        });


        // initiate view's
        //https://abhiandroid.com/ui/switch
        keeSwitch = findViewById(R.id.KeePassXCSwitch);
        fireSwitch = findViewById(R.id.FirefoxSwitch);
        linuxSwitch = findViewById(R.id.LinuxSwitch);
        fingerSwitch = findViewById(R.id.fingerSwitch);
        phraseSwitch = findViewById(R.id.passPhraseSwitch);
        faceSwitch = findViewById(R.id.faceSwitch);
        patternSwitch = findViewById(R.id.patternSwitch);
        pinSwitch = findViewById(R.id.pinSwitch);




        Button saveSettings = findViewById(R.id.saveSettings);
        //If KeePass is not checked, then Firefox needs to be unchecked.
        keeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(keeSwitch.isChecked())) {
                    fireSwitch.setChecked(false);
                }
                else if (keeSwitch.isChecked()) {
                    linuxSwitch.setChecked(true);
                }
            }
        });
        //If Firefox is checked, then KeePass needs to be checked.
        fireSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fireSwitch.isChecked()) {
                    keeSwitch.setChecked(true);
                    linuxSwitch.setChecked(true);
                }
            }
        });
        linuxSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (!(linuxSwitch.isChecked())) {
                    keeSwitch.setChecked(false);
                    fireSwitch.setChecked(false);
                }
            }
        });


        //alerts the user about invalid settings. One of the authentications needs to be met.
        saveSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!(fingerSwitch.isChecked()) && !(patternSwitch.isChecked()) && !(faceSwitch.isChecked()) && !(phraseSwitch.isChecked()) && !(pinSwitch.isChecked())) {
                    //insert alert dialogue
                    //https://www.tutorialspoint.com/android/android_alert_dialoges.htm
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                        alertDialogBuilder.setMessage("You need an Authentication Method!");
                                alertDialogBuilder.setPositiveButton("Choose method",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Toast.makeText(SettingsActivity.this,"Please choose an Authentication Method.",Toast.LENGTH_LONG).show();
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Cancel and discard settings",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(SettingsActivity.this, "settings were NOT saved", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                }

                else if (!(fingerSwitch.isChecked()) && !(patternSwitch.isChecked()) && !(faceSwitch.isChecked()) && !(phraseSwitch.isChecked()) && (pinSwitch.isChecked())) {
                    //insert alert dialogue
                    //https://www.tutorialspoint.com/android/android_alert_dialoges.htm
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                    alertDialogBuilder.setMessage("Pin is the weakest Authentication method. Are you sure?");
                    alertDialogBuilder.setPositiveButton("Select stronger method(s)",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Toast.makeText(SettingsActivity.this,"Please choose a stronger Method.",Toast.LENGTH_LONG).show();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("save anyway",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SettingsActivity.this, "Settings Saved", Toast.LENGTH_LONG).show();
                            /*Here the settings need to be saved!*/
                            saveData();
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else {
                    saveData();
                    Toast.makeText(SettingsActivity.this, "Settings Saved", Toast.LENGTH_LONG).show();
                    finish();
                }





            }
        });
        loadData();
    }


        //This function is very useful to show how the sharedPreferences class works. I think it's a
    //simple dictionary object. Hence the put___(Stringobject1, object2). I copied this format
    //for the passphrase and pin activities.
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.putString(TEXT, textView.getText().toString());
        editor.putBoolean(SWITCH_FACE, faceSwitch.isChecked());
        editor.putBoolean(SWITCH_FINGER, fingerSwitch.isChecked());
        editor.putBoolean(SWITCH_KEE, keeSwitch.isChecked());
        editor.putBoolean(SWITCH_FIRE, fireSwitch.isChecked());
        editor.putBoolean(SWITCH_PATTERN, patternSwitch.isChecked());
        editor.putBoolean(SWITCH_PIN, pinSwitch.isChecked());
        editor.putBoolean(SWITCH_PHRASE, phraseSwitch.isChecked());
        editor.putBoolean(SWITCH_LINUX, linuxSwitch.isChecked());

        editor.apply();
    }

    //You should use a function like this in the mainactivity to make sure that the correct
    //authentication methods are called per the user's preferences.
    //Is this a security risk? I don't know, I just want this functional.
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        faceSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_FACE, false));
        fingerSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_FINGER, false));
        keeSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_KEE, false));
        fireSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_FIRE, false));
        patternSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_PATTERN, false));
        pinSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_PIN, false));
        phraseSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_PHRASE, false));
        linuxSwitch.setChecked(sharedPreferences.getBoolean(SWITCH_LINUX, false));
    }

    public void openPinActivity() {
        Intent pin = new Intent(this, CreatePinActivity.class);
        startActivity(pin);


    }

    public void openPassPhraseActivity() {
        Intent passPhrase = new Intent(this, CreatePassphraseActivity.class);
        startActivity(passPhrase);

    }

    public void openPatternActivity() {
        Intent pattern = new Intent(this, CreatePatternActivity.class);
        pattern.putExtra ("ENABLE", "YES");
        startActivity(pattern);


    }
}

//In the XML for this file, I had to swap finger scan and face scan due to the fingerscan activity
//not working properly, yet the face scan activity is what prompts the thumbprint.
//What is happening is the face activity deals with biometrics in general with the ability to
//do fingerscan. Face scan altogether is difficult to test due to the emulators available to us
//not being new enough to test.