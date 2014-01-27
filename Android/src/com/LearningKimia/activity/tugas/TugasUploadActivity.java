package com.LearningKimia.activity.tugas;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.LearningKimia.R;
import com.LearningKimia.activity.FileChooserActivity;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.listener.DialogListener;
import com.LearningKimia.model.Option;
import com.LearningKimia.model.Tugas;
import com.LearningKimia.restfull.UploadFileTugasTask.UploadListener;
import com.LearningKimia.restfull.UploadFileTugasTask2;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Utility;

public class TugasUploadActivity extends BaseMyActivity implements UploadListener<Object>, DialogListener{
	private final int BROWSE_FILE = 11;
	
	protected Tugas tugasSelected;
	
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
	public void initBundle() {
		super.initBundle();
		if(getIntent().getExtras() != null){
			tugasSelected = (Tugas) getIntent().getExtras().getSerializable("tugasSelected");
		}
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		btnBack = (Button) findViewById(R.id.button_back);
		btnNext = (Button) findViewById(R.id.button_next);
		btnNext.setVisibility(View.INVISIBLE);
		
		editTextPath = (EditText) findViewById(R.id.EditTextPath);
		editTextPath.setInputType(InputType.TYPE_NULL);
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
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == BROWSE_FILE){
			if(data!=null){
				this.option = (Option) data.getSerializableExtra("option");
				editTextPath.setText(option.getPath());
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			onBackPressed();
			break;
		case R.id.btnBrowse:
			Intent intent = new Intent(this, FileChooserActivity.class);
			startActivityForResult(intent, BROWSE_FILE);
			break;
		case R.id.btnUpload:
			String filePath = editTextPath.getText().toString();
			if(filePath!=null && !filePath.equals("")){
				UploadFileTugasTask2 fileTugasTask = new UploadFileTugasTask2(this, this);
				if(tugasSelected!=null)
				fileTugasTask.execute(filePath, "id_siswa="+GlobalVar.getInstance().getSiswa().getIdSiswa()+"&id_tugas="+tugasSelected.getId_tugas());
				
				else Utility.showErrorMessage(this, "Tidak ada tugas yang dipilih");
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
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
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
		if(Constant.UPLOAD_SUKSES.equals( (String)params[0] )) {
			Utility.showMessage(this, "OK", (String) params[0], this);
		}
		else Utility.showMessage(this, "OK", (String) params[0]);
	}

	@Override
	public void onDialogClose() {
		setResult(RESULT_OK);
		finish();
	}

}
