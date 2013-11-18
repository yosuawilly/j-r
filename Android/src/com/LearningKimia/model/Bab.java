package com.LearningKimia.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bab implements Parcelable{

	private int idBab;
	private String labelBab;
	private String judulBab;
	
	public Bab() {
		// TODO Auto-generated constructor stub
	}
	
	public Bab(Parcel in){
		String[]data = new String[3];
		
		in.readStringArray(data);
		this.idBab = Integer.parseInt(data[0]);
		this.labelBab = data[1];
		this.judulBab = data[2];
	}

	public int getIdBab() {
		return idBab;
	}

	public void setIdBab(int idBab) {
		this.idBab = idBab;
	}

	public String getLabelBab() {
		return labelBab;
	}

	public void setLabelBab(String labelBab) {
		this.labelBab = labelBab;
	}

	public String getJudulBab() {
		return judulBab;
	}

	public void setJudulBab(String judulBab) {
		this.judulBab = judulBab;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeStringArray(new String[] {String.valueOf(this.idBab), this.labelBab, this.judulBab});
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Bab createFromParcel(Parcel in) {
			return new Bab(in);
		}

		@Override
		public Bab[] newArray(int size) {
			return new Bab[size];
		}
		
	};
	
}
