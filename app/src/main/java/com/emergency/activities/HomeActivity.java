package com.emergency.activities;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;


public class HomeActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	private static final String TWITTER_KEY = "UYvF257R5wmbv9CrHLahemBhM";
	private static final String TWITTER_SECRET = "99U8ruYV2ymBBxQMiQ3Uazecz9I5qilXcTHLpPi1UmvV5z6biw";

	private static final String MANAGE_USER_FRAGMENT = "manageUserFragment";
	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	Context context;

	Fragment manageUserFragment;
	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private DrawerLayout drawerLayout;

	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new TwitterCore(authConfig), new Digits(), new Crashlytics());

		//parse debut
		context = getApplicationContext();


		if (getFragmentManager().findFragmentByTag("manageUserFragment") == null) {
			manageUserFragment = new ManageUserFragment();
			manageUserFragment.setArguments(getIntent().getExtras());
		}
		else {
			manageUserFragment = (Fragment) getFragmentManager().findFragmentByTag("manageUserFragment");
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


	}

	@Override
	public void onNavigationDrawerItemSelected (int position) {
		// update the main content by replacing fragments
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
		switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = manageUserFragment;
				fragmentTag = MANAGE_USER_FRAGMENT;
				break;
			case 2:
				//        fragment = new WelcomeActivity();
				//       Bundle args = new Bundle();
				//      args.putString("regId", regId);
				//      fragment.setArguments(args);
				break;
			case 3:
				fragment = new GoogleMapActivity();
				break;
			case 4:
				fragment = new ManageSituation();
				break;
			case 5:
				fragment = new WhatsHotFragment();
				break;

			default:
				break;
		}

		if (fragment != null) {
			android.app.FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.container, fragment,fragmentTag).commit();

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
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.menu_main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onBackPressed () {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Closing Activity")
				.setMessage("Are you sure you want to close this activity?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick (DialogInterface dialog, int which) {
						finish();
					}

				})
				.setNegativeButton("No", null)
				.show();
	}

	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("saved", true);
	}
}





