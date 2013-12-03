package com.LearningKimia.activity.latihan.score;

import android.view.View;
import android.widget.LinearLayout;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;

public class ScoreSelectionActivity extends BaseMyActivity{
	LinearLayout btnLatihan, btnPeriodik;

	@Override
	public void initDesign() {
		super.initDesign();
		btnLatihan = (LinearLayout) findViewById(R.id.btnLatihan);
		btnPeriodik = (LinearLayout) findViewById(R.id.btnPeriodik);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnLatihan.setOnClickListener(this);
		btnPeriodik.setOnClickListener(this);
	}
	
	@Override
	public int getLayoutId() {
		return R.layout.score_selection_page;
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
		switch (v.getId()) {
		case R.id.btnLatihan:
			
			break;
		case R.id.btnPeriodik:
			
			break;
		default:
			super.onClick(v);
			break;
		}
	}

}
