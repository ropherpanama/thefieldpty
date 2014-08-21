package com.otacm.thefieldpty.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sample.R;
import com.otacm.thefieldpty.json.beans.TodayScores;
import com.otacm.thefieldpty.utils.AppUtils;

@SuppressLint({ "DefaultLocale", "InflateParams" })
public class TodayScoresAdapter extends ArrayAdapter<TodayScores> {
	private Context context;
	private final List<TodayScores> values;

	public TodayScoresAdapter(Context context, List<TodayScores> values) {
		super(context, R.layout.score_group_view, values);
		this.context = context;
		this.values = values;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TodayScores group = values.get(position);
		View rowView = inflater.inflate(R.layout.score_group_view, parent, false);

		TextView textEquipo1 = (TextView) rowView.findViewById(R.id.textEquipo1);
		textEquipo1.setText(group.getEquipo1());
		textEquipo1.setTypeface(AppUtils.normalFont(context));

		int id_drawable_1 = AppUtils.getDrawableByName(context, group.getEquipo1().trim().toLowerCase().replace(" ", ""));
		int id_drawable_2 = AppUtils.getDrawableByName(context, group.getEquipo2().trim().toLowerCase().replace(" ", ""));

		if (id_drawable_1 == 0)
			textEquipo1.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textEquipo1.setCompoundDrawablesWithIntrinsicBounds(id_drawable_1, 0, 0, 0);

		TextView textEquipo2 = (TextView) rowView.findViewById(R.id.textEquipo2);
		textEquipo2.setText(group.getEquipo2());
		textEquipo2.setTypeface(AppUtils.normalFont(context));

		if (id_drawable_2 == 0)
			textEquipo2.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textEquipo2.setCompoundDrawablesWithIntrinsicBounds(id_drawable_2, 0, 0, 0);
		
		TextView textPts1 = (TextView) rowView.findViewById(R.id.textPts1);
		textPts1.setTypeface(AppUtils.normalFont(context));
		if(group.getPts1() == null) textPts1.setText("-");
		else textPts1.setText(String.valueOf(group.getPts1()));
		
		TextView textPts2 = (TextView) rowView.findViewById(R.id.textPts2);
		textPts2.setTypeface(AppUtils.normalFont(context));
		if(group.getPts2() == null) textPts2.setText("-");
		else textPts2.setText(String.valueOf(group.getPts2()));
		
		TextView textStatus = (TextView) rowView.findViewById(R.id.textStatus);
		textStatus.setText(group.getStatus());

		return rowView;
	}
}
