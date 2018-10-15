package com.veryemp.testscripts;

import static com.jayway.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.ValidatableResponse;
import com.relevantcodes.extentreports.LogStatus;
import com.veryemp.listners.BaseClass;
import com.veryemp.listners.ExtentTestManager;

public class ChgPass extends BaseClass{
	
	Header contentTypeHeader = new Header("Content-Type", "application/json");

	String token;
	String jsonBody;
	String response;

	@Test(priority = 0, description = "Valid Login scenario with valid credentials")
	
	public void verifyCorrectLogin() {
		ExtentTestManager.getTest().setDescription("Valid login with valid data");
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		jsonBody = "{\"username\":\"joshi.jugal@gmail.com\",\"password\":\"Jugaljoshi@123\"}";
		System.out.println("dddd");
		
		token = given().when().header(contentTypeHeader).body(jsonBody)
				.post("http://49.50.88.93:9011/rest/authenticate/v1/appuser/login").then().extract()
				.path("data.accessToken");
		
        System.out.println(token);
        String JsonBody2 = "{\"newpassword\":\"Jugaljoshi@1233\",\"oldpassword\":\"Jugaljoshi@123\"}";
		String response = given().when().header(contentTypeHeader).header("Authorization", "Bearer " +
				 token).body(JsonBody2)
				.post("http://49.50.88.93:9011/secure/v1/appuser/changepassword").then().extract().asString();
		
		

		ValidatableResponse res = given().when().header(contentTypeHeader).header("Authorization", "Bearer " +
				 token).body(JsonBody2)
				.post("http://49.50.88.93:9011/secure/v1/appuser/changepassword").then();
        System.out.println("------"+response);
		int statusCode = res.extract().response().getStatusCode();

		Reporter.log("Access and Refresh Token is not empty", true);

		JsonPath validLoginResponse = new JsonPath(response);
		Boolean loginStatus = validLoginResponse.get("status");

		
	}

}
