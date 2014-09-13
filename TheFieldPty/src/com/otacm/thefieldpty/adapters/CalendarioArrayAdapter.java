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
import com.otacm.thefieldpty.R;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.utils.AppUtils;

public class CalendarioArrayAdapter extends ArrayAdapter<GroupCalendario> {

	private final Context context;
	private Typeface font;
	private LayoutInflater inflater;

	public CalendarioArrayAdapter(Context context, List<GroupCalendario> values) {
		super(context, R.layout.calendario_group_view, values);
		this.context = context;
		font = AppUtils.normalFont(context);
		inflater = LayoutInflater.from(context);
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GroupCalendario e = (GroupCalendario) this.getItem(position);
		TextView textoEquipo1;
		TextView textoEquipo2;
		TextView textoInfo;
		TextView textMarcador;
		int id_drawable_1 = 0;
		int id_drawable_2 = 0;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.calendario_group_view, null);
			textoEquipo1 = (TextView) convertView.findViewById(R.id.textoEquipo1);
			textoEquipo1.setTypeface(font);
			textoEquipo2 = (TextView) convertView.findViewById(R.id.textoEquipo2);
			textoEquipo2.setTypeface(font);
			textoInfo = (TextView) convertView.findViewById(R.id.textoInfo);
			textMarcador = (TextView) convertView.findViewById(R.id.textMarcador);

			convertView.setTag(new CalendarViewHolder(textoEquipo1, textoEquipo2, textoInfo, textMarcador));
			
		} else {
			CalendarViewHolder viewHolder = (CalendarViewHolder) convertView.getTag();
			textoEquipo1 = viewHolder.getTextoEquipo1();
			textoEquipo2 = viewHolder.getTextoEquipo2();
			textoInfo = viewHolder.getTextoInfo();
			textMarcador = viewHolder.getTextMarcador();
		}

		String nom_id1 = e.getCategoria() + "_" + e.getEquipo1();
		String nom_id2 = e.getCategoria() + "_" + e.getEquipo2();
		id_drawable_1 = AppUtils.getDrawableByName(context, nom_id1.trim().toLowerCase().replace(" ", ""));
		id_drawable_2 = AppUtils.getDrawableByName(context, nom_id2.trim().toLowerCase().replace(" ", ""));
		
		textoEquipo1.setText(e.getEquipo1());
		textoEquipo2.setText(e.getEquipo2());
		textoInfo.setText(e.getDetallePartido());
		textMarcador.setText(e.getMarcadorFinal());
		
		if (id_drawable_1 == 0)
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(id_drawable_1, 0, 0, 0);

		if (id_drawable_2 == 0)
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(id_drawable_2, 0, 0, 0);
		
		return convertView;
	}
}
