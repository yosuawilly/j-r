package com.LearningKimia.activity.base;

import com.LearningKimia.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BaseMenuListActivity extends BaseListActivity implements OnClickListener{
	protected Button buttonBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_list_page);
		
		buttonBack = (Button) findViewById(R.id.button_back);
		buttonBack.setOnClickListener(this);
	}
	
	public void setHeaderText(String header){
		((TextView) findViewById(R.id.headerText)).setText(header);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			BaseMenuListActivity.this.finish();
			onBackPressed();
			break;
		default:
			break;
		}
	}

}
