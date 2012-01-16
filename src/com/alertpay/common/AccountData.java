package com.alertpay.common;

import java.util.ArrayList;

public class AccountData {

	private double tax = 0;
	private double shipping = 0;
	private ArrayList<AccountItem> accountItems = new ArrayList<AccountItem>();

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
	
	public void addAccountItem(AccountItem accountItem){
		this.accountItems.add(accountItem);
	}
	
	public ArrayList<AccountItem> getAccountItems(){
		return this.accountItems;
	}
	
	
	public double getTotal(){
		return this.tax+this.shipping;
	}
	
	
	
}
