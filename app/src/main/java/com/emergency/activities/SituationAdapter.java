package com.emergency.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emergency.activities.R;
import com.emergency.entity.AlerteRecue;
import com.emergency.entity.Situation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elmehdiharabida on 03/06/15.
 */
public class SituationAdapter extends ArrayAdapter<Situation> {
	LayoutInflater mInflater;

	public SituationAdapter(Context context, int textViewResourceId,
	                        List<Situation> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView (int position, View convertView, ViewGroup parent) {


		ViewHolder holder = new ViewHolder();
		View rootview = null;// = mInflater.inflate(R.layout.fragment_alertelistitem_list,null);
		Situation item = getItem(position);
		if (convertView == null) {

			rootview = mInflater.inflate(R.layout.list_item_situation, null);

			holder = new ViewHolder();
			holder.titleText = (TextView)rootview.findViewById(R.id.txtSituation);
			rootview.setTag(holder);
		} else {
			rootview = convertView;
			holder = (ViewHolder) rootview.getTag();
		}
		holder.titleText.setText(item.getTitre().toString());
		return rootview;
	}

	/**
	 * Holder for the list items.
	 */
	private class ViewHolder{
		TextView titleText;
	}
	@Override
	public boolean isEnabled(int position)
	{
		return true;
	}
}
