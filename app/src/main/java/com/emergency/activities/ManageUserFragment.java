package com.emergency.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emergency.business.AsyncWsCaller;
import com.emergency.business.OnTaskCompleted;
import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.UserDTO;
import com.emergency.entity.User;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.JsonUtils;
import com.emergency.util.UserUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageUserFragment extends Fragment implements OnTaskCompleted<ManageUserOut> {
	private View rootView;
	private UserManager userManager;
	private User currentUser;
	// Progress Dialog Object
	ProgressDialog prgDialog;
	// Error Msg TextView Object
	TextView errorMsg;
	Context context;

	public ManageUserFragment () {
		userManager = new UserManagerImpl();
		currentUser = userManager.getUser();
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

		// Instantiate Progress Dialog object
		prgDialog = new ProgressDialog(getActivity());
		// Set Progress Dialog Text
		prgDialog.setMessage("Please wait...");
		// Set Cancelable as False
		prgDialog.setCancelable(false);
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

		if (savedInstanceState == null || !savedInstanceState.getBoolean("edited", true)) {
			fillUser(currentUser);
		}
		return rootView;
	}

	public void updateUser () {

		// Show Progress Dialog
		prgDialog.show();

		// Make RESTful webservice call using AsyncHttpClient object
		final AsyncHttpClient client = new AsyncHttpClient();
		ManageUserIn userIn = getUser();
		final ObjectMapper mapper = new ObjectMapper();


		try {
			client.post(context, EmergencyConstants.MANAGE_USER_URL, new ByteArrayEntity(mapper.writeValueAsBytes(userIn)), "application/json",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess (int statusCode, Header[] headers, byte[] responseBody) {
							// Hide Progress Dialog
							prgDialog.hide();
							try {
								if (responseBody != null) {
									Log.i("com.emergency.emergency", responseBody.toString());
								}
								// JSON Object
								//final JSONObject obj = new JSONObject(new String(responseBody));
								// When the JSON response has status boolean value
								// assigned with true

								ManageUserOut out = mapper.readValue(responseBody, ManageUserOut.class);//gson.fromJson(new String(responseBody), ManageUserOut.class);
								if (out.getAnomalie() == null) {
									Toast.makeText(getActivity().getApplicationContext(),
											"sauvegarde effectuée",
											Toast.LENGTH_LONG).show();
									// Navigate to Home screen
									userManager.edit(UserUtil.mapUser(getUser(), (short) 1));
								}
								// Else display error message
								else {
									errorMsg.setText(out.getAnomalie().getLibelleAnomalie());
									Toast.makeText(getActivity().getApplicationContext(),
											errorMsg.getText(),
											Toast.LENGTH_LONG).show();
								}
							} catch (final IOException e) {
								// TODO Auto-generated catch block
								Toast.makeText(
										getActivity().getApplicationContext(),
										"Error Occured [Server's JSON response might be invalid]!",
										Toast.LENGTH_LONG).show();
								e.printStackTrace();

							}
						}

						@Override
						public void onFailure (int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
							// Hide Progress Dialog
							prgDialog.hide();
							if (responseBody != null) {
								Log.i("com.emergency.emergency", responseBody.toString());
							}
							// When Http response code is '404'
							if (statusCode == 404) {
								Toast.makeText(getActivity().getApplicationContext(),
										"Requested resource not found",
										Toast.LENGTH_LONG).show();
							}
							// When Http response code is '500'
							else if (statusCode == 500) {
								Toast.makeText(getActivity().getApplicationContext(),
										"Something went wrong at server end",
										Toast.LENGTH_LONG).show();
							}
							// When Http response code other than 404, 500
							else {
								Toast.makeText(
										getActivity().getApplicationContext(),
										"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
										Toast.LENGTH_LONG).show();
							}
						}

					});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	public void onTaskCompleted (ManageUserOut manageUserOut) {
		if (manageUserOut != null && manageUserOut.getAnomalie() == null) {
			userManager.edit(UserUtil.mapUser(getUser(), (short) 1));
			Logger.getAnonymousLogger().log(Level.INFO, "userUpdated : ", userManager.getUser());
		}
		else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
			alertDialog.setTitle("Oops...");
			alertDialog.setMessage(getResources().getString(R.string.servercall_error));
			alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
				public void onClick (DialogInterface dialog, int which) {
					// here you can add functions
				}
			});
			alertDialog.setIcon(R.drawable.ic_communities);
			alertDialog.show();
		}

	}

	@Override
	public void onPreExecute () {

	}

	private ManageUserIn getUser () {
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
		manageUserIn.getUserDTO().setGroupSanguin((short) ((Spinner) rootView.findViewById(R.id.bloodtype_spinner)).getSelectedItemId());
		manageUserIn.getUserDTO().setGcmDeviceId(currentUser.getGcmDeviceId());
		if (((RadioButton) rootView.findViewById(R.id.radiohomme)).isChecked()) {
			manageUserIn.getUserDTO().setSexe((short) 1);
		}
		if (((RadioButton) rootView.findViewById(R.id.radiofemme)).isChecked()) {
			manageUserIn.getUserDTO().setSexe((short) 2);
		}
		manageUserIn.getUserDTO().setAutresInfos(String.valueOf(((EditText) rootView.findViewById(R.id.otherInfo)).getText()));
		//manageUserIn.getUserDTO().setDateNaissance(String.valueOf(((TextView) findViewById(R.id.textDtNaiss)).getText()));
		return manageUserIn;
	}

	private void fillUser (User user) {
		((EditText) rootView.findViewById(R.id.textTelephone)).setText(user.getTelephone());
		((EditText) rootView.findViewById(R.id.textNom)).setText(user.getNom());
		((EditText) rootView.findViewById(R.id.textprenom)).setText(user.getPrenom());
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

	}

	@Override
	public void onSaveInstanceState (Bundle outState) {super.onSaveInstanceState(outState);
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
