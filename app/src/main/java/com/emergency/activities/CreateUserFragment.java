package com.emergency.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import com.emergency.business.AsyncWsCaller;
import com.emergency.business.OnTaskCompleted;
import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.UserDTO;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.UserUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class CreateUserFragment extends ActionBarActivity implements OnTaskCompleted<ManageUserOut> {

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


    public CreateUserFragment() {
        userManager = new UserManagerImpl(this);
    }

    /*DateFormat fmtDateAndTime = DateFormat.getDateInstance();
    TextView lblDateAndTime;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        lblDateAndTime.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //parse debut
        context = getApplicationContext();

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
        // Config parse fin

        setContentView(R.layout.fragment_create_user);
        /*Spinner spinner = (Spinner) rootView.findViewById(R.id.bloodtype_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.manage_user_bloodtypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/
        EditText countryCodeText = (EditText) findViewById(R.id.textIndicatif);
        countryCodeText.setText(GetCountryZipCode());


        Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUser();
            }
        });

        /*lblDateAndTime = (TextView) rootView.findViewById(R.id.textDtNaiss);
        ImageButton btnDate = (ImageButton) rootView.findViewById(R.id.buttonSetDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        Logger.getAnonymousLogger().log(Level.INFO, "phone number : ", mTelephonyMgr.getLine1Number());
        ((EditText) findViewById(R.id.textPhone)).setText(mTelephonyMgr.getLine1Number());


    }

    public void saveUser() {
        new AsyncWsCaller<ManageUserIn, ManageUserOut>(this, getUser(), ManageUserOut.class,
                EmergencyConstants.MANAGE_USER_URL).execute();
    }

    public void onTaskCompleted(ManageUserOut manageUserOut) {
        if (manageUserOut == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Oops...");
            alertDialog.setMessage(getResources().getString(R.string.servercall_error));
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// here you can add functions
                }
            });
            alertDialog.setIcon(R.drawable.ic_communities);
            alertDialog.show();
        } else {
            userManager.create(UserUtil.mapUserDtoToUser(manageUserOut.getUserDTO(), (short) 1));
            Logger.getAnonymousLogger().log(Level.INFO, "userAdded : ", userManager.getUser());
            //TODO rediriger vers HomeActivity
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    private ManageUserIn getUser() {
        ManageUserIn manageUserIn = new ManageUserIn();
        manageUserIn.setCodeFonction((short) 1);
        manageUserIn.setUserDTO(new UserDTO());

        String phoneNr = "+" + String.valueOf((
                (EditText) findViewById(R.id.textIndicatif)).getText()) + String.valueOf((
                (EditText) findViewById(R.id.textPhone)).getText());

        manageUserIn.getUserDTO().setTelephone(phoneNr);
        manageUserIn.getUserDTO().setGcmDeviceId((regId));
        /*manageUserIn.getUserDTO().setNom(String.valueOf((
                (TextView) rootView.findViewById(R.id.textNom)).getText()));
        manageUserIn.getUserDTO().setPrenom(String.valueOf((
                (TextView) rootView.findViewById(R.id.textprenom)).getText()));
        manageUserIn.getUserDTO().setDateNaissance(myCalendar.getTime());*/
        //manageUserIn.getUserDTO().setDateNaissance(String.valueOf(((TextView) findViewById(R.id.textDtNaiss)).getText()));
        return manageUserIn;
    }

    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = mTelephonyMgr.getSimCountryIso().toUpperCase();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        return CountryZipCode;
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
        return getSharedPreferences(HomeActivity.class.getSimpleName(),
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
