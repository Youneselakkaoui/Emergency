package com.emergency.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.emergency.adapter.SampleAdapter;
import com.emergency.business.DefaultSituationManager;
import com.emergency.business.SituationManager;
import com.emergency.entity.Situation;
import com.emergency.helpers.GPSTracker;
import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener,AbsListView.OnItemClickListener {

    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    private SharedPreferences prefs = null;
    private StaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    private SampleAdapter mAdapter;
    private SituationManager situationManager;
    private String titre;
    private ArrayList<Situation> mData;
    private GPSTracker gps;

	public HomeFragment(){

    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        situationManager = new DefaultSituationManager(getActivity());

        prefs = getActivity().getSharedPreferences("com.emergency.app", Context.MODE_PRIVATE);

        //Tester est ce qu'il s'agit de premier lancement de l'app

        if (prefs.getBoolean("firstRun", true)) {
            Intent goToNextActivity = new Intent(getActivity().getApplicationContext(), SituationActivity.class);
            startActivity(goToNextActivity);
        } else {

            mGridView = (StaggeredGridView) rootView.findViewById(R.id.grid_view);

            mAdapter = new SampleAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    (ArrayList<Situation>) situationManager.getAll());

            if (mData == null) {
                mData = (ArrayList<Situation>) situationManager.getAll();
            }

            mGridView.setAdapter(mAdapter);
            mGridView.setOnScrollListener(this);
            mGridView.setOnItemClickListener(this);
        }
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //view.setBackgroundColor(Color.parseColor("#ffaf3f"));
        Intent intent = new Intent(getActivity().getApplicationContext(), EtatAlerteActivity.class);
        // create class object
        gps = new GPSTracker(getActivity());
        Situation s = situationManager.getAll().get(position);
        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getActivity().getApplicationContext(),
                           "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
                            Toast.LENGTH_LONG).show();
            intent.putExtra("Situation", s);
            startActivity(intent);
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        Toast.makeText(getActivity(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("APP", "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d("APP", "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
                //onLoadMoreItems();
            }
        }
    }

    private void onLoadMoreItems() {
        final ArrayList<Situation> sampleData = (ArrayList<Situation>)situationManager.getAll();
        for (Situation data : sampleData) {
            mAdapter.add(data);
        }
        // stash all the data in our backing store
        mData.addAll(sampleData);
        // notify the adapter that we can update now
        mAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstRun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstRun", false).commit();

        }
    }
}
