package com.LearningKimia.util;

public final class Constant {
	
//	public static final String BASE_URL = "http://10.0.2.2/Serverlearning/rest/";
//	public static final String DOWNLOAD_URL = "http://10.0.2.2/Serverlearning/materi/";
	public static final String BASE_URL = "http://192.168.16.90/Serverlearning/rest/";
	public static final String DOWNLOAD_URL = "http://192.168.16.90/Serverlearning/materi/";
	public static final String UPLOAD_TUGAS_URL = BASE_URL + "uploadTugas/";
	public static final String GET_TABLE_VERSION_URL = BASE_URL + "getTableVersion";
	public static final String DB_NAME="LearningKimiaDB";
	public static final String DB_PATH="/data/data/com.LearningKimia/databases/";
	public static final String MATERI_PATH="/data/data/com.LearningKimia/materi/";
	public static final String MATERI_PATH_SD_CARD=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/materi/";
	public static final String IMAGE_PERIODIK_PATH = "imagePeriodik/";
	public static final String IMAGE_VIEW_PERIODIK_PATH = "imageViewPeriodik";
	
	public static final int max_soal_latihan = 10;
	public static final int max_soal_latihan_periodik = 10;
	public static final int max_jumlah_score = 10;
	
	public static final int REST_GET = 0;
	public static final int REST_PUT = 1;
	public static final int REST_POST = 2;
	public static final int REST_DELETE = 3;
	
	public static final String URL = "url";
	public static final String REST_METHOD = "rest_method";
	public static final String REST_RESULT = "rest_result";
	public static final String REST_CONN_TIMEOUT = "conn_timeout";
	
	public static final String UPLOAD_SUKSES = "File Upload Completed";
	
	public static final int SYNC_DATA = 11;

}
