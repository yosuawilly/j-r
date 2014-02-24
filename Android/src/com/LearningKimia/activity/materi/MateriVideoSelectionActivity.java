package com.LearningKimia.activity.materi;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMenuListActivity;
import com.LearningKimia.adapter.MenuListAdapter;
import com.LearningKimia.model.LinkVideo;
import com.LearningKimia.model.Materi;
import com.LearningKimia.model.Menu;
import com.LearningKimia.util.Utility;

public class MateriVideoSelectionActivity extends BaseMenuListActivity{
	
	protected Materi materi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBundle();
		
		setHeaderText(materi!=null ? materi.getJudul():"Video Materi");
		
		List<Menu> menus = new ArrayList<Menu>();
		if(materi!=null){
			for(LinkVideo linkVideo : materi.getLinkVideos()) {
				menus.add(new Menu(linkVideo.getLink_video()));
			}
		}
		MenuListAdapter adapter = new MenuListAdapter(this, R.layout.list_layout, menus);
		setListAdapter(adapter);
	}
	
	private void initBundle(){
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			materi = (Materi) bundle.getSerializable("materi");
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		LinkVideo linkVideo = materi.getLinkVideos().get(position);
		
//		String video_path = "https://www.youtube.com/watch?v=qpQtlAW3mag";
		String video_path = linkVideo.getLink_video();
		if(!video_path.contains("www.youtube.com/watch")){
			Utility.showMessage(this, "Tutup", "Not valid youtube video url");
			return;
		}
	    Uri uri = Uri.parse(video_path);
	    
	    if(uri.getQueryParameter("v") == null){
	    	Utility.showMessage(this, "Tutup", "No video id found");
			return;
	    }
	    
	    uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
	    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	    startActivity(intent);
	    
//		LinkVideo linkVideo = materi.getLinkVideos().get(position);
//		
//		Intent intent = new Intent(this, MateriVideoViewActivity.class);
//		intent.putExtra("link", linkVideo);
//		startActivity(intent);
	}
	
}
