package com.utility;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class BaseTest {
	// Logger statement
	private static Logger log = LogManager.getLogger(BaseTest.class);
	public static PropertyFileReader prop_reader;
	public static DataBaseHelper db;
	public static String accesstoken;

	@BeforeTest
	public static void getTOken() {
		prop_reader = new PropertyFileReader();
		db = new DataBaseHelper();

		log.info("Using " + prop_reader.getProperty("username") + " to get token.");
		// Set base URL
		RestAssured.baseURI = prop_reader.getProperty("base_url");
		Response response = given().header("Content-Type", "application/json")
				.body("{\"email\":\"" + prop_reader.getProperty("username") + "\",\"password\":\""
						+ prop_reader.getProperty("password") + "\"}")
				.when().post(prop_reader.getProperty("authorize"));
		JsonPath jp = new JsonPath(response.asString());
		accesstoken = jp.get("accessToken");
		log.info("Token generated : " + accesstoken);

	}

	public void checkResponseTime(Response response) {

		if (response.getTime() > Integer.parseInt(prop_reader.getProperty("timeout"))) {
			Assert.fail("Expected less than "+prop_reader.getProperty("timeout")+" got "+response.getTime());
		}

	}

}
