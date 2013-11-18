package com.LearningKimia.activity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class SQLHelper extends SQLiteOpenHelper{

	private static String DB_PATH = "/data/data/com.LearningKimia/databases/";
	private static final String DATABASE_NAME = "learning_kimia";
	private static final int DATABASE_VERSION = 1;
	
	private SQLiteDatabase myDataBase; 
	 
    private final Context myContext;
	
	//private static final String DATABASE_NAME = "modul_fisika";
	//private static final int DATABASE_VERSION = 1;
	//private static String DB_PATH = "/data/data/conn.modul.fisika/databases/";
	//private static String ASSETS_DB_FOLDER = "db";
	//private static final String DATABSE_TABLE = "Questions";
    private final String NAMA_TABEL = "glosarium";
	//private final String 
	private final String BARIS_NAMA = "istilah";
	private final String BARIS_ARTI = "arti";
	
	EditText pilNama;
    
    
	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext=context;
		// TODO Auto-generated constructor stub
	}

	
	public void createDataBase() throws IOException{
		 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		 Toast.makeText(myContext, "createDatabase diakses \n "+
    				"Database sudah ada", Toast.LENGTH_SHORT).show();
    		//do nothing, DataBase is already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
        		
        		Toast.makeText(myContext, "DataBase tidak ada \n "+
        				"Panggil Method Copy Database", Toast.LENGTH_SHORT).show();
        		
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
    
    
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
    	
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DATABASE_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	
    	//Open the database
        String myPath = DB_PATH + DATABASE_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}

	//mengambil seluruh baris pada database
	 	public ArrayList<ArrayList<Object>> getAllRowsAsArrays(String pilihan)
		{
	 		
			ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
	 
			Cursor cursor;
		
			
			try {
				cursor = myDataBase.query(
						NAMA_TABEL,
						new String[]{BARIS_NAMA, BARIS_ARTI},
						BARIS_NAMA +" LIKE '"+pilihan+"%' order by istilah asc ", null, null, null, null
				);
					cursor.moveToFirst();
					
				if (!cursor.isAfterLast())
				{
					do
					{
						ArrayList<Object> dataList = new ArrayList<Object>();
	 

						dataList.add(cursor.getString(0));
						dataList.add(cursor.getString(1));
						dataArrays.add(dataList);
					}
					while (cursor.moveToNext());
				}
			}
			catch (SQLException e)
			{
				Log.e("DB Error", e.toString());
				e.printStackTrace();
			}
	 
			return dataArrays;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="create table Questions(_id integer primary key Autoincrement, Question text not null, Opt1 Text not null," +
        				 " Opt2 Text not null, Opt3 Text not null, Opt4 Text not null, Answer text not null);";
		Log.d("Data", "onCreate: "+sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}*/


}
