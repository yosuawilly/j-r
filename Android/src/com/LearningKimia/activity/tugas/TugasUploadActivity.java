package com.LearningKimia.activity.tugas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.LearningKimia.R;
import com.LearningKimia.activity.FileChooserActivity;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.model.Option;
import com.LearningKimia.restfull.UploadFileTugasTask;
import com.LearningKimia.restfull.UploadFileTugasTask.UploadListener;
import com.LearningKimia.util.Utility;

public class TugasUploadActivity extends BaseMyActivity implements UploadListener<Object>{
	private final int BROWSE_FILE = 11;
	
	protected EditText editTextPath;
	protected Button btnBack, btnNext;
	protected Button btnBrowse, btnUpload;
	
	protected Option option;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		btnBack = (Button) findViewById(R.id.button_back);
		btnNext = (Button) findViewById(R.id.button_next);
		btnNext.setVisibility(View.INVISIBLE);
		
		editTextPath = (EditText) findViewById(R.id.EditTextPath);
		btnBrowse = (Button) findViewById(R.id.btnBrowse);
		btnUpload = (Button) findViewById(R.id.btnUpload);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnBack.setOnClickListener(this);
		
		btnBrowse.setOnClickListener(this);
		btnUpload.setOnClickListener(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == BROWSE_FILE){
			if(data!=null){
				this.option = (Option) data.getSerializableExtra("option");
				editTextPath.setText(option.getPath());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			
			break;
		case R.id.btnBrowse:
			Intent intent = new Intent(this, FileChooserActivity.class);
			startActivityForResult(intent, BROWSE_FILE);
			break;
		case R.id.btnUpload:
			String filePath = editTextPath.getText().toString();
			if(filePath!=null){
				UploadFileTugasTask fileTugasTask = new UploadFileTugasTask(this, this);
				fileTugasTask.execute(filePath);
			} else {
				Utility.showErrorMessage(this, "Anda belum memilih file");
			}
			break;
		default:
			break;
		}
		super.onClick(v);
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
		return R.layout.tugas_upload_page;
	}

	@Override
	public void onUpload(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
