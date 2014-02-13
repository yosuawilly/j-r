package com.LearningKimia.activity.base;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.LearningKimia.LoginActivity;
import com.LearningKimia.R;
import com.LearningKimia.RestFullClientActivity;
import com.LearningKimia.StartUpActivity;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.restfull.AsyncTaskCompleteListener;
import com.LearningKimia.restfull.CallWebServiceTask;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseActivity extends Activity implements OnClickListener, AsyncTaskCompleteListener<Object>{
	protected static final int SYNCHRON_REQUEST_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		this.finish();
	}
	
    long pauseTime = System.currentTimeMillis();
	
	@Override
	protected void onPause() {
		super.onPause();
		pauseTime = System.currentTimeMillis();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (System.currentTimeMillis()-pauseTime > 10 * 60 * 1000) {
			Intent intent = new Intent(this, StartUpActivity.class);
			GlobalVar.getInstance().clearAllObject();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			overridePendingTransition(0, 0);
			finish();
		} else {
			if(getIntent().getBooleanExtra("EXIT", false)){
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i("class", this.getClass().getName());
		if(this.getClass().getName().contains("LoginActivity")) return false;
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_option_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.mainMenu:
			intent = new Intent(this, LearningKimiaActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			this.finish();
			return true;
		case R.id.syncMenu:
			CallWebServiceTask task = new CallWebServiceTask(this, this, "Getting Table Version...");
			task.execute(Constant.GET_TABLE_VERSION_URL, Constant.REST_GET);
			
			return true;
		case R.id.logoutMenu:
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Keluar")
	        .setMessage("Apakah anda yakin akan keluar dari aplikasi E-Learning?")
	        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(BaseActivity.this, LearningKimiaActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("EXIT", true);
					GlobalVar.getInstance().clearAllObject();
					startActivity(intent);
					BaseActivity.this.finish();
				}
			})
			.setNegativeButton("Tidak", null).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			onBackPressed();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SYNCHRON_REQUEST_CODE) {
			if(data!=null){
				Bundle b = data.getExtras();
				String result = b.getString(Constant.REST_RESULT);
				Log.i("result", result);
				if(Utility.cekValidResult(result, this)){
					if(resultCode == RestFullClientActivity.SUCCESS_RETURN_CODE){
						Utility.showMessage(this, "Tutup", "Data has been synchronized");
					} else {
						try {
							JSONObject jobj = new JSONObject(result);
							Utility.showErrorMessage(this, jobj.getString("fullMessage"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	public void onTaskComplete(Object... params) {
		String result = (String) params[0];
		if (Utility.cekValidResult(result, this)) {
			if (!result.equals("[]")) {
				ArrayList<String> tableToUpdate = new DatabaseHelper(this).getTableToUpdate(result);
				if(tableToUpdate!=null){
					if(tableToUpdate.size()>0){
						Intent intent = new Intent(this, RestFullClientActivity.class);
						intent.putStringArrayListExtra("tableToUpdate", tableToUpdate);
						startActivityForResult(intent, SYNCHRON_REQUEST_CODE);
					} else {
						Utility.showMessage(this, "Tutup", "Data has been synchronized");
					}
					
					return;
				}
			} else {
				Utility.showMessage(this, "Tutup", "Data has been synchronized");
			}
		}
	}

}
