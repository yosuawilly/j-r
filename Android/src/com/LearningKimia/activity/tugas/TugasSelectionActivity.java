package com.LearningKimia.activity.tugas;

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
import com.LearningKimia.model.Menu;
import com.LearningKimia.model.Tugas;

public class TugasSelectionActivity extends BaseMenuListActivity{
	protected List<Tugas> tugass;
	protected DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHeaderText("Daftar Tugas");
		List<Menu> menus = new ArrayList<Menu>();
		dbHelper = new DatabaseHelper(this);
		tugass = dbHelper.getAllTugas();
		if(tugass!=null){
			for(Tugas tugas : tugass){
				menus.add(new Menu(tugas.getJudul_tugas(), tugas.getCatatan()));
			}
		}
		MenuListAdapter adapter = new MenuListAdapter(this, R.layout.list_layout, menus);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, TugasShowActivity.class);
		intent.putExtra("tugas", tugass.get(position));
		startActivity(intent);
	}

}
