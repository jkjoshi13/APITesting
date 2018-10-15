package com.veryemp.testscripts;

import static com.jayway.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.ValidatableResponse;
import com.relevantcodes.extentreports.LogStatus;
import com.veryemp.listners.ExtentTestManager;

import junit.framework.Assert;

public class AppliedForJob {
	
	private Header contentTypeHeader = new Header("Content-Type", "application/json");
	private String token;
	private Header authHeader;
	private String response;
	private String JsonBody;
	private int statusCode;
	private ValidatableResponse validableRespons;
	
	@Test(priority = 0,description = "Valid Login scenario with valid credentials")
	
	public void loginApi() {
		
		Header contentTypeHeader = new Header("Content-Type", "application/json");
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		String jsonBody = "{\"username\":\"joshi.jugal@gmail.com\",\"password\":\"Pass@12345\"}";

		token = given().when().header(contentTypeHeader).body(jsonBody)
				.post("https://testapis.verimployee.com/rest/authenticate/v1/appuser/login").then().extract()
				.path("data.accessToken");
	}
	@Test(priority = 1,description = "Applied For Job")
	public void successMessageAppliedForJob() {
		authHeader = new Header("Authorization", "Bearer " + token);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		JsonBody = "{\"isnewuser\":\"1\","
				+ "\"isedited\":\"0\","
				+ "\"firstname\":\"Fatimaa\","
				+ "\"middlename\":\"ka\","
				+ "\"lastname\":\"khana\","
				+ "\"fatherfirstname\":\"Deewana\","
				+ "\"fathermiddlename\":\"ka\","
				+ "\"fatherlastname\":\"Khana\","
				+ "\"motherfirstname\":\"geta\","
				+ "\"mothermiddlename\":\"k\","
				+ "\"motherlastname\":\"singh\","
				+ "\"dob\":\"31/01/1986\","
				+ "\"pancard\":\"Fatimaa124\","
				+ "\"otheridentitycard\":\"Fatima1234\","
				+ "\"email\":\"jugal.kishore+44889@mytrusteddr.com\","
				+ "\"mobileno\":\"9654649479\","
				+ "\"addressline1\":\"hyd\","
				+ "\"addressline2\":\"hyd\","
				+ "\"addresspincode\":\"200300\" ,"
				+ "\"addresscityid\":\"null\","
				+ "\"addresscityname\":\"Ghaziabad\","
				+ "\"addressstateid\":\"1\","
				+ "\"birthtown\":\"mohannagar\","
				+ "\"birthstateid\":\"1\" }";
		
		response = given().when().header(contentTypeHeader).header(authHeader).body(JsonBody).when()
				.post("https://testapis.verimployee.com/rest/secure/v1/joboffer/appuser/appliedForJob").then().extract().asString();

		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("https://testapis.verimployee.com/rest/secure/v1/joboffer/appuser/appliedForJob").then();

		statusCode = validableRespons.extract().response().getStatusCode();
		
		JsonPath appliedForJobStatus = new JsonPath(response);
		Boolean apiStatus = appliedForJobStatus.get("status");
		System.out.println(response);
		
		
		if (apiStatus && statusCode == 200 && appliedForJobStatus.get("message").equals("SUCCESS") 
				&&  appliedForJobStatus.get("status").equals(true)
				&&  appliedForJobStatus.get("data")==null
	
				
				) {
			System.out.println(response);
		}
		
		else {
			System.out.println("Not Able to apply a job");
			Assert.assertEquals(apiStatus, Boolean.TRUE);
			Assert.assertEquals(appliedForJobStatus.get("message"), "SUCCESS","Success message apears");
			
		}
	}

}
