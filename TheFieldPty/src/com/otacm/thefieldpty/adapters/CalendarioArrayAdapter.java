package com.otacm.thefieldpty.adapters;

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
//import android.widget.ImageButton;
import android.widget.TextView;
import com.otacm.thefieldpty.R;
import com.otacm.thefieldpty.groups.GroupCalendario;
import com.otacm.thefieldpty.utils.AppUtils;

public class CalendarioArrayAdapter extends ArrayAdapter<GroupCalendario> {

	private final Context context;
	private Typeface font;
	private LayoutInflater inflater;
//	private ImageButton buttonRemainder;

	public CalendarioArrayAdapter(Context context, List<GroupCalendario> values) {
		super(context, R.layout.calendario_group_view, values);
		this.context = context;
		font = AppUtils.normalFont(context);
		inflater = LayoutInflater.from(context);
	}

	@SuppressLint({ "DefaultLocale", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GroupCalendario e = (GroupCalendario) this.getItem(position);
		TextView textoEquipo1;
		TextView textoEquipo2;
		TextView textoInfo;
		TextView textMarcador1;
		TextView textMarcador2;
		int id_drawable_1 = 0;
		int id_drawable_2 = 0;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.calendario_group_view, null);
			textoEquipo1 = (TextView) convertView.findViewById(R.id.textoEquipo1);
			textoEquipo1.setTypeface(font);
			textoEquipo2 = (TextView) convertView.findViewById(R.id.textoEquipo2);
			textoEquipo2.setTypeface(font);
			textoInfo = (TextView) convertView.findViewById(R.id.textoInfo);
			textMarcador1 = (TextView) convertView.findViewById(R.id.textMarcador1);
			textMarcador2 = (TextView) convertView.findViewById(R.id.textMarcador2);
//			buttonRemainder = (ImageButton) convertView.findViewById(R.id.buttonRemainder);
			
			convertView.setTag(new CalendarViewHolder(textoEquipo1, textoEquipo2, textoInfo, textMarcador1, textMarcador2));//, buttonRemainder)); 
			
		} else {
			CalendarViewHolder viewHolder = (CalendarViewHolder) convertView.getTag();
			textoEquipo1 = viewHolder.getTextoEquipo1();
			textoEquipo2 = viewHolder.getTextoEquipo2();
			textoInfo = viewHolder.getTextoInfo();
			textMarcador1 = viewHolder.getTextMarcador1();
			textMarcador2 = viewHolder.getTextMarcador2();
		}

		String nom_id1 = e.getCategoria() + "_" + e.getEquipo1();
		String nom_id2 = e.getCategoria() + "_" + e.getEquipo2();
		id_drawable_1 = AppUtils.getDrawableByName(context, nom_id1.trim().toLowerCase().replace(" ", ""));
		id_drawable_2 = AppUtils.getDrawableByName(context, nom_id2.trim().toLowerCase().replace(" ", ""));
		
		textoEquipo1.setText(e.getEquipo1());
		textoEquipo2.setText(e.getEquipo2());
		textoInfo.setText(e.getDetallePartido());
		
		if(e.getMarcadorFinal().contains("-")) {
			String array[] = e.getMarcadorFinal().split("-");
			textMarcador1.setText(array[0].trim());
			textMarcador2.setText(array[1].trim());
			System.out.println("[" + array[1].trim() + "]");
		}else {
			textMarcador1.setText("-");
			textMarcador2.setText("-");
		}
			
		if (id_drawable_1 == 0)
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textoEquipo1.setCompoundDrawablesWithIntrinsicBounds(id_drawable_1, 0, 0, 0);

		if (id_drawable_2 == 0)
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(AppUtils.getDrawableByName(context, "default_logo"), 0, 0, 0);
		else
			textoEquipo2.setCompoundDrawablesWithIntrinsicBounds(id_drawable_2, 0, 0, 0);
		
//		buttonRemainder.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				showAlert(e);
//			}
//		});
		
//		if(!e.getMarcadorFinal().equals(""))
//			buttonRemainder.setVisibility(View.GONE);
		
		return convertView;
	}
	
	public void showAlert(final GroupCalendario g) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Configurar recordatorio para " + g.getEquipo1() + " vs " + g.getEquipo2());
		builder.setTitle(context.getString(R.string.setup_alarms))
				.setCancelable(false)
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
//						buttonRemainder.setImageResource(R.drawable.ic_remainder_off);
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}
}
