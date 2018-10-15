package com.veryemp.testscripts;

import static com.jayway.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.ValidatableResponse;
import com.relevantcodes.extentreports.LogStatus;
import com.veryemp.listners.BaseClass;
import com.veryemp.listners.ExtentTestManager;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;

public class ChangePassword extends BaseClass {

	private Header contentTypeHeader = new Header("Content-Type", "application/json");
	private String token;
	private Header authHeader;
	private String response;
	private String JsonBody;
	private int statusCode;
	private ValidatableResponse validableRespons;

	@Test(priority = 0,description = "Valid Login scenario with valid credentials")
	public void loginAPI() {

		Header contentTypeHeader = new Header("Content-Type", "application/json");
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		String jsonBody = "{\"username\":\"joshi.jugal@gmail.com\",\"password\":\"Jugal@123\"}";

		token = given().when().header(contentTypeHeader).body(jsonBody)
				.post("http://49.50.88.93:9011/rest/authenticate/v1/appuser/login").then().extract()
				.path("data.accessToken");

	}

	@Test(priority = 1,description = "Change Password with Incorrect Existing Password")

	public void changePasswordWithIncorrectExistingPassword() throws JSONException {
		authHeader = new Header("Authorization", "Bearer " + token);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;

		JsonBody = "{\"newpassword\":\"Jugal@j21234\",\"oldpassword\":\"Jugal@j123\"}";

		response = given().when().header(contentTypeHeader).header(authHeader).body(JsonBody).when()
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().extract().asString();

		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then();

		statusCode = validableRespons.extract().response().getStatusCode();

		JsonPath changePasswordStatus = new JsonPath(response);
		Boolean apiStatus = changePasswordStatus.get("status");

		if (apiStatus.equals(false) && statusCode == 200
				&& changePasswordStatus.get("message").equals("Incorrect existing password.") &&

				changePasswordStatus.get("msgcode") == "VM_0013") {
			ExtentTestManager.getTest().log(LogStatus.INFO, "Verified the Change Password API with Incorrect Existing Password");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response is: " + validableRespons.extract().asString());
		} else {
			System.out.println("API response is not matched failed");
			Assert.assertEquals(statusCode, 200, "API Status code is 200ok");
			Assert.assertEquals(apiStatus, Boolean.FALSE);
			Assert.assertEquals(changePasswordStatus.get("message"), "Incorrect existing password.",
					"Message Field in the response should be Incorrect Old Password");
			Assert.assertNotNull(changePasswordStatus.get("msgcode"), "Error message code should be VM_009");
		}

	}

	@Test(priority = 3, description = "Change Password With valid Credentials")
	public void chagePasswordWithValidCredentials() {
		authHeader = new Header("Authorization", "Bearer " + token);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		JsonBody = "{\"newpassword\":\"Jugal@10\",\"oldpassword\":\"Jugal@123\"}";

		response = given().when().header(contentTypeHeader).header(authHeader).body(JsonBody).when()
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().extract().asString();

		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then();

		statusCode = validableRespons.extract().response().getStatusCode();

		JsonPath changePasswordStatus = new JsonPath(response);
		Boolean apiStatus = changePasswordStatus.get("status");

		if (apiStatus.equals(true) && statusCode == 200 && changePasswordStatus.get("message").equals("SUCCESS") &&

				changePasswordStatus.get("msgcode") == "") {
			ExtentTestManager.getTest().log(LogStatus.INFO, "Verified the Change Password API with Valid Password");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response is: " + validableRespons.extract().asString());
		} else {
			System.out.println("API response is not matched failed");
			Assert.assertEquals(statusCode, 200, "API Status code is 200ok");
			Assert.assertEquals(apiStatus, Boolean.FALSE);
			Assert.assertEquals(changePasswordStatus.get("message"), "SUCCESS", "Password Change successfully");
			Assert.assertNotNull(changePasswordStatus.get("msgcode"), "Message Code is empty");
		}
	}

	@Test(priority = 2,description = "Change Password with Last Three Password")
	public void chagePasswordWithLastThreePassword() {
		authHeader = new Header("Authorization", "Bearer " + token);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		JsonBody = "{\"newpassword\":\"Jugal@12\",\"oldpassword\":\"Jugal@123\"}";

		response = given().when().header(contentTypeHeader).header(authHeader).body(JsonBody).when()
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().extract().asString();
		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then();

		statusCode = validableRespons.extract().response().getStatusCode();

		JsonPath changePasswordStatus = new JsonPath(response);
		Boolean apiStatus = changePasswordStatus.get("status");

		if (apiStatus.equals(false) && statusCode == 200
				&& changePasswordStatus.get("message").equals("Password should not be same as last three passwords") &&

				changePasswordStatus.get("msgcode") == "VM_0013") {
			ExtentTestManager.getTest().log(LogStatus.INFO, "Verified the Change Password API with Last Three Password");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response is: " + validableRespons.extract().asString());
		} else {
			System.out.println("API response is not matched failed");
			Assert.assertEquals(statusCode, 200, "API Status code is 200ok");
			Assert.assertEquals(apiStatus, Boolean.FALSE);
			Assert.assertEquals(changePasswordStatus.get("message"), "Password should not be same as last three passwords",
					"Password same as last three Password");
			Assert.assertNotNull(changePasswordStatus.get("msgcode"),
					"Message Code is VM_0013 while using existing password");
		}

	}
	
	@Test(priority=4, description = "Change Password with Invalid Password Format")
	
	public void changePasswordWithInvalidPasswordFormat() {
		authHeader = new Header("Authorization", "Bearer " + token);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.defaultParser = Parser.JSON;
		JsonBody = "{\"newpassword\":\"Jugalj1234\",\"oldpassword\":\"Jugal@123\"}";

		response = given().when().header(contentTypeHeader).header(authHeader).body(JsonBody).when()
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then().extract().asString();
		validableRespons = given().when().header(contentTypeHeader).body(JsonBody)
				.post("http://49.50.88.93:9011/rest/secure/v1/appuser/changepassword").then();

		statusCode = validableRespons.extract().response().getStatusCode();

		JsonPath changePasswordStatus = new JsonPath(response);
		Boolean apiStatus = changePasswordStatus.get("status");

		if (apiStatus && statusCode == 200
				&& changePasswordStatus.get("message").equals("Invalid Request") &&

				changePasswordStatus.get("msgcode") == "VM_0000") {
			ExtentTestManager.getTest().log(LogStatus.INFO, "Verified the Change Password API with Invalid Password Format");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response is: " + validableRespons.extract().asString());
			
		} else {
			System.out.println("API response is not matched failed");
			Assert.assertEquals(statusCode, 200, "API Status code is 200ok");
			Assert.assertEquals(apiStatus, Boolean.FALSE);
			Assert.assertEquals(changePasswordStatus.get("message"), "Invalid Request");
			Assert.assertNotNull(changePasswordStatus.get("msgcode"),
					"Message Code is VM_0000 while using existing password");
		}

		
	}

}
