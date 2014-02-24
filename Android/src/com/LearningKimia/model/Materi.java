package com.LearningKimia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Materi implements Serializable{
	
	private static final long serialVersionUID = -1308190582814888392L;
	
	private String id_materi;
	private String judul;
	private String id_bab;
	private String semester;
	private String url;
	private List<LinkVideo> linkVideos = new ArrayList<LinkVideo>();

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
	
	public List<LinkVideo> getLinkVideos() {
		return linkVideos;
	}

	public void setLinkVideos(List<LinkVideo> linkVideos) {
		this.linkVideos = linkVideos;
	}

}
