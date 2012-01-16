package com.alertpay.activities;

import java.util.ArrayList;


import com.alertpay.R;
import com.alertpay.common.AccountData;
import com.alertpay.common.AccountItem;
import com.alertpay.common.AlertPay;
import com.alertpay.common.SimplePayment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestActivity extends Activity implements OnClickListener{
	private Button button;
	private SimplePayment simplePayment;
	private AlertPay alertPay;
	private AccountData accountData;
	private ArrayList<AccountItem> accountItems;
	
	
	
	
	private LinearLayout paymentDetailsView;
	private TextView receiverView;
	private LinearLayout testLayout;
	private LinearLayout accountItemView;
	private LinearLayout itemsStubLayout;
	
	private TextView itemNameView;
	private TextView totalItemPriceView;
	private TextView itemPriceView;
	private TextView itemQuantityView;
	private TextView taxView;
	private TextView shippingView;
	
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        
        this.alertPay = AlertPay.getInstance();
        simplePayment= (SimplePayment)alertPay.getAlertPayComponent();
        accountData = simplePayment.getAccountData();
        accountItems = accountData.getAccountItems();
        
        testLayout = (LinearLayout)findViewById(R.id.testLayout);
        paymentDetailsView = (LinearLayout)getLayoutInflater().inflate(R.layout.ap_simple_payment_details, null);
//        itemsStubLayout = (LinearLayout)paymentDetailsView.findViewById(R.id.sp_layout_itemsStub);
              
        receiverView = (TextView)paymentDetailsView.findViewById(R.id.sp_txt_receiver);
        receiverView.setText(simplePayment.getRecipientName()+" ("+simplePayment.getRecipientEmail()+")");
  
        
        for(int i=0; i<accountItems.size();i++){
        	
        	accountItemView = (LinearLayout)getLayoutInflater().inflate(R.layout.ap_simple_payment_item, null);
        	
        	AccountItem currentItem = accountItems.get(i);
        	
        	TextView itemName = (TextView)accountItemView.findViewById(R.id.sp_txt_item);
        	itemName.setText(itemName.getText()+currentItem.getName());        	
        	        	
        	TextView totalPrice = (TextView)accountItemView.findViewById(R.id.sp_txt_item_totalprice);
        	totalPrice.setText(Double.toString(currentItem.getTotalPrice())+" "+simplePayment.getCurrencyType());
        	
        	TextView unitPrice = (TextView)accountItemView.findViewById(R.id.sp_txt_unit_price);
        	unitPrice.setText(unitPrice.getText()+Double.toString(currentItem.getUnitPrice()));
        	
        	TextView quantity = (TextView)accountItemView.findViewById(R.id.sp_txt_unit_quantity);
        	quantity.setText(quantity.getText()+Integer.toString(currentItem.getQuantity()));
        	

//        	paymentDetailsLayout.addView(paymentItemView);
        	itemsStubLayout.addView(accountItemView);
        }
        
        TextView tax = (TextView)paymentDetailsView.findViewById(R.id.sp_txt_tax);
        tax.setText(Double.toString(accountData.getTax())+" "+simplePayment.getCurrencyType());
        
        TextView shipping = (TextView)paymentDetailsView.findViewById(R.id.sp_txt_shipping);
        shipping.setText(Double.toString(accountData.getShipping())+" "+simplePayment.getCurrencyType());
        
        
        TextView total = (TextView)paymentDetailsView.findViewById(R.id.sp_txt_payment_total);
        total.setText(Double.toString(simplePayment.getTotal())+" "+simplePayment.getCurrencyType());
        
        testLayout.addView(paymentDetailsView);

    }
    
    private void initLayout(){
    	
    	
    	
    }
   
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
