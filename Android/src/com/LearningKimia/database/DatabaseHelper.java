package com.LearningKimia.database;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.LearningKimia.model.Bab;
import com.LearningKimia.model.Katalog;
import com.LearningKimia.model.Materi;
import com.LearningKimia.util.Constant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	public static final String BAB_TABLE = "t_bab";
	public static final String MATERI_TABLE = "t_materi";
	public static final String TUGAS_TABLE = "t_tugas";
	public static final String SOALTUGAS_TABLE = "t_soal_tugas";
	public static final String KATALOG_TABLE = "t_katalog";
	public static final String QUIZ_TABLE = "t_quiz";
	public static final String J_QUIZ_TABLE = "t_jawaban_quiz";
	public static final String VERSION_TABLE = "t_version";
	
	protected SQLiteDatabase database;

	public DatabaseHelper(Context context) {
		super(context, Constant.DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableBab(db);
		createTableMateri(db);
		createTableTugas(db);
		createTableSoalTugas(db);
		createTableKatalog(db);
		createTableQuiz(db);
		createTableJawabanQuiz(db);
		createTableVersion(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVerson, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + BAB_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + MATERI_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TUGAS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SOALTUGAS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + KATALOG_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + J_QUIZ_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + VERSION_TABLE);
		
		onCreate(db);
	}
	
	public void createTableBab(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+BAB_TABLE+" (" +
				"id_bab INTEGER PRIMARY KEY, " +
				"label_bab VARCHAR(10), " +
				"judul_bab VARCHAR(30) " +
				");");
		Log.i("create", "Bab");
	}
	
	public void createTableMateri(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+MATERI_TABLE+" (" +
				"id_materi INTEGER PRIMARY KEY, " +
				"judul VARCHAR(30), " +
				"id_bab INTEGER, " +
				"semester VARCHAR(1), " +
				"url TEXT " +
				");");
		Log.i("create", "Materi");
	}
	
	public void createTableTugas(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TUGAS_TABLE+" (" +
				"id_tugas INTEGER PRIMARY KEY, " +
				"judul_tugas VARCHAR(30), " +
				"catatan VARCHAR(30) " +
				");");
		Log.i("create", "Tugas");
	}
	
	public void createTableSoalTugas(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+SOALTUGAS_TABLE+" (" +
				"isi_soal TEXT, " +
				"id_tugas INTEGER " +
				");");
		Log.i("create", "Soal Tugas");
	}
	
	public void createTableKatalog(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+KATALOG_TABLE+" (" +
				"id_katalog INTEGER AUTO_INCREMENT PRIMARY KEY, " +
				"nama VARCHAR(30), " +
				"arti VARCHAR(30) " +
				");");
		initDefaultKatalog(db);
		Log.i("create", "Katalog");
	}
	
	public void createTableQuiz(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+QUIZ_TABLE+" (" +
				"id_quiz INTEGER AUTO_INCREMENT PRIMARY KEY, " +
				"soal_quiz VARCHAR(30), " +
				"type_quiz VARCHAR(30), " +
				"jawaban VARCHAR(30) " +
				");");
		Log.i("create", "Quiz");
	}
	
	public void createTableJawabanQuiz(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+J_QUIZ_TABLE+" (" +
				"id_jawaban INTEGER AUTO_INCREMENT PRIMARY KEY, " +
				"jawaban VARCHAR(30), " +
				"id_quiz INTEGER, " +
				"benar BOOLEAN " +
				");");
		Log.i("create", "Jawaban Quiz");
	}
	
	public void createTableVersion(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+VERSION_TABLE+" (" +
				"nama_table VARCHAR(30), " +
				"versi VARCHAR(10) " +
				");");
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(VERSION_TABLE)
		.append(" (nama_table,versi) values (")
		.append("'"+BAB_TABLE+"'").append(",'0');");
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("INSERT INTO ").append(VERSION_TABLE)
		.append(" (nama_table,versi) values (")
		.append("'"+MATERI_TABLE+"'").append(",'0');");
		
		StringBuffer sql3 = new StringBuffer();
		sql3.append("INSERT INTO ").append(VERSION_TABLE)
		.append(" (nama_table,versi) values (")
		.append("'"+TUGAS_TABLE+"'").append(",'0');");
		
		StringBuffer sql4 = new StringBuffer();
		sql4.append("INSERT INTO ").append(VERSION_TABLE)
		.append(" (nama_table,versi) values (")
		.append("'"+SOALTUGAS_TABLE+"'").append(",'0');");
		
