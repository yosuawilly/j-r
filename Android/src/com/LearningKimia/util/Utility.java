package com.LearningKimia.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.LearningKimia.R;

import android.app.Activity;
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
	
	public static void showMessage(Context context, String textButton, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Information").setPositiveButton(textButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}
	
	public static void showErrorMessage(Context context, String pesan){
		new AlertDialog.Builder(context).setMessage(pesan)
		.setTitle("Error").setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				d.dismiss();
			}
		}).create().show();
	}
	
	public static boolean isSDCardExist(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	public static boolean cekValidResult(String result, Activity activity){
		String message="";
        String errorCode="";
        try{
        	if((result.indexOf("errorCode") < 0) && (result.indexOf("status") < 0)){
				return true;
			} else {
				JSONObject jsonObject = new JSONObject(result);
				if(jsonObject.has("errorCode"))
                {
					errorCode = jsonObject.getString("errorCode");
					message = (jsonObject.has("fullMessage"))?jsonObject.getString("fullMessage"):ResourceUtil.getBundle().getString(errorCode);
					showErrorMessage(activity, message);
					return false;
                } 
				else if(jsonObject.has("status"))
				{
					String status = jsonObject.getString("status");
					message = (jsonObject.has("fullMessage"))?jsonObject.getString("fullMessage"):ResourceUtil.getBundle().getString(errorCode);
					if("0".equals(status))
					{
						showErrorMessage(activity, message);
						return false;
					}
				}
			}
        } catch(JSONException e){
        	e.printStackTrace();
        	if(message.equals(null) || message=="")
            {
                 message = activity.getString(R.string.message_problemKomunikasiServer);
            }
        	showErrorMessage(activity, message);
        	return false;
        } catch (Exception e) {
        	e.printStackTrace();
        	if(message.equals(null) || message=="")
            {
                 message = activity.getString(R.string.message_problemKomunikasiServer);
            }
        	showErrorMessage(activity, message);
        	return false;
        }
        return true;
	}

}
