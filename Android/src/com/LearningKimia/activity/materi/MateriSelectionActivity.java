package com.LearningKimia.activity.materi;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMenuListActivity;
import com.LearningKimia.adapter.MenuListAdapter;
import com.LearningKimia.model.Bab;
import com.LearningKimia.model.Menu;

public class MateriSelectionActivity extends BaseMenuListActivity{
	protected int semester = 0;
	protected Bab bab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBundle();
		
		setHeaderText("Daftar Materi");
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(new Menu("Test Materi"));
		menus.add(new Menu("Test Materi 2"));
		MenuListAdapter adapter = new MenuListAdapter(this, R.layout.list_layout, menus);
		setListAdapter(adapter);
	}
	
	public void initBundle(){
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			bab = (Bab)bundle.get("bab");
			semester = bundle.getInt("semester");
			if(bab!=null){
				Log.i("bab", bab.getLabelBab());
				Log.i("semester", String.valueOf(semester));
			}
		}
	}

}
