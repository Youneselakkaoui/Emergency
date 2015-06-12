package com.emergency.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.emergency.activities.R;
import com.emergency.business.AlerteRecueManager;
import com.emergency.business.impl.AlerteRecueManagerImpl;
import com.emergency.entity.AlerteRecue;

public class NotificationAlerteActivity extends Activity {
	AlerteRecueManager alerteManager = new AlerteRecueManagerImpl();
	private String location;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_info_alerte);
		Bundle extras = getIntent().getExtras();


		if (extras != null && extras.getLong("idAlerte", 0L) != 0L) {
			long idAlerte = extras.getLong("idAlerte", 0L);
			AlerteRecue alerteRecue = alerteManager.getById(idAlerte);
			if (alerteRecue != null) {
				remplirAlerte(alerteRecue);
				location = alerteRecue.getLocalisationEmX().trim() + "," + alerteRecue.getLocalisationEmY().trim();
				alerteManager.majLue(idAlerte);
			}
		}

		ImageButton button = (ImageButton) findViewById(R.id.btn_suivant);
		((TableLayout) findViewById(R.id.table_del_alerte)).setVisibility(View.INVISIBLE);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + location));
				startActivity(i);
			}
		});


	}

//	@Override
//	public boolean onCreateOptionsMenu (Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_notification_alerte, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected (MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}

	private void remplirAlerte (AlerteRecue alerteRecue) {

		((TextView) findViewById(R.id.txtTitre)).setText(alerteRecue.getSituation().getTitre());
		((TextView) findViewById(R.id.txtMessage)).setText(alerteRecue.getSituation().getMessage());

		String nom = alerteRecue.getSituation().getUser().getNom();
		String prenom = alerteRecue.getSituation().getUser().getPrenom();
		if (alerteRecue.getSituation().getUser().getNom() == null) {
			nom = "";
		}
		if (alerteRecue.getSituation().getUser().getPrenom() == null) {
			prenom = "";
		}
		String nomprenom = nom + " " + prenom;
		((TextView) findViewById(R.id.txtNomPrenom)).setText(nomprenom);

		short sexe = alerteRecue.getSituation().getUser().getSexe();
		if (sexe == 1) {
			((TextView) findViewById(R.id.txtSexe)).setText("Homme");
		}
		if (sexe == 2) {
			((TextView) findViewById(R.id.txtSexe)).setText("Femme");
		}

		((TextView) findViewById(R.id.txtTel)).setText(alerteRecue.getSituation().getUser().getTelephone());

		short grpsng = alerteRecue.getSituation().getUser().getGroupSanguin();
		if (grpsng == 0) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("Inconnu");
		}
		if (grpsng == 1) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("O+");
		}
		if (grpsng == 2) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("O-");
		}
		if (grpsng == 3) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("A+");
		}
		if (grpsng == 4) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("A-");
		}
		if (grpsng == 5) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("B+");
		}
		if (grpsng == 6) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("B-");
		}
		if (grpsng == 7) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("AB+");
		}
		if (grpsng == 8) {
			((TextView) findViewById(R.id.txtGroupeSanguin)).setText("AB-");
		}

		short diab = alerteRecue.getSituation().getUser().getDiabete();
		if (diab == 0) {
			((TextView) findViewById(R.id.txtDiabete)).setText("NON");
		}
		if (diab == 1) {
			((TextView) findViewById(R.id.txtDiabete)).setText("OUI");
		}


		short choles = alerteRecue.getSituation().getUser().getCholesterol();
		if (choles == 0) {
			((TextView) findViewById(R.id.txtCholesterol)).setText("NON");
		}
		if (choles == 1) {
			((TextView) findViewById(R.id.txtCholesterol)).setText("OUI");
		}

		((TextView) findViewById(R.id.txtautreInfo)).setText(alerteRecue.getSituation().getUser().getAutresInfos());

	}


}
