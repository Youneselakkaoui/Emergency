package com.emergency.activities;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;


import com.emergency.adapter.AlertesRecuesAdapter;
import com.emergency.business.AlerteRecueManager;
import com.emergency.business.impl.AlerteRecueManagerImpl;
import com.emergency.entity.AlerteRecue;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class AlerteListItemFragment extends Fragment implements AbsListView.OnItemClickListener {


	public static final String INFO_ALERTE_FRAGMENT = "infoAlerteFragment";
	AlerteRecueManager alerteManager = new AlerteRecueManagerImpl();
	List<AlerteRecue> alertes;
	Fragment infoAlerteFragment;
	//private OnFragmentInteractionListener mListener;

	/**
	 * The fragment's ListView/GridView.
	 */
	private AbsListView mListView;

	/**
	 * The Adapter which will be used to populate the ListView/GridView with
	 * Views.
	 */
	private AlertesRecuesAdapter mAdapter;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public AlerteListItemFragment () {
	}

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		if (getFragmentManager().findFragmentByTag(INFO_ALERTE_FRAGMENT) == null) {
			infoAlerteFragment = new InfoAlerte();
			infoAlerteFragment.setArguments(getActivity().getIntent().getExtras());
		}
		else {
			infoAlerteFragment = getFragmentManager().findFragmentByTag(INFO_ALERTE_FRAGMENT);
		}



	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_alertelistitem, container, false);
		alertes = alerteManager.getAll();
		mAdapter = new AlertesRecuesAdapter(getActivity(), R.layout.fragment_alertelistitem_list, alertes);
		// Set the adapter
		mListView = (AbsListView) view.findViewById(android.R.id.list);
		mListView.setAdapter(mAdapter);

		// Set OnItemClickListener so we can be notified on item clicks
		mListView.setOnItemClickListener(this);

		return view;
	}


	@Override
	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
		AlerteRecue item = this.alertes.get(position);
		Bundle bundle = new Bundle();
		bundle.putLong("idAlerte", item.getId());
		infoAlerteFragment = new InfoAlerte();
		infoAlerteFragment.setArguments(bundle);

		FragmentManager fragmentManager = getActivity().getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, infoAlerteFragment, INFO_ALERTE_FRAGMENT).commit();
	}

	@Override
	public void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		Fragment onTopFragment = getFragmentManager().findFragmentById(R.id.container);
		if (!(onTopFragment instanceof InfoAlerte)) {
			infoAlerteFragment = null;
		}
	}


}
