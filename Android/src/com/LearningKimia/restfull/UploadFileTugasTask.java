package com.LearningKimia.restfull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.LearningKimia.util.Constant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class UploadFileTugasTask extends AsyncTask<String, Object, Object>{
	private Context context;
	private UploadListener<Object> uploadListener;
	private ProgressDialog dialog;
	private String message = "Uploading file ...";
	
	public UploadFileTugasTask(Context context, UploadListener<Object> uploadListener) {
		this.context = context;
		this.uploadListener = uploadListener;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context,    
                "Please wait...", message, true, true, 
                new DialogInterface.OnCancelListener(){
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            }
        );
	}

	@Override
	protected Object doInBackground(String... params) {
		String fileName = params[0];
		
		int serverResponseCode;
		String result = "SUKSES";
		  
        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(fileName);
        
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :" + fileName);
            
            return "Source File not exist :" + fileName;
        } 
        else 
        {
        	try {
                
                // open a URL connection to the Servlet
              FileInputStream fileInputStream = new FileInputStream(sourceFile);
              URL url = new URL(Constant.UPLOAD_TUGAS_URL);
               
              // Open a HTTP  connection to  the URL
              conn = (HttpURLConnection) url.openConnection(); 
              conn.setDoInput(true); // Allow Inputs
              conn.setDoOutput(true); // Allow Outputs
              conn.setUseCaches(false); // Don't use a Cached Copy
              conn.setRequestMethod("POST");
              conn.setRequestProperty("Connection", "Keep-Alive");
              conn.setRequestProperty("ENCTYPE", "multipart/form-data");
              conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
              conn.setRequestProperty("uploaded_file", fileName); 
               
              dos = new DataOutputStream(conn.getOutputStream());
     
              dos.writeBytes(twoHyphens + boundary + lineEnd); 
              dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=" + fileName + "" + lineEnd);
               
              dos.writeBytes(lineEnd);
     
              // create a buffer of  maximum size
              bytesAvailable = fileInputStream.available(); 
     
              bufferSize = Math.min(bytesAvailable, maxBufferSize);
              buffer = new byte[bufferSize];
     
              // read file and write it into form...
              bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                 
              while (bytesRead > 0) {
                   
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                 
               }
     
              // send multipart form data necesssary after file data...
              dos.writeBytes(lineEnd);
              dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
              
//              String paramku = "nama=yosua";
//              dos.writeBytes("Content-Disposition: form-data; name=\"chunk\"" + lineEnd);
//              dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
//              dos.writeBytes("Content-Length: " + paramku.length() + lineEnd);
//              dos.writeBytes(lineEnd);
//              dos.writeBytes(paramku + lineEnd);
//              dos.writeBytes(twoHyphens + boundary + lineEnd);
     
              // Responses from the server (code and message)
              serverResponseCode = conn.getResponseCode();
              String serverResponseMessage = conn.getResponseMessage();
              
              InputStream in = new BufferedInputStream(conn.getInputStream());
              BufferedReader br = new BufferedReader(new InputStreamReader(in));
               
              if(serverResponseCode == 200){
            	  String line = ""; result = "";
            	  while ((line = br.readLine()) != null) {
            		  result += line;
            	  }
//            	  result = br.readLine();
              }    
              
              Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode + ": " + result);
               
              //close the streams //
              in.close();
              br.close();
              fileInputStream.close();
              dos.flush();
              dos.close();
                
        	} catch (MalformedURLException ex) {
        		ex.printStackTrace();
        		Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        		result = ex.getMessage();
        	} catch (Exception e) {
        		e.printStackTrace();
        		Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e); 
        		result = e.getMessage();
        	}
        }
        
		return result;
	}
	
	@Override
	protected void onPostExecute(Object result) {
		if (dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (IllegalArgumentException e) {				
				//do nothing
				e.printStackTrace();
			}
		}
		
		uploadListener.onUpload(result);
	}
	
	public static interface UploadListener<T> {
		public void onUpload(T... params);
	}

}
