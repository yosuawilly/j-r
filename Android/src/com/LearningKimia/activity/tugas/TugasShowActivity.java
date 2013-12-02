package com.LearningKimia.activity.tugas;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseActivity;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.SoalTugas;
import com.LearningKimia.model.Tugas;
import com.LearningKimia.util.Functional;

public class TugasShowActivity extends BaseActivity implements Functional{
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
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

}
