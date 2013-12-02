package com.LearningKimia.model;

import java.io.Serializable;

public class SoalTugas implements Serializable{
	private static final long serialVersionUID = -7206067066236490631L;
	private String isi_soal;
	private int id_tugas;

	public SoalTugas() {
		// TODO Auto-generated constructor stub
	}
	
	public SoalTugas(String isi_soal, int id_tugas){
		this.isi_soal = isi_soal;
		this.id_tugas = id_tugas;
	}

	public String getIsi_soal() {
		return isi_soal;
	}

	public void setIsi_soal(String isi_soal) {
		this.isi_soal = isi_soal;
	}

	public int getId_tugas() {
		return id_tugas;
	}

	public void setId_tugas(int id_tugas) {
		this.id_tugas = id_tugas;
	}
	
}
