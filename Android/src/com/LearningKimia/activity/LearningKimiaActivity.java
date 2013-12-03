package com.LearningKimia.activity;

import com.LearningKimia.LoginActivity;
import com.LearningKimia.R;
import com.LearningKimia.activity.CariGlossarium;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.activity.base.BaseActivity;
import com.LearningKimia.activity.latihan.LatihanSelectionActivity;
import com.LearningKimia.activity.materi.MateriMenuActivity;
import com.LearningKimia.activity.tugas.TugasSelectionActivity;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.util.Functional;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class LearningKimiaActivity extends BaseActivity implements Functional{
	
	ImageView menu_katalog, menu_materi, menu_quiz, menu_tugas, tablePeriodik, btn_logout;
	Menu menu;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        initBundle();
        initObject();
        initDesign();
        initObjectToDesign();
        initListener();
    }

    public boolean onCreateOptionsMenu(Menu menu){
    	this.menu = menu;
    	menu.add(0, 1, 0, "Help").setIcon(android.R.drawable.ic_menu_help);
        menu.add(0, 2, 0, "About").setIcon(android.R.drawable.ic_menu_info_details);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem Item) {
    	switch (Item.getItemId()) {
            case 1:            
            //fileList();
        	Dialog dialog = new Dialog(this);
    		dialog.setContentView(R.layout.bantuan);
    		dialog.show();
        	return true;
            case 2:
        	Dialog dialog1 = new Dialog(this);
    		dialog1.setContentView(R.layout.about);
    		dialog1.show();
            return true;
        }
    	return false;
    }

	@Override
	public void initBundle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDesign() {
		menu_katalog = (ImageView) findViewById(R.id.menu_katalog);
		menu_materi = (ImageView) findViewById(R.id.menu_materi);
		menu_quiz = (ImageView) findViewById(R.id.menu_quiz);
		menu_tugas = (ImageView) findViewById(R.id.menu_tugas);
		tablePeriodik = (ImageView) findViewById(R.id.menu_table_periodik);
		btn_logout = (ImageView) findViewById(R.id.btn_logout);
	}

	@Override
	public void initObjectToDesign() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		menu_katalog.setOnClickListener(this);
		menu_materi.setOnClickListener(this);
		menu_quiz.setOnClickListener(this);
		menu_tugas.setOnClickListener(this);
		tablePeriodik.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.menu_katalog:
			intent = new Intent(LearningKimiaActivity.this, CariGlossarium.class);
			startActivity(intent);
			break;
		case R.id.menu_materi:
			intent = new Intent (LearningKimiaActivity.this, MateriMenuActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_quiz:
			intent = new Intent (LearningKimiaActivity.this, LatihanSelectionActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_tugas:
			intent = new Intent(LearningKimiaActivity.this, TugasSelectionActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_table_periodik:
			break;
		case R.id.btn_logout:
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Keluar")
	        .setMessage("Apakah anda yakin akan keluar dari aplikasi E-Learning?")
	        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(LearningKimiaActivity.this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					GlobalVar.getInstance().clearAllObject();
					startActivity(intent);
					LearningKimiaActivity.this.finish();
				}
			})
			.setNegativeButton("Tidak", null).show();
			break;
		default:
			break;
		}
	}
    
}