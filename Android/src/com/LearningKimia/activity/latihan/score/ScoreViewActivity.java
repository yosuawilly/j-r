package com.LearningKimia.activity.latihan.score;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.database.ScoreDbHelper;
import com.LearningKimia.model.Score;
import com.LearningKimia.util.ScoreType;

public class ScoreViewActivity extends BaseMyActivity{
	protected TableLayout tableScore;
	protected ScoreDbHelper scoreDbHelper;
	protected List<Score> scores;
	protected boolean fromLatihan = false;
	
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
			fromLatihan = getIntent().getBooleanExtra("fromLatihan", false);
			ScoreType scoreType = bundle.getParcelable("type");
			if(scoreType!=null){
				scoreDbHelper = new ScoreDbHelper(this);
				scores = scoreDbHelper.getAllScore(scoreType);
			}
		}
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		if(fromLatihan){
			((Button) findViewById(R.id.button_back)).setVisibility(Button.INVISIBLE);
		} else {
			((Button) findViewById(R.id.button_back)).setOnClickListener(this);
		}
		((Button) findViewById(R.id.button_next)).setVisibility(Button.INVISIBLE);
		
		tableScore = (TableLayout) findViewById(R.id.tableScore);
		
		updateTableScore();
	}
	
	public void updateTableScore(){
		tableScore.removeViews(1, tableScore.getChildCount() - 1);
		int position = 0;
		for(Score score : scores){
			TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.row_table_katalog, null);
			
			TextView txtNama = (TextView) tableRow.findViewById(R.id.TvNama);
    		txtNama.setTextSize(14);
    		txtNama.setText(score.getNama());
    		
    		TextView txtArti = (TextView) tableRow.findViewById(R.id.TvArti);
    		txtArti.setTextSize(14);
    		txtArti.setText(score.getScore());
    		
    		if (position % 2 == 0){
    			tableRow.setBackgroundColor(Color.rgb(234, 234, 234));
    		}else{
    			tableRow.setBackgroundColor(Color.rgb(255, 255, 255));
    		}
    		
    		tableScore.addView(tableRow);
    		
    		position++;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			onBackPressed();
			break;
		default:
			super.onClick(v);
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if(fromLatihan){
			Intent intent = new Intent(this, LearningKimiaActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			this.finish();
		} else 
		super.onBackPressed();
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
		return R.layout.score_view_page;
	}

}
