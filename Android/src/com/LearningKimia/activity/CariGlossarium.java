package com.LearningKimia.activity;

import java.io.IOException;
import java.util.ArrayList;

import com.LearningKimia.R;
import com.LearningKimia.R.layout;
import com.LearningKimia.activity.base.BaseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class CariGlossarium extends BaseActivity implements OnClickListener{
	TableLayout lytLinear;
	Button OK, bmenu;
	TextView txtNama, txtArti;
	SQLHelper myDbHelper;
	EditText pilihan;
	String pilNama;
	protected Cursor cursor;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glossarium);
        
        ((Button) findViewById(R.id.button_back)).setOnClickListener(this);
        
    /**    bmenu = (Button) findViewById (R.id.menu);
		bmenu.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity (new Intent ("com.firdan.MENU"));
			}
		}); 
      */
      /**  pilihan = (EditText) findViewById (R.id.pilNamaLaki);
        
        OK = (Button) findViewById (R.id.okNamaLaki);
        OK.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			pilNama = pilihan.getText().toString();
				//updateTable();	
			}
		});*/
          
       /**lytLinear = (TableLayout)findViewById(R.id.lytLinearNamalaki);
       myDbHelper = new SQLHelper(this);
       
      try{
        	myDbHelper.createDataBase();
        }catch (IOException ioe){
        	throw new Error ("Unable to creata database");
        }
        try{
        	Toast.makeText(this, "database dibuka", Toast.LENGTH_SHORT);
        	myDbHelper.openDataBase();
        }catch (SQLException sqle){
        	throw sqle;
        }
        
        updateTable();*/
    }
    

	public void updateTable(){	
    	
    	Toast.makeText(this, "update table di akses", Toast.LENGTH_SHORT);
    	myDbHelper.openDataBase();
    	
    	while (lytLinear.getChildCount() > 1){
    		lytLinear.removeViewAt(1);
    	}
    	
		ArrayList<ArrayList<Object>> data = myDbHelper.getAllRowsAsArrays(pilNama);
 
    	//buat baris baru setiap perulangan dan masukkan data ke dalam 
    	//masing-masing baris
		
    	for (int position=0; position < data.size(); position++)
    	{
    		
    		TableRow tableRow = new TableRow(this);
    		tableRow.setOnClickListener(listener);
    		
    		ArrayList<Object> row = data.get(position);
    		 
    		txtNama = new TextView(this);
    		txtNama.setText(row.get(0).toString());
    		txtNama.setTextColor(Color.BLACK);
    		txtNama.setTextSize(14);
    		tableRow.addView(txtNama);
			
    		if (position % 2 == 0){
    			txtNama.setBackgroundColor(Color.rgb(234, 234, 234));
    		}else{
    			txtNama.setBackgroundColor(Color.rgb(255, 255, 255));
    		}

    		txtArti = new TextView(this);
    		txtArti.setText(row.get(1).toString());
    		txtArti.setTextColor(Color.BLACK);
    		txtArti.setTextSize(14);
    		tableRow.addView(txtArti);
    		
    		if (position % 2 == 0){
    			txtArti.setBackgroundColor(Color.rgb(234, 234, 234));
    		}else{
    			txtArti.setBackgroundColor(Color.rgb(255, 255, 255));
    		}
    			
    		
    		lytLinear.addView(tableRow);
    	}
    }
    
    public AdapterView.OnClickListener listener = new AdapterView.OnClickListener(){


		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alert = new AlertDialog.Builder(CariGlossarium.this);
		      alert.setTitle("Artinya");
		      LinearLayout lila1= new LinearLayout(CariGlossarium.this);
		      lila1.setOrientation(1); 
		      final TextView artinya = new TextView(CariGlossarium.this);
		      lila1.addView(artinya);
		      alert.setView(lila1);
		      alert.create();
		      alert.show();
		    
		    //  ArrayList<ArrayList<Object>> data = myDbHelper.getAllRowsAsArrays(pilNama);
		     // for (int position=0; position < data.size(); position++)
		    //	{
		    //	  ArrayList<Object> row = data.get(position);
		    //	  txtNama.setText(row.get(0).toString());
		      artinya.setText(txtNama.getText());
		    //	}   	
		}
    	
    };

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			onBackPressed();
			break;
		default:
			break;
		}
	}
}
