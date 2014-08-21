package com.otacm.thefieldpty.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sample.R;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.utils.AppUtils;

public class CalendarioArrayAdapter extends ArrayAdapter<GroupCalendario>{

	private final Context context;
	private final List<GroupCalendario> values;
	private Typeface font;
	
	public CalendarioArrayAdapter(Context context, List<GroupCalendario> values) {
		super(context, R.layout.calendario_group_view, values);
		this.context = context;
		this.values = values;
		font = AppUtils.normalFont(context);
	}
	
	@SuppressLint({ "ViewHolder", "DefaultLocale" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final GroupCalendario e = values.get(position);
		
		View rowView = inflater.inflate(R.layout.calendario_group_view, parent, false);
		
		int id_drawable_1 = AppUtils.getDrawableByName(context, e.getEquipo1().trim().toLowerCase().replace(" ", ""));
		int id_drawable_2 = AppUtils.getDrawableByName(context, e.getEquipo2().trim().toLowerCase().replace(" ", ""));
		
		TextView textoEquipo1 = (TextView) rowView.findViewById(R.id.textoEquipo1);
		textoEquipo1.setText(e.getEquipo1());
		textoEquipo1.setTypeface(font);
		
		TextView textoEquipo2 = (TextView) rowView.findViewById(R.id.textoEquipo2);
		textoEquipo2.setText(e.getEquipo2());
		textoEquipo2.setTypeface(font); 
		
		if(id_drawable_1 == 0)
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"),0,0,0);
		else
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(id_drawable_1, 0, 0, 0); 
		
		if(id_drawable_2 == 0)
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"),0,0,0);
		else
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(id_drawable_2, 0, 0, 0); 
		
		return rowView;
	}
}
