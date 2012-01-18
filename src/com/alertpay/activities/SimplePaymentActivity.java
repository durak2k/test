package com.alertpay.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alertpay.R;
import com.alertpay.R.layout;
import com.alertpay.callbacks.ApiRequestCallback;
import com.alertpay.common.AccountData;
import com.alertpay.common.AccountItem;
import com.alertpay.common.AlertPay;
import com.alertpay.common.ErrorMessage;
import com.alertpay.common.SimplePayment;
import com.alertpay.transfer.ConnectionManager;
import com.alertpay.views.ErrorDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SimplePaymentActivity extends AlertPayActivity implements
		OnClickListener {
	/** Called when the activity is first created. */

	public static final int RESULT_FAILURE = 2;
	public static final String REFERENCENUMBER = "REFERENCENUMBER";

	public static SimplePaymentActivity _simplePaymentActivity;
	private SimplePayment simplePayment;
	private String serverSuffix = "sendmoney";
	private AccountData accountData;
	private ArrayList<AccountItem> accountItems = new ArrayList<AccountItem>();
	protected List<NameValuePair> paymentCredentials = new ArrayList<NameValuePair>();

	// Views
	private TextView loginSuccessText;
	private View loginSuccessView;
	private View paymentApprovalView;

	private Button payButton;
	private View paymentDetailsToggleView;
	private Button paymentDetailsToggleButton;
	private LinearLayout paymentDetailsView;
	private LinearLayout itemsStubView;
	private LinearLayout accountItemView;
	private TextView receiverView;

	ApiRequestCallback apiRequestCallback = new ApiRequestCallback() {

		@Override
		public void onSuccess(HashMap<String, String> response, boolean isLogin) {
			// TODO Auto-generated method stub

			if (isLogin) {
				// Login nicht erfolgreich
				if (Arrays.binarySearch(alertPay.loginErrorCodes,
						Integer.valueOf(response.get("RETURNCODE"))) >= 0) {
					loginSuccess(response);

					username = "";
					password = "";
					// loginFailed(response);
				} else {
					// Login erfolgreich
					loginSuccess(response);
				}
				// Zahlung erfolgreich
			} else if (Integer.valueOf(response.get("RETURNCODE")) == alertPay.REQUEST_SUCCESS_CODE) {
				paymentSucess(response);
			} else {
				// Zahlung nicht erfolgreich
				paymentFailed(response);
			}
		}

		@Override
		public void onFailure(String errorMessage) {
			// TODO Auto-generated method stub
			ErrorDialog errorDialog = new ErrorDialog(
					SimplePaymentActivity.this, SimplePaymentActivity.this,
					new ErrorMessage("Error", errorMessage), true);
			errorDialog.show();
		}
	};

	public static SimplePaymentActivity getInstance() {
		return _simplePaymentActivity;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_simplePaymentActivity = this;

		this.requestUrl = alertPay.getServer(serverSuffix);
		simplePayment = (SimplePayment) alertPay.getAlertPayComponent();

		if (simplePayment.getAccountData() != null) {
			accountData = simplePayment.getAccountData();
			accountItems = accountData.getAccountItems();
		}

		initializeViews();
		registerListeners();

	}

	private void paymentFailed(HashMap<String, String> response) {
		// TODO Auto-generated method stub

		ErrorDialog errorDialog = new ErrorDialog(this, this, new ErrorMessage(
				"Payment failed !", response.get("DESCRIPTION")), true);
		errorDialog.show();

	}

	private void paymentSucess(HashMap<String, String> response) {
		// TODO Auto-generated method stub

		Intent i = new Intent();
		i.putExtra(SimplePaymentActivity.REFERENCENUMBER,
				response.get("REFERENCENUMBER"));
		setResult(Activity.RESULT_OK, i);
		finish();

	}

	protected void initializeViews() {
		super.initializeViews();

		// add button above login to toggle payment-details

		paymentApprovalView = getLayoutInflater().inflate(
				R.layout.ap_simple_payment_approval, null);
		
		paymentDetailsToggleView = getLayoutInflater().inflate(
				R.layout.ap_simple_payment_details_button, null);
		

		int index = mainLayout.indexOfChild(loginView);

		mainLayout.addView(paymentDetailsToggleView, index);

		// Buttons
		paymentDetailsToggleButton = (Button) mainLayout
				.findViewById(R.id.sp_btn_paymentDetails);

		showPaymentDetails();

	}

	private void showPaymentDetails() {

		paymentDetailsView = (LinearLayout) getLayoutInflater().inflate(
				R.layout.ap_simple_payment_details, null);

		String recipient = "";

		if (simplePayment.getRecipientName() != null) {
			recipient = recipient.concat(simplePayment.getRecipientName()
					+ " (" + simplePayment.getRecipientEmail() + ")");
		} else {
			recipient = recipient.concat(simplePayment.getRecipientEmail());
		}

		receiverView = (TextView) paymentDetailsView
				.findViewById(R.id.sp_txt_receiver);
		receiverView.setText(recipient);

		
		if (accountItems.size() > 0) {
			showAccountItems();
		} else {
			showNoAccountItems();
		}

		TextView tax = (TextView) paymentDetailsView.findViewById(R.id.sp_txt_tax);
		String taxString = (accountData == null) ? "0.0" : Double.toString(accountData.getTax());

		tax.setText(taxString + " " + simplePayment.getCurrencyType());

		/*
		 * tax.setText(Double.toString(accountData.getTax()) + " " +
		 * simplePayment.getCurrencyType());
		 */
		TextView shipping = (TextView) paymentDetailsView
				.findViewById(R.id.sp_txt_shipping);

		String shippingString = (accountData == null) ? "0.0" : Double
				.toString(accountData.getShipping());
		
		shipping.setText(shippingString + " " +	simplePayment.getCurrencyType());
		
		/*
		 * shipping.setText(Double.toString(accountData.getShipping()) + " " +
		 * simplePayment.getCurrencyType());
		 */
		TextView total = (TextView) paymentDetailsView
				.findViewById(R.id.sp_txt_payment_total);
		total.setText(Double.toString(simplePayment.getTotal()) + " "
				+ simplePayment.getCurrencyType());

	}

	private void showNoAccountItems() {

		// falls kein Account-Item hinzugefügt wurde, Subtotal anzeigen

		View stub = ((ViewStub) paymentDetailsView
				.findViewById(R.id.sp_stub_noitems)).inflate();
		TextView subtotal = (TextView) stub
				.findViewById(R.id.sp_txt_noitems_subtotal);
		subtotal.setText(simplePayment.getSubTotal() + " "
				+ simplePayment.getCurrencyType());
	}

	private void showAccountItems() {

		// Account-items darstellen
		for (int i = 0; i < accountItems.size(); i++) {

			accountItemView = (LinearLayout) getLayoutInflater().inflate(
					R.layout.ap_simple_payment_item, null);

			AccountItem currentItem = accountItems.get(i);

			TextView itemName = (TextView) accountItemView
					.findViewById(R.id.sp_txt_item);
			itemName.setText(itemName.getText() + currentItem.getName());

			TextView totalPrice = (TextView) accountItemView
					.findViewById(R.id.sp_txt_item_totalprice);
			totalPrice.setText(Double.toString(currentItem.getTotalPrice())
					+ " " + simplePayment.getCurrencyType());

			TextView unitPrice = (TextView) accountItemView
					.findViewById(R.id.sp_txt_unit_price);
			TextView quantity = (TextView) accountItemView
					.findViewById(R.id.sp_txt_unit_quantity);

			if (currentItem.getUnitPrice() == 0
					|| currentItem.getQuantity() == 0) {
				accountItemView.removeView(unitPrice);
				accountItemView.removeView(quantity);
			} else {
				unitPrice.setText(unitPrice.getText()
						+ Double.toString(currentItem.getUnitPrice()));
				quantity.setText(quantity.getText()
						+ Integer.toString(currentItem.getQuantity()));
			}
			// AccountItems unterhalb von Receiver anzeigen
			paymentDetailsView.addView(accountItemView,
					paymentDetailsView.indexOfChild(receiverView) + 1);

		}
	}

	private void registerListeners() {

		loginButton.setOnClickListener(this);
		paymentDetailsToggleButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// login
		if (v == loginButton) {
			this.username = ((TextView) findViewById(R.id.edit_login_username))
					.getText().toString();
			this.password = ((TextView) findViewById(R.id.edit_login_password))
					.getText().toString();
			this.loginCredentials = createLoginRequest(username, password);
			// login(loginCredentials,requestUrl);

			connectionmanager = new ConnectionManager(loginCredentials,
					requestUrl, true, apiRequestCallback);
			connectionmanager.execute();

		}

		if (v == paymentDetailsToggleButton) {

			// Payment-details an oder aus
			if (mainLayout.indexOfChild(paymentDetailsView) == -1) {
				int index = mainLayout.indexOfChild(paymentDetailsToggleView) + 1;
				mainLayout.addView(paymentDetailsView, index);
			} else {
				mainLayout.removeView(paymentDetailsView);
			}
			
			
			
		}

		if (v == payButton) {

			this.paymentCredentials = createPaymentRequest();

		}

	}

	protected List<NameValuePair> createPaymentRequest() {

		List<NameValuePair> paymentCredentials = new ArrayList<NameValuePair>();
		paymentCredentials.add(new BasicNameValuePair("USER", this.username));
		paymentCredentials
				.add(new BasicNameValuePair("PASSWORD", this.password));
		paymentCredentials.add(new BasicNameValuePair("AMOUNT", Double
				.toString(simplePayment.getTotal())));
		paymentCredentials.add(new BasicNameValuePair("CURRENCY", simplePayment
				.getCurrencyType()));
		paymentCredentials.add(new BasicNameValuePair("RECEIVEREMAIL",
				simplePayment.getRecipientEmail()));
		paymentCredentials.add(new BasicNameValuePair("SENDEREMAIL",
				simplePayment.getSender()));
		paymentCredentials.add(new BasicNameValuePair("PURCHASETYPE", Integer
				.toString(simplePayment.getPurchaseType())));
		paymentCredentials.add(new BasicNameValuePair("NOTE", simplePayment
				.getNote()));
		paymentCredentials.add(new BasicNameValuePair("TESTMODE", Integer
				.toString(alertPay.getMode())));

		return paymentCredentials;
	}

	protected void loginSuccess(HashMap<String, String> response) {

		mainLayout.removeView(loginView);
		mainLayout.addView(paymentApprovalView);

		payButton = (Button) mainLayout.findViewById(R.id.sp_btn_pay);
		payButton.setOnClickListener(this);

		// Set Receiver and Total Amount of Payment

		String recipient = "";

		if (simplePayment.getRecipientName() != null) {
			recipient = recipient.concat(simplePayment.getRecipientName()
					+ " (" + simplePayment.getRecipientEmail() + ")");
		} else {
			recipient = recipient.concat(simplePayment.getRecipientEmail());
		}

		((TextView) mainLayout.findViewById(R.id.sp_txt_approval_receiver))
				.setText(recipient);
		((TextView) mainLayout.findViewById(R.id.sp_txt_approval_total))
				.setText(simplePayment.getTotal() + " "
						+ simplePayment.getCurrencyType());

	}

}
