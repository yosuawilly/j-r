package com.LearningKimia.model;

import java.io.Serializable;

public class LinkVideo implements Serializable{
	
	private static final long serialVersionUID = -5440321187597277464L;
	private String id_materi;
	private String link_video;
	
	public LinkVideo() {
		// TODO Auto-generated constructor stub
	}
	
	public LinkVideo(String id_materi, String link_video) {
		this.id_materi = id_materi;
		this.link_video = link_video;
	}

	public String getId_materi() {
		return id_materi;
	}

	public void setId_materi(String id_materi) {
		this.id_materi = id_materi;
	}

	public String getLink_video() {
		return link_video;
	}

	public void setLink_video(String link_video) {
		this.link_video = link_video;
	}

}
