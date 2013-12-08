package com.LearningKimia.activity.latihan;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.activity.latihan.score.ScoreSelectionActivity;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.Quiz;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.MyLCG;

public class LatihanSelectionActivity extends BaseMyActivity{
	LinearLayout btnLatihan, btnPeriodik, btnScore;
	Button btnBack;
	DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initObject() {
		super.initObject();
		dbHelper = new DatabaseHelper(this);
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		
		btnLatihan = (LinearLayout) findViewById(R.id.btnLatihan);
		btnPeriodik = (LinearLayout) findViewById(R.id.btnPeriodik);
		btnScore = (LinearLayout) findViewById(R.id.btnScore);
		btnBack = (Button) findViewById(R.id.button_back);
		((Button) findViewById(R.id.button_next)).setVisibility(Button.INVISIBLE);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		btnLatihan.setOnClickListener(this);
		btnPeriodik.setOnClickListener(this);
		btnScore.setOnClickListener(this);
		btnBack.setOnClickListener(this);
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
		return R.layout.latihan_selection_page;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnLatihan:
			List<Quiz> quizs = dbHelper.getQuiz();
			List<Quiz> acakQuiz = new ArrayList<Quiz>();
			int[]acak = MyLCG.getLCG(quizs.size());
			for(int i=0;i<acak.length;i++){
				if(i>=Constant.max_soal_latihan) break;
				if(acakQuiz.contains(quizs.get(acak[i]))){
					for(Quiz q : quizs){
						if(!acakQuiz.contains(q)){
							acakQuiz.add(q);
							break;
						}
					}
				} else 
				acakQuiz.add(quizs.get(acak[i]));
			}
			intent = new Intent(this, LatihanActivity.class);
			intent.putParcelableArrayListExtra("acak_quiz", (ArrayList<? extends Parcelable>) acakQuiz);
			startActivity(intent);
			break;
		case R.id.btnPeriodik:
			
			break;
		case R.id.btnScore:
			intent = new Intent(this, ScoreSelectionActivity.class);
			startActivity(intent);
			break;
		case R.id.button_back:
			onBackPressed();
			break;
		default:
			super.onClick(v);
			break;
		}
	}

}
