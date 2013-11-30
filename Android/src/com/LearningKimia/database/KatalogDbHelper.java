package com.LearningKimia.database;

import com.LearningKimia.model.Katalog;

import android.content.Context;

public class KatalogDbHelper extends DatabaseHelper{

	public KatalogDbHelper(Context context) {
		super(context);
	}
	
	public void addKatalog(Katalog katalog){
		database = this.getReadableDatabase();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO "+KATALOG_TABLE+" (nama, arti) values ")
		.append("('"+katalog.getNama()+"','"+katalog.getArti()+"')");
		database.execSQL(strSQL.toString());
		database.close();
	}
	
	public void updateKatalog(String nama, String arti){
		database = this.getReadableDatabase();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("UPDATE "+KATALOG_TABLE+" SET arti = '"+arti+"' ")
		.append("WHERE nama = '"+nama+"'");
		database.execSQL(strSQL.toString());
		database.close();
	}
	
	public void deleteKatalog(String nama){
		database = this.getReadableDatabase();
		String strSQL = "DELETE FROM "+KATALOG_TABLE+" WHERE nama = '"+nama+"'";
		database.execSQL(strSQL);
		database.close();
	}

}
