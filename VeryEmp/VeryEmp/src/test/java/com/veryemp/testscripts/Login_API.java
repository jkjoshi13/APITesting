package com.veryemp.testscripts;

import static com.jayway.restassured.RestAssured.given;

import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.ValidatableResponse;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.veryemp.listners.BaseClass;
import com.veryemp.listners.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

public class Login_API extends BaseClass {
	
	private Header contentTypeHeader = new Header("Content-Type", "application/json");
	private String token;
	private Header authHeader;
	private String response;
	private String JsonBody;
	private int statusCode;
	private ValidatableResponse validableRespons;

	@Test(priority = 0, description = "Valid Login scenario with valid credentials",enabled=false)

	public void verifyCorrectLogin() {
		ExtentTestManager.getTest().setDescription("Valid login with valid data");
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		JsonBody = "{\"username\":\"joshi.jugal@gmail.com\",\"password\":\"Jugal@12345\"}";
		token = given().when().header(contentTypeHeader).body(JsonBody)
				.post("https://testapis.verimployee.com/rest/authenticate/v1/appuser/login").then().extract()
				.path("data.accessToken");

	 response = given().when().header(contentTypeHeader).body(JsonBody)
				.post("https://testapis.verimployee.com/rest/authenticate/v1/appuser/login").then().extract().asString();

		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("https://testapis.verimployee.com/rest/authenticate/v1/appuser/login").then();

		statusCode = validableRespons.extract().response().getStatusCode();

		Reporter.log("Access and Refresh Token is not empty", true);

		JsonPath validLoginResponse = new JsonPath(response);
		Boolean loginStatus = validLoginResponse.get("status");

		if (loginStatus && statusCode == 200 && validLoginResponse.get("message").equals("SUCCESS")
				&& validLoginResponse.get("data.accessToken") != null
				&& validLoginResponse.get("data.refreshToken") != null) {

			ExtentTestManager.getTest().log(LogStatus.INFO, "Verified the Login API successfully");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response is: " + validableRespons.extract().asString());
		} else {
			System.out.println("API response is not matched failed");
			Assert.assertEquals(statusCode, 200, "API Status code is 200ok");
			Assert.assertEquals(loginStatus, Boolean.TRUE);
			Assert.assertEquals(validLoginResponse.get("message"), "SUCCESS",
					"Message Field in the response should be Success");
			Assert.assertEquals(validLoginResponse.get("statusCode"), "200",
					"Code Field in the response should be 200");
			Assert.assertNotNull(validLoginResponse.get("data.accessToken"), "AccessToken Should not be null");
			Assert.assertNotNull(validLoginResponse.get("data.refreshToken"), "RefreshToken Should not be null");
		}
	}

	@Test(priority = 1, description = "Login scenario with Invalid credentials")
	public void verfyIncorrectLogin() {

		JsonBody = "{\"username\":\"joshi.jugal@gmail.com\",\"password\":\"Jugajoshi@1\"}";
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("https://testapis.verimployee.com/rest/authenticate/v1/appuser/login").then();
		int statusCode = validableRespons.extract().response().getStatusCode();
		System.out.println(statusCode);
		response = given().when().header(contentTypeHeader).body(JsonBody)
				.post("https://testapis.verimployee.com/rest/authenticate/v1/appuser/login").then().extract().asString();

		JsonPath validLoginResponse = new JsonPath(response);
		Boolean loginStatus = validLoginResponse.get("status");
		Boolean loginData = validLoginResponse.get("data");

		if (loginStatus.equals(false) && statusCode == 200
				&& validLoginResponse.get("message").equals("Invalid username or password.")
				&& validLoginResponse.get("msgcode") == "VM_0009")
		// && validLoginResponse.get("data") == null)
		{

			ExtentTestManager.getTest().log(LogStatus.INFO,
					"Verified the Login API With Invalid Credentials successfully");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response is: " + validableRespons.extract().asString());
		} else {
			System.out.println("Access Token doesn't work is not matched failed");
			// Assert.assertEquals(statusCode, "200","API Status code is 200ok");
			Assert.assertEquals(loginStatus, Boolean.FALSE);
			Assert.assertEquals(validLoginResponse.get("message"), "Invalid username or password.",
					"Message Field in the response should be Invalid User Name and password");
			// Assert.assertEquals(validLoginResponse.get("statusCode"), "200", "Code Field
			// in the response should be 200");
			Assert.assertNotNull(validLoginResponse.get("msgcode"), "Error message code should be VM_009");
			// Assert.assertNotNull(validLoginResponse.get("data"), "Data Should be null for
			// invalid login credentials");
		}

	}
	
	@Test
	public void invalidUserName() {
		
	}

}
