package com.LearningKimia.database;

import java.util.ArrayList;
import java.util.List;

import com.LearningKimia.model.Score;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.ScoreType;

import android.content.Context;
import android.database.Cursor;

/**
 * @author LenovoDwidasa
 *
 */
public class ScoreDbHelper extends DatabaseHelper{

	public ScoreDbHelper(Context context) {
		super(context);
	}
	
	/**
	 * @param scoreType type score will get
	 * @return List of Score
	 */
	public List<Score> getAllScore(ScoreType scoreType){
		this.database = getWritableDatabase();
		List<Score> scores = new ArrayList<Score>();
		String sql = "SELECT * FROM "+SCORE_TABLE+" WHERE type = '"+scoreType.toString()+"';";
    	Cursor cursor = database.rawQuery(sql, null);
    	if(cursor!=null)
    	while(cursor.moveToNext()){
    		Score score = new Score();
    		score.setId_score(cursor.getInt(cursor.getColumnIndex("id_score")));
    		score.setNama(cursor.getString(cursor.getColumnIndex("nama")));
    		score.setScore(cursor.getString(cursor.getColumnIndex("score")));
    		score.setScoreType(ScoreType.valueOf(cursor.getString(cursor.getColumnIndex("type"))));
    		scores.add(score);
    	}
    	database.close();
    	cursor.close();
    	return scores;
	}
	
	/**
	 * @param score Score to be added to database
	 */
	public void addScore(Score score){
		if(isScoreWithNameExist(score)){
			updateScore(score, score.getNama(), true);
		} else {
			if(isJumlahScoreIsMax()){
				updateScore(score, score.getNama(), false);
			} else {
				this.database = getWritableDatabase();
				String sql = "INSERT INTO "+SCORE_TABLE+" (nama, score, type) VALUES " + 
				             "('"+score.getNama()+"','"+score.getScore()+"','"+score.getScoreType().toString()+"');";
				database.execSQL(sql);
				database.close();
			}
		}
	}
	
	
	/**
	 * @param score
	 * @param nama
	 * @param replace Replace existing row or not(if not, delete first row, and insert new row)
	 */
	public void updateScore(Score score, String nama, boolean replace){
		this.database = getWritableDatabase();
		if(replace){
			String sql = "UPDATE "+SCORE_TABLE+" SET score = '"+score.getScore()+"' WHERE nama = '"+nama+"';";
			database.execSQL(sql);
			database.close();
		} else {
			String sql = "DELETE FROM "+SCORE_TABLE+" WHERE nama = (SELECT nama FROM "+SCORE_TABLE+" ORDER BY ROWID ASC LIMIT 1)";
			database.execSQL(sql);
			addScore(score);
		}
	}
	
	/**
	 * @return true jika jumlah row of score di database sudah maximal, false otherwise.
	 */
	public boolean isJumlahScoreIsMax(){
		boolean max = true;
		this.database = getWritableDatabase();
		String query = "SELECT count(*) FROM "+SCORE_TABLE;
		Cursor cursor = database.rawQuery(query, null);
		if(cursor.moveToFirst()){
			int jumlah = cursor.getInt(0);
			if(jumlah<Constant.max_jumlah_score) max = false;
		}
		
		cursor.close();
		this.database.close();
		
		return max;
	}
	
    /**
     * @param score Score to be check(Exist or Not)
     * @return true jika Score sudah ada di dalam database, false otherwise.
     */
    public boolean isScoreWithNameExist(Score score){
		boolean exist = false;
		this.database = getWritableDatabase();
		String query = "SELECT * FROM "+SCORE_TABLE+" WHERE nama = '"+score.getNama()+"';";
		Cursor cursor = database.rawQuery(query, null);
		if(cursor!=null)
		if(cursor.moveToFirst()) exist = true;
		
		cursor.close();
		this.database.close();
		
		return exist;
	}

}
