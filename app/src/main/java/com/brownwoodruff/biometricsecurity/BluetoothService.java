/*
Authors: Jonathan Brown & Khalil Woodruff
Date: 4/29/2020
Description:
Breaking up the bluetooth tasks into another class is allowing for module-like testing.

This should be the class that sets up connection with another bluetooth device. It will also send
and read data from this same bluetooth point.

Intellectual ideas come from:
https://developer.android.com/guide/topics/connectivity/bluetooth
https://stackoverflow.com/questions/22899475/android-sample-bluetooth-code-to-send-a-simple-string-via-bluetooth
https://github.com/googlearchive/android-BluetoothChat
Mitch Tabian: https://www.youtube.com/watch?v=Fz_GT7VGGaQ
 */
package com.brownwoodruff.biometricsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothService extends AppCompatActivity {

    private static final String TAG = "BluetoothService";
    private static final String APP_NAME = "BiometricSecurity";
    private static final UUID APP_UUID = UUID.fromString("ce4a0e9f-cd18-4d88-8db6-84bb9781e95d");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    ConnectedThread mConnectedThread;

    public BluetoothService(Context context) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = context;
        start();
    }

    // This is following the tutorial. Future work should "listen" as the server-side, but for the
    // sake of speed, we are not using this.
    private class AcceptThread extends Thread {
        // local server socket.
        private final BluetoothServerSocket mServerSocket;
        public AcceptThread() {
             // Create a new listening server socket.
             BluetoothServerSocket tmp = null;
             try {
                 tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME,
                         APP_UUID);
             } catch (IOException e) {
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
             }

             mServerSocket = tmp;
        }
        public void run() {
            Log.d(TAG, "run: AcceptThread Running.");

            BluetoothSocket socket = null;

            try {
                // This is a blocking call and will only return on a successful connection or an
                // exception.
                Log.d(TAG, "run: RFCOM server socket start.....");

                socket = mServerSocket.accept();

                Log.d(TAG, "run: RFCOM server socket accepted connection.");
            }catch (IOException e) {
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
            }

            //Third video.
            if(socket != null){
                connected(socket,mDevice);
            }

            Log.d(TAG, "END mAcceptThread.");
        }

        public void cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try {
                mServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed."
                        + e.getMessage() );
            }
        }

    }

    // Grabs the socket from AcceptThread.run.
    private class ConnectThread extends Thread {
        private BluetoothSocket mSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mDevice = device;
            deviceUUID = uuid;
        }

        public void run(){
            BluetoothSocket tmp = null;
            //Cannot tell the Log.?, so I used d - JB 4/30
            Log.d(TAG, "RUN mConnectThread");

            // Get a BluetoothSocket for a connection with the given BluetoothDevice.
            try {
                Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                        +APP_UUID);
                tmp = mDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket "
                        + e.getMessage());
            }

            mSocket = tmp;
            if(mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }

            try {
                // This blocking call will only return a successful connection or an exception.
                mSocket.connect();

                Log.d(TAG, "Run: ConnectThread connected.");
            } catch (IOException e) {
                try {
                    mSocket.close();
                    Log.d(TAG, "run: Closed Socket.");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectThread: run: Unable to close connection in socket "
                        + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + APP_UUID);
            }

            //Third Video.
            connected(mSocket, mDevice);
        }

        public void cancel() {
            try {
                Log.d(TAG, "cancel: Closing Client Socket.");
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mSocket in ConnectThread failed. "
                        + e.getMessage());
            }
        }

    }

    //This method is going to initiate the AcceptThread. Listening for a connection.
    public synchronized void start() {
        Log.d(TAG, "start");

        //cancel any thread attempting to make a connection.
        if(mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    //This method is going to initiate the ConnectThread.
        /*
        AcceptThread starts and sits waiting for a connection.
        Then ConnectThread starts and attempts to make a connection with the other device's
        AcceptThread.
         */
    public void startClient (BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startClient: Started.");

        /*

        //initprogress dialog, lets user know it's establishing a connection.
        mProgressDialog = ProgressDialog.show(mContext, "Connecting Bluetooth",
                "Please Wait...", true);
                */
        mConnectThread = new ConnectThread(device, uuid);
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mSocket;
        private final InputStream mInStream;
        private final OutputStream mOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");

            mSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
/*
            // the progressdialog box when connection is established.
            try {
                mProgressDialog.dismiss();
            } catch (NullPointerException e) {
                Log.e(TAG, "ConnectedThread: Constructor, nullpointer exception when" +
                        " dismissing ProgressDialog.");
            }

 */

            try {
                tmpIn = mSocket.getInputStream();
                tmpOut = mSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();;
            }

            mInStream = tmpIn;
            mOutStream = tmpOut;
        }

        public void run(){
            //get the input from the input stream.
            byte[] buffer = new byte[1024]; // buffer store from the stream.
            //Int object to read bytes from the input stream.
            int bytes; // bytes returned from read();

            // listen to input stream constantly.
            // Jonathan: I think while (true) is sloppy programming, but again, I'm following a
            // tutorial for speed's sake. This will be readdressed in the future. - JB 4/30
            while (true) {
                //Read from InputStream.
                try {
                    bytes = mInStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.d(TAG, "InputStream: " + incomingMessage);
                } catch (IOException e) {
                    Log.e(TAG, "Write: Error reading input." + e.getMessage());
                    break;
                }

            }
        }

        //call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) throws IOException {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + text);
            mOutStream.write(bytes);
        }

        // call this from the main activity to shutdown the connection.
        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mSocket in ConnectedThread failed. "
                        + e.getMessage());
            }
        }

    }

    private void connected(BluetoothSocket mSocket, BluetoothDevice mDevice) {
        Log.d(TAG, "connected: Starting.");

        // Start the thread to manage the connection and perform transmissions.
        mConnectedThread = new ConnectedThread(mSocket);
        mConnectedThread.start();
    }

    // MainActivity cannot access ConnectedThread.write(), so this method is a wrapper.
    public void write(byte[] out) throws IOException {
        // Create temporary object.
        ConnectedThread r;

        // Synchronize a copy of the ConnectedThread.
        Log.d(TAG, "write: Write Called.");
        //Perform the write.
        mConnectedThread.write(out);
    }
}
