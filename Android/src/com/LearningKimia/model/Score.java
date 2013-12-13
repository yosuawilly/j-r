package com.LearningKimia.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.LearningKimia.util.ScoreType;

public class Score implements Parcelable{
	
	private int id_score;
	private String nama;
	private String score;
	private ScoreType scoreType;
	
	public Score() {
		
	}
	
	public Score(int id_score, String nama, String score, ScoreType scoreType){
		this.id_score = id_score;
		this.nama = nama;
		this.score = score;
		this.scoreType = scoreType;
	}

	public int getId_score() {
		return id_score;
	}

	public void setId_score(int id_score) {
		this.id_score = id_score;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public ScoreType getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Score(Parcel in){
		this.id_score = in.readInt();
		this.nama = in.readString();
		this.score = in.readString();
		this.scoreType = ScoreType.valueOf(in.readString());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id_score);
		dest.writeString(nama);
		dest.writeString(score);
		dest.writeString(scoreType.name());
	}
	
	public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
		@Override
		public Score createFromParcel(Parcel source) {
			return new Score(source);
		}

		@Override
		public Score[] newArray(int size) {
			return new Score[size];
		}
	};

}
