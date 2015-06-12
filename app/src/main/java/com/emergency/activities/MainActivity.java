package com.emergency.activities;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;

import com.emergency.business.AlerteRecueManager;
import com.emergency.business.UserManager;
import com.emergency.business.impl.AlerteRecueManagerImpl;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.entity.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.springframework.util.StringUtils;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	//GCM
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static final String TAG = "Emergency";
	GoogleCloudMessaging gcm;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private String regId;
	String SENDER_ID = "1028860605775";


	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	private static final String TWITTER_KEY = "UYvF257R5wmbv9CrHLahemBhM";
	private static final String TWITTER_SECRET = "99U8ruYV2ymBBxQMiQ3Uazecz9I5qilXcTHLpPi1UmvV5z6biw";

	private static final String MANAGE_USER_FRAGMENT = "manageUserFragment";
	public static final String HOME_FRAGMENT = "homeFragment";
	public static final String ALERT_LIST_FRAGMENT = "alertListFragment";
	public static final String INFO_ALERTE_FRAGMENT = "infoAlerteFragment";
	public static final String LISTE_SITUATION = "listeSituationFragment";
	public static final String ABOUT_FRAGMENT = "aboutFragment";
	private static String onTop;
	private boolean itemSelected;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	Context context;

	Fragment manageUserFragment;
	Fragment homeFragment;
	Fragment alertListFragment;
	Fragment infosAlerte;
	Fragment situationListFragment;
	Fragment aboutFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private DrawerLayout drawerLayout;

	//nbr alertes
	AlerteRecueManager alerteRecueManager = new AlerteRecueManagerImpl();
	UserManager userManager = new UserManagerImpl();

	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new TwitterCore(authConfig), new Digits(), new Crashlytics());
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		//parse debut
		context = getApplicationContext();


		if (getFragmentManager().findFragmentByTag(MANAGE_USER_FRAGMENT) == null) {
			manageUserFragment = new ManageUserFragment();
			manageUserFragment.setArguments(getIntent().getExtras());
		}
		else {
			manageUserFragment = getFragmentManager().findFragmentByTag(MANAGE_USER_FRAGMENT);
		}

		if (getFragmentManager().findFragmentByTag(HOME_FRAGMENT) == null) {
			homeFragment = new HomeFragment();
			homeFragment.setArguments(getIntent().getExtras());
		}
		else {
			homeFragment = getFragmentManager().findFragmentByTag(HOME_FRAGMENT);
		}

		if (getFragmentManager().findFragmentByTag(ALERT_LIST_FRAGMENT) == null) {
			alertListFragment = new AlerteListItemFragment();
			alertListFragment.setArguments(getIntent().getExtras());
		}
		else {
			alertListFragment = getFragmentManager().findFragmentByTag(ALERT_LIST_FRAGMENT);
		}
		if (getFragmentManager().findFragmentByTag(INFO_ALERTE_FRAGMENT) == null) {
			infosAlerte = new InfoAlerte();
			infosAlerte.setArguments(getIntent().getExtras());
		}
		else {
			infosAlerte = getFragmentManager().findFragmentByTag(INFO_ALERTE_FRAGMENT);
		}
		if (getFragmentManager().findFragmentByTag(LISTE_SITUATION) == null) {
			situationListFragment = new  SituationListFragment();
			situationListFragment.setArguments(getIntent().getExtras());
		}
		else {
			situationListFragment = getFragmentManager().findFragmentByTag(LISTE_SITUATION);
		}
		if (getFragmentManager().findFragmentByTag(ABOUT_FRAGMENT) == null) {
			aboutFragment = new WhatsHotFragment();
			aboutFragment.setArguments(getIntent().getExtras());
		}
		else {
			aboutFragment = getFragmentManager().findFragmentByTag(ABOUT_FRAGMENT);
		}

		setContentView(R.layout.activity_home);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
		ImageLoader.getInstance().init(config);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);

		if (savedInstanceState != null && INFO_ALERTE_FRAGMENT.equals(savedInstanceState.getString("onTop"))) {

			getFragmentManager().beginTransaction()
					.replace(R.id.container, infosAlerte, INFO_ALERTE_FRAGMENT).commit();
		}
		//	if (savedInstanceState != null && top != null)
		refreshAlertCounter();

		if (!StringUtils.hasText(userManager.getUser().getGcmDeviceId())) {
			registerInBackground();
		}

	}

	@Override
	public void onNavigationDrawerItemSelected (int position) {
		// update the main content by replacing fragments
		itemSelected = true;
		displayView(position);

	}

	public void onSectionAttached (int number) {
		switch (number) {
			case 1:
				mTitle = getString(R.string.suiviAlerte);
				break;
			case 2:
				mTitle = getString(R.string.persInfo);
				break;
			case 3:
				mTitle = getString(R.string.situation);
				break;
		}
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 */
	private void displayView (int position) {
		// update the main content by replacing fragments
		android.app.Fragment fragment = null;
		String fragmentTag = "";
		android.app.FragmentManager fragmentManager = getFragmentManager();
		if (INFO_ALERTE_FRAGMENT.equals(onTop) && !itemSelected) {
			fragment = infosAlerte;
			fragmentTag = INFO_ALERTE_FRAGMENT;
			itemSelected = false;
		}
		else

		{
			switch (position) {
				case 0:
					fragment = homeFragment;
					fragmentTag = HOME_FRAGMENT;
					break;
				case 1:
					//fragment = manageUserFragment;
					fragment = new ManageUserFragment();
					fragmentTag = MANAGE_USER_FRAGMENT;
					break;
				case 2:
					fragment = alertListFragment;
					fragmentTag = ALERT_LIST_FRAGMENT;
					break;
				case 3:
					//fragment = new InfoAlerte();
				//	break;
				//case 4:
					fragmentTag = LISTE_SITUATION;
					fragment = situationListFragment;
					break;
				case 4:
					fragmentTag = ABOUT_FRAGMENT;
					fragment = aboutFragment;
					break;

				default:
					break;
			}
		}

		if (fragment != null) {

			fragmentManager.beginTransaction()
					.replace(R.id.container, fragment, fragmentTag).commit();

			// update selected item and title, then close the drawer
			//mDrawerList.setItemChecked(position, true);
			//mDrawerList.setSelection(position);
			//setTitle(navMenuTitles[position]);
			//mDrawerLayout.closeDrawer(mDrawerList);
		}
		else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	public void restoreActionBar () {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
//		if (!mNavigationDrawerFragment.isDrawerOpen()) {
//			// Only show items in the action bar relevant to this screen
//			// if the drawer is not showing. Otherwise, let the drawer
//			// decide what to show in the action bar.
//			getMenuInflater().inflate(R.menu.menu_main, menu);
//			restoreActionBar();
//			return true;
//		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}

		return super.onOptionsItemSelected(item);
	}


	public void refreshAlertCounter () {
		mNavigationDrawerFragment.setAlertsNumber(alerteRecueManager.nbrNonLues());
	}

	@Override
	public void onBackPressed () {

		refreshAlertCounter ();
//		super.onBackPressed();
		Fragment f = getFragmentManager().findFragmentById(R.id.container);
		if (f instanceof ManageUserFragment || f instanceof AlerteListItemFragment || f instanceof SituationListFragment || f instanceof WhatsHotFragment) {
			getFragmentManager().beginTransaction().replace(R.id.container, homeFragment, HOME_FRAGMENT).commit();
			//findViewById(R.id.manage_user_frame).invalidate();
		}
		else if (f instanceof HomeFragment) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.stat_notify_error)
					.setTitle(getString(R.string.titre_confirmation_close))
					.setMessage(getString(R.string.texte_confirmation_close))
					.setPositiveButton(getString(R.string.confirmation_yes), new DialogInterface.OnClickListener() {
						@Override
						public void onClick (DialogInterface dialog, int which) {
							finish();
						}

					})
					.setNegativeButton(getString(R.string.confirmation_no), null)
					.show();
		}
		else if (f instanceof InfoAlerte) {
			getFragmentManager().beginTransaction().replace(R.id.container, alertListFragment, ALERT_LIST_FRAGMENT).commit();

		}
		else if (f instanceof SituationCreateFragment || f instanceof SituationCrudFragment) {
			getFragmentManager().beginTransaction().replace(R.id.container, situationListFragment, LISTE_SITUATION).commit();

		}


	}

	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("onTop", onTop);


	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices () {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else {
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
	private void storeRegistrationId (Context context, String regId) {
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
	private String getRegistrationId (Context context) {
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
	private void registerInBackground () {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground (Void... params) {
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
			protected void onPostExecute (String msg) {
				Log.i(TAG, "msg : " + msg);
			}
		}.execute(null, null, null);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion (Context context) {
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
	private SharedPreferences getGcmPreferences (Context context) {
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
	private void sendRegistrationIdToBackend () {
		if (StringUtils.hasText(regId))

		{
			User user = userManager.getUser();
			user.setGcmDeviceId(regId);
			userManager.edit(user);
		}
		Log.i(TAG, "reg id : " + regId);
	}
}





