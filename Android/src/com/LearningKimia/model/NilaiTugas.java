package com.LearningKimia.model;

import java.io.Serializable;

public class NilaiTugas implements Serializable{
	
	private static final long serialVersionUID = 5899865595886781489L;
	
	private String id_siswa;
	private String judul_tugas;
	private String nilai;
	
	public NilaiTugas() {
		// TODO Auto-generated constructor stub
	}

	public String getId_siswa() {
		return id_siswa;
	}

	public void setId_siswa(String id_siswa) {
		this.id_siswa = id_siswa;
	}

	public String getJudul_tugas() {
		return judul_tugas;
	}

	public void setJudul_tugas(String judul_tugas) {
		this.judul_tugas = judul_tugas;
	}

	public String getNilai() {
		return nilai;
	}

	public void setNilai(String nilai) {
		this.nilai = nilai;
	}

}
