package com.otacm.thefieldpty.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.otacm.thefieldpty.R;
import com.otacm.thefieldpty.database.beans.Favoritos;
import com.otacm.thefieldpty.database.beans.Remainder;
import com.otacm.thefieldpty.database.daos.FavoritosDAO;
import com.otacm.thefieldpty.database.daos.RemaindersDAO;
import com.otacm.thefieldpty.remainders.EngyneRemainders;
import com.otacm.thefieldpty.utils.AppUtils;

public class FavoritosAdapter extends ArrayAdapter<Favoritos>{
	private LayoutInflater inflater;
	private Context context;
	private Typeface font;
	private ImageButton buttonRemainder;
	private RemaindersDAO rdao;
	private FavoritosDAO fdao;
	
	public FavoritosAdapter(Context context, List<Favoritos> favoritos) {
		super(context, R.layout.favoritos_list_item, R.id.delete_favorito, favoritos);
		inflater = LayoutInflater.from(context);
		this.context = context;
		rdao = new RemaindersDAO(context);
		fdao = new FavoritosDAO(context);
	}
	
	@SuppressLint({ "DefaultLocale", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		font = AppUtils.normalFont(context);
		final Favoritos f = this.getItem(position);
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
			
			buttonRemainder = (ImageButton) convertView.findViewById(R.id.buttonRemainder);
			
			if(f.getRemainder() == 1)
				buttonRemainder.setImageResource(R.drawable.ic_remainder_on);
			
			buttonRemainder.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					showAlert(f);
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
		
		String nom_id = f.getCategoria() + "_" + f.getNombre();
		
		int id_drawable = AppUtils.getDrawableByName(context, nom_id.trim().toLowerCase().replace(" ", ""));
		
		if(id_drawable == 0)
			textHeader.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textHeader.setCompoundDrawablesWithIntrinsicBounds(id_drawable, 0, 0, 0); 
		
		return convertView;
	}
	
	/**
	 * Muestra una alerta de confirmacion para el usuario
	 * para programar alarmas para el favorito
	 * @param description
	 */
	public void showAlert(final Favoritos f) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		//Se necesita contar con el objeto mas actual de la base de datos, no de la lista actual
		final Favoritos realFav = fdao.getFavoritoByID(f.getId());
		
		if(realFav.getRemainder() == 0)
        	builder.setMessage(String.format(context.getString(R.string.add_remainder_msg), realFav.getNombre()));
        else
        	builder.setMessage(String.format(context.getString(R.string.rem_remainder_msg), realFav.getNombre())); 
		
        builder.setTitle("Programar alarmas")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                	public void onClick(DialogInterface dialog, int id) {
                		if(realFav.getRemainder() == 0) {
                			List<Remainder> rems = new ArrayList<Remainder>();
                    		rems = EngyneRemainders.getRemaindersByTeam(context, realFav.getNombre(), realFav.getId());
                    		
                    		if(rems != null && rems.size() > 0) {
                    			for(Remainder r : rems)
                        			rdao.insertRemainder(r);
                        		rems = null;
                        		
                        		fdao.updateRemainder(realFav.getId(), 1);//Se activa el ind de remainder 
                        		buttonRemainder.setImageResource(R.drawable.ic_remainder_on);
                        		Toast.makeText(context, context.getString(R.string.set_alarms), Toast.LENGTH_SHORT).show();
                    		}else
                    			Toast.makeText(context, context.getString(R.string.no_alarms) + rems, Toast.LENGTH_SHORT).show();
                		}else {
                			rdao.deleteRemaindersByFavId(realFav.getId());  
                			fdao.updateRemainder(realFav.getId(), 0);//Se desactiva el ind de remainder
                			buttonRemainder.setImageResource(R.drawable.ic_remainder_off);
                			Toast.makeText(context, String.format(context.getString(R.string.unset_alarms), realFav.getNombre()), Toast.LENGTH_SHORT).show();
                		}
                		
                		dialog.cancel();
                	}
                });
        
        AlertDialog alert = builder.create();
        alert.show();
	}
}
