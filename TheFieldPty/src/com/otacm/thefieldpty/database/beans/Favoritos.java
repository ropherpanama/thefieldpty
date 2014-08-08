package com.otacm.thefieldpty.database.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "favoritos")
public class Favoritos {
	@DatabaseField(columnName = "id", id = true, useGetSet = true, generatedId = true) private int id;
	@DatabaseField(columnName = "nombre", useGetSet = true, canBeNull = false) private String nombre;

	private boolean selected = false;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Favoritos() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
