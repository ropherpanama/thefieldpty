package com.otacm.thefieldpty.adapters;

import android.widget.CheckBox;
import android.widget.TextView;

public class FavoritosViewHolder {
	private TextView textHeader;
	private CheckBox checkbox;

	public FavoritosViewHolder(TextView textHeader, CheckBox checkbox) {
		super();
		this.textHeader = textHeader;
		this.checkbox = checkbox;
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
}
