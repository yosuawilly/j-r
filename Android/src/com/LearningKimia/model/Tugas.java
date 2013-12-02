package com.LearningKimia.model;

import java.io.Serializable;

public class Tugas implements Serializable{
	private static final long serialVersionUID = -445985990370593876L;
	private int id_tugas;
	private String judul_tugas;
	private String catatan;

	public Tugas() {
		// TODO Auto-generated constructor stub
	}
	
	public Tugas(int id_tugas, String judul_tugas, String catatan){
		this.id_tugas = id_tugas;
		this.judul_tugas = judul_tugas;
		this.catatan = catatan;
	}

	public int getId_tugas() {
		return id_tugas;
	}

	public void setId_tugas(int id_tugas) {
		this.id_tugas = id_tugas;
	}

	public String getJudul_tugas() {
		return judul_tugas;
	}

	public void setJudul_tugas(String judul_tugas) {
		this.judul_tugas = judul_tugas;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}
	
}
