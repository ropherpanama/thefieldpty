package com.otacm.thefieldpty.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sample.R;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.utils.AppUtils;

public class FavoritosAdapter extends ArrayAdapter<Favoritos>{
	private LayoutInflater inflater;
	private Context context;
	private Typeface font;
	
	public FavoritosAdapter(Context context, List<Favoritos> favoritos) {
		super(context, R.layout.favoritos_list_item, R.id.delete_favorito, favoritos);
		inflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	@SuppressLint({ "DefaultLocale", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		font = AppUtils.normalFont(context);
		Favoritos f = this.getItem(position);
		TextView textHeader;
		CheckBox checkbox;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.favoritos_list_item, null);
			textHeader = (TextView) convertView.findViewById(R.id.text_head);
			checkbox = (CheckBox) convertView.findViewById(R.id.delete_favorito);
			textHeader.setTypeface(font, Typeface.ITALIC);
			
			convertView.setTag(new FavoritosViewHolder(textHeader, checkbox));
			
			checkbox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Favoritos f = (Favoritos) cb.getTag();
					f.setSelected(cb.isChecked());
				}
			});
			
		}else{
			FavoritosViewHolder viewHolder = (FavoritosViewHolder) convertView.getTag();
			textHeader = viewHolder.getTextHeader();
			checkbox = viewHolder.getCheckbox();
		}
		
		textHeader.setText(f.getNombre());
		checkbox.setTag(f);
		checkbox.setChecked(f.isSelected());
		
		return convertView;
	}
}
