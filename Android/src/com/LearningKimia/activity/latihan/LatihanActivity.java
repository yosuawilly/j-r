package com.LearningKimia.activity.latihan;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.model.Quiz;

public class LatihanActivity extends BaseMyActivity{
	protected TextView nomorSoal, isiSoal;
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
				Intent intent = new Intent(this, LatihanShowResultActivity.class);
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
		return R.layout.latihan_page;
	}

}
