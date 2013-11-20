package com.LearningKimia.activity.base;

import com.LearningKimia.LoginActivity;
import com.LearningKimia.R;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.global.GlobalVar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BaseMenuListActivity extends BaseListActivity implements OnClickListener{
	protected Button buttonBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_list_page);
		
		buttonBack = (Button) findViewById(R.id.button_back);
		buttonBack.setOnClickListener(this);
	}
	
	public void setHeaderText(String header){
		((TextView) findViewById(R.id.headerText)).setText(header);
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
		case R.id.logoutMenu:
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Keluar")
	        .setMessage("Apakah anda yakin akan keluar dari aplikasi E-Learning?")
	        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(BaseMenuListActivity.this, LearningKimiaActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("EXIT", true);
					GlobalVar.getInstance().clearAllObject();
					startActivity(intent);
					BaseMenuListActivity.this.finish();
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
			BaseMenuListActivity.this.finish();
			onBackPressed();
			break;
		default:
			break;
		}
	}

}
