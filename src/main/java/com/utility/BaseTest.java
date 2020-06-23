package com.utility;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

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
				.body("{\"email\":\"adarsh.jayanna@costrategix.com\",\"password\":\"12345\"}").when()
				.post(prop_reader.getProperty("authorize"));
		JsonPath jp = new JsonPath(response.asString());
		accesstoken = jp.get("accessToken");
		log.info("Token generated : " + accesstoken);

	}

}