//		.append("'"+BAB_TABLE+"'").append(",'0'),(")
//		.append("'"+MATERI_TABLE+"'").append(",'0'),(")
//		.append("'"+TUGAS_TABLE+"'").append(",'0'),(")
//		.append("'"+SOALTUGAS_TABLE+"'").append(",'0');");
		db.execSQL(sql.toString());
		db.execSQL(sql2.toString());
		db.execSQL(sql3.toString());
		db.execSQL(sql4.toString());
		Log.i("create", "Version");
	}
	
	public void initDefaultKatalog(SQLiteDatabase db){
		List<Katalog> katalogs = new ArrayList<Katalog>(){{
			add(new Katalog("1", "Satu"));
			add(new Katalog("2", "Dua"));
			add(new Katalog("3", "Tiga"));
			add(new Katalog("4", "Empat"));
			add(new Katalog("5", "Lima"));
		}};
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("INSERT INTO "+KATALOG_TABLE+" (nama, arti) ");
		for(int i=0;i<katalogs.size();i++){
			if(i!=0) strSQL.append(" UNION ");
			
			strSQL.append("SELECT '"+katalogs.get(i).getNama()+"' AS nama, ")
			.append("'"+katalogs.get(i).getArti()+"' AS arti");
		}
		strSQL.append(";");
		Log.i("sql katalog", strSQL.toString());
		db.execSQL(strSQL.toString());
	}
	
	public void initTable(String namaTable, String json){
		if(namaTable.equals(BAB_TABLE)){
			initTableBab(json);
		} else if(namaTable.equals(MATERI_TABLE)){
			initTableMateri(json);
		} else if(namaTable.equals(TUGAS_TABLE)){
			initTableTugas(json);
		} else if(namaTable.equals(SOALTUGAS_TABLE)){
			initTableSoalTugas(json);
		}
	}
	
	public void initTableBab(String json){
		database = this.getWritableDatabase();
		StringBuffer sql;
		String version = null;
		boolean insertSukses = true;
		try {
			JSONObject jobj = new JSONObject(json);
			version = jobj.getString("version");
			JSONArray table = jobj.getJSONArray("table");
			int len = table.length();
			String strSQL = "DELETE FROM "+BAB_TABLE;
			database.execSQL(strSQL);
			for(int i=0;i<len;i++){
				sql = new StringBuffer();
				sql.append("INSERT INTO ").append(BAB_TABLE).append(" values ")
				.append("('"+table.getJSONObject(i).getString("id_bab")+"', ")
				.append("'"+table.getJSONObject(i).getString("label_bab")+"', ")
				.append("'"+table.getJSONObject(i).getString("judul_bab")+"');");
				database.execSQL(sql.toString());
			}
		} catch (JSONException e) {
			insertSukses = false;
			e.printStackTrace();
		} catch (Exception e){
			insertSukses = false;
			e.printStackTrace();
		} finally {
			if(insertSukses){
				sql = new StringBuffer();
				sql.append("UPDATE ").append(VERSION_TABLE).append(" SET versi = '"+version+"' ")
				.append("WHERE nama_table = '"+BAB_TABLE+"';");
				database.execSQL(sql.toString());
				Log.i("init table", "sukses");
			}
		}
	}
	
    public void initTableMateri(String json){
    	database = this.getWritableDatabase();
		StringBuffer sql;
		String version = null;
		boolean insertSukses = true;
		try {
			JSONObject jobj = new JSONObject(json);
			version = jobj.getString("version");
			JSONArray table = jobj.getJSONArray("table");
			int len = table.length();
			String strSQL = "DELETE FROM "+MATERI_TABLE;
			database.execSQL(strSQL);
			for(int i=0;i<len;i++){
				sql = new StringBuffer();
				sql.append("INSERT INTO ").append(MATERI_TABLE).append(" values ")
				.append("('"+table.getJSONObject(i).getString("id_materi")+"', ")
				.append("'"+table.getJSONObject(i).getString("judul")+"', ")
				.append("'"+table.getJSONObject(i).getString("id_bab")+"', ")
				.append("'"+table.getJSONObject(i).getString("semester")+"', ")
				.append("'"+table.getJSONObject(i).getString("url")+"');");
				database.execSQL(sql.toString());
			}
		} catch (JSONException e) {
			insertSukses = false;
			e.printStackTrace();
		} catch (Exception e){
			insertSukses = false;
			e.printStackTrace();
		} finally {
			if(insertSukses){
				sql = new StringBuffer();
				sql.append("UPDATE ").append(VERSION_TABLE).append(" SET versi = '"+version+"' ")
				.append("WHERE nama_table = '"+MATERI_TABLE+"';");
				database.execSQL(sql.toString());
				Log.i("init table materi", "sukses");
			}
		}
	}
    
    public void initTableTugas(String json){
		
	}
    
    public void initTableSoalTugas(String json){
		
	}
    
    public List<Bab> getBabs(){
    	database = this.getWritableDatabase();
    	List<Bab> babs = new ArrayList<Bab>();
    	String sql = "SELECT * FROM "+BAB_TABLE;
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
    	while(cursor.moveToNext()){
    		Bab bab = new Bab();
    		bab.setIdBab(cursor.getInt(cursor.getColumnIndex("id_bab")));
    		bab.setLabelBab(cursor.getString(cursor.getColumnIndex("label_bab")));
    		bab.setJudulBab(cursor.getString(cursor.getColumnIndex("judul_bab")));
    		babs.add(bab);
    	}
    	database.close();
    	cursor.close();
    	return babs;
    }
    
    public List<Materi> getAllMateri(){
    	database = this.getWritableDatabase();
    	List<Materi> materis = new ArrayList<Materi>();
    	String sql = "SELECT * FROM "+MATERI_TABLE;
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
        while(cursor.moveToNext()){
        	Materi materi = new Materi();
        	materi.setId_materi(cursor.getString(cursor.getColumnIndex("id_materi")));
        	materi.setJudul(cursor.getString(cursor.getColumnIndex("judul")));
        	materi.setId_bab(cursor.getString(cursor.getColumnIndex("id_bab")));
        	materi.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
        	materi.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        	materis.add(materi);
        }
    	database.close();
    	cursor.close();
    	return materis;
    }
    
    public List<Materi> getMateris(String semester, String idBab){
    	database = this.getWritableDatabase();
    	List<Materi> materis = new ArrayList<Materi>();
    	String sql = "SELECT * FROM "+MATERI_TABLE+" WHERE semester = '"+semester+"' AND id_bab = '"+idBab+"'";
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
        while(cursor.moveToNext()){
        	Materi materi = new Materi();
        	materi.setId_materi(cursor.getString(cursor.getColumnIndex("id_materi")));
        	materi.setJudul(cursor.getString(cursor.getColumnIndex("judul")));
        	materi.setId_bab(cursor.getString(cursor.getColumnIndex("id_bab")));
        	materi.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
        	materi.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        	materis.add(materi);
        }
    	database.close();
    	cursor.close();
    	return materis;
    }
    
    public List<Katalog> getKatalogs(String cari){
    	database = this.getWritableDatabase();
    	List<Katalog> katalogs = new ArrayList<Katalog>();
    	String sql = "";
    	if(cari==null){
    		sql = "SELECT * FROM "+KATALOG_TABLE;
    	} else sql = "SELECT * FROM "+KATALOG_TABLE+" WHERE nama LIKE '%"+cari+"%'";
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
    	while(cursor.moveToNext()){
    		Katalog katalog = new Katalog();
    		katalog.setId_katalog(cursor.getInt(cursor.getColumnIndex("id_katalog")));
    		katalog.setNama(cursor.getString(cursor.getColumnIndex("nama")));
    		katalog.setArti(cursor.getString(cursor.getColumnIndex("arti")));
    		katalogs.add(katalog);
    	}
    	database.close();
    	cursor.close();
    	return katalogs;
    }
	
	public String getVersionOfTable(String tableName){
		database = this.getWritableDatabase();
		String sql = "SELECT * FROM "+VERSION_TABLE+" WHERE nama_table = '"+tableName+"'";
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToFirst();
		String version = cursor.getString(cursor.getColumnIndex("versi"));
		database.close();
		this.close();
		cursor.close();
		return version;
	}
	
	public ArrayList<String> getTableToUpdate(String json){
		ArrayList<String> result = new ArrayList<String>();
		try {
			JSONArray j_array = new JSONArray(json);
			int len = j_array.length();
			for(int i=0;i<len;i++){
				String tableName = j_array.getJSONObject(i).getString("nama_table");
				String version = j_array.getJSONObject(i).getString("version");
				String currentVersi = getVersionOfTable(tableName);
				if(!version.equals(currentVersi)){
					result.add(tableName);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

}
