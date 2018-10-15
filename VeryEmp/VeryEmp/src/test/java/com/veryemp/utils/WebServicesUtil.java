package com.veryemp.utils;

import com.veryemp.webservices.methods.WebServices;

public class WebServicesUtil {
	
	public static void logintoApplication() {
		String auth = getLogin();
		WebServices.authToken = auth;
			
		
	}

	private static String getLogin() {
		
		return null;
	}

}
