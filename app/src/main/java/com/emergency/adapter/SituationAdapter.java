package com.emergency.adapter;

/**
 * Created by Noureddine on 26/04/2015.
 */

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

import com.emergency.activities.R;
import com.emergency.entity.Situation;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.Random;


public class SituationAdapter extends ArrayAdapter<Situation> {

    private static final String TAG = "SituationAdapter";

    private final LayoutInflater mLayoutInflater;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public SituationAdapter(Context context, int textViewResourceId,
                            ArrayList<Situation> objects) {
        super(context, textViewResourceId, objects);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_list_item,
                    parent, false);
            vh = new ViewHolder();

            TextView tv = (TextView) convertView
                    .findViewById(R.id.situation_title);

            vh.btnDelete = (Button) convertView
                    .findViewById(R.id.delete_btn);

            vh.btnUpdate = (Button) convertView
                    .findViewById(R.id.update_btn);

            //tv.setTextColor(Color.parseColor("#ff3f3f"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv.setPadding(5, 5, 5, 5);

            tv.setText(getItem(position).getTitre());

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView titleTxt;
        Button btnUpdate;
        Button btnDelete;
    }

}