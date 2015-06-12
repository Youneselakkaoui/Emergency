package com.emergency.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emergency.activities.R;
import com.emergency.entity.AlerteRecue;
import com.emergency.entity.Situation;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elmehdiharabida on 03/06/15.
 */
public class AlertesRecuesAdapter extends ArrayAdapter<AlerteRecue> {
	LayoutInflater mInflater;

	public AlertesRecuesAdapter (Context context, int textViewResourceId,
	                             List<AlerteRecue> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView (int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();
		View viewToUse = null;// = mInflater.inflate(R.layout.fragment_alertelistitem_list,null);
		AlerteRecue item = getItem(position);
		if (convertView == null) {

			viewToUse = mInflater.inflate(R.layout.fragment_alertelistitem_list, null);


			holder = new ViewHolder();
			holder.titleText = (TextView) viewToUse.findViewById(R.id.alerteRow);
			holder.detailText = (TextView) viewToUse.findViewById(R.id.detailsAlerteRow);
			viewToUse.setTag(holder);
		}
		else {
			viewToUse = convertView;
			holder = (ViewHolder) viewToUse.getTag();
		}
		holder.titleText.setText(item.getSituation().getTitre());
		holder.detailText.setText(getContext().getString(R.string.alert_received,  DateFormat.getInstance().format(item.getDateEnvoi())));
		if (!item.isLue()) {
			holder.titleText.setTextColor(Color.RED);
			holder.detailText.setTextColor(Color.RED);
			holder.titleText.setTypeface(null, Typeface.BOLD);
			holder.detailText.setTypeface(null, Typeface.BOLD_ITALIC);
		}
		else{
			holder.titleText.setTextColor(Color.BLACK);
			holder.detailText.setTextColor(Color.BLACK);
			holder.titleText.setTypeface(null, Typeface.NORMAL);
			holder.detailText.setTypeface(null, Typeface.NORMAL);
		}
		return viewToUse;
	}

	/**
	 * Holder for the list items.
	 */
	private class ViewHolder {
		TextView titleText;
		TextView detailText;
	}

	@Override
	public boolean isEnabled (int position) {
		return true;
	}
}
