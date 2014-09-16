package com.otacm.thefieldpty.json.beans;

public class Posiciones {
	private String nombreEquipo;
	private int idCategoria;
	private String nombreGrupo;
	private int cantJG;
	private int cantJP;
	private int cantJE;
	private int cantPts;
	private int cantJuegos;

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

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

	public int getCantJG() {
		return cantJG;
	}

	public void setCantJG(int cantJG) {
		this.cantJG = cantJG;
	}

	public int getCantJP() {
		return cantJP;
	}

	public void setCantJP(int cantJP) {
		this.cantJP = cantJP;
	}

	public int getCantJE() {
		return cantJE;
	}

	public void setCantJE(int cantJE) {
		this.cantJE = cantJE;
	}

	public int getCantPts() {
		return cantPts;
	}

	public void setCantPts(int cantPts) {
		this.cantPts = cantPts;
	}

	public int getCantJuegos() {
		return cantJuegos;
	}

	public void setCantJuegos(int cantJuegos) {
		this.cantJuegos = cantJuegos;
	}
}
