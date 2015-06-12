package com.emergency.activities;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;

import com.emergency.business.UserManager;
import com.emergency.business.impl.SituationManagerImpl;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.entity.RecepteursSituation;
import com.emergency.entity.Situation;
import com.emergency.entity.User;
import com.emergency.util.SyncUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class WelcomeActivity extends Activity {


	private UserManager userManager;

	Context context;

	public WelcomeActivity () {
		userManager = new UserManagerImpl();
		//

	}


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getApplicationContext();


		//TODO Bouchon User
//		if (userManager.getUser() == null) {
//			User user = new User();
//			user.setTelephone("+000000000");
//			user.setDateNaissance(new Date());
//			user.save();
//		}

//		if (new SituationManagerImpl().getAll() == null || new SituationManagerImpl().getAll().size() == 0) {
//			User user = new UserManagerImpl().getUser();
//			if (user != null)
//
//			{
//				Situation s1 = new Situation();
//
//				s1.setTitre("situation 1");
//				s1.setIdSituation(1);
//				s1.setTypeEnvoi((short) 1);
//				s1.setUser(user);
//				s1.setMessage("message situation 1");
//				s1.setDateLastModif(new Date());
//				s1.setDateSync(new Date(s1.getDateLastModif().getTime() - 1000L));
//				List<RecepteursSituation> recepteurs = new ArrayList<>();
//				RecepteursSituation r1s1 = new RecepteursSituation();
//				r1s1.setNumUser("0665642637");
//				r1s1.setSituation(s1);
//				recepteurs.add(r1s1);
//				RecepteursSituation r2s1 = new RecepteursSituation();
//				r2s1.setNumUser("0635143396");
//				r2s1.setSituation(s1);
//				recepteurs.add(r2s1);
//				s1.save();
//				r1s1.save();
//				r2s1.save();
//			}
//		}
//		if (new SituationManagerImpl().getAll() == null || new SituationManagerImpl().getAll().size() == 1) {
//			User user = new UserManagerImpl().getUser();
//			if (user != null)
//
//			{
//				Situation s2 = new Situation();
//
//				s2.setTitre("situation 2");
//				s2.setIdSituation(1);
//				s2.setTypeEnvoi((short) 1);
//				s2.setUser(user);
//				s2.setMessage("message situation 1");
//				s2.setDateLastModif(new Date());
//				s2.setDateSync(new Date(s2.getDateLastModif().getTime() - 1000L));
//				List<RecepteursSituation> recepteurs = new ArrayList<>();
//				RecepteursSituation r1s2 = new RecepteursSituation();
//				r1s2.setNumUser("0665642637");
//				r1s2.setSituation(s2);
//				recepteurs.add(r1s2);
//				RecepteursSituation r2s2 = new RecepteursSituation();
//				r2s2.setNumUser("0660318974");
//				r2s2.setSituation(s2);
//				recepteurs.add(r2s2);
//				s2.save();
//				r1s2.save();
//				r2s2.save();
//			}
//		}
		//TODO Fin bouchon user

		SyncUtils.CreateSyncAccount(this);

		checkUserRegistered();

	}

	private void checkUserRegistered () {
		if (userManager.getUser() != null) {
			startActivity(new Intent(this, MainActivity.class));
		}
		else {
			startActivity(new Intent(this, SignupActivity.class));
		}
		finish();
	}


}
