package com.emergency.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.entity.User;

import com.emergency.util.SyncUtils;


import java.text.DateFormat;
import java.util.Calendar;


public class ManageUserFragment extends Fragment {
	private View rootView;
	private UserManager userManager;
	private User currentUser;

	Context context;

	public ManageUserFragment () {

	}

	DateFormat fmtDateAndTime = DateFormat.getDateInstance();
	TextView lblDateAndTime;
	Calendar myCalendar = Calendar.getInstance();

	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet (DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateLabel();
		}
	};

	private void updateLabel () {
		lblDateAndTime.setText(fmtDateAndTime.format(myCalendar.getTime()));
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_manage_user, container, false);
		userManager = new UserManagerImpl();
		currentUser = userManager.getUser();
		context = getActivity().getApplicationContext();
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
			public void onClick (View v) {
				updateUser();

				SyncUtils.TriggerRefresh();
				Toast.makeText(
						getActivity(),
						getString(R.string.user_saved),
						Toast.LENGTH_LONG).show();
			}
		});

		lblDateAndTime = (TextView) rootView.findViewById(R.id.textDtNaiss);
		ImageButton btnDate = (ImageButton) rootView.findViewById(R.id.buttonSetDate);
		btnDate.setOnClickListener(new View.OnClickListener() {
			public void onClick (View v) {
				new DatePickerDialog(getActivity(), d, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});


		fillUser(currentUser);

		return rootView;
	}

	public void updateUser () {
		getUser();
		userManager.edit(currentUser);
	}


	private void getUser () {


		currentUser.setNom(String.valueOf((
				(TextView) rootView.findViewById(R.id.textNom)).getText()));
		currentUser.setPrenom(String.valueOf((
				(TextView) rootView.findViewById(R.id.textprenom)).getText()));
		currentUser.setDateNaissance(myCalendar.getTime());
		currentUser.setGroupSanguin((short) ((Spinner) rootView.findViewById(R.id.bloodtype_spinner)).getSelectedItemId());

		if (((RadioButton) rootView.findViewById(R.id.radiohomme)).isChecked()) {
			currentUser.setSexe((short) 1);
		}
		if (((RadioButton) rootView.findViewById(R.id.radiofemme)).isChecked()) {
			currentUser.setSexe((short) 2);
		}

		currentUser.setDiabete(((CheckBox) rootView.findViewById(R.id.user_check_diabete)).isChecked() ? (short) 1 : 0);
		currentUser.setCholesterol(((CheckBox) rootView.findViewById(R.id.user_check_cholesterol)).isChecked() ? (short) 1 : 0);
		currentUser.setAutresInfos(String.valueOf(((EditText) rootView.findViewById(R.id.otherInfo)).getText()));
		//manageUserIn.getUserDTO().setDateNaissance(String.valueOf(((TextView) findViewById(R.id.textDtNaiss)).getText()));


	}

	private void fillUser (User user) {
		((EditText) rootView.findViewById(R.id.textTelephone)).setText(user.getTelephone());
		((EditText) rootView.findViewById(R.id.textNom)).setText(user.getNom());
		((EditText) rootView.findViewById(R.id.textprenom)).setText(user.getPrenom());
		((EditText) rootView.findViewById(R.id.otherInfo)).setText(user.getAutresInfos());
		if (user.getDateNaissance() != null) {
			myCalendar.setTime(user.getDateNaissance());
			//((EditText) rootView.findViewById(R.id.textDtNaiss)).setText(user.getDateNaissance().toString());
			updateLabel();
		}
		if (1 == user.getSexe()) {
			((RadioButton) rootView.findViewById(R.id.radiohomme)).setChecked(true);
		}
		if (2 == user.getSexe()) {
			((RadioButton) rootView.findViewById(R.id.radiofemme)).setChecked(true);
		}
		//switch (user.getGroupSanguin()){
		//   case (short)1 :
		((Spinner) rootView.findViewById(R.id.bloodtype_spinner)).setSelection(user.getGroupSanguin());

		((CheckBox) rootView.findViewById(R.id.user_check_cholesterol)).setChecked(currentUser.getCholesterol() == 1);
		((CheckBox) rootView.findViewById(R.id.user_check_diabete)).setChecked(currentUser.getDiabete() == 1);

	}

	@Override
	public void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("edited", true);
	}


	@Override
	public void onPause () {
		super.onPause();
	}

	@Override
	public void onResume () {
		super.onResume();
	}


}
