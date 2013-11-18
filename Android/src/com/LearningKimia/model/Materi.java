package com.LearningKimia.model;

public class Materi {
	
	private String id_materi;
	private String judul;
	private String id_bab;
	private String semester;
	private String url;
	
	public Materi() {
		// TODO Auto-generated constructor stub
	}

	public String getId_materi() {
		return id_materi;
	}

	public void setId_materi(String id_materi) {
		this.id_materi = id_materi;
	}

	public String getJudul() {
		return judul;
	}

	public void setJudul(String judul) {
		this.judul = judul;
	}

	public String getId_bab() {
		return id_bab;
	}

	public void setId_bab(String id_bab) {
		this.id_bab = id_bab;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
