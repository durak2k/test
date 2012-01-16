package com.alertpay.common;


import java.util.zip.Inflater;

import android.R.bool;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alertpay.activities.AlertPayActivity;
import com.alertpay.activities.MassPaymentActivity;
import com.alertpay.activities.SimplePaymentActivity;
import com.alertpay.activities.TestActivity;

public class AlertPay {
	
	public static final int NORMAL_MODE = 0;
	public static final int TEST_MODE = 1;
	public static final int ENV_LIVE = 1;
	public static final int ENV_SANDBOX = 2;
	public static final String SERVER_LIVE = "https://api.alertpay.com/svc/api.svc/";
	public static final String SERVER_SANDBOX = "https://sandbox.alertpay.com/api/api.svc/";
	public String[] currencyCodes = {"AUD","BGN","CAD","CHF","CZK","DKK","EEK","EUR","GBP","HKD",
			"HUF","INR","LTL","MYR","MKD","NOK","NZD","PLN","RON","SEK","SGD","USD","ZAR"};
	public static final int PURCHASETYPE_SERVICE = 1;
	public static final int PURCHASETYPE_GOODS = 2;
	public static final int PURCHASETYPE_AUCTION_GOODS = 3;
	public static final int PURCHASETYPE_OTHERS = 3;
	
	public final int REQUEST_SUCCESS_CODE = 100;
	public final int[] loginErrorCodes = {201,202,203,204,205,206,207,208,209,210,211,212,221,222,
			223,224,225,226};
	
	private static AlertPay _alertPay;
	private String server = SERVER_SANDBOX; 
	private int mode = 1;
	private AlertPayComponent alertPayComponent;

	public static AlertPay getInstance() {
		if (_alertPay == null) {
			_alertPay = new AlertPay();
		}
		return _alertPay;
	}
	
	public void setServer(int paramServer){
		if(paramServer == Util.ENV_LIVE){
			this.server = Util.SERVER_LIVE;
		} else if (paramServer == Util.ENV_SANDBOX) {
			this.server = Util.SERVER_SANDBOX;
		}

	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	public int getMode(){
		return this.mode;
	}
	
	public String getServer(String serverAffix){
		return this.server.concat(serverAffix);
	}
	
	public AlertPayComponent getAlertPayComponent(){
		return this.alertPayComponent;
	}
	
	
	public void init(int paramServer, boolean testMode) {
		if(paramServer == Util.ENV_LIVE){
			this.server = Util.SERVER_LIVE;
		} else if (paramServer == Util.ENV_SANDBOX) {
			this.server = Util.SERVER_SANDBOX;
		}	
		
		// SimplePaymentActivity.ge

	}

	public Intent checkout(AlertPayComponent alertPayComponent, Context context) {		
		this.alertPayComponent = alertPayComponent;
		
		Class alertPayClass = null;
		
		if(alertPayComponent instanceof SimplePayment){
			alertPayClass = SimplePaymentActivity.class;
		} else if (alertPayComponent instanceof MassPayment) {
			alertPayClass = MassPaymentActivity.class;
		} else if (alertPayComponent instanceof MassPayment) {
			alertPayClass = MassPaymentActivity.class;
		}
		
		Intent i = new Intent(context, alertPayClass);	
		
		return i;
	}
	
	public void validate() throws Exception {
		
		if(this.server != SERVER_LIVE && this.server != SERVER_SANDBOX){
			
		}
		
		
	}
	
	public class Util{
		public static final int ENV_LIVE = 1;
		public static final int ENV_SANDBOX = 2;
		public static final boolean testMode = false;
		public static final String SERVER_LIVE = "https://api.alertpay.com/svc/api.svc/";
		public static final String SERVER_SANDBOX = "https://sandbox.alertpay.com/api/api.svc/";
		public String[] currencyCodes = {"AUD","BGN","CAD","CHF","CZK","DKK","EEK","EUR","GBP","HKD",
				"HUF","INR","LTL","MYR","MKD","NOK","NZD","PLN","RON","SEK","SGD","USD","ZAR"};				
	}



}
