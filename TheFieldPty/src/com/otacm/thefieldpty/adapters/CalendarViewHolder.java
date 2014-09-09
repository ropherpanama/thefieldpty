package com.otacm.thefieldpty.adapters;

import android.widget.TextView;

/**
 * Se implementa el ViewHolder Design Patter para 
 * evitar leaks de memoria en los eventos de Scroll
 * @author rospena
 *
 */
public class CalendarViewHolder {
	private TextView textoEquipo1;
	private TextView textoEquipo2;
	private TextView textoInfo;
	private TextView textMarcador;

	public CalendarViewHolder(TextView textoEquipo1, TextView textoEquipo2, TextView textoInfo, TextView textMarcador) {
		super();
		this.textoEquipo1 = textoEquipo1;
		this.textoEquipo2 = textoEquipo2;
		this.textoInfo = textoInfo;
		this.textMarcador = textMarcador;
	}

	public CalendarViewHolder() {
		super();
	}

	public TextView getTextoEquipo1() {
		return textoEquipo1;
	}

	public void setTextoEquipo1(TextView textoEquipo1) {
		this.textoEquipo1 = textoEquipo1;
	}

	public TextView getTextoEquipo2() {
		return textoEquipo2;
	}

	public void setTextoEquipo2(TextView textoEquipo2) {
		this.textoEquipo2 = textoEquipo2;
	}

	public TextView getTextoInfo() {
		return textoInfo;
	}

	public void setTextoInfo(TextView textoInfo) {
		this.textoInfo = textoInfo;
	}

	public TextView getTextMarcador() {
		return textMarcador;
	}

	public void setTextMarcador(TextView textMarcador) {
		this.textMarcador = textMarcador;
	}
}
