package test;

import java.util.ArrayList;

import com.alertpay.common.AccountData;
import com.alertpay.common.AccountItem;
import com.alertpay.common.AlertPay;
import com.alertpay.common.SimplePayment;


import junit.framework.TestCase;

public class SimplePaymentTest extends TestCase {

	
	
	
	public void testSimplePayment(){
		
		SimplePayment simplePayment = new SimplePayment();
		
		AlertPay alertPay = AlertPay.getInstance();
		
		simplePayment = new SimplePayment();
		simplePayment.setCurrencyType("USD");
		simplePayment.setSubTotal(9.0);
		simplePayment.setRecipientEmail("rambonn@gmx.de");
		simplePayment.setRecipientName("Andreas Rammelmeyer");
		
		
		AccountData	accountData = new AccountData();
		accountData.setTax(2.0);
		accountData.setShipping(3.0);
		
		AccountItem	accountItem1 = new AccountItem();
		accountItem1.setTotalPrice(4.0);
		accountItem1.setName("Wurst");
		accountItem1.setUnitPrice(2.0);
		accountItem1.setQuantity(2);
		accountData.addAccountItem(accountItem1);
		
		AccountItem accountItem2 = new AccountItem();
		accountItem2.setTotalPrice(5.0);
		accountItem2.setName("Salat");
		accountItem2.setUnitPrice(2.5);
		accountItem2.setQuantity(2);
		
		accountData.addAccountItem(accountItem2);
		
		simplePayment.setAccountData(accountData);
		
		
		ArrayList<AccountItem> accountItems = simplePayment.getAccountData().getAccountItems();
		accountItem1 = accountItems.get(0);
		
		
		assertEquals(accountItems.size(), 2);
		assertEquals(accountItem1.getName(), "Wurst");
		assertEquals(accountItem1.getQuantity(), 2);
		
	}
	
}
