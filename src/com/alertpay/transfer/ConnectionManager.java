package com.alertpay.transfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alertpay.activities.SimplePaymentActivity;
import com.alertpay.callbacks.ApiRequestCallback;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectionManager extends
		AsyncTask<Void, Void, HashMap<String, String>> {

	private HttpClient client;
	private List<NameValuePair> request;
	private String requestUrl;
	private boolean isLoginRequest;
	private ApiRequestCallback callback;
	private Exception connectionException;
	private String errorMessage;

	public ConnectionManager(List<NameValuePair> request, String requestUrl,
			boolean isLoginRequest, ApiRequestCallback callback) {
		this.request = request;
		this.requestUrl = requestUrl;
		this.isLoginRequest = isLoginRequest;
		this.callback = callback;

	}

	/*
	 * public HashMap<String, String> processRequest(List<NameValuePair>
	 * request, String requestUrl){
	 * 
	 * 
	 * 
	 * }
	 */

	private static HashMap<String, String> parseResponse(String response) {
		String[] parts = response.split("\\&");

		HashMap<String, String> keyValue = new HashMap<String, String>();

		for (int i = 0; i < parts.length; i++) {

			String[] subParts = parts[i].split("\\=");

			// Falls Value nicht existiert
			if (subParts.length != 2) {
				keyValue.put(subParts[0], "None");
			} else {
				keyValue.put(subParts[0], subParts[1]);
			}
		}
		return keyValue;
	}

	@Override
	protected HashMap<String, String> doInBackground(Void... params) {
		// TODO Auto-generated method stub

		HashMap<String, String> responseMap = new HashMap<String, String>();

		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(requestUrl);
			httpPost.setEntity(new UrlEncodedFormEntity(request));

			HttpResponse response = client.execute(httpPost);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String line = "";
			String responseString = "";

			while ((line = rd.readLine()) != null) {
				responseString = responseString.concat(URLDecoder.decode(line));
			}

			responseMap = parseResponse(responseString);
			
			if(responseMap.size() == 0){
				throw new Exception("This is not a valid Response !");
			}
			
		} catch (IOException e) {
			
			this.errorMessage = "The Request could not be performed successfully !";
			cancel(false);
			
			// TODO: handle exception

		} catch (Exception e) {
			// TODO: handle exception
		}

		return responseMap;

	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		callback.onFailure(errorMessage);

	}

	@Override
	protected void onPostExecute(HashMap<String, String> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		callback.onSuccess(result, isLoginRequest);
		
	}

}
