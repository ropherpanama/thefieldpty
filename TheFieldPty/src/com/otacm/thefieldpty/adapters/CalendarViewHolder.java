package com.otacm.thefieldpty.adapters;

//import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Se implementa el ViewHolder Design Patter para evitar leaks de memoria en los
 * eventos de Scroll
 * 
 * @author rospena
 * 
 */
public class CalendarViewHolder {
	private TextView textoEquipo1;
	private TextView textoEquipo2;
	private TextView textoInfo;
	private TextView textMarcador1;
	private TextView textMarcador2;
//	private ImageButton buttonRemainder;

	public CalendarViewHolder(TextView textoEquipo1, TextView textoEquipo2,
			TextView textoInfo, TextView textMarcador1, TextView textMarcador2) {
			//ImageButton buttonRemainder) {
		super();
		this.textoEquipo1 = textoEquipo1;
		this.textoEquipo2 = textoEquipo2;
		this.textoInfo = textoInfo;
		this.textMarcador1 = textMarcador1;
		this.textMarcador2 = textMarcador2;
//		this.buttonRemainder = buttonRemainder;
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

//	public ImageButton getButtonRemainder() {
//		return buttonRemainder;
//	}
//
//	public void setButtonRemainder(ImageButton buttonRemainder) {
//		this.buttonRemainder = buttonRemainder;
//	}

	public TextView getTextMarcador1() {
		return textMarcador1;
	}

	public void setTextMarcador1(TextView textMarcador1) {
		this.textMarcador1 = textMarcador1;
	}

	public TextView getTextMarcador2() {
		return textMarcador2;
	}

	public void setTextMarcador2(TextView textMarcador2) {
		this.textMarcador2 = textMarcador2;
	}
}
