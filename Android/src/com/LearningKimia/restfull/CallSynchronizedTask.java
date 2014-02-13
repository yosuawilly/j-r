package com.LearningKimia.restfull;

import java.util.ArrayList;
import java.util.List;

import com.LearningKimia.model.Materi;
import com.LearningKimia.util.Constant;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class CallSynchronizedTask extends CallUpdateDataTask {

	public CallSynchronizedTask(AsyncTaskCompleteListener<Object> callback,
			Context context, Handler handler, String username, String password, ArrayList<String> tableToUpdate) {
		super(callback, context, handler, username, password, tableToUpdate);
	}
	
	public CallSynchronizedTask(AsyncTaskCompleteListener<Object> callback, Context context, Handler handler, ArrayList<String> tableToUpdate) {
		super(callback, context, handler, "", "", tableToUpdate);
	}
	
	@Override
	protected String doInBackground(Object... arg0) {
		String url = "", result = "{\"status\":\"0\",\"fullMessage\":\"Proses gagal\"}";
		try{
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
	    	
	    	result = "{\"status\":\"1\",\"fullMessage\":\"Proses Sukses\"}";
	    	
		} catch(Exception e) {
			e.printStackTrace();
			result = "{\"status\":\"0\",\"fullMessage\":\""+e.getMessage()+"\"}";
		}
    	
		return result;
	}

}
