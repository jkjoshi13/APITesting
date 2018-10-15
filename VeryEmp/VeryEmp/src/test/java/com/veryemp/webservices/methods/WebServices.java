package com.veryemp.webservices.methods;

import org.json.JSONException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class WebServices {
	    public static String authToken;
		public static Response  PostCallWithHeader(String uRI, String stringJSON) {
						
			RequestSpecification requestSpecification = RestAssured.given().body(stringJSON);
			requestSpecification.contentType(ContentType.JSON);
			requestSpecification.headers("Authorization",authToken );
			Response response = requestSpecification.post(uRI);
			return response;
			
		}
		
		public static Response GetCallWithHeader(String uRI) {
			
			RequestSpecification requestSpecification = RestAssured.given();
			requestSpecification.contentType(ContentType.JSON);
			requestSpecification.headers("Authorization",authToken );
			Response response = requestSpecification.get(uRI);
			return response;
			
		}
		
		public static void logintoApplication(String uRI,String userName, String password) throws JSONException {
			RequestSpecification requestSpecification = RestAssured.given().auth().form("joshi.jugal@gmail.com", "123456789");
			Response response = requestSpecification.get(uRI);
			org.json.JSONObject jsonObject = new org.json.JSONObject(response);
			String auth = jsonObject.getString("authToken");
		}

}
