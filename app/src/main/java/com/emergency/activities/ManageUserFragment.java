package com.emergency.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.emergency.business.AsyncWsCaller;
import com.emergency.business.OnTaskCompleted;
import com.emergency.emergency.dto.ManageUserIn;
import com.emergency.emergency.dto.ManageUserOut;
import com.emergency.emergency.dto.UserDTO;
import com.emergency.emergency.util.EmergencyConstants;

public class ManageUserFragment extends Fragment  implements OnTaskCompleted<ManageUserOut> {
	private View rootView;

	public ManageUserFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_manage_user, container, false);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.bloodtype_spinner);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.manage_user_bloodtypes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return rootView;
    }

    public void saveUser(View v){
        new AsyncWsCaller<ManageUserIn,ManageUserOut>(this,getUser(),ManageUserOut.class,
                                                      EmergencyConstants.MANAGE_USER_URL).execute();
    }

    public void onTaskCompleted(ManageUserOut manageUserOut){

    }
    private ManageUserIn getUser() {
        ManageUserIn manageUserIn = new ManageUserIn();
        manageUserIn.setCodeFonction((short)1);
        manageUserIn.setUserDTO(new UserDTO());

        manageUserIn.getUserDTO().setTelephone(String.valueOf((
                (TextView) rootView.findViewById(R.id.textTelephone)).getText()));
        manageUserIn.getUserDTO().setNom(String.valueOf((
                (TextView) rootView.findViewById(R.id.textNom)).getText()));
        manageUserIn.getUserDTO().setPrenom(String.valueOf((
                (TextView) rootView.findViewById(R.id.textprenom)).getText()));
        //manageUserIn.getUserDTO().setDateNaissance(String.valueOf(((TextView) findViewById(R.id.textDtNaiss)).getText()));
        return manageUserIn;
    }
}
