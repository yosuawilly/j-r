package com.LearningKimia.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.LearningKimia.R;
import com.LearningKimia.adapter.FileArrayAdapter;
import com.LearningKimia.model.Option;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooserActivity extends ListActivity{
	public static int NO_RESULT = 0;
	public static int RESULT_SUKSES = 1;
	
	private File currentDir;
    private FileArrayAdapter adapter;
    private final String FTYPE = ".mp3";
//    private final String FTYPE = ".prop";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentDir = new File(Environment.getDataDirectory()+"");
        fill(currentDir);
	}
	
	private void fill(File f)
    {
    	File[]dirs = f.listFiles();
		 this.setTitle("Current Dir: "+f.getName());
		 List<Option>dir = new ArrayList<Option>();
		 List<Option>fls = new ArrayList<Option>();
		 try{
			 for(File ff: dirs)
			 {
				if(ff.isDirectory())
					dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
				else
				{
					if(!ff.getName().toLowerCase().contains(FTYPE))continue;
					fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
				}
			 }
		 }catch(Exception e)
		 {
			 
		 }
		 Collections.sort(dir);
		 Collections.sort(fls);
		 dir.addAll(fls);
//		 if(!f.getName().equalsIgnoreCase("sdcard"))
		 if(!f.getName().equals(""))
			 dir.add(0,new Option("..","Parent Directory",f.getParent()));
		 adapter = new FileArrayAdapter(FileChooserActivity.this,R.layout.file_view,dir);
		 this.setListAdapter(adapter);
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Option o = adapter.getItem(position);
		if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
				currentDir = new File(o.getPath());
				fill(currentDir);
		}
		else
		{
			onFileClick(o);
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("option", new Option());
		setResult(NO_RESULT, intent);
//		Intent intent = new Intent(this, SettingActivity.class);
//		Option o = new Option("", "", "");
//		intent.putExtra("nama", o.getName());
//		intent.putExtra("data", o.getData());
//		intent.putExtra("path", o.getPath());
//		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//		startActivity(intent);
//		super.onBackPressed();
	}
	
	private void onFileClick(Option o)
    {
		Intent intent = new Intent();
		intent.putExtra("option", o);
		setResult(RESULT_SUKSES, intent);
//		Intent intent = new Intent(this, SettingActivity.class);
//		intent.putExtra("nama", o.getName());
//		intent.putExtra("data", o.getData());
//		intent.putExtra("path", o.getPath());
//		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//		startActivity(intent);
//    	Toast.makeText(this, "File Clicked: "+o.getName(), Toast.LENGTH_SHORT).show();
    }

}
