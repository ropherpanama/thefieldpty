package com.otacm.thefieldpty.adapters;

import java.util.List;

import twitter4j.Status;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sample.R;

public class TwitterAdapter extends ArrayAdapter<Status>{

	private final Context context;
	private final List<Status> values;
	
	public TwitterAdapter(Context context, List<Status> values) {
		super(context, R.layout.twitter_item, values);
		this.context = context;
		this.values = values;
	}

	@SuppressLint({ "ViewHolder", "DefaultLocale" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final Status e = values.get(position);
		View rowView = inflater.inflate(R.layout.twitter_item, parent, false);
		TextView textTwitt = (TextView) rowView.findViewById(R.id.textTwitt);
		textTwitt.setText(e.getText()); 
		return rowView;
	}
}
