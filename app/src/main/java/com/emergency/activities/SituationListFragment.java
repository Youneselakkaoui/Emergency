package com.emergency.activities;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.emergency.dao.impl.SituationDaoImpl;
import com.emergency.entity.Situation;

import java.util.List;

public class SituationListFragment extends Fragment implements AbsListView.OnItemClickListener {


	public static final String INFO_SITUATION_FRAGMENT = "infoSituationFragment";
    SituationDaoImpl sitDao = new SituationDaoImpl();
	//AlerteRecueManager alerteManager = new AlerteRecueManagerImpl();
	List<Situation> situations;

	Fragment infoSituationFragment;
	private AbsListView mListView;
	private SituationAdapter mAdapter;
    ImageButton btn_add;

	public SituationListFragment() {
	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getFragmentManager().findFragmentByTag(INFO_SITUATION_FRAGMENT) == null) {
            infoSituationFragment = new SituationListFragment();
            infoSituationFragment.setArguments(getActivity().getIntent().getExtras());
		}
		else {
            infoSituationFragment = getFragmentManager().findFragmentByTag(INFO_SITUATION_FRAGMENT);
		}

	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {

        situations = sitDao.findAll();
		View view = inflater.inflate(R.layout.fragment_situation_list, container, false);

		// Set the adapter
		//mListView = (AbsListView) view.findViewById(android.R.id.list);
        //mListView = (AbsListView) view.findViewById(R.id.liste_situations);
        mListView = (ListView) view.findViewById(R.id.liste_situations);
        mAdapter = new SituationAdapter(getActivity(), R.layout.fragment_situation_list, situations);
		mListView.setAdapter(mAdapter);

		// Set OnItemClickListener so we can be notified on item clicks
		mListView.setOnItemClickListener(this);

        btn_add = (ImageButton) view.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SituationCreateFragment();
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, null).addToBackStack(null).commit();
            }
        });

        mAdapter.notifyDataSetChanged();
		return view;
	}


    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        Situation item = this.situations.get(position);
        Bundle bundle = new Bundle();
        bundle.putLong("idSituation", item.getId());
        infoSituationFragment = new SituationCrudFragment();
        infoSituationFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, infoSituationFragment, INFO_SITUATION_FRAGMENT).addToBackStack(null).commit();

//        Toast.makeText(getActivity(), item.getIdSituation() + " Clicked!"
//                , Toast.LENGTH_SHORT).show();
    }


}
