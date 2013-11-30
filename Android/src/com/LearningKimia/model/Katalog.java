package com.LearningKimia.model;

import java.io.Serializable;

public class Katalog implements Serializable{
	private static final long serialVersionUID = 7829001067626559486L;
	private int id_katalog;
	private String nama;
	private String arti;
	
	public Katalog() {
		// TODO Auto-generated constructor stub
	}
	
	public Katalog(String nama, String arti){
		this.nama = nama;
		this.arti = arti;
	}

	public int getId_katalog() {
		return id_katalog;
	}

	public void setId_katalog(int id_katalog) {
		this.id_katalog = id_katalog;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getArti() {
		return arti;
	}

	public void setArti(String arti) {
		this.arti = arti;
	}

}
