package com.emergency.activities;


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
import com.emergency.entity.User;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.UserUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.fabric.sdk.android.Fabric;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class WelcomeActivity extends Activity {

	private UserManager userManager;

	Context context;

	public WelcomeActivity () {
		userManager = new UserManagerImpl();
		//

	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getApplicationContext();


		//TODO Bouchon User
		if (userManager.getUser() == null) {
			User user = new User();
			user.setTelephone("+000000000");
			user.setDateNaissance(new Date());
			user.save();
		}
		//TODO Fin bouchon user
		checkUserRegistered();

	}

	private void checkUserRegistered () {
		if (userManager.getUser() != null) {
			startActivity(new Intent(this, HomeActivity.class));
		}
		else {
			startActivity(new Intent(this, SignupActivity.class));
		}
		finish();
	}


}
