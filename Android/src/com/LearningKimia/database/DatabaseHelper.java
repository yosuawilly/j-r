package com.LearningKimia.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.LearningKimia.model.Bab;
import com.LearningKimia.model.JawabanQuiz;
import com.LearningKimia.model.Katalog;
import com.LearningKimia.model.Materi;
import com.LearningKimia.model.Quiz;
import com.LearningKimia.model.SoalPeriodik;
import com.LearningKimia.model.SoalTugas;
import com.LearningKimia.model.Tugas;
import com.LearningKimia.util.Constant;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	protected Context context;
	
	public static final String BAB_TABLE = "t_bab";
	public static final String MATERI_TABLE = "t_materi";
	public static final String TUGAS_TABLE = "t_tugas";
	public static final String SOALTUGAS_TABLE = "t_soal_tugas";
	public static final String KATALOG_TABLE = "t_katalog";
	public static final String QUIZ_TABLE = "t_quiz";
	public static final String J_QUIZ_TABLE = "t_jawaban_quiz";
	public static final String VERSION_TABLE = "t_version";
	public static final String SCORE_TABLE = "t_score";
	
	protected SQLiteDatabase database;

	public DatabaseHelper(Context context) {
		super(context, Constant.DB_NAME, null, 1);
		this.context = context;
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
		createTableScore(db);
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
		db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE);
		
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
//				"id_katalog INTEGER AUTO_INCREMENT PRIMARY KEY, " + //to set auto_increment do not use AUTO_INCREMENT
				"id_katalog INTEGER PRIMARY KEY, " +
				"nama VARCHAR(30), " +
				"arti VARCHAR(30) " +
				");");
		initDefaultKatalog(db);
		Log.i("create", "Katalog");
	}
	
	public void createTableQuiz(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+QUIZ_TABLE+" (" +
//				"id_quiz INTEGER AUTO_INCREMENT PRIMARY KEY, " +
				"id_quiz INTEGER PRIMARY KEY, " +
				"soal_quiz VARCHAR(30) " +
//				"type_quiz VARCHAR(30), " +
//				"jawaban VARCHAR(30) " +
				");");
		Log.i("create", "Quiz");
	}
	
	public void createTableJawabanQuiz(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+J_QUIZ_TABLE+" (" +
//				"id_jawaban INTEGER AUTO_INCREMENT PRIMARY KEY, " +
				"id_jawaban INTEGER PRIMARY KEY, " +
				"jawaban VARCHAR(30), " +
				"id_quiz INTEGER, " +
				"benar BOOLEAN " +
				");");
		initDefaultQuiz(db);
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
	
	public void createTableScore(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS "+SCORE_TABLE+" (" +
				"id_score INTEGER PRIMARY KEY, " +
				"nama VARCHAR(30), " +
				"score VARCHAR(30), " +
				"type VARCHAR(15)" +
				");");
		Log.i("create", "Score");
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
	
	public void initDefaultQuiz(SQLiteDatabase db){
		try {
			String line = "";
			String statement = "";
			int lastId = 0;
			InputStream stream = context.getAssets().open("soal_latihan");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			while((line = reader.readLine()) != null){
				if(!line.contains("@")){
					statement += line;
				} else {
					String[]soals = statement.split("\\|\\|");
					String sql = "INSERT INTO "+QUIZ_TABLE+" (soal_quiz) values ('"+soals[0]+"');";
					db.execSQL(sql);
					sql = "SELECT ROWID from "+QUIZ_TABLE+" order by ROWID DESC limit 1";
					Cursor cursor = db.rawQuery(sql, null);
					if(cursor != null && cursor.moveToFirst()){
						lastId = (int)cursor.getLong(0);
						Log.i("last id", String.valueOf(lastId));
						StringBuffer strSQL = new StringBuffer();
						strSQL.append("INSERT INTO "+J_QUIZ_TABLE+" (jawaban, id_quiz, benar) ");
						for(int i=1;i<soals.length;i++){
							if(i!=1) strSQL.append(" UNION ");
							
							strSQL.append("SELECT '"+( (soals[i].startsWith("#"))?soals[i].substring(1):soals[i] )+"' AS jawaban, ")
							.append("'"+lastId+"' AS id_quiz, ")
							.append( ((soals[i].startsWith("#"))?1:0) + " AS benar");
						}
						strSQL.append(";");
						Log.i("strSQL", strSQL.toString());
						db.execSQL(strSQL.toString());
					}
//					Log.i("line", soals[0]);
					statement = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    	database = this.getWritableDatabase();
		StringBuffer sql;
		String version = null;
		boolean insertSukses = true;
		try {
			JSONObject jobj = new JSONObject(json);
			version = jobj.getString("version");
			JSONArray table = jobj.getJSONArray("table");
			int len = table.length();
			String strSQL = "DELETE FROM "+TUGAS_TABLE;
			database.execSQL(strSQL);
			for(int i=0;i<len;i++){
				sql = new StringBuffer();
				sql.append("INSERT INTO ").append(TUGAS_TABLE).append(" values ")
				.append("('"+table.getJSONObject(i).getString("id_tugas")+"', ")
				.append("'"+table.getJSONObject(i).getString("judul_tugas")+"', ")
				.append("'"+table.getJSONObject(i).getString("catatan")+"');");
				database.execSQL(sql.toString());
			}
		} catch (JSONException e) {
			insertSukses = false;
			e.printStackTrace();
		} catch (Exception e) {
			insertSukses = false;
			e.printStackTrace();
		} finally {
			if(insertSukses){
				sql = new StringBuffer();
				sql.append("UPDATE ").append(VERSION_TABLE).append(" SET versi = '"+version+"' ")
				.append("WHERE nama_table = '"+TUGAS_TABLE+"';");
				database.execSQL(sql.toString());
				Log.i("init table tugas", "sukses");
			}
		}
	}
    
    public void initTableSoalTugas(String json){
    	database = this.getWritableDatabase();
		StringBuffer sql;
		String version = null;
		boolean insertSukses = true;
		try {
			JSONObject jobj = new JSONObject(json);
			version = jobj.getString("version");
			JSONArray table = jobj.getJSONArray("table");
			int len = table.length();
			String strSQL = "DELETE FROM "+SOALTUGAS_TABLE;
			database.execSQL(strSQL);
			for(int i=0;i<len;i++){
				sql = new StringBuffer();
				sql.append("INSERT INTO ").append(SOALTUGAS_TABLE).append(" values ")
				.append("('"+table.getJSONObject(i).getString("isi_soal")+"', ")
				.append("'"+table.getJSONObject(i).getString("id_tugas")+"');");
				database.execSQL(sql.toString());
			}
		} catch (JSONException e) {
			insertSukses = false;
			e.printStackTrace();
		} catch (Exception e) {
			insertSukses = false;
			e.printStackTrace();
		} finally {
			if(insertSukses){
				sql = new StringBuffer();
				sql.append("UPDATE ").append(VERSION_TABLE).append(" SET versi = '"+version+"' ")
				.append("WHERE nama_table = '"+SOALTUGAS_TABLE+"';");
				database.execSQL(sql.toString());
				Log.i("init table soal tugas", "sukses");
			}
		}
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
    
    public List<Tugas> getAllTugas(){
    	database = this.getWritableDatabase();
    	List<Tugas> tugass = new ArrayList<Tugas>();
    	String sql = "SELECT * FROM "+TUGAS_TABLE;
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
    	while(cursor.moveToNext()){
    		Tugas tugas = new Tugas();
    		tugas.setId_tugas(cursor.getInt(cursor.getColumnIndex("id_tugas")));
    		tugas.setJudul_tugas(cursor.getString(cursor.getColumnIndex("judul_tugas")));
    		tugas.setCatatan(cursor.getString(cursor.getColumnIndex("catatan")));
    		tugass.add(tugas);
    	}
    	database.close();
    	cursor.close();
    	return tugass;
    }
    
    public List<SoalTugas> getSoalTugas(int idTugas){
    	database = this.getWritableDatabase();
    	List<SoalTugas> soalTugass = new ArrayList<SoalTugas>();
    	String sql = "SELECT * FROM "+SOALTUGAS_TABLE+" WHERE id_tugas = '"+idTugas+"';";
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
    	while(cursor.moveToNext()){
    		SoalTugas soalTugas = new SoalTugas();
    		soalTugas.setId_tugas(cursor.getInt(cursor.getColumnIndex("id_tugas")));
    		soalTugas.setIsi_soal(cursor.getString(cursor.getColumnIndex("isi_soal")));
    		soalTugass.add(soalTugas);
    	}
    	database.close();
    	cursor.close();
    	return soalTugass;
    }
    
    public List<Quiz> getQuiz(){
    	database = this.getWritableDatabase();
    	List<Quiz> quizs = new ArrayList<Quiz>();
    	String sql = "SELECT * FROM "+QUIZ_TABLE;
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
    	while (cursor.moveToNext()) {
    		int id_quiz = cursor.getInt(cursor.getColumnIndex("id_quiz"));
    		String soal_quiz = cursor.getString(cursor.getColumnIndex("soal_quiz"));
    		List<JawabanQuiz> jawabanQuizs = new ArrayList<JawabanQuiz>();
			sql = "SELECT * FROM "+J_QUIZ_TABLE+" WHERE id_quiz = '"+id_quiz+"'";
			Cursor c_jwb = database.rawQuery(sql, null);
			if(c_jwb!=null){
				while(c_jwb.moveToNext()){
					int id_jawaban = c_jwb.getInt(c_jwb.getColumnIndex("id_jawaban"));
					String jawaban = c_jwb.getString(c_jwb.getColumnIndex("jawaban"));
					boolean benar = (c_jwb.getString(c_jwb.getColumnIndex("benar")).equals("1"))?true:false;
					JawabanQuiz jawabanQuiz = new JawabanQuiz(id_jawaban, jawaban, id_quiz, benar);
					jawabanQuizs.add(jawabanQuiz);
				}
				c_jwb.close();
			}
			Quiz quiz = new Quiz(id_quiz, soal_quiz, jawabanQuizs);
			quizs.add(quiz);
		}
    	database.close();
    	cursor.close();
    	return quizs;
    }
    
    public List<SoalPeriodik> getAllSoalPeriodik(){
    	List<SoalPeriodik> soalPeriodiks = new ArrayList<SoalPeriodik>();
    	String line = "";
    	try {
			InputStream stream = context.getAssets().open("soal_latihan_periodik");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			while((line = reader.readLine()) != null){
				String[]soals = line.split("\\|\\|");
				SoalPeriodik soalPeriodik = new SoalPeriodik(soals[0], soals[1]);
				soalPeriodiks.add(soalPeriodik);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return soalPeriodiks;
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
