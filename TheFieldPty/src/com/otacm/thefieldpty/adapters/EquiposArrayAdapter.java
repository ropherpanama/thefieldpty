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

import com.otacm.thefieldpty.R;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.database.daos.FavoritosDAO;
import com.otacm.thefieldpty.json.beans.Equipos;
import com.otacm.thefieldpty.utils.AppUtils;

public class EquiposArrayAdapter extends ArrayAdapter<Equipos> {
	private final Context context;
	private FavoritosDAO dao;
	private final ArrayAdapter<Equipos> arrayAdapter = this;
	private LayoutInflater inflater;
	
	public EquiposArrayAdapter(Context context, List<Equipos> values) {
		super(context, R.layout.list_equipos_item, values);
		inflater = LayoutInflater.from(context);
		this.context = context;
		dao = new FavoritosDAO(context);
	}
	
	@SuppressLint({ "ViewHolder", "DefaultLocale", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Equipos e = (Equipos) this.getItem(position);
		ImageButton imageButton;
		TextView nameEquipo;
		TextView detailEquipo;
		
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.list_equipos_item, null);
			nameEquipo = (TextView) convertView.findViewById(R.id.nameEquipo);
			nameEquipo.setTypeface(AppUtils.normalFont(context)); 
			detailEquipo = (TextView) convertView.findViewById(R.id.detailEquipo);
			imageButton = (ImageButton)convertView.findViewById(R.id.imageButton);
	 
			convertView.setTag(new EquiposViewHolder(imageButton, nameEquipo, detailEquipo));
			
		}else {
			EquiposViewHolder viewHolder = (EquiposViewHolder) convertView.getTag();
			imageButton = viewHolder.getImageButton();
			nameEquipo = viewHolder.getNameEquipo();
			detailEquipo = viewHolder.getDetailEquipo();
		}
		
		nameEquipo.setText(e.getNombre());
		detailEquipo.setText(e.getLiga() + ": " + e.getCategoria());
		String nom_id = e.getCategoria() + "_" + e.getNombre();
		int id_drawable = AppUtils.getDrawableByName(context, nom_id.trim().toLowerCase().replace(" ", ""));
		
		if(id_drawable == 0)
			nameEquipo.setCompoundDrawablesWithIntrinsicBounds(0, AppUtils.getDrawableByName(context, "default_logo"), 0, 0);
		else
			nameEquipo.setCompoundDrawablesWithIntrinsicBounds(0, id_drawable, 0, 0);
		
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					System.out.println("Checked " + e.getNombre());
					Favoritos f = new Favoritos();
					f.setNombre(e.getNombre());
					f.setCategoria(e.getCategoria());
					dao.insertFavorito(f);
					Toast.makeText(context, e.getNombre() + " " + context.getString(R.string.saved), Toast.LENGTH_SHORT).show();
					arrayAdapter.remove(e); //Remueve de la lista lo que se selecciona
				}catch(android.database.sqlite.SQLiteConstraintException ex) {
					Toast.makeText(context, e.getNombre() + " ya existe", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		return convertView;
	}
}