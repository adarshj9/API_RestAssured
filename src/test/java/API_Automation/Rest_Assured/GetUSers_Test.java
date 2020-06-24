package API_Automation.Rest_Assured;

import static org.hamcrest.Matchers.lessThan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.utility.BaseTest;
import com.utility.ExcelHandler;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetUSers_Test extends BaseTest {
	private static Logger log = LogManager.getLogger(GetUSers_Test.class.getName());
	GetUsers user = new GetUsers();
	ExcelHandler e = new ExcelHandler();

	@Test(description="validating get user API : validating data fron the csv, and API response")
	public void validateGetUser() {
		log.info("Inside " + this.getClass().getSimpleName());
		System.out.println("Inside " + this.getClass().getSimpleName());

		// Getting email and ID from the API response
		Response usersAPI = user.getUsersAPI();
		usersAPI.then().assertThat().statusCode(200).and().time(lessThan(3000L));
		user.writeResponseToCSV(usersAPI);
		JsonPath jp = new JsonPath(usersAPI.asString());
		List<String> actual_emails = jp.getList("data.email");
		List<String> actual_name = jp.getList("data.firstName");

		// Getting emails and id from the CSV
		List<String> expected_emails = new ArrayList<String>();
		List<String> expected_name = new ArrayList<String>();
		CSVParser readCSV = e.readCSV("get_users");

		for (CSVRecord csvRecord : readCSV) {
			expected_emails.add(csvRecord.get("email"));
			expected_name.add(csvRecord.get("firstName"));
		}

		// Sorting the List

		Collections.sort(actual_emails);
		Collections.sort(expected_emails);
		Assert.assertEquals(expected_name, actual_name);
		Collections.sort(actual_name);
		Collections.sort(expected_name);

		// Validating emails
		Assert.assertEquals(expected_emails, actual_emails);

		// Asserting Id
		Assert.assertEquals(expected_name, actual_name);
		log.info("Execution " + this.getClass().getSimpleName() + " Completed");

	}

}
