package com.emergency.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.emergency.adapter.SampleAdapter;
import com.emergency.adapter.SituationAdapter;
import com.emergency.business.DefaultSituationManager;
import com.emergency.business.SituationManager;
import com.emergency.entity.Situation;
import com.rey.material.widget.Button;

import java.util.ArrayList;

public class ManageSituation extends Fragment {
    private SituationManager situationManager;
    private ArrayList<Situation> situations;
    private ListView situationsListView;
    private SituationAdapter mAdapter;

    public ManageSituation() {
        situationManager = new DefaultSituationManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.manage_situation_fragment, container, false);

        situationsListView = (ListView) rootView.findViewById(R.id.situations_list);

        Button btnAdd = (Button) rootView.findViewById(R.id.add_btn);

        mAdapter = new SituationAdapter(getActivity(), R.layout.row_list_item,
                (ArrayList<Situation>) situationManager.getAll());

        situationsListView.setAdapter(mAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNextActivity = new Intent(getActivity().getApplicationContext(), SituationActivity.class);
                startActivity(goToNextActivity);
            }
        });

        return rootView;
    }
}
