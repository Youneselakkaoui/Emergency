package com.emergency.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.PersistableBundle;
import android.util.Log;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.emergency.business.AsyncWsCaller;
import com.emergency.business.OnTaskCompleted;
import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.UserDTO;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.JsonUtils;
import com.emergency.util.UserUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private TaskFragment mTaskFragment;
	private boolean prDialogShown;

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

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);
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

		ByteArrayEntity entity = null;
		try {
			entity = new ByteArrayEntity(JsonUtils.toJson(userIn).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		client.post(context, EmergencyConstants.MANAGE_USER_URL, entity, "application/json",
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
							final JSONObject obj = new JSONObject(new String(responseBody));
							// When the JSON response has status boolean value
							// assigned with true
							Gson gson = new Gson();
							ManageUserOut out = gson.fromJson(new String(responseBody), ManageUserOut.class);
							if (out.getAnomalie() == null) {
								Toast.makeText(getApplicationContext(),
										"You are successfully logged in!",
										Toast.LENGTH_LONG).show();
								// Navigate to Home screen
								userManager.create(UserUtil.mapUserDtoToUser(out.getUserDTO(), (short) 1));
								redirectToHomeActivity();
							}
							// Else display error message
							else {
								errorMsg.setText(out.getAnomalie().getLibelleAnomalie());
								Toast.makeText(getApplicationContext(),
										errorMsg.getText(),
										Toast.LENGTH_LONG).show();
							}
						} catch (final JSONException e) {
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

	}


	private void redirectToHomeActivity () {
		startActivity(new Intent(this, HomeActivity.class));
		finish();
	}

	private ManageUserIn getUser (String phoneNr, String regId, long digitsId) {
		ManageUserIn manageUserIn = new ManageUserIn();
		manageUserIn.setCodeFonction((short) 1);
		manageUserIn.setUserDTO(new UserDTO());


		manageUserIn.getUserDTO().setDigitsId(digitsId);
		manageUserIn.getUserDTO().setTelephone(phoneNr);
		manageUserIn.getUserDTO().setGcmDeviceId((regId));
		return manageUserIn;
	}


	/**
	 * Gets the current registration ID for application on GCM service, if there is one.
	 * <p/>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 * registration ID.
	 */
	private String getRegistrationId (Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}

		return registrationId;
	}


	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences (Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(HomeActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
	 * messages to your app. Not needed for this demo since the device sends upstream messages
	 * to a server that echoes back the message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend () {
		Log.i(TAG, "reg id : " + regId);
	}

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        if (error)
//            outState.putString("ERROR", "ERROR");
//    }


	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("dialgShown", prgDialog.isShowing());

	}
}

