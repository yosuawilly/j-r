package com.LearningKimia.activity;

import com.LearningKimia.R;
import com.LearningKimia.activity.materi.MateriVideoSelectionActivity;
import com.LearningKimia.model.Materi;
import com.LearningKimia.util.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

public class PdfViewer extends PdfViewerActivity{
	
	protected Materi materi;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initBundle();
    }
	
	private void initBundle(){
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			materi = (Materi) bundle.getSerializable("materi");
		}
	}

	@Override
    public int getPreviousPageImageResource() {
        return R.drawable.left_arrow;
    }

	@Override
    public int getNextPageImageResource() {
        return R.drawable.right_arrow;
    }

	@Override
    public int getZoomInImageResource() {
        return R.drawable.zoom_in;
    }

	@Override
    public int getZoomOutImageResource() {
        return R.drawable.zoom_out;
    }

	@Override
    public int getPdfPasswordLayoutResource() {
        return R.layout.pdf_file_password;
    }

	@Override
    public int getPdfPageNumberResource() {
        return R.layout.dialog_pagenumber;
    }

	@Override
    public int getPdfPasswordEditField() {
        return R.id.etPassword;
    }

	@Override
    public int getPdfPasswordOkButton() {
        return R.id.btOK;
    }

	@Override
    public int getPdfPasswordExitButton() {
        return R.id.btExit;
    }

	@Override
    public int getPdfPageNumberEditField() {
        return R.id.pagenum_edit;
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.link_video_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.linkVideo:
			if(materi!=null){
				if(materi.getLinkVideos().size()==0){
					Utility.showMessage(this, "Tutup", "Tidak ada link video untuk materi ini");
				} else {
					Intent intent = new Intent(this, MateriVideoSelectionActivity.class);
					intent.putExtra("materi", materi);
					startActivity(intent);
				}
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
