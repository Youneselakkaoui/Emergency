package com.emergency.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;

import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.entity.User;

import java.util.Date;


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
		if (userManager.getUser() == null) {
			User user = new User();
			user.setTelephone("+000000000");
			user.setDateNaissance(new Date());
			user.save();
		}
		//TODO Fin bouchon user
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
