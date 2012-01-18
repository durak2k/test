package com.alertpay.common;

import java.util.ArrayList;

import com.alertpay.exceptions.PaymentInvalidException;

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
	
	
	public double getTaxShippingTotal(){
		return this.tax+this.shipping;
	}
	
	public double getItemsTotal(){
		
		double total = 0;
		
		for(int i=0; i<accountItems.size(); i++){
			total = total + accountItems.get(i).getTotalPrice();
		}
		
		return total;
	}
	
	public void validate() throws PaymentInvalidException {
		
		if (this.getAccountItems().size() > 0) {
			for (int i = 0; i < this.getAccountItems().size(); i++) {
				AccountItem accItem = this.getAccountItems().get(i);
				accItem.validate();
			}

		}
	}
	

	
	
	
}
