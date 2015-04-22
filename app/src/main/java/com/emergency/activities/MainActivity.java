package com.emergency.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emergency.business.DefaultSituationManager;
import com.emergency.business.SituationManager;
import com.emergency.entity.Situation;


public class MainActivity extends ActionBarActivity {
    private SharedPreferences prefs = null;
    private GridLayout gridLayout   = null;
    private TextView[] textViews    = null;
    private SituationManager situationManager;
    private String titre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        situationManager = new DefaultSituationManager(this);

        prefs = getSharedPreferences("com.emergency.app", MODE_PRIVATE);

        //Tester est ce qu'il s'agit de premier lancement de l'app
        if (prefs.getBoolean("firstRun", true)) {
            Intent goToNextActivity = new Intent(getApplicationContext(), SituationActivity.class);
            startActivity(goToNextActivity);
        } else {

            //
            gridLayout = new GridLayout(this);

            gridLayout.setOrientation(GridLayout.HORIZONTAL);
            gridLayout.setColumnCount(3);
            gridLayout.setRowCount(12);

            int lSize = situationManager.getAll().size();

            textViews = new TextView[lSize];

            for(int i =0; i < lSize; i++) {
                textViews[i] = new TextView(this);
                textViews[i].setText(situationManager.getAll().get(i).getTitre());
                textViews[i].setTextSize(25);
                textViews[i].setPadding(50, 25, 10, 25);
                gridLayout.addView(textViews[i]);
            }

            setContentView(gridLayout);

            for(int i =0; i < lSize; i++)
            {
                titre = situationManager.getAll().get(i).getTitre();
                textViews[i].setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(),
                                       titre + " Clicked",
                                       Toast.LENGTH_SHORT).show();

                    }
                });
            }
            //setContentView(gridView);

        }



    }
/*
    public void showSituations() {
        gridLayouts = new GridLayout(MainActivity.this);
        gridLayouts.setLayoutParams(new GridLayout.LayoutParams());

        gridLayouts.setOrientation(GridLayout.HORIZONTAL);

        gridLayouts.setColumnCount(4);

        gridLayouts.setRowCount(4);

        text = new TextView[16];

        for (int i = 0; i < 16; i++ ) {
            text[i] = new TextView(MainActivity.this);
            text[i].setLayoutParams(new GridLayout.LayoutParams());
            text[i].setText(String.valueOf(i));
            text[i].setTextSize(25);
            text[i].setPadding(50, 25, 10, 25);
            text[i].setText("Situation " + i);
            gridLayouts.addView(text[i]);
        }
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent goToNextActivity = null;

        switch (item.getItemId()) {

            case R.id.createSituation:
                goToNextActivity = new Intent(getApplicationContext(), SituationActivity.class);
                startActivity(goToNextActivity);
                return true;
            case R.id.persInfo:
                goToNextActivity = new Intent(getApplicationContext(), ManageUser.class);
                startActivity(goToNextActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstRun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstRun", false).commit();

        }
    }
}
