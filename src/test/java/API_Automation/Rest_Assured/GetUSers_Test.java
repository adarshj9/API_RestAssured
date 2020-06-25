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
	Response usersAPI;
	JsonPath jp;
	List<String> actual_emails;
	List<String> actual_name;
	List<String> expected_emails;
	List<String> expected_name;

	@Test(priority = 0, groups = "GetUSers", description = "validating get user API status code")
	public void validateGetUser() {
		log.info("Inside " + this.getClass().getSimpleName());
		System.out.println("Inside " + this.getClass().getSimpleName());

		// Getting email and ID from the API response
		usersAPI = user.getUsersAPI();
		usersAPI.then().assertThat().statusCode(200);
		user.writeResponseToCSV(usersAPI);
		jp = new JsonPath(usersAPI.asString());
		actual_emails = jp.getList("data.email");
		actual_name = jp.getList("data.firstName");

		// Getting emails and id from the CSV
		expected_emails = new ArrayList<String>();
		expected_name = new ArrayList<String>();
		CSVParser readCSV = e.readCSV("get_users");

		for (CSVRecord csvRecord : readCSV) {
			expected_emails.add(csvRecord.get("email"));
			expected_name.add(csvRecord.get("firstName"));
		}

		

	}

	@Test(priority = 1, groups = "GetUSers", description = "Validate email list")
	public void validatEmailList() {
		// Sorting the List
		Collections.sort(actual_emails);
		Collections.sort(expected_emails);
		// Validating email
		Assert.assertEquals(expected_emails, actual_emails);

	}

	@Test(priority = 2, groups = "GetUSers", description = "Validate name list")
	public void validatNameList() {
		Collections.sort(actual_name);
		Collections.sort(expected_name);
		Assert.assertEquals(expected_name, actual_name);

	}

	@Test(priority = 3, groups = "GetUSers", description = "Validate get uesr API response time")
	public void validateGetUserResponseTime() {
		checkResponseTime(usersAPI);
		log.info("Execution " + this.getClass().getSimpleName() + " Completed");

	}
	

}
