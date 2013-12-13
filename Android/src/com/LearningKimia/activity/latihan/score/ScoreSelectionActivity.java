package com.LearningKimia.activity.latihan.score;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.util.ScoreType;

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
		Intent intent;
		switch (v.getId()) {
		case R.id.btnLatihan:
			intent = new Intent(this, ScoreViewActivity.class);
			intent.putExtra("type", (Parcelable)ScoreType.PILIHAN_GANDA);
			startActivity(intent);
			break;
		case R.id.btnPeriodik:
			intent = new Intent(this, ScoreViewActivity.class);
			intent.putExtra("type", (Parcelable)ScoreType.PERIODIK);
			startActivity(intent);
			break;
		default:
			super.onClick(v);
			break;
		}
	}

}
