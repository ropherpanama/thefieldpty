package com.otacm.thefieldpty.adapters;

import java.util.List;

import twitter4j.Status;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.otacm.thefieldpty.R;

public class TwitterAdapter extends ArrayAdapter<Status>{

	private final Context context;
	private final List<Status> values;
	private final Bitmap bmp;
	
	public TwitterAdapter(Context context, List<Status> values, Bitmap bmp) {
		super(context, R.layout.twitter_item, values);
		this.context = context;
		this.values = values;
		this.bmp = bmp;
	}

	@SuppressLint({ "ViewHolder", "DefaultLocale" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Status e = values.get(position);
			
			View rowView = inflater.inflate(R.layout.twitter_item, parent, false);
			TextView textTwitt = (TextView) rowView.findViewById(R.id.textTwitt);
			textTwitt.setText(e.getText()); 
			
			if(bmp != null) {
				ImageView userImage = (ImageView) rowView.findViewById(R.id.userImage);
				userImage.setImageBitmap(bmp); 
				userImage.setLayoutParams(new TableRow.LayoutParams(65, 65));
			}

			return rowView;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
