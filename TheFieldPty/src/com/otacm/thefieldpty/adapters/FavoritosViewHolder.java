package com.otacm.thefieldpty.adapters;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class FavoritosViewHolder {
	private TextView textHeader;
	private CheckBox checkbox;
	private ImageButton buttonRemainder;

	public FavoritosViewHolder(TextView textHeader, CheckBox checkbox, ImageButton buttonRemainder) {
		this.textHeader = textHeader;
		this.checkbox = checkbox;
		this.buttonRemainder = buttonRemainder;
	}

	public FavoritosViewHolder() {
		super();
	}

	public TextView getTextHeader() {
		return textHeader;
	}

	public void setTextHeader(TextView textHeader) {
		this.textHeader = textHeader;
	}

	public CheckBox getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}

	public ImageButton getButtonRemainder() {
		return buttonRemainder;
	}

	public void setButtonRemainder(ImageButton buttonRemainder) {
		this.buttonRemainder = buttonRemainder;
	}
}
