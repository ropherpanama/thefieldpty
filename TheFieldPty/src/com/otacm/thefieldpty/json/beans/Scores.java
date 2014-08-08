package com.otacm.thefieldpty.json.beans;

import java.util.ArrayList;

public class Scores {
	private String liga;
	private String categoria;
	private String nomEquipo1;
	private String nomEquipo2;
	private ArrayList<String> periodos;
	private int totalPtsEquipo1;
	private int totalPtsEquipo2;
//	private int numPeriodo;
	private int idPartido;

	public int getTotalPtsEquipo1() {
		return totalPtsEquipo1;
	}

	public void setTotalPtsEquipo1(int totalPtsEquipo1) {
		this.totalPtsEquipo1 = totalPtsEquipo1;
	}

	public int getTotalPtsEquipo2() {
		return totalPtsEquipo2;
	}

	public void setTotalPtsEquipo2(int totalPtsEquipo2) {
		this.totalPtsEquipo2 = totalPtsEquipo2;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNomEquipo1() {
		return nomEquipo1;
	}

	public void setNomEquipo1(String nomEquipo1) {
		this.nomEquipo1 = nomEquipo1;
	}

	public String getNomEquipo2() {
		return nomEquipo2;
	}

	public void setNomEquipo2(String nomEquipo2) {
		this.nomEquipo2 = nomEquipo2;
	}

//	public int getPtsEquipo1() {
//		return ptsEquipo1;
//	}
//
//	public void setPtsEquipo1(int ptsEquipo1) {
//		this.ptsEquipo1 = ptsEquipo1;
//	}
//
//	public int getPtsEquipo2() {
//		return ptsEquipo2;
//	}
//
//	public void setPtsEquipo2(int ptsEquipo2) {
//		this.ptsEquipo2 = ptsEquipo2;
//	}
//
//	public int getNumPeriodo() {
//		return numPeriodo;
//	}
//
//	public void setNumPeriodo(int numPeriodo) {
//		this.numPeriodo = numPeriodo;
//	}

	public int getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public ArrayList<String> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(ArrayList<String> periodos) {
		this.periodos = periodos;
	}
	
}
