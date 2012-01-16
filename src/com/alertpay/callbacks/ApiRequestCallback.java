package com.alertpay.callbacks;

import java.util.HashMap;

public interface ApiRequestCallback {

	void onSuccess(HashMap<String, String> response, boolean isLogin);

	void onFailure(String errorMessage);

}
