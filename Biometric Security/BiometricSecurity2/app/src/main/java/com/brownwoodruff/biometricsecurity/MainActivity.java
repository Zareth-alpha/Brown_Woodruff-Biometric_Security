package com.brownwoodruff.biometricsecurity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
                Intent startSettings = new Intent(getApplicationContext(), FirstConfig.class);
                startActivity(startSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
