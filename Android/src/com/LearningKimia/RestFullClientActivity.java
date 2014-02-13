package com.LearningKimia;

import java.util.ArrayList;

import com.LearningKimia.restfull.AsyncTaskCompleteListener;
import com.LearningKimia.restfull.CallSynchronizedTask;
import com.LearningKimia.restfull.CallUpdateDataTask;
import com.LearningKimia.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class RestFullClientActivity extends Activity implements AsyncTaskCompleteListener<Object>{
	public final static int SUCCESS_RETURN_CODE = 1;
	public final static int FAILED_RETURN_CODE = 0;
	
//	private String url;
//	private int method;
	private String username, password;
	ArrayList<String> tableToUpdate;
	private String result = null;
//	private DatabaseHelper dbHelper;
	private Bundle data;
	private TextView progressText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restfull_client_page);
		
		data = getIntent().getExtras();
		username = data.getString("username");
		password = data.getString("password");
		tableToUpdate = data.getStringArrayList("tableToUpdate");
		
		progressText = (TextView) findViewById(R.id.progressText);
		progressText.setText(" Connect to server...");
		
		if(username==null && password==null) {
			CallSynchronizedTask synchronizedTask = new CallSynchronizedTask(this, this, handler, tableToUpdate);
			synchronizedTask.execute((Object)null);
		}
		else 
		{
			CallUpdateDataTask updateDataTask = new CallUpdateDataTask(this, this, handler, username, password, tableToUpdate);
			updateDataTask.execute((Object)null);
		}
	}
	
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String msgStr = (String) msg.obj;
			progressText.append("\n" + msgStr);
		}
	};
	
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
	}

	@Override
	public void onTaskComplete(Object... params) {
		result = (String) params[0];
		Intent intent = new Intent();
		Bundle b = new Bundle();
		b.putString(Constant.REST_RESULT, result);
		intent.putExtras(b);
		
		setResult(SUCCESS_RETURN_CODE, intent);
		
//		try {
//			JSONObject jobj = new JSONObject(result);
//			if(jobj.getString("status").equals("0")){
//				setResult(FAILED_RETURN_CODE, intent);
//			} else {
//				setResult(SUCCESS_RETURN_CODE, intent);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		finish();
	}

}
