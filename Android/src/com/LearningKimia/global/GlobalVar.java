package com.LearningKimia.global;

import java.io.Serializable;

import com.LearningKimia.model.Siswa;

public class GlobalVar implements Serializable{
	
	private static final long serialVersionUID = 6668652291040678573L;
	
	private static GlobalVar instance;
	
	private Siswa siswa;
	
	public GlobalVar() {
	}
	
	public Siswa getSiswa() {
		return siswa;
	}
	
	public void setSiswa(Siswa siswa) {
		this.siswa = siswa;
	}
	
	static {
		instance = new GlobalVar();
	}
	
	public static GlobalVar getInstance() {
		return GlobalVar.instance;
	}
	
	public void clearAllObject(){
		getInstance().setSiswa(null);
	}

}
