/*AUTHORS: Jonathan Brown & Khalil Woodruff
 * DATE: 3/27/2020
 * PROJECT: Brown_Woodruff-Biometric_Security
 * API: 29
 * DESCRIPTION: This activity prompts the user for a new passphrase.
 *
 * Intellectual contributions are from: Android Studio, Google, developer.android.com, Arizona State University,
 * and
 * Antinaa Murthy (https://proandroiddev.com/5-steps-to-implement-biometric-authentication-in-android-dbeb825aeee8)
 *
 */
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

//Please note: I do not know why Android wanted to create different API versions. I've been trying
//to only modify the v26 layout, because that's what comes up in my virtual tests.

//Authentication: Let's just have these re-configure prompts authenticate the given prompt.
//e.g. new pattern authenticates previous pattern, new phrase authenticates previous phrase.
public class CreatePassphraseActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private TextView textView;  //Modify the textView on the layout so that it says "enter the new passphrase."
                                //or "Enter your passphrase" depending on if the user is authenticating
                                //or giving a new passphrase.
    private EditText editText;  //this is where you grab user input.
    private String current; //Need to authenticate compared to the current password. This variable
                            //is to hold just that.
    private String newPhrase;   //This will hold the string for the new passphrase the user is inputing.
                                //then this will need to be saved using the function.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passphrase);

        //Setting the two to the layout's objects.
        editText = findViewById(R.id.editTextPassphrase);
        textView = findViewById(R.id.passActivityTitle);

        /*
        This will have to be moved into the OnClickListener below.

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
        //grab the button from the layout
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

    //This function was going to be used whether the user needs to authenticate or not.
    private void enterNewPassphrase() {
        //Android studio gave me an error that .setText could not take string literals... ok then.
        String text = "Please enter a new Passphrase:";
        textView.setText(text);

        //grab user input from editText
        //save the input.
    }

    //I'm just storing these in plaintext. I know it's not secure, but it'll get this app functional
    //and we can get back to encryption later.
    public boolean authenticate() {
        boolean authentication = false;
        String test = editText.getText().toString();
        if(test.equals(loadData())) {
            authentication = true;
        }
        return authentication;
    }
}
