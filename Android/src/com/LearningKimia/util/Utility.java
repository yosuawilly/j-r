package com.LearningKimia.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utility {
	
	public static void showMessage(Context context, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Information").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}
	
	public static void showErrorMessage(Context context, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Error").setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}

}
