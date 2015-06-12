package com.emergency.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;


import com.emergency.activities.R;
import com.emergency.business.AlerteRecueManager;
import com.emergency.business.impl.AlerteRecueManagerImpl;
import com.emergency.entity.AlerteRecue;


public class InfoAlerte extends Fragment {


	private View rootView;
	AlerteRecueManager alerteManager = new AlerteRecueManagerImpl();
	private String location;
	public AlerteRecue alerte;
	Fragment alertListFragment;
	public static final String ALERT_LIST_FRAGMENT = "alertListFragment";


	public InfoAlerte () {
		// Required empty public constructor
	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void remplirAlerte (AlerteRecue alerteRecue) {

		((TextView) rootView.findViewById(R.id.txtTitre)).setText(alerteRecue.getSituation().getTitre());
		((TextView) rootView.findViewById(R.id.txtMessage)).setText(alerteRecue.getSituation().getMessage());

		String nom = alerteRecue.getSituation().getUser().getNom();
		String prenom = alerteRecue.getSituation().getUser().getPrenom();
		if (alerteRecue.getSituation().getUser().getNom() == null) {
			nom = "";
		}
		if (alerteRecue.getSituation().getUser().getPrenom() == null) {
			prenom = "";
		}
		String nomprenom = nom + " " + prenom;
		((TextView) rootView.findViewById(R.id.txtNomPrenom)).setText(nomprenom);

		short sexe = alerteRecue.getSituation().getUser().getSexe();
		if (sexe == 1) {
			((TextView) rootView.findViewById(R.id.txtSexe)).setText("Homme");
		}
		if (sexe == 2) {
			((TextView) rootView.findViewById(R.id.txtSexe)).setText("Femme");
		}

		((TextView) rootView.findViewById(R.id.txtTel)).setText(alerteRecue.getSituation().getUser().getTelephone());

		short grpsng = alerteRecue.getSituation().getUser().getGroupSanguin();
		if (grpsng == 0) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("Inconnu");
		}
		if (grpsng == 1) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("O+");
		}
		if (grpsng == 2) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("O-");
		}
		if (grpsng == 3) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("A+");
		}
		if (grpsng == 4) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("A-");
		}
		if (grpsng == 5) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("B+");
		}
		if (grpsng == 6) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("B-");
		}
		if (grpsng == 7) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("AB+");
		}
		if (grpsng == 8) {
			((TextView) rootView.findViewById(R.id.txtGroupeSanguin)).setText("AB-");
		}

		short diab = alerteRecue.getSituation().getUser().getDiabete();
		if (diab == 0) {
			((TextView) rootView.findViewById(R.id.txtDiabete)).setText("NON");
		}
		if (diab == 1) {
			((TextView) rootView.findViewById(R.id.txtDiabete)).setText("OUI");
		}


		short choles = alerteRecue.getSituation().getUser().getCholesterol();
		if (choles == 0) {
			((TextView) rootView.findViewById(R.id.txtCholesterol)).setText("NON");
		}
		if (choles == 1) {
			((TextView) rootView.findViewById(R.id.txtCholesterol)).setText("OUI");
		}

		((TextView) rootView.findViewById(R.id.txtautreInfo)).setText(alerteRecue.getSituation().getUser().getAutresInfos());

	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		rootView = inflater.inflate(R.layout.fragment_info_alerte,
				container, false);
		Bundle b = getArguments();

		if (getFragmentManager().findFragmentByTag(ALERT_LIST_FRAGMENT) == null) {
			alertListFragment = new AlerteListItemFragment();
			alertListFragment.setArguments(getActivity().getIntent().getExtras());
		}
		else {
			alertListFragment = getFragmentManager().findFragmentByTag(ALERT_LIST_FRAGMENT);
		}

		long idAlerte = b.getLong("idAlerte", 0L);
		if (b != null && idAlerte != 0L) {
			final AlerteRecue alerteRecue = alerteManager.getById(idAlerte);
			if (alerteRecue != null) {
				remplirAlerte(alerteRecue);
				location = alerteRecue.getLocalisationEmX().trim() + "," + alerteRecue.getLocalisationEmY().trim();


				ImageButton button = (ImageButton) rootView.findViewById(R.id.btn_suivant);

				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick (View v) {
						Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + location));
						startActivity(i);
					}
				});
				alerteManager.majLue(idAlerte);
				((MainActivity) getActivity()).refreshAlertCounter();
				((ImageButton) rootView.findViewById(R.id.btn_del_alert)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick (View v) {
						new AlertDialog.Builder(getActivity())
								.setTitle(getString(R.string.title_delete_alert))
								.setMessage(getString(R.string.message_delete_alert))
								.setIcon(R.drawable.corbeil_icon)
								.setPositiveButton(getString(R.string.confirmation_yes),
										new DialogInterface.OnClickListener() {

											public void onClick (DialogInterface dialog, int id) {
												alerteManager.remove(alerteRecue.getId());
												Fragment fragment = new AlerteListItemFragment();
												FragmentManager fragmentManager = getActivity().getFragmentManager();
												fragmentManager.beginTransaction()
														.replace(R.id.container, alertListFragment, null).commit();
												dialog.cancel();

											}
										})
								.setNegativeButton(getString(R.string.confirmation_no), new DialogInterface.OnClickListener() {

									public void onClick (DialogInterface dialog, int id) {
										dialog.cancel();
									}
								}).show();
					}
				});
			}
		}
		return rootView;
	}


}
