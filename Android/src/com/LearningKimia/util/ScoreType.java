package com.LearningKimia.util;

import android.os.Parcel;
import android.os.Parcelable;

public enum ScoreType implements Parcelable{
	
	PILIHAN_GANDA,
	PERIODIK;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(ordinal());
	}
	
	public static final Creator<ScoreType> CREATOR = new Creator<ScoreType>() {
        @Override
        public ScoreType createFromParcel(final Parcel source) {
            return ScoreType.values()[source.readInt()];
        }

        @Override
        public ScoreType[] newArray(final int size) {
            return new ScoreType[size];
        }
    };

}
