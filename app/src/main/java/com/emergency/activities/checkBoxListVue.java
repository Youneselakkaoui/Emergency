package com.emergency.activities;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

public class checkBoxListVue extends ListActivity {
    private ListView list;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Récupération automatique de la liste (l'id de cette liste est nommé obligatoirement @android:id/list afin d'être détecté)
        list = getListView();

        // Création de la ArrayList qui nous permettra de remplir la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;

        map = new HashMap<String, String>();
        map.put("nom", "Mouse");
        map.put("prenom", "Mickey");
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("nom", "Bunny");
        map.put("prenom", "Bugs");
        listItem.add(map);

        //Utilisation de notre adaptateur qui se chargera de placer les valeurs de notre liste automatiquement et d'affecter un tag à nos checkbox

        MyList mSchedule = new MyList(this.getBaseContext(), listItem,
                R.layout.list_detail, new String[]{"nom", "prenom"}, new int[]{
                R.id.nom, R.id.telephone});

        // On attribue à notre listView l'adaptateur que l'on vient de créer
        list.setAdapter(mSchedule);
    }

    //Fonction appelée au clic d'une des checkbox
    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        //on récupère la position à l'aide du tag défini dans la classe MyList
        int position = Integer.parseInt(cb.getTag().toString());

        // On récupère l'élément sur lequel on va changer la couleur
        View o = list.getChildAt(position).findViewById(
                R.id.blocCheck);

        //On change la couleur
        if (cb.isChecked()) {
            o.setBackgroundResource(R.color.green);
        } else {
            o.setBackgroundResource(R.color.blue);
        }
    }
}
