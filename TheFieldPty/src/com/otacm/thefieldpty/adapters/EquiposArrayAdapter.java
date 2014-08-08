package com.otacm.thefieldpty.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample.R;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.database.daos.FavoritosDAO;
import com.otacm.thefieldpty.json.beans.Equipos;
import com.otacm.thefieldpty.utils.AppUtils;

public class EquiposArrayAdapter extends ArrayAdapter<Equipos> {
	private final Context context;
	private final List<Equipos> values;
	private FavoritosDAO dao;
	
	public EquiposArrayAdapter(Context context, List<Equipos> values) {
		super(context, R.layout.list_equipos_item, values);
		this.context = context;
		this.values = values;
		dao = new FavoritosDAO(context);
	}
	
	@SuppressLint({ "ViewHolder", "DefaultLocale" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final Equipos e = values.get(position);
		
		View rowView = inflater.inflate(R.layout.list_equipos_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.nameEquipo);
		textView.setText(e.getNombre()); 
		textView.setTypeface(AppUtils.normalFont(context)); 
		
		int id_drawable = AppUtils.getDrawableByName(context, e.getNombre().trim().toLowerCase().replace(" ", ""));
		
		if(id_drawable == 0)
			textView.setCompoundDrawablesWithIntrinsicBounds(0, AppUtils.getDrawableByName(context, "default_logo"), 0, 0);
		else
			textView.setCompoundDrawablesWithIntrinsicBounds(0, id_drawable, 0, 0); 
		
		final ImageButton button = (ImageButton)rowView.findViewById(R.id.imageButton);
 
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					System.out.println("Checked " + e.getNombre());
					Favoritos f = new Favoritos();
					f.setNombre(e.getNombre());
					dao.insertFavorito(f);
					Toast.makeText(context, e.getNombre() + " guardado", Toast.LENGTH_SHORT).show();
					button.setEnabled(false);
				}catch(android.database.sqlite.SQLiteConstraintException ex) {
					Toast.makeText(context, e.getNombre() + " ya existe", Toast.LENGTH_SHORT).show();
				}
			}
		});
		   
		return rowView;
	}
}