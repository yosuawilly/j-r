package com.LearningKimia.model;

import java.util.ArrayList;
import java.util.List;

public class Siswa {
	
	private String idSiswa;
	private String username;
	private String password;
	private String nama;
	private String jenisKelamin;
	private String alamat;
	private List<NilaiTugas> nilaiTugas = new ArrayList<NilaiTugas>();
	
	public Siswa() {
		// TODO Auto-generated constructor stub
	}

	public String getIdSiswa() {
		return idSiswa;
	}

	public void setIdSiswa(String idSiswa) {
		this.idSiswa = idSiswa;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public List<NilaiTugas> getNilaiTugas() {
		return nilaiTugas;
	}

	public void setNilaiTugas(List<NilaiTugas> nilaiTugas) {
		this.nilaiTugas = nilaiTugas;
	}

}
