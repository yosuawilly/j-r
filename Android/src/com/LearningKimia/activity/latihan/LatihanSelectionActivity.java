package com.LearningKimia.activity.latihan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.activity.latihan.score.ScoreSelectionActivity;

public class LatihanSelectionActivity extends BaseMyActivity{
	LinearLayout btnLatihan, btnPeriodik, btnScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		
		btnLatihan = (LinearLayout) findViewById(R.id.btnLatihan);
		btnPeriodik = (LinearLayout) findViewById(R.id.btnPeriodik);
		btnScore = (LinearLayout) findViewById(R.id.btnScore);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnLatihan.setOnClickListener(this);
		btnPeriodik.setOnClickListener(this);
		btnScore.setOnClickListener(this);
	}

	@Override
	public int getLayoutId() {
		return R.layout.latihan_selection_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		return 0;
	}

	@Override
	public int getIdViewToInflate() {
		return 0;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnLatihan:
			intent = new Intent(this, LatihanActivity.class);
			startActivity(intent);
			break;
		case R.id.btnPeriodik:
			
			break;
		case R.id.btnScore:
			intent = new Intent(this, ScoreSelectionActivity.class);
			startActivity(intent);
			break;
		default:
			super.onClick(v);
			break;
		}
	}

}
