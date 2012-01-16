package com.alertpay.common;

import android.os.Parcel;
import android.os.Parcelable;

public class ErrorMessage {
	
	private String title;
	private String content;
	
	
	public ErrorMessage(String title, String content){
		this.title = title;
		this.content = content;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getContent(){
		return this.content;
	}

	
}
