package com.otacm.thefieldpty.groups;

public class GroupCalendario {
	private String equipo1;
	private String equipo2;
	private String detallePartido;
	private String categoria;
	private String marcadorFinal;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDetallePartido() {
		return detallePartido;
	}

	public void setDetallePartido(String detallePartido) {
		this.detallePartido = detallePartido;
	}

	public String getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}

	public String getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}

	public String getMarcadorFinal() {
		return marcadorFinal;
	}

	public void setMarcadorFinal(String marcadorFinal) {
		this.marcadorFinal = marcadorFinal;
	}
}