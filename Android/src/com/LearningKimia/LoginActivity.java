package com.LearningKimia;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.LearningKimia.R;
import com.LearningKimia.activity.LearningKimiaActivity;
import com.LearningKimia.activity.base.BaseActivity;
import com.LearningKimia.global.GlobalVar;
import com.LearningKimia.model.Siswa;
import com.LearningKimia.restfull.AsyncTaskCompleteListener;
import com.LearningKimia.restfull.CallWebServiceTask;
import com.LearningKimia.util.Constant;
import com.LearningKimia.util.Functional;
import com.LearningKimia.util.ResourceUtil;
import com.LearningKimia.util.Utility;

public class LoginActivity extends BaseActivity implements Functional,OnClickListener,AsyncTaskCompleteListener<Object>{
	protected static final int SUB_ACTIVITY_REQUEST_CODE = 100;
	
	protected EditText usernameEdit, passwordEdit;
	protected Button btnLogin;
	protected ArrayList<String> tableToUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		initBundle();
		initObject();
		initDesign();
		initObjectToDesign();
		initListener();
	}

	@Override
	public void initBundle() {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			if(bundle.containsKey("tableToUpdate")){
				tableToUpdate = bundle.getStringArrayList("tableToUpdate");
//				Log.i("bundle", tableToUpdate.get(0));
			}
		}
	}

	@Override
	public void initObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDesign() {
		usernameEdit = (EditText) findViewById(R.id.usernameEdit);
		passwordEdit = (EditText) findViewById(R.id.passwordEdit);
		btnLogin = (Button) findViewById(R.id.btnLogin);
	}

	@Override
	public void initObjectToDesign() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		btnLogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnLogin:
			String username = usernameEdit.getText().toString();
			String password = passwordEdit.getText().toString();
			if(username.trim().equals("")){
				Utility.showMessage(this, "Username belum diisi");
			} else if(password.trim().equals("")){
				Utility.showMessage(this, "Password belum diisi");
			} else 
			if(tableToUpdate!=null){
				if(tableToUpdate.size()>0){
					intent = new Intent(this, RestFullClientActivity.class);
					intent.putExtra("username", username);
					intent.putExtra("password", password);
					intent.putStringArrayListExtra("tableToUpdate", tableToUpdate);
					startActivityForResult(intent, SUB_ACTIVITY_REQUEST_CODE);
				} else {
					String url = Constant.BASE_URL + "login/"+username+"/"+password;
					CallWebServiceTask task = new CallWebServiceTask(this, this);
					task.execute(url, Constant.REST_GET);
				}
			} else {
				String url = Constant.BASE_URL + "login/"+username+"/"+password;
				CallWebServiceTask task = new CallWebServiceTask(this, this);
				task.execute(url, Constant.REST_GET);
			}
			break;
		default:
			break;
		}
	}
	
	public void setSiswaWasLogin(String result){
		try {
			JSONObject jobj = new JSONObject(result);
			JSONObject j_siswa = jobj.getJSONObject("siswa");
			Siswa siswa = new Siswa();
			siswa.setIdSiswa(j_siswa.getString("id_siswa"));
			siswa.setUsername(j_siswa.getString("username"));
			siswa.setPassword(j_siswa.getString("password"));
			siswa.setNama(j_siswa.getString("nama"));
			siswa.setJenisKelamin(j_siswa.getString("jenis_kelamin"));
			siswa.setAlamat(j_siswa.getString("alamat"));
			GlobalVar.getInstance().setSiswa(siswa);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUB_ACTIVITY_REQUEST_CODE){
			if(data!=null){
				Bundle b = data.getExtras();
				String result = b.getString(Constant.REST_RESULT);
				Log.i("result", result);
				if(Utility.cekValidResult(result, this)){
					if(resultCode == RestFullClientActivity.SUCCESS_RETURN_CODE){
						setSiswaWasLogin(result); //set Object Siswa to GlobalVar
						Intent intent = new Intent(this, LearningKimiaActivity.class);
						startActivity(intent);
						finish();
					} else {
						try {
							JSONObject jobj = new JSONObject(result);
							Utility.showErrorMessage(this, jobj.getString("fullMessage"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	public void onTaskComplete(Object... params) {
		String result = (String)params[0];
		Log.i("result", result);
		if(Utility.cekValidResult(result, this)){
			try {
				JSONObject jobj = new JSONObject(result);
				if(jobj.getString("status").equals("0")){
					Utility.showErrorMessage(this, jobj.getString("fullMessage"));
				} else if(jobj.getString("status").equals("1")) {
					setSiswaWasLogin(result); //set Object Siswa to GlobalVar
					Intent intent = new Intent(this, LearningKimiaActivity.class);
					startActivity(intent);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Utility.showErrorMessage(this, ResourceUtil.getBundle().getString("LK-0000"));
			}
		}
	}

}
