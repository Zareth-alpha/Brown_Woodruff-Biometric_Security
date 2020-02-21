package com.brownwoodruff.biometricsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlertDialog;

public class FirstConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_config);

        /*read from a saved file if there is one!*/

        // initiate view's
        //https://abhiandroid.com/ui/switch
        final Switch keeSwitch = findViewById(R.id.KeePassXCSwitch);
        final Switch fireSwitch = findViewById(R.id.FirefoxSwitch);
        final Switch linuxSwitch = findViewById(R.id.LinuxSwitch);
        final Switch fingerSwitch = findViewById(R.id.fingerSwitch);
        final Switch phraseSwitch = findViewById(R.id.passPhraseSwitch);
        final Switch faceSwitch = findViewById(R.id.faceSwitch);
        final Switch patternSwitch = findViewById(R.id.patternSwitch);
        final Switch pinSwitch = findViewById(R.id.pinSwitch);
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FirstConfig.this);
                        alertDialogBuilder.setMessage("You need an Authentication Method!");
                                alertDialogBuilder.setPositiveButton("Choose method",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Toast.makeText(FirstConfig.this,"Please choose an Authentication Method.",Toast.LENGTH_LONG).show();
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Cancel and discard settings",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(FirstConfig.this, "settings were NOT saved", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                }

                if (!(fingerSwitch.isChecked()) && !(patternSwitch.isChecked()) && !(faceSwitch.isChecked()) && !(phraseSwitch.isChecked()) && (pinSwitch.isChecked())) {
                    //insert alert dialogue
                    //https://www.tutorialspoint.com/android/android_alert_dialoges.htm
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FirstConfig.this);
                    alertDialogBuilder.setMessage("Pin is the weakest Authentication method. Are you sure?");
                    alertDialogBuilder.setPositiveButton("Select stronger method(s)",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Toast.makeText(FirstConfig.this,"Please choose a stronger Method.",Toast.LENGTH_LONG).show();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("save anyway",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(FirstConfig.this, "Settings Saved", Toast.LENGTH_LONG).show();
                            /*Here the settings need to be saved!*/
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }
}
