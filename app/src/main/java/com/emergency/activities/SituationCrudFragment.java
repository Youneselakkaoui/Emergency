package com.emergency.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.emergency.dao.impl.RecepteurSituationDaoImpl;
import com.emergency.dao.impl.SituationDaoImpl;
import com.emergency.dao.impl.UserDaoImpl;
import com.emergency.entity.RecepteursSituation;
import com.emergency.entity.Situation;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class SituationCrudFragment extends Fragment {

	private OnFragmentInteractionListener mListener;
	SituationDaoImpl sitDao = new SituationDaoImpl();
	RecepteurSituationDaoImpl rsDao = new RecepteurSituationDaoImpl();
	View rootview;
	private ListView list;
	private String location;
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> adapter;
	short typenvoie;
	ImageButton btn_del;
	ImageButton btn_maj;
	Button btn_addContact;
	Button btn_delContact;
	ListView listcontact;

	EditText edit_title;
	EditText edit_message;

	CheckBox choixSms;
	CheckBox choixNotif;

	RadioButton radioHaut;
	RadioButton radioMoyen;
	RadioButton radioBas;


	private static final int CONTACT_PICKER_RESULT = 1001;

	// TODO: Rename and change types and number of parameters
	public static SituationCrudFragment newInstance (String param1, String param2) {
		SituationCrudFragment fragment = new SituationCrudFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public SituationCrudFragment () {
		// Required empty public constructor
	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootview = inflater.inflate(R.layout.fragment_situation_crud, container, false);

		list = (ListView) rootview.findViewById(R.id.list_contact);
		arrayList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayList);
		// Here, you set the data in your ListView
		list.setAdapter(adapter);

		edit_title = (EditText) rootview.findViewById(R.id.titre_label);
		edit_message = (EditText) rootview.findViewById(R.id.situation_message);

		btn_del = (ImageButton) rootview.findViewById(R.id.btn_del);
		btn_maj = (ImageButton) rootview.findViewById(R.id.btn_maj);
		btn_addContact = (Button) rootview.findViewById(R.id.btn_addContact);
		btn_delContact = (Button) rootview.findViewById(R.id.btn_delContact);
		choixSms = (CheckBox) rootview.findViewById(R.id.sms);
		choixNotif = (CheckBox) rootview.findViewById(R.id.notif);

		radioHaut = (RadioButton) rootview.findViewById(R.id.haut);
		radioMoyen = (RadioButton) rootview.findViewById(R.id.moyen);
		radioBas = (RadioButton) rootview.findViewById(R.id.bas);


		final EditText edit_title = (EditText) rootview.findViewById(R.id.titre_label);
		final EditText edit_message = (EditText) rootview.findViewById(R.id.situation_message);

		choixSms = (CheckBox) rootview.findViewById(R.id.sms);
		choixNotif = (CheckBox) rootview.findViewById(R.id.notif);
		listcontact = (ListView) rootview.findViewById(R.id.list_contact);


		btn_addContact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				Intent i = new Intent(Intent.ACTION_PICK,
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(i, CONTACT_PICKER_RESULT);
			}
		});


		btn_delContact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {
//				Toast.makeText(getActivity(), "Item has been added into your list", Toast.LENGTH_LONG)
//						.show();


				SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();
				int itemCount = list.getCount();

				for (int i = itemCount - 1; i >= 0; i--) {
					if (checkedItemPositions.get(i)) {
						adapter.remove(arrayList.get(i));
					}
				}
				checkedItemPositions.clear();
				adapter.notifyDataSetChanged();


			}
		});


		Bundle b = getArguments();
		final long idSituation = b.getLong("idSituation", 0L);

		if (b != null && idSituation != 0L) {
			Situation situation = sitDao.findById(idSituation);
			List<RecepteursSituation> recepeteurs = rsDao.findBySituationId(idSituation);
			if (situation != null) {
				remplirSituation(situation);
				//location = situation.getLocalisationEmX().trim() + "," + alerteRecue.getLocalisationEmY().trim();
			}
			if (recepeteurs != null) {
				remplirRecepteurSituation(recepeteurs);
				//location = situation.getLocalisationEmX().trim() + "," + alerteRecue.getLocalisationEmY().trim();
			}
		}

		btn_del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				Situation situation = sitDao.findById(idSituation);
				confirmation_del(situation);
			}
		});

		btn_maj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {

				if (!controls()) {

					Situation situation = sitDao.findById(idSituation);
					situation.setTitre(edit_title.getText().toString());
					situation.setMessage(edit_message.getText().toString());

					if (choixSms.isChecked() && (!choixNotif.isChecked())) {
						typenvoie = 1;
					}

					if (choixSms.isChecked() && choixNotif.isChecked()) {
						typenvoie = 2;
					}

					if ((!choixSms.isChecked()) && choixNotif.isChecked()) {
						typenvoie = 3;
					}

					situation.setTitre(edit_title.getText().toString());
					situation.setMessage(edit_message.getText().toString());
					situation.setTypeEnvoi(typenvoie);
					situation.setUser(new UserDaoImpl().selectUser());

					if (radioBas.isChecked()) {
						situation.setNiveau(0);
					}
					if (radioMoyen.isChecked()) {
						situation.setNiveau(1);
					}
					if (radioHaut.isChecked()) {
						situation.setNiveau(2);
					}

					confirmation_maj(situation);
				}
			}
		});

		return rootview;
	}

	private boolean controls () {

		boolean bol = false;
		AlertDialog.Builder ald = new AlertDialog.Builder(getActivity())
				.setTitle("Attention")
				.setIcon(R.drawable.add_icon)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@TargetApi(11)
							public void onClick (DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		if (!StringUtils.hasText(edit_title.getText().toString())) {
			ald.setMessage("Le titre de la situation est obligatoire !").show();
			bol = true;
		}
		else if (!choixSms.isChecked() && !choixNotif.isChecked()) {
			ald.setMessage("Le type d'envoi est obligatoire !").show();
			bol = true;
		}

		else if (list.getCount() == 0) {
			ald.setMessage("veuillez attacher un contact !").show();
			bol = true;
		}
		return bol;
	}

	public void onActivityResult (int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (reqCode) {
				case CONTACT_PICKER_RESULT:
					Cursor cursor = null;
					String number = "";
					String lastName = "";
					try {
						Uri result = data.getData();
						// get the id from the uri
						String id = result.getLastPathSegment();
						// query
						cursor = getActivity().getContentResolver().query(
								ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
								ContactsContract.CommonDataKinds.Phone._ID
										+ " = ? ", new String[]{id}, null);

						int numberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);


						if (cursor.moveToFirst()) {
							number = cursor.getString(numberIdx);
							//lastName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
							//lastName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

						}
						else {
							// WE FAILED
						}
					} catch (Exception e) {
						// failed
					} finally {
						if (cursor != null) {
							cursor.close();
						}
						else {
						}
					}
					arrayList.add(number.toString());
					// next thing you have to do is check if your adapter has changed
					adapter.notifyDataSetChanged();
			}

		}
	}

	public void confirmation_del (final Situation situation) {
		new AlertDialog.Builder(getActivity())
				.setTitle("Suppression")
				.setMessage("Voullez-vous supprimer la situation ?")
				.setIcon(R.drawable.corbeil_icon)
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							@TargetApi(11)
							public void onClick (DialogInterface dialog, int id) {
								sitDao.delete(situation);
								fragment_list();
								dialog.cancel();

							}
						})
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@TargetApi(11)
					public void onClick (DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}


	public void confirmation_maj (final Situation situation) {
		new AlertDialog.Builder(getActivity())
				.setTitle("Modification")
				.setMessage("Voullez-vous modifier la situation ?")
				.setIcon(R.drawable.edit_icon)
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							@TargetApi(11)
							public void onClick (DialogInterface dialog, int id) {
								sitDao.update(situation);
								rsDao.deleteByIdSituation(situation.getId());
								for (int i = 0; i < listcontact.getAdapter().getCount(); i++) {
									RecepteursSituation reSit = new RecepteursSituation();
									reSit.setNumUser(listcontact.getAdapter().getItem(i).toString());
									reSit.setSituation(situation);
									rsDao.insert(reSit);
									System.out.print(reSit.getNumUser().toString());
								}
								fragment_list();
								dialog.cancel();
							}
						})
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@TargetApi(11)
					public void onClick (DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}


	public void fragment_list () {
		Fragment fragment = new SituationListFragment();
		FragmentManager fragmentManager = getActivity().getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, fragment, null).addToBackStack(null).commit();
	}

	private void remplirSituation (Situation Situation) {

		edit_title.setText(Situation.getTitre());
		edit_message.setText(Situation.getMessage());

		Short typenvoie = Situation.getTypeEnvoi();
		if (typenvoie == 1) {
			choixSms.setChecked(true);
			choixNotif.setChecked(false);
		}
		if (typenvoie == 2) {
			choixSms.setChecked(true);
			choixNotif.setChecked(true);
		}
		if (typenvoie == 3) {
			choixSms.setChecked(false);
			choixNotif.setChecked(true);
		}

		int niveau = Situation.getNiveau();
		if (niveau == 0) {
			radioBas.setChecked(true);
		}
		if (niveau == 1) {
			radioMoyen.setChecked(true);
		}
		if (niveau == 2) {
			radioHaut.setChecked(true);
		}

	}

	private void remplirRecepteurSituation (List<RecepteursSituation> recepteurs) {
		arrayList.clear();
		for (RecepteursSituation rs : recepteurs)
			arrayList.add(rs.getNumUser());
	}


	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed (Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction (Uri uri);
	}

}
