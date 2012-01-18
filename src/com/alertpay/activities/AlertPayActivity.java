package com.alertpay.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alertpay.R;
import com.alertpay.common.AlertPay;
import com.alertpay.common.ErrorMessage;
import com.alertpay.transfer.ConnectionManager;
import com.alertpay.views.ErrorDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class AlertPayActivity extends Activity {
	
	// WURSTSALAT
	
	public static final String EXTRA_ERROR_TITLE = "ERROR_TITLE";
	public static final String EXTRA_ERROR_MESSAGE = "ERROR_MESSAGE";
	
	protected String username;
	protected String password;
	protected String requestUrl;
	protected AlertPay alertPay;
	protected final int RESULT_FAILURE = 2;
	protected ConnectionManager connectionmanager;
	protected List<NameValuePair> loginCredentials = new ArrayList<NameValuePair>();

	protected TextView loginSuccessText;
	protected View loginView;
	protected View loginSuccessView;
	protected LinearLayout mainLayout;
	protected Button loginButton;

	// Views

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ap_main);
		this.alertPay = AlertPay.getInstance();
		

	}

	protected void initializeViews() {
		// TODO Auto-generated method stub

		mainLayout = (LinearLayout) findViewById(R.id.ap_mainLayout);
		loginView = mainLayout.findViewById(R.id.login_view);
		loginButton = (Button) findViewById(R.id.btn_login);

	}

	public void closeActivity(ErrorMessage errorMessage) {
		Intent i = new Intent();
		i.putExtra(EXTRA_ERROR_TITLE, errorMessage.getTitle());
		i.putExtra(EXTRA_ERROR_MESSAGE, errorMessage.getContent());			
		setResult(SimplePaymentActivity.RESULT_FAILURE, i);
		
		finish();
	}

	protected List<NameValuePair> createLoginRequest(String username,
			String password) {

		List<NameValuePair> loginCredentials = new ArrayList<NameValuePair>();
		loginCredentials.add(new BasicNameValuePair("USER", username));
		loginCredentials.add(new BasicNameValuePair("PASSWORD", password));
		return loginCredentials;
	}
	
	/*
	protected void login(List<NameValuePair> loginCredentials, String requestUrl) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> response = new HashMap<String, String>();
		response = connectionmanager.processRequest(loginCredentials, requestUrl);
		
		// Login nicht erfolgreich
		if(Arrays.binarySearch(alertPay.loginErrorCodes, Integer.valueOf(response.get("RETURNCODE"))) >= 0){						
			loginFailed(response);
		} else {
			//Login erfolgreich			
			loginSuccess(response);			
		}
	ffsfdsfsfdsfdsfsfsfsf
	}
	*/
	
	
	
	protected void loginSuccess(HashMap<String, String> response){
	}
	
	protected void loginFailed(HashMap<String, String> response){

		ErrorDialog errorDialog = new ErrorDialog(this, this, new ErrorMessage("Login not successful !",response.get("DESCRIPTION")),false);
		errorDialog.show();
	}

}
