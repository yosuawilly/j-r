package com.LearningKimia.activity.latihan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.activity.latihan.score.ScoreViewActivity;
import com.LearningKimia.database.ScoreDbHelper;
import com.LearningKimia.model.Score;
import com.LearningKimia.util.ScoreType;
import com.LearningKimia.util.Utility;

public class LatihanShowResultActivity extends BaseMyActivity{
	Button btnOk;
	EditText namaEdit;
	TextView scoreTv, jumlahSoalTv, jawabanBenarTv;
	int score, jumlahSoal, jawabanBenar;
	
	ScoreDbHelper scoreDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initBundle() {
		super.initBundle();
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			score = bundle.getInt("score");
			jumlahSoal = bundle.getInt("jumlah_soal");
			jawabanBenar = bundle.getInt("jawaban_benar");
		}
	}
	
	@Override
	public void initObject() {
		super.initObject();
		scoreDbHelper = new ScoreDbHelper(this);
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		((Button) findViewById(R.id.button_back)).setVisibility(Button.INVISIBLE);
		((Button) findViewById(R.id.button_next)).setVisibility(Button.INVISIBLE);
		btnOk = (Button) findViewById(R.id.btnOk);
		namaEdit = (EditText) findViewById(R.id.namaEdit);
		
		scoreTv = (TextView) findViewById(R.id.scoreTv);
		jumlahSoalTv = (TextView) findViewById(R.id.jumlahSoalTv);
		jawabanBenarTv = (TextView) findViewById(R.id.jawabanBenarTv);
	}
	
	@Override
	public void initObjectToDesign() {
		super.initObjectToDesign();
		scoreTv.setText(String.valueOf(score));
		jumlahSoalTv.setText(String.valueOf(jumlahSoal));
		jawabanBenarTv.setText(String.valueOf(jawabanBenar));
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
				String nama = namaEdit.getText().toString();
				Score score = new Score(0, nama, String.valueOf(this.score), ScoreType.PILIHAN_GANDA);
				scoreDbHelper.addScore(score);
				Intent intent = new Intent(this, ScoreViewActivity.class);
				startActivity(intent);
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
