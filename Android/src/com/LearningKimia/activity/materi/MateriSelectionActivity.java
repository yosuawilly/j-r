package com.LearningKimia.activity.materi;

import java.util.ArrayList;
import java.util.List;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.LearningKimia.R;
import com.LearningKimia.activity.PdfViewer;
import com.LearningKimia.activity.base.BaseMenuListActivity;
import com.LearningKimia.adapter.MenuListAdapter;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.model.Bab;
import com.LearningKimia.model.Materi;
import com.LearningKimia.model.Menu;
import com.LearningKimia.restfull.CallWebServiceTask;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MateriSelectionActivity extends BaseMenuListActivity{
	protected int semester = 0;
	protected Bab bab;
	protected List<Materi> materis;
	protected DatabaseHelper dbHelper;
	
	protected Materi selectedMateri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBundle();
		
		setHeaderText("Daftar Materi");
		List<Menu> menus = new ArrayList<Menu>();
		dbHelper = new DatabaseHelper(this);
		materis = dbHelper.getMateris(String.valueOf(semester), String.valueOf(bab.getIdBab()));
		if(materis!=null){
			for(Materi materi : materis){
				menus.add(new Menu(materi.getJudul()));
			}
		}
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		selectedMateri = materis.get(position);
		
		CallWebServiceTask task = new CallWebServiceTask(this, this, "Getting link video for materi");
		task.execute(Constant.GET_LINK_VIDEO_URL + selectedMateri.getId_materi(), Constant.REST_GET);
		
//		Intent intent = new Intent(this, PdfViewer.class);
//		Materi materi = materis.get(position);
//		String urlPathFile = ((Utility.isSDCardExist())?Constant.MATERI_PATH_SD_CARD:Constant.MATERI_PATH) + materi.getJudul() + ".pdf";
//		intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, urlPathFile);
//		startActivity(intent);
	}
	
	@Override
	public void onTaskComplete(Object... params) {
		String result = (String) params[0];
		if (Utility.cekValidResult(result, this)) {
			Gson gson = new GsonBuilder().serializeNulls().create();
			Materi materi = gson.fromJson(result, Materi.class);
			selectedMateri.setLinkVideos(materi.getLinkVideos());
			
			Intent intent = new Intent(this, PdfViewer.class);
			String urlPathFile = ((Utility.isSDCardExist())?Constant.MATERI_PATH_SD_CARD:Constant.MATERI_PATH) + selectedMateri.getJudul() + ".pdf";
			intent.putExtra("materi", selectedMateri);
			intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, urlPathFile);
			startActivity(intent);
		}
	}

}
