package com.otacm.thefieldpty.adapters;

import android.widget.ImageButton;
import android.widget.TextView;

public class EquiposViewHolder {
	private ImageButton imageButton;
	private TextView nameEquipo;
	private TextView detailEquipo;

	public EquiposViewHolder(ImageButton imageButton, TextView nameEquipo, TextView detailEquipo) {
		super();
		this.imageButton = imageButton;
		this.nameEquipo = nameEquipo;
		this.detailEquipo = detailEquipo;
	}

	public ImageButton getImageButton() {
		return imageButton;
	}

	public void setImageButton(ImageButton imageButton) {
		this.imageButton = imageButton;
	}

	public TextView getNameEquipo() {
		return nameEquipo;
	}

	public void setNameEquipo(TextView nameEquipo) {
		this.nameEquipo = nameEquipo;
	}

	public TextView getDetailEquipo() {
		return detailEquipo;
	}

	public void setDetailEquipo(TextView detailEquipo) {
		this.detailEquipo = detailEquipo;
	}
}
