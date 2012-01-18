package com.alertpay.common;

import com.alertpay.exceptions.PaymentInvalidException;

public class AccountItem {

	
	private String name = "";
	protected double totalPrice = -1;
	private double unitPrice;
	private int quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalPrice() {
		
		// total price not set
		if(totalPrice < 0){
			return quantity*unitPrice; 
		} else {
			return totalPrice;
		}
		
		/*
		if(quantity == 0 || unitPrice == 0 || totalPrice != 0){
			return totalPrice;
		} else {
			return quantity*unitPrice; 
		}
		*/
		
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void validate() throws PaymentInvalidException {
		
		if((totalPrice == -1 && getQuantity() == 0)
				|| (totalPrice == -1 && getUnitPrice() == 0)){
			throw new PaymentInvalidException("Total price and quantity or unit-price of item " + getName() +
					" have not been set");
		}
		
		if (getQuantity() > 0 && getUnitPrice() > 0) {

			if (!checkAccountItem()) {
				throw new PaymentInvalidException(
						"The total price of item " + getName()
								+ " has to be quantity*unit price");
			}
		}
	}
	
	private boolean checkAccountItem() {
		return (getQuantity() * getUnitPrice()) == getTotalPrice();
	}
	

	
}
