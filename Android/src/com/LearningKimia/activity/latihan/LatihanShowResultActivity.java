package com.LearningKimia.activity.latihan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.util.Utility;

public class LatihanShowResultActivity extends BaseMyActivity{
	Button btnOk;
	EditText namaEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initBundle() {
		super.initBundle();
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		((Button) findViewById(R.id.button_back)).setVisibility(Button.INVISIBLE);
		((Button) findViewById(R.id.button_next)).setVisibility(Button.INVISIBLE);
		btnOk = (Button) findViewById(R.id.btnOk);
		namaEdit = (EditText) findViewById(R.id.namaEdit);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnOk.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOk:
			if(namaEdit.getText().toString().trim().equals("")){
				Utility.showMessage(this, "OK", "Anda belum memasukkan nama");
			} else {
				
			}
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
		return R.layout.latihan_result_page;
	}

}
