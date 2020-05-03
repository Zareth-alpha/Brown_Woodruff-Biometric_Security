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
 * CodingWithMitch: https://www.youtube.com/watch?v=sifzY2SA1XU
 */
package com.brownwoodruff.biometricsecurity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Switch;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH_FINGER = "switchFinger";
    public static final String SWITCH_PHRASE = "switchPhrase";
    public static final String SWITCH_FACE = "switchFace";
    public static final String SWITCH_PATTERN = "switchPattern";
    public static final String SWITCH_PIN = "switchPin";
    public static final String TAG ="MainActivity";

    BluetoothService mBluetoothService;
    private static final UUID APP_UUID = UUID.fromString("ce4a0e9f-cd18-4d88-8db6-84bb9781e95d");
    private static final UUID SERVER_UUID = UUID.fromString("a2087a8e-7dd3-11ea-bc55-0242ac130003");
    private static final String PC_MAC = "A0:C5:89:74:12:2D";
    BluetoothDevice mBTDevice;
    BluetoothAdapter mBluetoothAdapter;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBTDevices = new ArrayList<>();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        enableBT();
        mBTDevice = mBluetoothAdapter.getRemoteDevice(PC_MAC);
        mBluetoothService = new BluetoothService(this);
        mBTDevice.createBond();
        if(mBTDevice != null) {
            startBTConnection(mBTDevice, SERVER_UUID);
        }
        String message = "lock";
        try {
            bluetoothSend(message);
        } catch (IOException e) {
            Log.e(TAG, "onCreate: bluetoothSend(message)");
        }
    }

    // Connection will fail and app will crash if you have not paired first!
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOMM Bluetooth Connection.");

        mBluetoothService.startClient(device, uuid);
    }

    //Yes this is a repeat of the above. I wonder why he did a wrapper, and want to see if this
    //works - JB 4/30
    public void startConnection(){
        Log.d(TAG, "startBTConnection: Initializing RFCOMM Bluetooth Connection.");

        mBluetoothService.startClient(mBTDevice, SERVER_UUID);
    }

    //This method will take a String, convert it to bytes, then send it to a paired bluetooth device
    //that is connected with a BluetoothService object.
    public void bluetoothSend(String message) throws IOException {
        Log.d(TAG, "bluetoothSend: starting method.");

        byte[] bytes = message.getBytes(Charset.defaultCharset());
        mBluetoothService.write(bytes);
    }

    //This Method will Enable bluetooth if it is turned off.
    public void enableBT(){
        if(mBluetoothAdapter == null) {
            Log.d(TAG, "enableBT: Does not have BT capabilities.");
        }
        if(!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if(mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableBT: Already on.");
        }
    }

    public void discover(){
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Restarting discovery.");

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){
            Log.d(TAG, "btnDiscover: Starting discovery.");

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    //A BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery finds a device.
            if(action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /*
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     *
     * Jonathan: I included this for future purposes, or just in case it is needed for sending data.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };

    /*
    * Broadcast Receiver for listing devices that are not yet paired. Executed by discover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND");

            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.activity_device_list_adapter, mBTDevices);
            }
        }
    };

    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
                permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
                if (permissionCheck != 0) {
                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
                }
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
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
