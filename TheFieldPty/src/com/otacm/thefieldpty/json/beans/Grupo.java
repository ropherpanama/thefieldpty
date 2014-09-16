package com.otacm.thefieldpty.json.beans;

import java.util.List;

public class Grupo {
	private int idCategoria;
	private String nombreGrupo;
	private List<Posiciones> posiciones;

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public List<Posiciones> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(List<Posiciones> posiciones) {
		this.posiciones = posiciones;
	}
}
