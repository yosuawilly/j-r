package com.LearningKimia.activity.latihan.periodik;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.activity.latihan.LatihanShowResultActivity;
import com.LearningKimia.model.SoalPeriodik;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.ScoreType;
import com.LearningKimia.util.Utility;

public class LatihanPeriodikActivity extends BaseMyActivity{
	protected TextView jumlahSoalTv;
	protected Button btnBack, btnNext;
	protected ImageView imageSoalPeriodik;
	protected EditText editTextJwbPeriodik;
	protected List<SoalPeriodik> soalPeriodiks;
	protected SoalPeriodik currentSoalPeriodik;
	protected int currentIdxSoal = 0;
	protected int jumlahSoal = 0;
	protected int jawabanBenar;
	
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
			if(soalPeriodiks!=null) jumlahSoal = soalPeriodiks.size();
		}
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		btnBack = (Button) findViewById(R.id.button_back);
		btnNext = (Button) findViewById(R.id.button_next);
		
		jumlahSoalTv = (TextView) findViewById(R.id.jumlah_soal);
		
		imageSoalPeriodik = (ImageView) findViewById(R.id.imageSoalPeriodik);
		editTextJwbPeriodik = (EditText) findViewById(R.id.editTextJwbPeriodik);
	}
	
	@Override
	public void initObjectToDesign() {
		super.initObjectToDesign();
		if(soalPeriodiks!=null){
			showSoal();
		}
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnBack.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}
	
	public void showSoal(){
		currentSoalPeriodik = soalPeriodiks.get(currentIdxSoal);
		jumlahSoalTv.setText(String.valueOf(currentIdxSoal+1) + "/" + String.valueOf(soalPeriodiks.size()));
		editTextJwbPeriodik.setText("");
		Drawable drawable = Utility.getDrawableFromAsset(this, Constant.IMAGE_PERIODIK_PATH + currentSoalPeriodik.getImagePath());
		if(drawable!=null){
//			imageSoalPeriodik.setImageDrawable(drawable);
			imageSoalPeriodik.setBackgroundDrawable(drawable);
		} else {
			Utility.showErrorMessage(this, "Drawable is null");
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_next:
			String jawaban = editTextJwbPeriodik.getText().toString();
			if(currentIdxSoal<jumlahSoal-1){
				if(jawaban.equalsIgnoreCase(currentSoalPeriodik.getJawaban())){
					jawabanBenar++;
				}
				currentIdxSoal++;
				showSoal();
			} else {
				if(jawaban.equalsIgnoreCase(currentSoalPeriodik.getJawaban())){
					jawabanBenar++;
				}
				int score = ( 100 / soalPeriodiks.size() ) * jawabanBenar;
				Intent intent = new Intent(this, LatihanShowResultActivity.class);
				intent.putExtra("score", score);
				intent.putExtra("jumlah_soal", soalPeriodiks.size());
				intent.putExtra("jawaban_benar", jawabanBenar);
				intent.putExtra("type", (Parcelable)ScoreType.PERIODIK);
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
		return R.layout.latihan_periodik_page;
	}

}
