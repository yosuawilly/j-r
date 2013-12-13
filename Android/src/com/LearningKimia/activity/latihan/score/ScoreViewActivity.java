package com.LearningKimia.activity.latihan.score;

import android.os.Bundle;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;

public class ScoreViewActivity extends BaseMyActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}

	@Override
	public int getLayoutId() {
		return R.layout.base_layout_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		return R.id.linearLayoutMain;
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.score_view_page;
	}

}
