package com.LearningKimia.activity.materi;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMenuListActivity;
import com.LearningKimia.adapter.MenuListAdapter;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.Bab;
import com.LearningKimia.model.Menu;

public class BabSelectionActivity extends BaseMenuListActivity{
	protected int semester = 0;
	protected List<Bab> babs;
	protected DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBundle();
		
		setHeaderText("Daftar Bab");
		List<Menu> menus = new ArrayList<Menu>();
		dbHelper = new DatabaseHelper(this);
		babs = dbHelper.getBabs();
		if(babs!=null){
			for(Bab bab : babs){
				menus.add(new Menu(bab.getLabelBab(), bab.getJudulBab()));
			}
		}
		MenuListAdapter adapter = new MenuListAdapter(this, R.layout.list_layout, menus);
		setListAdapter(adapter);
	}
	
	public void initBundle(){
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			semester = bundle.getInt("semester");
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, MateriSelectionActivity.class);
		intent.putExtra("bab", babs.get(position));
		intent.putExtra("semester", semester);
		startActivity(intent);
	}

}
