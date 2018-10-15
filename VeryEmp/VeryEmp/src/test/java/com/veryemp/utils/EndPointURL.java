package com.veryemp.utils;

public enum EndPointURL {
	
	APPUSER_LOGIN("/login"),
	GENERATE_OTP_FOR_FORGOT_PASSWORD("/generateOtpForForgetPassword"),
	CHANGE_PASSWORD("/changepassword");
	String resourcePath;
	
	EndPointURL(String resourcePath){
		this.resourcePath=resourcePath;
	}
	
	public String getResourcePath() {
		return this.resourcePath;
	}
	
	public String getResourcePath(String data) {
		return this.resourcePath+data;
	}
    
	
	public static void main(String args[]) {
		//System.out.println(EndPointURL.APPUSER_LOGIN.getResourcePath());
		
		System.out.println("Making FUll URL of Login");
		
		System.out.println(URL.fixURL+EndPointURL.APPUSER_LOGIN.getResourcePath());
	}
}
