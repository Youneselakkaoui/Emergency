package com.emergency.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.emergency.business.OnTaskCompleted;
import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.UserDTO;
import com.emergency.entity.User;
import com.emergency.util.UserUtil;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageUserFragment extends Fragment implements OnTaskCompleted<ManageUserOut> {
    private View rootView;
    private UserManager userManager;
    private User currentUser = new User();

    public ManageUserFragment() {
        userManager = new UserManagerImpl(getActivity());
        currentUser = userManager.getUser();
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_manage_user, container, false);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.bloodtype_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.manage_user_bloodtypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUser();
            }
        });

        lblDateAndTime = (TextView) rootView.findViewById(R.id.textDtNaiss);
        ImageButton btnDate = (ImageButton) rootView.findViewById(R.id.buttonSetDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TelephonyManager mTelephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);


        Logger.getAnonymousLogger().log(Level.INFO, "phone number : ", mTelephonyMgr.getLine1Number());
        ((TextView) rootView.findViewById(R.id.textTelephone)).setText(mTelephonyMgr.getLine1Number());

        return rootView;
    }

    public void updateUser() {
        userManager.create(UserUtil.mapUser(getUser()));
        Logger.getAnonymousLogger().log(Level.INFO, "userAdded : ", userManager.getUser());
        //new AsyncWsCaller<ManageUserIn,ManageUserOut>(this,getUser(),ManageUserOut.class,
        //                                            EmergencyConstants.MANAGE_USER_URL).execute();
    }

    public void onTaskCompleted(ManageUserOut manageUserOut) {

    }

    private ManageUserIn getUser() {
        ManageUserIn manageUserIn = new ManageUserIn();
        manageUserIn.setCodeFonction((short) 1);
        manageUserIn.setUserDTO(new UserDTO());

        manageUserIn.getUserDTO().setTelephone(String.valueOf((
                (TextView) rootView.findViewById(R.id.textTelephone)).getText()));
        manageUserIn.getUserDTO().setNom(String.valueOf((
                (TextView) rootView.findViewById(R.id.textNom)).getText()));
        manageUserIn.getUserDTO().setPrenom(String.valueOf((
                (TextView) rootView.findViewById(R.id.textprenom)).getText()));
        manageUserIn.getUserDTO().setDateNaissance(myCalendar.getTime());
        //manageUserIn.getUserDTO().setDateNaissance(String.valueOf(((TextView) findViewById(R.id.textDtNaiss)).getText()));
        return manageUserIn;
    }
}
