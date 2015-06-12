package com.emergency.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;


import com.emergency.adapter.SampleAdapter;
import com.emergency.business.AsyncWsCaller;
import com.emergency.business.TaskCallbacks;
import com.emergency.business.impl.SituationManagerImpl;
import com.emergency.business.OnTaskCompleted;
import com.emergency.business.SituationManager;
import com.emergency.business.taskfragments.SendAlertFragment;
import com.emergency.dto.AlerteDTO;
import com.emergency.dto.ManageAlerteIn;
import com.emergency.dto.ManageAlerteOut;
import com.emergency.dto.ManageUserOut;
import com.emergency.entity.RecepteursSituation;
import com.emergency.entity.Situation;
import com.emergency.entity.SuiviAlerte;
import com.emergency.helpers.GPSTracker;
import com.emergency.util.AlerteUtil;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.SituationUtil;
import com.etsy.android.grid.StaggeredGridView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener,
		AbsListView.OnItemClickListener, TaskCallbacks<ManageAlerteIn, Void, ManageAlerteOut> {
	private static final String TAG_TASK_FRAGMENT = "task_fragment";

	private SendAlertFragment<ManageAlerteIn, Void, ManageAlerteOut> mTaskFragment;
	public static final String SAVED_DATA_KEY = "SAVED_DATA";
	private SharedPreferences prefs = null;
	private StaggeredGridView mGridView;
	private boolean mHasRequestedMore;
	private SampleAdapter mAdapter;
	private SituationManager situationManager;
	private String titre;
	private ArrayList<Situation> mData;
	private GPSTracker gps;
	private AlerteDTO alerte;
	// Progress Dialog Object
	ProgressDialog prgDialog;

	Situation s;
	public HomeFragment () {

	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		gps = new GPSTracker(getActivity());
		gps.getLocation();

		situationManager = new SituationManagerImpl();

		prefs = getActivity().getSharedPreferences("com.emergency.app", Context.MODE_PRIVATE);

		//Tester est ce qu'il s'agit de premier lancement de l'app


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
		//   }


		FragmentManager fm = getFragmentManager();
		mTaskFragment = (SendAlertFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

		// If the Fragment is non-null, then it is currently being
		// retained across a configuration change.
		if (mTaskFragment == null) {
			mTaskFragment = new SendAlertFragment<ManageAlerteIn, Void, ManageAlerteOut>();
			fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
		}
		if (mAdapter == null || mAdapter.isEmpty()) {
			rootView = inflater.inflate(R.layout.empty_adapter, container, false);
		}

		return rootView;
	}

	@Override
	public void onItemClick (AdapterView<?> adapterView, View view, int position, long id) {
		//view.setBackgroundColor(Color.parseColor("#ffaf3f"));
		// Intent intent = new Intent(getActivity().getApplicationContext(), SituationCompteRenduActivity.class);
		// create class object

		s = situationManager.getAll().get(position);
		// check if GPS enabled
		if (gps.canGetLocation()) {


			alerte = new AlerteDTO();
			alerte.setDateEnvoi(new Date());

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			alerte.setLocalisationEmX(gps.getLatitude() + "");
			alerte.setLocalisationEmY(gps.getLongitude() + "");


			alerte.setStatut((short) 0);

//			List<SuiviAlerte> suivies = new ArrayList<SuiviAlerte>();
			List<RecepteursSituation> rs = new ArrayList<>();
			int i = 0;

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			alerte.setLocalisationEmX(gps.getLatitude() + "");
			alerte.setLocalisationEmY(gps.getLongitude() + "");
			if (rs != null) {
				for (RecepteursSituation tel : s.getRecepteursSituations()) {
					PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

					try {
						if (s.getTypeEnvoi() == 1 || s.getTypeEnvoi() == 2) {
							SmsManager smsManager = SmsManager.getDefault();
							String locationLink = "http://maps.google.com/?q=" + latitude + "," + longitude;
							smsManager.sendTextMessage(tel.getNumUser(), null, getString(R.string.sms_alerte, locationLink) + " " + s.getMessage(), null, null);
							i++;
						}
						Phonenumber.PhoneNumber pn = phoneUtil.parse(s.getUser().getTelephone(), "MA");

						tel.setNumUser(phoneUtil.format(phoneUtil.parse(tel.getNumUser(), phoneUtil.getRegionCodeForNumber(pn)), PhoneNumberUtil.PhoneNumberFormat.E164));

					} catch (NumberParseException e) {
						AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

						alert.setTitle("Oops...");
						alert.setMessage(getResources().getString(R.string.alerte_incorrect_number, tel.getNumUser()));
						alert.setPositiveButton("OK", null);
						alert.show();
					}

				}
			}
			if (s.getTypeEnvoi() == 1 || s.getTypeEnvoi() == 2) {
				Toast.makeText(
						getActivity(),
						getString(R.string.sms_sent_to, String.valueOf(i)),
						Toast.LENGTH_LONG).show();
			}
			alerte.setSituation(SituationUtil.situationToSituationDTO(s));

//
//				for (int i = 0; i < rs.size(); i++) {
//					SuiviAlerte sa = new SuiviAlerte();
//					//sa.setAlerte(alerte);
//
//					suivies.add(sa);
//				}
//			}

//			alerte.setSuiviAlertes(suivies);

			//
			alerte.setPieceJointes(null);


			// \n is for new line
//			Toast.makeText(getActivity().getApplicationContext(),
//					"Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
//					Toast.LENGTH_LONG).show();
			//    intent.putExtra("Situation", s);
			//    startActivity(intent);
			if (s.getTypeEnvoi() == 2 || s.getTypeEnvoi() == 3) {
				mTaskFragment.setmCallbacks(this);
				mTaskFragment.runTask(new ManageAlerteIn());
			}

		}
		else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}

	@Override
	public void onScrollStateChanged (AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll (AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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

	private void onLoadMoreItems () {
		final ArrayList<Situation> sampleData = (ArrayList<Situation>) situationManager.getAll();
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
	public void onResume () {
		super.onResume();

		if (prefs.getBoolean("firstRun", true)) {
			// Do first run stuff here then set 'firstrun' as false
			// using the following line to edit/commit prefs
			prefs.edit().putBoolean("firstRun", false).commit();

		}
	}


	public ManageAlerteIn getAlerte () {
		ManageAlerteIn man = new ManageAlerteIn();
		man.setCodeFonction((short) 1);
		man.setAlerteDTO(alerte);

		return man;
	}

	@Override
	public void onPreExecute () {
		// Instantiate Progress Dialog object
		if (prgDialog == null) {
			prgDialog = ProgressDialog.show(getActivity(), getString(R.string.envoi_alerte_titre), getString(R.string.envoi_alerte_message), true, false);

		}
		else {
			prgDialog.show();
		}

		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

	}

	@Override
	public ManageAlerteOut doInBackground (ManageAlerteIn... ignore) {
//		ObjectMapper mapper = new ObjectMapper();
		ManageAlerteOut out = null;
		try {
			ManageAlerteIn malerteIn = new ManageAlerteIn();
			malerteIn.setCodeFonction((short) 0);
			malerteIn.setAlerteDTO(alerte);
//			mapper.writeValueAsString(malerteIn);

			RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			out = restTemplate.postForObject(EmergencyConstants.SEND_ALERT, malerteIn, ManageAlerteOut.class);
		} catch (Exception e) {

		}
		return out;
	}

	@Override
	public void onProgressUpdate (Void... percent) {

	}

	@Override
	public void onCancelled () {
		if (prgDialog != null) {
			prgDialog.dismiss();
		}
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}

	@Override
	public void onPostExecute (ManageAlerteOut manageAlerteOut) {
		if (prgDialog != null) {
			prgDialog.dismiss();
		}
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		if (manageAlerteOut == null) {
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

			alert.setTitle("Oops...");
			alert.setMessage(getResources().getString(R.string.servercall_error));
			alert.setPositiveButton("OK", null);
			alert.show();
		}
		else {
			Toast.makeText(
					getActivity(),
					getString(R.string.notification_sent),
					Toast.LENGTH_LONG).show();
		}
	}

	private ClientHttpRequestFactory clientHttpRequestFactory () {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(15000);
		factory.setConnectTimeout(15000);
		return factory;
	}



}



