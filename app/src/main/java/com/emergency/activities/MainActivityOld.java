package com.emergency.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

import com.emergency.adapter.NavDrawerListAdapter;
import com.emergency.business.DefaultSituationManager;
import com.emergency.business.SituationManager;
import com.emergency.entity.Situation;
import com.emergency.helpers.GPSTracker;
import com.emergency.model.NavDrawerItem;

import java.util.ArrayList;


public class MainActivityOld extends Activity {
    private SharedPreferences prefs = null;
    private GridLayout gridLayout = null;
    private Button[] buttons = null;
    private String[] menuItems;
    private SituationManager situationManager;
    private String titre;
    private GPSTracker gps;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    // nav drawer title
    private CharSequence drawerTitle;

    // user to store app title
    private CharSequence title;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private ListView drawerList;
    private Situation s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        situationManager = new DefaultSituationManager(this);

        prefs = getSharedPreferences("com.emergency.app", MODE_PRIVATE);

        //Tester est ce qu'il s'agit de premier lancement de l'app
        /*
        if (prefs.getBoolean("firstRun", true)) {
            Intent goToNextActivity = new Intent(getApplicationContext(), SituationActivity.class);
            startActivity(goToNextActivity);
        } else {*/
        title = drawerTitle = getTitle();

        //load slide menu Items
        navMenuTitles = getResources().getStringArray(R.array.main_menu);

        //load drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));

        navMenuIcons.recycle();

        drawerList.setOnItemClickListener(new SlideMenuClickListener());
        //setting the nav drawer list Adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        drawerList.setAdapter(adapter);
        //enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        if (savedInstanceState == null) {
            displayView(0);
        }

            /*
            gridLayout = new GridLayout(this);

            gridLayout.setOrientation(0);
            gridLayout.setColumnCount(3);
            gridLayout.setRowCount(12);

            int lSize = situationManager.getAll().size();

            buttons = new Button[lSize];

            for(int i =0; i < lSize; i++) {
                buttons[i] = new Button(this);
                buttons[i].setText(situationManager.getAll().get(i).getTitre());
                buttons[i].setTextSize(12);
                //buttons[i].setBackgroundColo(777777);
                gridLayout.addView(buttons[i]);
            }

            setContentView(gridLayout);

            for(int i =0; i < lSize; i++)
            {
                s = situationManager.getAll().get(i);
                titre = s.getTitre();
                buttons[i].setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), EtatAlerteActivity.class);
                        // create class object
                        gps = new GPSTracker(MainActivity.this);

                        // check if GPS enabled
                        if(gps.canGetLocation()){

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            // \n is for new line
                            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            intent.putExtra("Situation", s);
                            startActivity(intent);
                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }
                    }
                });
            }*/
        //setContentView(gridView);

        //}
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        /*
        switch (item.getItemId()) {
            case R.id.createSituation:
                Intent goToNextActivity = new Intent(getApplicationContext(), SituationActivity.class);
                startActivity(goToNextActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/

    }


    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        android.app.Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new FindPeopleFragment();
                break;
            case 2:
                fragment = new PhotosFragment();
                break;
            case 3:
                fragment = new ManageUserFragment();
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
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            drawerLayout.closeDrawer(drawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        title = title;
        getActionBar().setTitle(title);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstRun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstRun", false).commit();

        }
    }
}
