package com.LearningKimia.activity.latihan.periodik;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.model.SoalPeriodik;

public class LatihanPeriodikActivity extends BaseMyActivity{
	protected Button btnBack, btnNext;
	List<SoalPeriodik> soalPeriodiks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initBundle() {
		super.initBundle();
		if(getIntent().getExtras()!=null){
			soalPeriodiks = getIntent().getParcelableArrayListExtra("soal_periodik");
		}
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		btnBack = (Button) findViewById(R.id.button_back);
		btnNext = (Button) findViewById(R.id.button_next);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnBack.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_next:
			
			break;
		default:
			super.onClick(v);
			break;
		}
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
		return R.layout.latihan_periodik_page;
	}

}
