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

public class CreatePassphraseActivity extends AppCompatActivity {
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
        current = loadData() ;
        if (current.equals("")) {
            enterNewPassphrase();
        }


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

    private void enterNewPassphrase() {
        String text = "Please enter a new Passphrase:";
        textView.setText(text);
    }

    public boolean authenticate() {
        boolean authentication = false;
        String test = editText.getText().toString();
        if(test.equals(loadData())) {
            authentication = true;
        }
        return authentication;
    }
}
