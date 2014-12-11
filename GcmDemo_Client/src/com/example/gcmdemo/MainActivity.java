package com.example.gcmdemo;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    String SENDER_ID = "179730785344";
    String regid;
    
    static final String TAG = "GCMDemo";
    SharedPreferences prefs;
    Context context;

    EditText mDisplay;
    AtomicInteger msgId = new AtomicInteger();
	
	GoogleCloudMessaging gcm;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDisplay = (EditText) findViewById(R.id.display);
		
        context = getApplicationContext();
        
        if (checkPlayServices()) {
        	
        	mDisplay.setText(" Play Services Available");
        	SystemClock.sleep(800);
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);
            
            if (regid.isEmpty()) {
            	mDisplay.setText("Registering plz. wait..");
                registerInBackground();  //Registering if Not Registered Before
            }
            else{
            	Log.i(TAG, "RegID : "+regid);  // Displaying RegID if already registered
            	mDisplay.setText("Already Registered ,  RegID = "+regid);
            }
            
        } else {
            Log.i(TAG, "No valid Google Play Services APK found..");
            mDisplay.setText(" Play Services Not Available");
        }

	}//onCreate
	
	private boolean checkPlayServices() {  // Checking Google Play Services exists in Device
		
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        }
	        else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	private void registerInBackground() {
		new regInBackground().execute();
	}
	
	class regInBackground extends AsyncTask<Void, Void, String>{  // Registering

		@Override
		protected String doInBackground(Void... params) {
			String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID = " + regid;
                storeRegistrationId(context, regid);
                
            } catch (IOException ex) {  msg = "Error :" + ex.getMessage();  }
            return msg;
		}
		
		@Override
		protected void onPostExecute(String msg) {
			super.onPostExecute(msg);
			mDisplay.setText(msg);
			Log.i(TAG,msg);
		}
	}

	private void storeRegistrationId(Context context, String regId) { // Storing RegID in Shared Preferences
		
	    final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),Context.MODE_PRIVATE);
	    int appVersion = getAppVersion(context);
	    
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	
	private String getRegistrationId(Context context) {
		
	    final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),Context.MODE_PRIVATE);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}

	
}
