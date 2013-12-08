package com.LearningKimia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Quiz implements Serializable, Parcelable{
	private static final long serialVersionUID = -8283057659916951740L;
	private int id_quiz;
	private String soal_quiz;
	private List<JawabanQuiz> jawabanQuizs;
	
	public Quiz() {
		// TODO Auto-generated constructor stub
	}
	
	public Quiz(int id_quiz, String soal_quiz, List<JawabanQuiz> jawabanQuizs){
		this.id_quiz = id_quiz;
		this.soal_quiz = soal_quiz;
		this.jawabanQuizs = jawabanQuizs;
	}

	public int getId_quiz() {
		return id_quiz;
	}

	public void setId_quiz(int id_quiz) {
		this.id_quiz = id_quiz;
	}

	public String getSoal_quiz() {
		return soal_quiz;
	}

	public void setSoal_quiz(String soal_quiz) {
		this.soal_quiz = soal_quiz;
	}

	public List<JawabanQuiz> getJawabanQuizs() {
		return jawabanQuizs;
	}

	public void setJawabanQuizs(List<JawabanQuiz> jawabanQuizs) {
		this.jawabanQuizs = jawabanQuizs;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public Quiz(Parcel in){
		this.id_quiz = in.readInt();
		this.soal_quiz = in.readString();
		this.jawabanQuizs = new ArrayList<JawabanQuiz>();
		in.readTypedList(jawabanQuizs, JawabanQuiz.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id_quiz);
		dest.writeString(soal_quiz);
		dest.writeTypedList(jawabanQuizs);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Quiz createFromParcel(Parcel in) {
			return new Quiz(in);
		}

		@Override
		public Quiz[] newArray(int size) {
			return new Quiz[size];
		}
		
	};

}
