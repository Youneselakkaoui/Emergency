package com.emergency.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emergency.activities.R;

public class SituationActivity extends ActionBarActivity implements View.OnClickListener {
    private TextView titreText;
    private    EditText     descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation);

        Button btnSuivant = (Button) findViewById(R.id.btn_suivant);
        titreText = (TextView) findViewById(R.id.situation_title);
        descText = (EditText) findViewById(R.id.message);

        btnSuivant.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("SITUATION TITRE: ", titreText.getText().toString());
        Log.d("SITUATION DESC: ", descText.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_situation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
