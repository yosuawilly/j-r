package com.LearningKimia;

import java.util.ArrayList;
import com.LearningKimia.R;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.restfull.RestfulHttpMethod;
import com.LearningKimia.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class StartUpActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.wellcome);
		
//		Thread timer = new Thread(){
//			
//			@Override
//			public void run() {
//				try{
//					int time = 0;
//					while(time < 3000){
//						sleep(100);
//						time += 100;
//					}
//				}catch(Exception ex){
//					ex.printStackTrace();
//				} finally {
//					DatabaseHelper databaseHelper = new DatabaseHelper(StartUpActivity.this);
//					startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
//					finish();
//				}
//			}
//			
//		};
//		
//		timer.start();
		
		new LoadingTask().execute((Object)null);
	}
	
	private class LoadingTask extends AsyncTask<Object, Void, Intent> {

		@Override
		protected Intent doInBackground(Object... arg) {
			Intent intent = null;
			String url = Constant.BASE_URL+"getTableVersion";
			try {
				intent = new Intent(StartUpActivity.this, LoginActivity.class);
				String result = RestfulHttpMethod.connect(url, Constant.REST_GET);
				Log.i("result", result);
				if(result!=null && !result.equals("[]")){
					ArrayList<String> tableToUpdate = new DatabaseHelper(StartUpActivity.this).getTableToUpdate(result);
					intent.putStringArrayListExtra("tableToUpdate", tableToUpdate);
				}
			} catch (Exception e) {
				intent = new Intent(StartUpActivity.this, LoginActivity.class);
				e.printStackTrace();
			}
			return intent;
		}
		
		@Override
		protected void onPostExecute(Intent result) {
			startActivity(result);
			finish();
		}
		
	}

}
