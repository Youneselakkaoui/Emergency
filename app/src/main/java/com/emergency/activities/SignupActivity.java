package com.emergency.activities;

import android.app.ProgressDialog;
import android.os.Bundle;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.app.Fragment;
import android.util.Log;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.UserDTO;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;

import java.io.IOException;
import java.util.Calendar;

import io.fabric.sdk.android.Fabric;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class SignupActivity extends Activity /*implements OnTaskCompleted<ManageUserOut>*/ {

	private UserManager userManager;

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static final String TAG = "Emergency";
	GoogleCloudMessaging gcm;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	String SENDER_ID = "1028860605775";
	private String regId;
	Context context;
	private static final String TWITTER_KEY = "UYvF257R5wmbv9CrHLahemBhM";
	private static final String TWITTER_SECRET = "99U8ruYV2ymBBxQMiQ3Uazecz9I5qilXcTHLpPi1UmvV5z6biw";
	DigitsSession digitsSession;
	String phoneNr;
	// Progress Dialog Object
	ProgressDialog prgDialog;
	// Error Msg TextView Object
	TextView errorMsg;

	//DigitsClient client = digitsSession.
	public SignupActivity () {
		userManager = new UserManagerImpl();
		//

	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new TwitterCore(authConfig), new Digits(), new Crashlytics());
		setContentView(R.layout.activity_welcome);
		context = getApplicationContext();
		// Instantiate Progress Dialog object
		prgDialog = new ProgressDialog(this);
		// Set Progress Dialog Text
		prgDialog.setMessage("Please wait...");
		// Set Cancelable as False
		prgDialog.setCancelable(false);


		// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
		// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regId = getRegistrationId(context);

			if (regId.isEmpty()) {
				registerInBackground();
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}
		if (phoneNr == null || "".equals(phoneNr)) {
			Digits.getSessionManager().clearActiveSession();
		}

		DigitsAuthButton digitsButton =
				(DigitsAuthButton) findViewById(R.id.auth_button);
		digitsButton.setCallback(new AuthCallback() {


			@Override
			public void success (DigitsSession session,
			                     String phoneNumber) {
				//Log.i("com.example", String.valueOf(session.getId()));
				//Log.i("com.example", phoneNumber);
				digitsSession = session;
				if (phoneNumber != null && !"".equals(phoneNumber)) {
					phoneNr = phoneNumber;
				}
				saveUser(phoneNr, regId, session.getId());


			}

			@Override
			public void failure (DigitsException exception) {

				AlertDialog.Builder alert = new AlertDialog.Builder(SignupActivity.this);
				alert.setTitle("Oops...");
				alert.setMessage(getResources().getString(R.string.servercall_error));
				alert.setPositiveButton("OK", null);
				alert.show();
			}
		});

		// Find Error Msg Text View control by ID
		errorMsg = (TextView) findViewById(R.id.login_error);
		if (savedInstanceState != null && savedInstanceState.getBoolean("dialgShown", false)) {
			prgDialog.show();
		}


	}


	public void saveUser (String phoneNr, String regId, long digitsId) {
		// new AsyncWsCaller<ManageUserIn, ManageUserOut>(this, getUser(phoneNr, regId, digitsId), ManageUserOut.class,
		//        EmergencyConstants.MANAGE_USER_URL).execute();
		// Show Progress Dialog
		prgDialog.show();
		// Make RESTful webservice call using AsyncHttpClient object
		final AsyncHttpClient client = new AsyncHttpClient();
		ManageUserIn userIn = getUser(phoneNr, regId, digitsId);

		ObjectMapper mapper = new ObjectMapper();
		try {
			client.post(context, EmergencyConstants.MANAGE_USER_URL, new ByteArrayEntity(mapper.writeValueAsBytes(userIn)), "application/json",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess (int statusCode, Header[] headers, byte[] responseBody) {
							// Hide Progress Dialog
							prgDialog.hide();
							try {
								if (responseBody != null) {
									Log.i("com.emergency.emergency", responseBody.toString());
								}
								// JSON Object
								//final JSONObject obj = new JSONObject(new String(responseBody));
								// When the JSON response has status boolean value
								// assigned with true

								ObjectMapper mapper = new ObjectMapper();

								ManageUserOut out = mapper.readValue(responseBody,ManageUserOut.class);
								userManager.create(UserUtil.mapUserDtoToUser(out.getUserDTO(), (short) 1));
								if (out.getAnomalie() == null) {
									Toast.makeText(getApplicationContext(),
											"You are successfully logged in!",
											Toast.LENGTH_LONG).show();
									// Navigate to Home screen

									redirectToHomeActivity();
								}
								// Else display error message
								else {
									errorMsg.setText(out.getAnomalie().getLibelleAnomalie());
									Toast.makeText(getApplicationContext(),
											errorMsg.getText(),
											Toast.LENGTH_LONG).show();
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								Toast.makeText(
										getApplicationContext(),
										"Error Occured [Server's JSON response might be invalid]!",
										Toast.LENGTH_LONG).show();
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure (int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
							// Hide Progress Dialog
							prgDialog.hide();
							if (responseBody != null) {
								Log.i("com.emergency.emergency", responseBody.toString());
							}
							// When Http response code is '404'
							if (statusCode == 404) {
								Toast.makeText(getApplicationContext(),
										"Requested resource not found",
										Toast.LENGTH_LONG).show();
							}
							// When Http response code is '500'
							else if (statusCode == 500) {
								Toast.makeText(getApplicationContext(),
										"Something went wrong at server end",
										Toast.LENGTH_LONG).show();
							}
							// When Http response code other than 404, 500
							else {
								Toast.makeText(
										getApplicationContext(),
										"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
										Toast.LENGTH_LONG).show();
							}
						}

					});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			Toast.makeText(
					getApplicationContext(),
					"Error Occured [Server's JSON response might be invalid]!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

	}


	private void redirectToHomeActivity () {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	private ManageUserIn getUser (String phoneNr, String regId, long digitsId) {
		ManageUserIn manageUserIn = new ManageUserIn();
		manageUserIn.setCodeFonction((short) 1);
		manageUserIn.setUserDTO(new UserDTO());


		manageUserIn.getUserDTO().setDigitsId(digitsId);
		manageUserIn.getUserDTO().setTelephone(phoneNr);
		manageUserIn.getUserDTO().setGcmDeviceId((regId));
		Calendar cal = Calendar.getInstance();
		cal.set(2001,0,1);
		manageUserIn.getUserDTO().setDateNaissance(cal.getTime());
		return manageUserIn;
	}








	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("dialgShown", prgDialog.isShowing());

	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId   registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there is one.
	 * <p/>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 * registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p/>
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regId;

					// You should send the registration ID to your server over HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the device will send
					// upstream messages to a server that echo back the message using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.i(TAG, "msg : " + msg);
			}
		}.execute(null, null, null);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
	 * messages to your app. Not needed for this demo since the device sends upstream messages
	 * to a server that echoes back the message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
		Log.i(TAG, "reg id : " + regId);
	}

}

