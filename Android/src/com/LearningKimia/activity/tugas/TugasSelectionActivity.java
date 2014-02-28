package com.LearningKimia.activity.tugas;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMenuListActivity;
import com.LearningKimia.adapter.MenuListAdapter;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.model.Menu;
import com.LearningKimia.model.Siswa;
import com.LearningKimia.model.Tugas;
import com.LearningKimia.restfull.CallWebServiceTask;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TugasSelectionActivity extends BaseMenuListActivity{
	protected List<Tugas> tugass;
	protected DatabaseHelper dbHelper;
	private final int LIHAT_NILAI = 11;
	
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
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(1, this.LIHAT_NILAI, 2, "Lihat Nilai Tugas");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case LIHAT_NILAI:
			CallWebServiceTask task = new CallWebServiceTask(this, this, "Getting nilai tugas...");
			task.execute(Constant.GET_NILAI_TUGAS_URL+GlobalVar.getInstance().getSiswa().getIdSiswa(), Constant.REST_GET);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, TugasShowActivity.class);
		intent.putExtra("tugas", tugass.get(position));
		startActivity(intent);
	}
	
	@Override
	public void onTaskComplete(Object... params) {
		String result = (String) params[0];
		if (Utility.cekValidResult(result, this)) {
			Gson gson = new GsonBuilder().serializeNulls().create();
			Siswa siswa = gson.fromJson(result, Siswa.class);
			if(siswa!=null){
				GlobalVar.getInstance().getSiswa().setNilaiTugas(siswa.getNilaiTugas());
			}
			
			Intent intent = new Intent(this, TugasShowNilaiActivity.class);
			startActivity(intent);
		}
	}

}
