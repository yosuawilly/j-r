package com.LearningKimia.activity.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.LearningKimia.R;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.util.Functional;

public abstract class BaseMyActivity extends BaseActivity implements Functional{
	protected boolean inflateView = false;
	
	protected MyLayout myLayout = MyLayout.LINEARLAYOUT;
	
	public enum MyLayout {
		LINEARLAYOUT, RELATIVELAYOUT
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		
		initBundle();
		initObject();
		initDesign();
		initObjectToDesign();
		initListener();
	}
	
	public void setLayoutMode(boolean inflateView, MyLayout myLayout){
		this.inflateView = inflateView;
		this.myLayout = myLayout;
	}

	@Override
	public void initBundle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDesign() {
		if(inflateView) {
			if(myLayout == MyLayout.LINEARLAYOUT) {
				LinearLayout base = (LinearLayout) findViewById(getIdViewToAppendFromInflate());
				if(base != null){
					View toInflate = getLayoutInflater().inflate(getIdViewToInflate(), null);
					base.addView(toInflate);
				}
			} else if(myLayout == MyLayout.RELATIVELAYOUT) {
				RelativeLayout base = (RelativeLayout) findViewById(getIdViewToAppendFromInflate());
				if(base != null){
					View toInflate = getLayoutInflater().inflate(getIdViewToInflate(), null);
					base.addView(toInflate);
				}
			}
		}
	}

	@Override
	public void initObjectToDesign() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
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
					Intent intent = new Intent(BaseMyActivity.this, LearningKimiaActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("EXIT", true);
					GlobalVar.getInstance().clearAllObject();
					startActivity(intent);
					BaseMyActivity.this.finish();
				}
			})
			.setNegativeButton("Tidak", null).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public abstract int getLayoutId();
	
	public abstract int getIdViewToAppendFromInflate();
	
	public abstract int getIdViewToInflate();

}
