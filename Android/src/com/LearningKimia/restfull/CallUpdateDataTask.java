package com.LearningKimia.restfull;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.util.Constant;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CallUpdateDataTask extends AsyncTask<Object, String, String>{
	private AsyncTaskCompleteListener<Object> callback;
	private Context context;
	private Handler handler;
	private ArrayList<String> tableToUpdate;
	private String username;
	private String password;
	
	private DatabaseHelper dbHelper;
	
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
		sendProgresMessage("Login proses");
		String url = Constant.BASE_URL + "login/"+username+"/"+password;
		try{
		    result = RestfulHttpMethod.connect(url, Constant.REST_GET);
		    JSONObject jobj = new JSONObject(result);
		    if(jobj.getString("status").equals("0")){
		    	return result;
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
		    	result = "{\"status\":\"1\",\"fullMessage\":\"Proses Sukses\"}";
		    }
		}catch(Exception e){
			e.printStackTrace();
			result = "{\"status\":\"0\",\"fullMessage\":\""+e.getMessage()+"\"}";
		}
		return result;
	}
	
	private void sendProgresMessage(String pesan){
		Message message = handler.obtainMessage();
		message.obj = "- "+pesan+"...";
		handler.sendMessage(message);
	}
	
	private String getNameOfTable(String table){
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
