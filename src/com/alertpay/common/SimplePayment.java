package com.alertpay.common;

public class SimplePayment implements AlertPayComponent  {
	
	private String currencyType;
	private double subTotal = 0;
	private String recipientEmail;
	private AccountData accountData;
	private int purchaseType;
	private String sender;
	private String note;
	private double total;
	private String recipientName;
	
	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public double getTotal(){
		return this.subTotal+this.accountData.getTotal();		
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

	public void setCurrencyType(String currencyType){
		this.currencyType = currencyType;
	}
	
	public void setSubTotal(double subTotal){
		this.subTotal = subTotal;
	}
	
	public double getSubTotal() {
		return subTotal;
	}
	
	public void setRecipientEmail(String recipientEmail){
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
	
}
