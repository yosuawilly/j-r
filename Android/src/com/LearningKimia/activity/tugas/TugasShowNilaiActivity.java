package com.LearningKimia.activity.tugas;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseMyActivity;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.model.NilaiTugas;

public class TugasShowNilaiActivity extends BaseMyActivity{
	
	private TableLayout tableNilai;
	private List<NilaiTugas> nilaiTugas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLayoutMode(true, MyLayout.LINEARLAYOUT);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void initObject() {
		super.initObject();
		nilaiTugas = GlobalVar.getInstance().getSiswa().getNilaiTugas();
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		((Button) findViewById(R.id.button_next)).setVisibility(View.INVISIBLE);
		tableNilai = (TableLayout) findViewById(R.id.table_nilai);
		
		initTableNilai();
	}
	
	@Override
	public void initListener() {
		((Button) findViewById(R.id.button_back)).setOnClickListener(this);
	}
	
	private void initTableNilai(){
		tableNilai.removeViews(1, tableNilai.getChildCount() - 1);
		
		int position = 0;
		for(NilaiTugas tugas : nilaiTugas){
			TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.row_table_nilai_tugas, null);
    		tableRow.setId(position);
//    		tableRow.setTag(tugas.getId_siswa());
    		registerForContextMenu(tableRow);
    		
    		TextView txtId = (TextView) tableRow.findViewById(R.id.TvIdKatalog);
    		txtId.setText(tugas.getId_siswa());
    		
    		TextView txtNama = (TextView) tableRow.findViewById(R.id.TvNama);
    		txtNama.setTextSize(14);
    		txtNama.setText(tugas.getJudul_tugas());
    		
    		TextView txtArti = (TextView) tableRow.findViewById(R.id.TvArti);
    		txtArti.setTextSize(14);
    		txtArti.setText(tugas.getNilai());
    		
    		if (position % 2 == 0){
    			tableRow.setBackgroundColor(Color.rgb(234, 234, 234));
    		}else{
    			tableRow.setBackgroundColor(Color.rgb(255, 255, 255));
    		}
    		
    		tableNilai.addView(tableRow);
    		
    		position++;
		}
	}

	@Override
	public int getLayoutId() {
		return R.layout.base_layout_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		return R.id.linearLayoutMain;
	}

	@Override
	public int getIdViewToInflate() {
		return R.layout.tugas_show_nilai_page;
	}

}
