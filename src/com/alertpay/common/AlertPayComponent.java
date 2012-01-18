package com.alertpay.common;

import com.alertpay.exceptions.ParameterInvalidException;
import com.alertpay.exceptions.ParameterNotSetException;
import com.alertpay.exceptions.PaymentInvalidException;

public interface AlertPayComponent {
	
	
	public boolean isValid();
	
	public void validate() throws ParameterInvalidException, ParameterNotSetException, PaymentInvalidException;
}
