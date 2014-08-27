package com.otacm.thefieldpty.json.beans;

public class TodayScores {
	private String equipo1;
	private String equipo2;
	private Integer pts1;
	private Integer pts2;
	private String status;
	private String hora;
	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public Integer getPts1() {
		return pts1;
	}

	public void setPts1(Integer pts1) {
		this.pts1 = pts1;
	}

	public Integer getPts2() {
		return pts2;
	}

	public void setPts2(Integer pts2) {
		this.pts2 = pts2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
