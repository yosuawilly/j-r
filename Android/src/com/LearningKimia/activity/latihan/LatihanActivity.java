package com.LearningKimia.activity.latihan;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.model.Quiz;
import com.LearningKimia.util.ScoreType;

public class LatihanActivity extends BaseMyActivity{
	protected TextView nomorSoal, isiSoal, jumlahSoalTv;
	protected RadioButton rbJwb1, rbJwb2, rbJwb3, rbJwb4;
	protected Button btnBack, btnNext;
	protected List<Quiz> quizs;
	protected List<Integer> jawaban;
	protected int currentIdxSoal = 0;
	protected int jumlahSoal = 0;
	
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
			quizs = bundle.getParcelableArrayList("acak_quiz");
			jumlahSoal = quizs.size();
		}
		jawaban = new ArrayList<Integer>();
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		nomorSoal = (TextView) findViewById(R.id.nomorSoal);
		isiSoal = (TextView) findViewById(R.id.isiSoal);
		jumlahSoalTv = (TextView) findViewById(R.id.jumlah_soal);
		
		rbJwb1 = (RadioButton) findViewById(R.id.jwb1);
		rbJwb2 = (RadioButton) findViewById(R.id.jwb2);
		rbJwb3 = (RadioButton) findViewById(R.id.jwb3);
		rbJwb4 = (RadioButton) findViewById(R.id.jwb4);
		
		btnBack = (Button) findViewById(R.id.button_back);
		btnNext = (Button) findViewById(R.id.button_next);
	}
	
	@Override
	public void initObjectToDesign() {
		super.initObjectToDesign();
		if(quizs != null){
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
		Quiz quiz = quizs.get(currentIdxSoal);
		jumlahSoalTv.setText(String.valueOf(currentIdxSoal+1) + "/" + String.valueOf(quizs.size()));
		
		nomorSoal.setText((currentIdxSoal+1)+". ");
		isiSoal.setText(quiz.getSoal_quiz());
		rbJwb1.setText((quiz.getJawabanQuizs().get(0)!=null)?quiz.getJawabanQuizs().get(0).getJawaban():"");
		rbJwb2.setText((quiz.getJawabanQuizs().get(1)!=null)?quiz.getJawabanQuizs().get(1).getJawaban():"");
		rbJwb3.setText((quiz.getJawabanQuizs().get(2)!=null)?quiz.getJawabanQuizs().get(2).getJawaban():"");
		rbJwb4.setText((quiz.getJawabanQuizs().get(3)!=null)?quiz.getJawabanQuizs().get(3).getJawaban():"");
		
		rbJwb1.setChecked(false);
		rbJwb2.setChecked(false);
		rbJwb3.setChecked(false);
		rbJwb4.setChecked(false);
	}
	
	public int getJawaban(){
		if(rbJwb1.isChecked()){
			return 0;
		} else if(rbJwb2.isChecked()){
			return 1;
		} else if(rbJwb3.isChecked()){
			return 2;
		} else if(rbJwb4.isChecked()){
			return 3;
		} else {
			return -1;
		}
	}
	
	public int getJawabanBenar(){
		int jawabanbenar = 0;
		if(quizs.size() == jawaban.size()){
			for(int i=0; i<quizs.size(); i++){
				if(jawaban.get(i)==-1) continue; //berarti jawaban tidak diisi
				if( quizs.get(i).getJawabanQuizs().get(jawaban.get(i)).isBenar() ){
					jawabanbenar++;
				}
			}
		} else jawabanbenar = -1;
		return jawabanbenar;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			onBackPressed();
			break;
		case R.id.button_next:
			if(currentIdxSoal<jumlahSoal-1){
				jawaban.add(currentIdxSoal, getJawaban());
				currentIdxSoal++;
				showSoal();
			} else {
				jawaban.add(currentIdxSoal, getJawaban());
				
				int jawabanBenar = getJawabanBenar();
				int score = ( 100 / quizs.size() ) * jawabanBenar;
				if(jawabanBenar != -1){
					Intent intent = new Intent(this, LatihanShowResultActivity.class);
					intent.putExtra("score", score);
					intent.putExtra("jumlah_soal", quizs.size());
					intent.putExtra("jawaban_benar", jawabanBenar);
					intent.putExtra("type", (Parcelable)ScoreType.PILIHAN_GANDA);
					startActivity(intent);
				}
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
		return R.layout.latihan_page;
	}

}
