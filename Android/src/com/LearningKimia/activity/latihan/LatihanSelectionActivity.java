package com.LearningKimia.activity.latihan;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.activity.latihan.periodik.LatihanPeriodikActivity;
import com.LearningKimia.activity.latihan.score.ScoreSelectionActivity;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.Quiz;
import com.LearningKimia.model.SoalPeriodik;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.MyLCG;
import com.LearningKimia.util.ResourceUtil;
import com.LearningKimia.util.Utility;

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
			if(acakQuiz.size()==0){
				Utility.showMessage(this, "Tutup", ResourceUtil.getBundle().getString("LK-0003"));
				return;
			}
			intent = new Intent(this, LatihanActivity.class);
			intent.putParcelableArrayListExtra("acak_quiz", (ArrayList<? extends Parcelable>) acakQuiz);
			startActivity(intent);
			break;
		case R.id.btnPeriodik:
			new LoadSoalPeriodik(this).execute(new Object());
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
	
	public class LoadSoalPeriodik extends AsyncTask<Object, String, List<SoalPeriodik>>{
		ProgressDialog dialog;
		Context context;
		
		public LoadSoalPeriodik(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(context,    
	                "Please wait...", "Acak Soal Periodik", true, true, 
	                new DialogInterface.OnCancelListener(){
	                @Override
	                public void onCancel(DialogInterface dialog) {
	                }
	            }
	        );
		}

		@Override
		protected List<SoalPeriodik> doInBackground(Object... params) {
			DatabaseHelper dbHelper = new DatabaseHelper(context);
			List<SoalPeriodik> soalPeriodiks = dbHelper.getAllSoalPeriodik();
			List<SoalPeriodik> acakSoal = new ArrayList<SoalPeriodik>();
			int[]acak = MyLCG.getLCG(soalPeriodiks.size());
			for(int i=0;i<acak.length;i++){
				if(i>=Constant.max_soal_latihan_periodik) break;
				if(acakSoal.contains(soalPeriodiks.get(acak[i]))){
					for(SoalPeriodik s : soalPeriodiks){
						if(!acakSoal.contains(s)){
							acakSoal.add(s);
							break;
						}
					}
				} else 
				acakSoal.add(soalPeriodiks.get(acak[i]));
			}
			return acakSoal;
		}
		
		@Override
		protected void onPostExecute(List<SoalPeriodik> soalPeriodiks) {
			if(dialog!=null) dialog.dismiss();
			if(soalPeriodiks.size()==0){
				Utility.showMessage(LatihanSelectionActivity.this, "Tutup", ResourceUtil.getBundle().getString("LK-0003"));
				return;
			}
			Intent intent = new Intent(LatihanSelectionActivity.this, LatihanPeriodikActivity.class);
			intent.putParcelableArrayListExtra("soal_periodik", (ArrayList<? extends Parcelable>) soalPeriodiks);
			startActivity(intent);
		}
		
	}

}
