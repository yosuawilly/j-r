package com.LearningKimia.activity.base;

import com.LearningKimia.R;
import com.LearningKimia.StartUpActivity;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.util.Functional;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		}
	}

	@Override
	public void onClick(View v) {
		
	}

}
