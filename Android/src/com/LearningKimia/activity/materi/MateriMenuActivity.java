package com.LearningKimia.activity.materi;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MateriMenuActivity extends BaseActivity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.materi);
		
		((ImageView) findViewById(R.id.button_semester1)).setOnClickListener(this);
		((ImageView) findViewById(R.id.button_semester2)).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button_semester1:
			intent = new Intent(this, BabSelectionActivity.class);
			intent.putExtra("semester", 1);
			startActivity(intent);
			break;
		case R.id.button_semester2:
			intent = new Intent(this, BabSelectionActivity.class);
			intent.putExtra("semester", 2);
			startActivity(intent);
			break;
		default:
			super.onClick(v);
			break;
		}
	}
	
}
