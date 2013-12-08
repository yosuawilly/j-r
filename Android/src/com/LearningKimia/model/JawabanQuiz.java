package com.LearningKimia.model;

import java.io.Serializable;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class JawabanQuiz implements Serializable, Parcelable{
	private static final long serialVersionUID = 94342454501117626L;
	private int id_jawaban;
	private String jawaban;
	private int id_quiz;
	private boolean benar;
	
	public JawabanQuiz() {
		// TODO Auto-generated constructor stub
	}
	
	public JawabanQuiz(int id_jawaban, String jawaban, int id_quiz, boolean benar){
		this.id_jawaban = id_jawaban;
		this.jawaban = jawaban;
		this.id_quiz = id_quiz;
		this.benar = benar;
	}

	public int getId_jawaban() {
		return id_jawaban;
	}

	public void setId_jawaban(int id_jawaban) {
		this.id_jawaban = id_jawaban;
	}

	public String getJawaban() {
		return jawaban;
	}

	public void setJawaban(String jawaban) {
		this.jawaban = jawaban;
	}

	public int getId_quiz() {
		return id_quiz;
	}

	public void setId_quiz(int id_quiz) {
		this.id_quiz = id_quiz;
	}

	public boolean isBenar() {
		return benar;
	}

	public void setBenar(boolean benar) {
		this.benar = benar;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public JawabanQuiz(Parcel in){
		Bundle bundle = in.readBundle();
		this.id_jawaban = bundle.getInt("id_jawaban");
		this.jawaban = bundle.getString("jawaban");
		this.id_quiz = bundle.getInt("id_quiz");
		this.benar = bundle.getBoolean("benar");
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle bundle = new Bundle();
		bundle.putInt("id_jawaban", this.id_jawaban);
		bundle.putString("jawaban", this.jawaban);
		bundle.putInt("id_quiz", this.id_quiz);
		bundle.putBoolean("benar", this.benar);
		dest.writeBundle(bundle);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public JawabanQuiz createFromParcel(Parcel in) {
			return new JawabanQuiz(in);
		}

		@Override
		public JawabanQuiz[] newArray(int size) {
			return new JawabanQuiz[size];
		}
		
	};

}
