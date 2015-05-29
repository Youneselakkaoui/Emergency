package com.emergency.activities;


import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.emergency.business.AsyncWsCaller;
import com.emergency.business.OnTaskCompleted;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.UserDTO;
import com.emergency.util.EmergencyConstants;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ManageUser extends ActionBarActivity implements OnTaskCompleted<ManageUserOut> {

    DateFormat fmtDateAndTime = DateFormat.getDateInstance();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        Spinner spinner = (Spinner) findViewById(R.id.bloodtype_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.manage_user_bloodtypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        lblDateAndTime = (TextView) findViewById(R.id.textDtNaiss);
        ImageButton btnDate = (ImageButton) findViewById(R.id.buttonSetDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(ManageUser.this, d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        Logger.getAnonymousLogger().log(Level.INFO, "phone number : ", mTelephonyMgr.getLine1Number());
        ((TextView) findViewById(R.id.textTelephone)).setText(mTelephonyMgr.getLine1Number());
// ajouter spinner indicatif pays
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    /**
     * Sauvegarder user
     */
    public void saveUser(View v) {
        new AsyncWsCaller<ManageUserIn, ManageUserOut>(this, getUser(), ManageUserOut.class, EmergencyConstants.MANAGE_USER_URL).execute();
    }

    public void onTaskCompleted(ManageUserOut manageUserOut) {

    }

    @Override
    public void onPreExecute() {

    }

    private ManageUserIn getUser() {
        ManageUserIn manageUserIn = new ManageUserIn();
        manageUserIn.setCodeFonction((short) 1);
        manageUserIn.setUserDTO(new UserDTO());

        manageUserIn.getUserDTO().setTelephone(String.valueOf(((TextView) findViewById(R.id.textTelephone)).getText()));
        manageUserIn.getUserDTO().setNom(String.valueOf(((TextView) findViewById(R.id.textNom)).getText()));
        manageUserIn.getUserDTO().setPrenom(String.valueOf(((TextView) findViewById(R.id.textprenom)).getText()));
        manageUserIn.getUserDTO().setDateNaissance(myCalendar.getTime());
        return manageUserIn;
    }


}
