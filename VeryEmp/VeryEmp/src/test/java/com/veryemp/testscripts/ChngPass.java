package com.veryemp.testscripts;

import static com.jayway.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.ValidatableResponse;
import com.relevantcodes.extentreports.LogStatus;
import com.veryemp.listners.BaseClass;
import com.veryemp.listners.ExtentTestManager;

public class ChngPass extends BaseClass {
	Header contentTypeHeader = new Header("Content-Type", "application/json");
	String token;

	@Test(priority = 0)
	public void validLogin() {

		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		String jsonBody = "{\"username\":\"joshi.jugal@gmail.com\",\"password\":\"Jugaljoshi@123\"}";

		String token = given().when().header(contentTypeHeader).body(jsonBody)
				.post("http://49.50.88.93:9011/rest/authenticate/v1/appuser/login").then().extract()
				.path("data.accessToken");
		//Header authHeader = new Header("Authorization", "Bearer " + token);

		String response = given().when().header(contentTypeHeader).body(jsonBody).post("http://49.50.88.93:9011/rest/authenticate/v1/appuser/login").then().extract().asString();
		System.out.println("Response is " + response);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Name :");
		System.out.println("\n" + "****Login API working Properly get the follwoing accessToken******" + "\n" + "\n"
				+ token + "\n");
	}

	@Test(priority = 1)
	public void chagePasswordWithValidCredentials() {
         Header authheader = new Header("Authorization", "Bearer " + token);
		String jsonBodyFP = "{\"newpassword\":\"Jugal@123\",\"oldpassword\":\"Jugaljoshi@123\"}";

//		String changePass = given().when().header(contentTypeHeader).header(authheader)
//				.body(jsonBodyFP).when().post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().extract().path("message");
		
		String changePass = given().when().header(contentTypeHeader).header(authheader)
		.body(jsonBodyFP).when().post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().extract().asString();
		
		
		System.out.println(changePass);
        System.out.println("Token received on this step = "+token);
		System.out.println("Etracted Message on Success " + changePass);
		String validateResponse = given().when().header("Authorization", "Bearer " + token).header(contentTypeHeader)
				.body(jsonBodyFP).when().post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().assertThat().statusCode(200)
				.assertThat().extract().path("message", "SUCESS");
		
		System.out.println("Message receive on Valid Login ==" + validateResponse);
		Reporter.log("Verify the sucess", true);

	}
}
