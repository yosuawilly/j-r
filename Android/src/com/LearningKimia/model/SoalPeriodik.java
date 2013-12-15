package com.LearningKimia.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SoalPeriodik implements Parcelable{
	
	private String imagePath;
	private String jawaban;
	
	public SoalPeriodik() {
		// TODO Auto-generated constructor stub
	}
	
	public SoalPeriodik(String imagePath, String jawaban){
		this.imagePath = imagePath;
		this.jawaban = jawaban;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getJawaban() {
		return jawaban;
	}

	public void setJawaban(String jawaban) {
		this.jawaban = jawaban;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public SoalPeriodik(Parcel in){
		String[]data = new String[2];
		in.readStringArray(data);
		this.imagePath = data[0];
		this.jawaban = data[1];
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[]{this.imagePath, this.jawaban});
	}
	
	public static final Parcelable.Creator<SoalPeriodik> CREATOR = new Parcelable.Creator<SoalPeriodik>() {
		@Override
		public SoalPeriodik createFromParcel(Parcel in) {
			return new SoalPeriodik(in);
		}

		@Override
		public SoalPeriodik[] newArray(int size) {
			return new SoalPeriodik[size];
		}
	};

}
