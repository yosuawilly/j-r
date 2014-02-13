package com.LearningKimia.restfull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.Materi;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Utility;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CallUpdateDataTask extends AsyncTask<Object, String, String>{
	private final String TAG = this.getClass().getCanonicalName();
	protected AsyncTaskCompleteListener<Object> callback;
	protected Context context;
	protected Handler handler;
	protected ArrayList<String> tableToUpdate;
	protected String username;
	protected String password;
	
	/*For download task*/
	private static final int TIMEOUT = 120;
	protected final String ERROR = "INVALID_URL";
	protected final String FILE_CORRUPT = "FILE_CORRUPT";
	protected final String SUCCESS = "SUCCESS";
	protected final String CANCEL = "CANCEL";
	
	protected DatabaseHelper dbHelper;
	
	public CallUpdateDataTask(AsyncTaskCompleteListener<Object> callback, Context context, Handler handler, String username, String password, ArrayList<String> tableToUpdate) {
		this.callback = callback;
		this.context = context;
		this.handler = handler;
		this.username = username;
		this.password = password;
		this.tableToUpdate = tableToUpdate;
		dbHelper = new DatabaseHelper(context);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		String result = "{\"status\":\"0\",\"fullMessage\":\"Proses gagal\"}";
		String resultLogin = "{\"status\":\"0\",\"fullMessage\":\"Login gagal\"}";
		sendProgresMessage("Login proses");
		String url = Constant.BASE_URL + "login/"+username+"/"+password;
		try{
		    resultLogin = RestfulHttpMethod.connect(url, Constant.REST_GET);
		    JSONObject jobj = new JSONObject(resultLogin);
		    if(jobj.getString("status").equals("0")){
		    	return resultLogin;
		    } else {
		    	int len = tableToUpdate.size();
		    	for(int i=0;i<len;i++){
		    		sendProgresMessage("Update table " + getNameOfTable(tableToUpdate.get(i)));
		    		url = Constant.BASE_URL + "getDataTable/"+tableToUpdate.get(i);
		    		result = RestfulHttpMethod.connect(url, Constant.REST_GET);
		    		if(result.contains("status")) return result;
		    		dbHelper.initTable(tableToUpdate.get(i), result);
		    		Log.i("result update", result);
		    	}
		    	
		    	if(tableToUpdate.contains("t_materi")){
		    		sendProgresMessage("Download update materi");
		    		List<Materi> materis = dbHelper.getAllMateri();
		    		for(Materi materi : materis){
		    			downloadUpdateMateri(materi.getJudul());
		    		}
		    	}
		    	
//		    	result = "{\"status\":\"1\",\"fullMessage\":\"Proses Sukses\"}";
		    	result = resultLogin;
		    }
		}catch(Exception e){
			e.printStackTrace();
			result = "{\"status\":\"0\",\"fullMessage\":\""+e.getMessage()+"\"}";
		}
		return result;
	}
	
	protected String downloadUpdateMateri(String judul){
		String PATH = (Utility.isSDCardExist())?Constant.MATERI_PATH_SD_CARD:Constant.MATERI_PATH;
		File file = new File(PATH);
		file.delete();
        file.mkdirs();
        File destFile = new File(file, judul+".pdf");
        
        HttpURLConnection conn = null;
		InputStream stream = null; //to read
	    FileOutputStream out = null; //to write
	    double fileSize = 0;
	    double downloaded = 0; //
	    BufferedOutputStream bufferOut = null;
	    try {
	    	String strUrl = Constant.DOWNLOAD_URL + URLEncoder.encode(judul + ".pdf" , "UTF-8").replace("+", "%20");
	    	URL url = new URL(strUrl);
	    	
	    	conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TIMEOUT * 1000);
			conn.setReadTimeout(TIMEOUT * 1000);
			conn.connect();
			
			if (conn.getResponseCode() == 404) {
				conn.disconnect();
				return ERROR + "Invalid Update URL";
			}
			
			fileSize = conn.getContentLength();
			out = new FileOutputStream(destFile);
			final int fileSizeKb = (int) (fileSize / 1024) + 1;
			stream = conn.getInputStream();

			byte buffer[] = new byte[1024];
		    int read = -1;
		    int i = 0;
		    while ((read = stream.read(buffer))> -1) {
		    	i++;
		        out.write(buffer, 0, read);
		        if (isCancelled()) {
		        	out.flush();
		        	handler.post(new Runnable() {
		        		public void run() {
		        			Toast.makeText(context, "Download dibatalkan", Toast.LENGTH_LONG).show();
		        		}
		        	});

		        	return CANCEL;
		        }
		        downloaded += read;
		        if (i % 5 == 0) {
		        	out.flush();
		        	final int downloadedKb = (int) (downloaded / 1024);
			        final int progress = (int) ((downloaded / fileSize) * 100);
		        }
		    }
		    out.flush();
		    out.close();
			
			return SUCCESS;
	    } catch(Exception e){
	    	Log.d("Error download", "error in reading/extract file", e);
			return ERROR;
	    } finally {
	    	if (out != null) {
				try {
					out.close();
				} catch (IOException ie) {
					Log.d(TAG, "error in closing file", ie);
				}
			}
			if (conn != null) {
				try {
					if (conn.getInputStream() != null)
						conn.getInputStream().close();
					if(conn.getErrorStream() != null)
						conn.getErrorStream().close();
				} catch (Exception e) {
					Log.d(TAG, "error in closing connection", e);
				}
			}
			if (bufferOut != null) {
				try {
					bufferOut.close();
				} catch (IOException ie) {
					Log.d(TAG, "error in closing file", ie);
				}
			}
	    }
	}
	
	protected void sendProgresMessage(String pesan){
		Message message = handler.obtainMessage();
		message.obj = "- "+pesan+"...";
		handler.sendMessage(message);
	}
	
	protected String getNameOfTable(String table){
		if(table.equals("t_bab")) return "Bab";
		else if(table.equals("t_materi")) return "Materi";
		else if(table.equals("t_tugas")) return "Tugas";
		else return "Soal Tugas";
	}
	
	@Override
	protected void onPostExecute(String result) {
		dbHelper.close();
		callback.onTaskComplete(result);
	}

}
