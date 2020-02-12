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

        // initiate view's
        //https://abhiandroid.com/ui/switch
        final Switch keeSwitch = findViewById(R.id.KeePassXCSwitch);
        final Switch fireSwitch = findViewById(R.id.FirefoxSwitch);
        Button saveSettings = findViewById(R.id.saveSettings);
        //If KeePass is not checked, then Firefox needs to be unchecked.
        keeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(keeSwitch.isChecked())) {
                    fireSwitch.setChecked(false);
                }
            }
        });
        //If Firefox is checked, then KeePass needs to be checked.
        fireSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fireSwitch.isChecked()) {
                    keeSwitch.setChecked(true);
                }
            }
        });
        //alerts the user about invalid settings.
        saveSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!(keeSwitch.isChecked()) && !(((Switch) findViewById(R.id.KeePassXCSwitch)).isChecked())) {
                    //insert alert dialogue
                    //https://www.tutorialspoint.com/android/android_alert_dialoges.htm
                    //WORK IN PROGRESS!
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FirstConfig.this);
                        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Toast.makeText(FirstConfig.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                            }
                                        });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
