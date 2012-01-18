package com.alertpay.views;

import com.alertpay.activities.AlertPayActivity;
import com.alertpay.common.ErrorMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ErrorDialog  {
	
	
	private AlertPayActivity activity;
	private Context context;
	private String title;
	private String message;
	private AlertDialog alertDialog;
	private boolean cancelActivity;
	private ErrorMessage errorMessage;

	
	public ErrorDialog(AlertPayActivity activity, Context context, ErrorMessage errorMessage, boolean cancelActivity){
		this.activity = activity;
		this.title = title;
		this.errorMessage = errorMessage;
		this.context = context;
		this.cancelActivity = cancelActivity;
		createDialog();
	}
	
	public void show(){
		this.alertDialog.show();
	}
		
	private void createDialog(){
				
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		builder.setTitle(errorMessage.getTitle())
			   .setMessage(errorMessage.getContent())
		       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(cancelActivity){
//						activity.finish();
						activity.closeActivity(errorMessage);
						
					} else {
						dialog.dismiss();

					}
				}
			});
		
		this.alertDialog = builder.create();	
	}	
	
}

