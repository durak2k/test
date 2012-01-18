package com.alertpay.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alertpay.exceptions.ParameterInvalidException;
import com.alertpay.exceptions.ParameterNotSetException;
import com.alertpay.exceptions.PaymentInvalidException;

public class SimplePayment implements AlertPayComponent {

	private String currencyType;
	private double subTotal = 0;
	private String recipientEmail;
	private AccountData accountData = null;
	private int purchaseType;
	private String sender;
	private String note;
	private double total;
	private String recipientName = "";

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public double getTotal() {

		if (this.accountData != null) {
			return this.getSubTotal() + this.accountData.getTaxShippingTotal();
		} else {
			return this.subTotal;
		}

	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AccountData getAccountData() {
		return accountData;
	}

	public void setAccountData(AccountData accountData) {
		this.accountData = accountData;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getSubTotal() {
		if (this.subTotal == 0) {
			return this.accountData.getItemsTotal();
		}
		return subTotal;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
	@Override
	public void validate() throws ParameterInvalidException,
			ParameterNotSetException, PaymentInvalidException {

		// currency
		if (this.currencyType == null) {
			throw new ParameterNotSetException("Currency not specified !");
		}

		if (!checkCurrency()) {
			throw new ParameterInvalidException("Currency is invalid !");
		}

		// recipient
		if (this.recipientEmail == null) {
			throw new ParameterNotSetException(
					"Email of recipient not specified !");
		}

		if (this.getTotal() <= 0) {
			throw new PaymentInvalidException(
					"Total amount of the payment must be at least 1.00");
		}

		if (this.accountData != null) {

			this.accountData.validate();
			
			if (!checkSubTotal()) {
				throw new PaymentInvalidException(
						"The subtotal differs from the total amount of the account items ");
			}
		}
		

	}


	public boolean checkAccountItem(AccountItem accItem) {
		return (accItem.getQuantity() * accItem.getUnitPrice()) == accItem.getTotalPrice();
	}

	public boolean checkCurrency() {
		return (Arrays.binarySearch(AlertPay.currencyCodes, this.currencyType) >= 0);
	}

	public boolean checkSubTotal() {
		return getSubTotal() == accountData.getItemsTotal();
	}
	


}
