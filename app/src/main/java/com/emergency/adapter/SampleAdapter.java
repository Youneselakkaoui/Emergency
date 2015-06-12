package com.emergency.adapter;

/**
 * Created by Noureddine on 26/04/2015.
 */

import com.emergency.activities.R;
import com.emergency.entity.Situation;
import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class SampleAdapter extends ArrayAdapter<Situation> {

	private static final String TAG = "SampleAdapter";

	private final LayoutInflater mLayoutInflater;
	private final Random mRandom;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

	public SampleAdapter (Context context, int textViewResourceId,
	                      ArrayList<Situation> objects) {
		super(context, textViewResourceId, objects);
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mRandom = new Random();
	}

	@Override
	public View getView (final int position, View convertView,
	                     final ViewGroup parent) {

		ViewHolder vh;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.row_grid_item,
					parent, false);
			vh = new ViewHolder();
			vh.imgView = (DynamicHeightImageView) convertView
					.findViewById(R.id.imgView);
			TextView tv = (DynamicHeightTextView) convertView
					.findViewById(R.id.txtView);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			tv.setPadding(5, 5, 5, 5);
			tv.setText(getItem(position).getTitre());


			convertView.setTag(vh);
		}
		else {
			vh = (ViewHolder) convertView.getTag();
		}

		double positionHeight = getPositionRatio(position, getItem(position).getNiveau());

		vh.imgView.setHeightRatio(positionHeight);
		switch (getItem(position).getNiveau()) {
			case 2:
				//convertView.getBackground().setColorFilter(Color.parseColor("#FF3D3D"), PorterDuff.Mode.DARKEN);
				convertView.getBackground().setColorFilter(Color.parseColor("#FF7171"), PorterDuff.Mode.DARKEN);
				break;
			case 1:
				//convertView.getBackground().setColorFilter(Color.parseColor("#FF6600"), PorterDuff.Mode.DARKEN);
				convertView.getBackground().setColorFilter(Color.parseColor("#FFB871"), PorterDuff.Mode.DARKEN);
				break;
			case 0:
				//convertView.getBackground().setColorFilter(Color.parseColor("#99FF66"), PorterDuff.Mode.DARKEN);
				convertView.getBackground().setColorFilter(Color.parseColor("#FFFF71"), PorterDuff.Mode.DARKEN);
				break;
			default :
				convertView.getBackground().setColorFilter(Color.parseColor(getRandomColor()), PorterDuff.Mode.DARKEN);
		}
		//convertView.getBackground().setColorFilter(Color.parseColor(getRandomColor()), PorterDuff.Mode.DARKEN);
		//ImageLoader.getInstance().displayImage(getItem(position), vh.imgView);

		return convertView;
	}

	static class ViewHolder {
		DynamicHeightImageView imgView;
	}

	private double getPositionRatio (final int position, int niveau) {
		double ratio = 0.0; // sPositionHeightRatios.get(position, 0.0);
		// if not yet done generate and stash the columns height
		// in our real world scenario this will be determined by
		// some match based on the known height and width of the image
		// and maybe a helpful way to get the column height!
		if (ratio == 0) {
			ratio = getRandomHeightRatio(niveau);
			sPositionHeightRatios.append(position, ratio);
			Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio (int niveau) {
		return (((2 * niveau + 1.0) / 5.0) / 2.0) + 1.0; // height will be 1.0 - 1.5
		// the width
	}

	private String getRandomColor () {
		String[] colors = new String[]{"#DADADA", "#B1F8F4", "#D2D2FF", "#BEAAFB", "#99FF00"};
		return colors[mRandom.nextInt(5)];
	}
}