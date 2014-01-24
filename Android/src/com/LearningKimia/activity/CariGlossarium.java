package com.LearningKimia.activity;

import java.util.List;

import com.LearningKimia.R;
import com.LearningKimia.activity.base.BaseActivity;
import com.LearningKimia.database.DatabaseHelper;
import com.LearningKimia.database.KatalogDbHelper;
import com.LearningKimia.model.Katalog;
import com.LearningKimia.util.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CariGlossarium extends BaseActivity implements OnClickListener, TextWatcher{
	TableLayout lytLinear;
	DatabaseHelper dbHelper;
	KatalogDbHelper katalogDbHelper;
	EditText editTextCari;
	Button btnCari;
	List<Katalog> katalogs;
	private int posKatalogSelected;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glossarium);
        
        editTextCari = (EditText) findViewById(R.id.EditTextCari);
        editTextCari.addTextChangedListener(this);
        btnCari = (Button) findViewById(R.id.btnCari);
        
        dbHelper = new DatabaseHelper(this);
        
        ((Button) findViewById(R.id.button_back)).setOnClickListener(this);
        btnCari.setOnClickListener(this);
          
        lytLinear = (TableLayout)findViewById(R.id.lytLinearNamalaki);
        
        updateTable(null);
    }
    

	public void updateTable(String cari){	
    	lytLinear.removeViews(1, lytLinear.getChildCount() - 1);
    	
		katalogs = dbHelper.getKatalogs(cari);
 
    	//buat baris baru setiap perulangan dan masukkan data ke dalam 
    	//masing-masing baris
		
		int position = 0;
    	for (Katalog katalog : katalogs)
    	{
    		TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.row_table_katalog, null);
    		tableRow.setOnClickListener(listener);
    		tableRow.setId(position);
    		tableRow.setTag(katalog.getNama());
    		registerForContextMenu(tableRow);
    		
    		TextView txtId = (TextView) tableRow.findViewById(R.id.TvIdKatalog);
    		txtId.setText(String.valueOf(katalog.getId_katalog()));
    		
    		TextView txtNama = (TextView) tableRow.findViewById(R.id.TvNama);
    		txtNama.setTextSize(14);
    		txtNama.setText(katalog.getNama());
    		
    		TextView txtArti = (TextView) tableRow.findViewById(R.id.TvArti);
    		txtArti.setTextSize(14);
    		txtArti.setText(katalog.getArti());
    		
    		if (position % 2 == 0){
    			tableRow.setBackgroundColor(Color.rgb(234, 234, 234));
    		}else{
    			tableRow.setBackgroundColor(Color.rgb(255, 255, 255));
    		}
    		
    		lytLinear.addView(tableRow);
    		
    		position++;
    	}
    }
	
	public void tambahUpdateKatalog(final boolean isTambah, String nama, String arti){
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.tambah_katalog_layout);
		dialog.setTitle((isTambah)?"Tambah Katalog":"Ubah Katalog");
		
		final EditText editNama = (EditText) dialog.findViewById(R.id.editNama);
		final EditText editArti = (EditText) dialog.findViewById(R.id.editArti);
		if(!isTambah) {
			editNama.setEnabled(false);
			editNama.setText(nama);
			editArti.setText(arti);
			editArti.requestFocus();
		}
		Button btnSimpan = (Button) dialog.findViewById(R.id.btnSimpan);
		Button btnBatal = (Button) dialog.findViewById(R.id.btnBatal);
		
		btnSimpan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String nama = editNama.getText().toString().trim();
				String arti = editArti.getText().toString().trim();
				if(nama.equals("") || arti.equals("")){
					Utility.showMessage(CariGlossarium.this, "Tutup", "Anda belum memasukkan data dengan benar");
				} else {
					katalogDbHelper = new KatalogDbHelper(CariGlossarium.this);
					if(isTambah)
					katalogDbHelper.addKatalog(new Katalog(nama, arti));
					else katalogDbHelper.updateKatalog(nama, arti);
					updateTable(null);
				}
				dialog.dismiss();
			}
		});
		btnBatal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		menu.setHeaderTitle(v.getTag().toString());
		posKatalogSelected = v.getId();
		inflater.inflate(R.menu.menu_katalog, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_edit:
			if(katalogs!=null){
				Katalog katalog = katalogs.get(posKatalogSelected);
				tambahUpdateKatalog(false, katalog.getNama(), katalog.getArti());
			}
			return true;
		case R.id.menu_delete:
			if(katalogs!=null){
				final Katalog katalog = katalogs.get(posKatalogSelected);
				new AlertDialog.Builder(this).setTitle("Hapus "+katalog.getNama())
				.setIcon(android.R.drawable.ic_dialog_alert).setMessage("Apakah anda yakin?")
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int i) {
						katalogDbHelper = new KatalogDbHelper(CariGlossarium.this);
						katalogDbHelper.deleteKatalog(katalog.getNama());
						dialog.dismiss();
						updateTable(null);
					}
				})
				.setNegativeButton("Tidak", null).show();
			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.katalog_option_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.tambahKatalog:
			tambahUpdateKatalog(true, "", "");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    
    public AdapterView.OnClickListener listener = new AdapterView.OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
//			AlertDialog.Builder alert = new AlertDialog.Builder(CariGlossarium.this);
//		      alert.setTitle("Artinya");
//		      LinearLayout lila1= new LinearLayout(CariGlossarium.this);
//		      lila1.setOrientation(1); 
//		      final TextView artinya = new TextView(CariGlossarium.this);
//		      lila1.addView(artinya);
//		      alert.setView(lila1);
//		      alert.create();
//		      alert.show();
		}
    	
    };

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			onBackPressed();
			break;
		case R.id.btnCari:
			String cari = editTextCari.getText().toString().trim();
			if(!cari.equals("")){
				updateTable(cari);
			}
			break;
		default:
			break;
		}
	}


	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(editTextCari.getText().toString().trim().equals("")){
			updateTable(null);
		}
	}
}
