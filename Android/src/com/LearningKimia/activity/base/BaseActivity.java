package com.LearningKimia.activity.base;

import com.LearningKimia.LoginActivity;
import com.LearningKimia.R;
import com.LearningKimia.StartUpActivity;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.global.GlobalVar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseActivity extends Activity implements OnClickListener{
	
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

}
