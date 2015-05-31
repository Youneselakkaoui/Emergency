package com.emergency.activities;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.emergency.business.AsyncWsCaller;
import com.emergency.util.EmergencyConstants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.util.DisplayMetrics;
import com.google.android.gms.maps.model.BitmapDescriptor;
import android.content.Context;


import com.emergency.business.OnTaskCompleted;
//import com.emergency.business.AlerteManager;
import com.emergency.dto.ManageAlerteIn;
import com.emergency.dto.ManageAlerteOut;
import com.emergency.dto.AlerteDTO;
import com.emergency.entity.Alerte;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GoogleMapActivity extends Fragment  {


    private static  LatLng CASABLANCA = new LatLng(33.5930556, -7.6163889);




    private View rootView;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.∂∂
    private FragmentActivity myContext;

    //private AlerteManager alerteManager;
    private Alerte currentAlerte = new Alerte();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_google_map2, container, false);

        // Use green marker icon
        BitmapDescriptor defaultMarker =
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_google_map2);
        setUpMapIfNeeded();
      //  TextView locationTv = (TextView) rootView.findViewById(R.id.latlongLocation);
     //   TextView alerteTv = (TextView) rootView.findViewById(R.id.suiviAlerte);
      //  TextView alerteDetail = (TextView) rootView.findViewById(R.id.message);

      //  locationTv.setText("Latitude:" + CASABLANCA.latitude + ", Longitude:" + CASABLANCA.longitude);
     //   alerteTv.setText("Nom Alerte:" + "alerte 49");
     //   alerteDetail.setText("Detail alerte:" + "Crise cardiaque");



      //  Marker davao = mMap.addMarker(new MarkerOptions()
      //          .position(CASABLANCA)
      //          .title(alerteTv.getText().toString())
      //          .snippet(alerteDetail.getText().toString()).icon(defaultMarker));

        Marker davao = mMap.addMarker(new MarkerOptions()
                .position(CASABLANCA)
                .title("Alerte1")
                .snippet("Crise cardiaque").icon(defaultMarker));

        // zoom in the camera to Davao city
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CASABLANCA, 15));

        // animate the zoom process
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    //    new AsyncWsCaller<ManageAlerteIn,ManageAlerteOut>(this,null,ManageAlerteOut.class,
       //         EmergencyConstants.MANAGE_ALERTE_URL).execute();



        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) myContext.getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }



    public void onTaskCompleted(ManageAlerteOut manageAlerteOut) {
      // CASABLANCA= new LatLng(Double.parseDouble(manageAlerteOut.getAlerteDTO().getLocalisationEmX()),
            //    Double.parseDouble(manageAlerteOut.getAlerteDTO().getLocalisationEmY())) ;

    }





    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
}
