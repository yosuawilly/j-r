package com.LearningKimia.restfull;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.LearningKimia.util.Constant;

import android.content.Context;
import android.util.Log;

public class UploadFileTugasTask2 extends UploadFileTugasTask{

	public UploadFileTugasTask2(Context context, UploadListener<Object> uploadListener) {
		super(context, uploadListener);
	}
	
	@Override
	protected Object doInBackground(String... params) {
		String result = "SUKSES";
		
		String fileName = params[0];
		File sourceFile = new File(fileName);
		String otherParams = params[1];
		String[]others = otherParams.split("&");
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.UPLOAD_TUGAS_URL);
		
		if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :" + fileName);
            
            return "Source File not exist :" + fileName;
        }
		else 
		{
			try {
				MultipartEntity entity = new MultipartEntity();
				entity.addPart("uploaded_file", new FileBody(sourceFile));
				for(String other : others) {
					String[]keyValue = other.split("=");
					entity.addPart(keyValue[0], new StringBody(keyValue[1]));
				}
				
				httppost.setEntity(entity);
				HttpResponse response = httpclient.execute(httppost);
				
				HttpEntity httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
		
		return result;
//		return super.doInBackground(params);
	}

}
