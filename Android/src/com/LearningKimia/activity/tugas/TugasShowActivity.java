package com.LearningKimia.activity.tugas;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseActivity;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.SoalTugas;
import com.LearningKimia.model.Tugas;
import com.LearningKimia.util.Functional;

public class TugasShowActivity extends BaseActivity implements Functional{
	public static final int REQUEST_UPLOAD = 0;
	
	protected Tugas tugasSelected;
	protected List<SoalTugas> soalTugas;
	protected DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tugas_show_page);
		initBundle();
		
		initObject();
		initDesign();
		initObjectToDesign();
		initListener();
	}

	@Override
	public void initBundle() {
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			tugasSelected = (Tugas)bundle.getSerializable("tugas");
		}
	}

	@Override
	public void initObject() {
		dbHelper = new DatabaseHelper(this);
		soalTugas = new ArrayList<SoalTugas>();
		if(tugasSelected != null){
			soalTugas = dbHelper.getSoalTugas(tugasSelected.getId_tugas());
		}
	}

	@Override
	public void initDesign() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initObjectToDesign() {
		LinearLayout linearTugasMain = (LinearLayout) findViewById(R.id.linearTugasMain);
		int numRow = 1;
		for(SoalTugas soal : soalTugas){
			LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.row_soal_tugas_layout, null);
			((TextView) row.findViewById(R.id.TvNomor)).setText(numRow+". ");
			((TextView) row.findViewById(R.id.TvIsiSoal)).setText(soal.getIsi_soal());
			linearTugasMain.addView(row);
			numRow++;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.upload_tugas_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.menu_upload:
			intent = new Intent(this, TugasUploadActivity.class);
			intent.putExtra("tugasSelected", tugasSelected);
			startActivityForResult(intent, REQUEST_UPLOAD);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

}
