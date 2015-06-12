package com.emergency.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import com.emergency.dao.RecepteurSituationDao;
import com.emergency.dao.SituationDao;
import com.emergency.dao.impl.RecepteurSituationDaoImpl;
import com.emergency.dao.impl.SituationDaoImpl;
import com.emergency.dao.impl.UserDaoImpl;
import com.emergency.entity.RecepteursSituation;
import com.emergency.entity.Situation;
import com.emergency.entity.User;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class SituationCreateFragment extends Fragment {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private List<User> listUser;
    SituationDao stDao = new SituationDaoImpl();
    RecepteurSituationDao rsDao = new RecepteurSituationDaoImpl();


     EditText edit_title ;
     EditText edit_message;
     ImageButton btn_save;
     Button btn_add_contact ;
     Button btn_del_contact ;
     CheckBox choixsms  ;
     CheckBox choixntif;
     RadioButton radioHaut;
     RadioButton radioMoyen;
     RadioButton radioBas;


            String number;
    String titre;
    String message;
    short typenvoie;
    long id_sit;
    private View rootView;

    private static final int CONTACT_PICKER_RESULT = 1001;

    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static SituationCreateFragment newInstance(String param1, String param2) {
        SituationCreateFragment fragment = new SituationCreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SituationCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.frag_create_situation, container, false);
        rootView = inflater.inflate(R.layout.fragment_situation_create, container, false);


        list = (ListView) rootView.findViewById(R.id.list_contact);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayList);
        // Here, you set the data in your ListView
        list.setAdapter(adapter);


       edit_title = (EditText) rootView.findViewById(R.id.titre_label);
       edit_message = (EditText) rootView.findViewById(R.id.situation_message);
       btn_save = (ImageButton) rootView.findViewById(R.id.btn_save);
       btn_add_contact = (Button) rootView.findViewById(R.id.btn_add_contact);
       btn_del_contact = (Button) rootView.findViewById(R.id.btn_del_contact);
       choixsms = (CheckBox) rootView.findViewById(R.id.sms);
       choixntif = (CheckBox) rootView.findViewById(R.id.notif);

       radioHaut = (RadioButton) rootView.findViewById(R.id.haut);
       radioMoyen = (RadioButton) rootView.findViewById(R.id.moyen);
       radioBas = (RadioButton) rootView.findViewById(R.id.bas);

       radioMoyen.setChecked(true);
       choixntif.setChecked(true);


        btn_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, CONTACT_PICKER_RESULT);
            }
        });

        btn_del_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();
                int itemCount = list.getCount();

                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){
                        adapter.remove(arrayList.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        });





        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!controls())
                {
                    ListView listcontact = (ListView) rootView.findViewById(R.id.list_contact);
                    Situation st = new Situation();

                    if (choixsms.isChecked() && (!choixntif.isChecked())) {typenvoie = 1;}
                    if (choixsms.isChecked() && choixntif.isChecked()) {typenvoie = 2;}
                    if ((!choixsms.isChecked()) && choixntif.isChecked()) {typenvoie = 3;}

                    if (radioBas.isChecked()) {st.setNiveau(0);}
                    if (radioMoyen.isChecked()) {st.setNiveau(1);}
                    if (radioHaut.isChecked()) {st.setNiveau(2);}

                    st.setTitre(edit_title.getText().toString());
                    st.setMessage(edit_message.getText().toString());
                    st.setTypeEnvoi(typenvoie);
                    //TODO
                    st.setUser(new UserDaoImpl().selectUser());

                    stDao.insert(st);

                    for (int i = 0; i < listcontact.getAdapter().getCount(); i++) {
                        RecepteursSituation reSit = new RecepteursSituation();
                        reSit.setNumUser(listcontact.getAdapter().getItem(i).toString());
                        reSit.setSituation(st);
                        rsDao.insert(reSit);
                        System.out.print(reSit.getNumUser().toString());
                    }

                    Fragment fragment = new SituationListFragment();
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment, null).addToBackStack(null).commit();


                }
            }
        });

        return rootView;
    }

    private boolean controls(){

        boolean bol = false;
        AlertDialog.Builder ald = new AlertDialog.Builder(getActivity())
                .setTitle("Attention")
                .setIcon(R.drawable.add_icon)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        if (!StringUtils.hasText(edit_title.getText().toString())) {
            ald.setMessage("Le titre de la situation est obligatoire !").show();
           bol = true;
        }
        else if (!choixsms.isChecked() && !choixntif.isChecked())
        {
            ald.setMessage("Le type d'envoi est obligatoire !").show();
            bol = true;
        }

        else if (list.getCount() == 0)
        {
            ald.setMessage("veuillez attacher un contact !").show();
            bol = true;
        }
        return bol;
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
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

                        } else {
                            // WE FAILED
                        }
                    } catch (Exception e) {
                        // failed
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        } else {
                        }
                    }


                    arrayList.add(number.toString());
                    // next thing you have to do is check if your adapter has changed
                    adapter.notifyDataSetChanged();


            }

        }
    }

    public void titre_remplie() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Attention")
                .setMessage("Le titre de la situation est obligatoire !")
                .setIcon(R.drawable.add_icon)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction (Uri uri);
    }

}
