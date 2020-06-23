package API_Automation.Rest_Assured;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.utility.BaseTest;
import com.utility.ExcelHandler;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetUSers_Test extends BaseTest {
	GetUsers user = new GetUsers();
	ExcelHandler e = new ExcelHandler();

	@Test
	public void validateGetUser() {

		// Getting email and ID from the API response
		Response usersAPI = user.getUsersAPI();
		System.out.println("Time  " + usersAPI.getTime());
		user.writeResponseToCSV(usersAPI);
		JsonPath jp = new JsonPath(usersAPI.asString());
		List<String> actual_emails = jp.getList("data.email");
		List<String> actual_id = jp.getList("data.id");

		// Getting emails and id from the CSV
		List<String> expected_emails = new ArrayList<String>();
		List<String> expected_id = new ArrayList<String>();
		CSVParser readCSV = e.readCSV("get_users");
		for (CSVRecord csvRecord : readCSV) {
			expected_emails.add(csvRecord.get("email"));
			expected_id.add(csvRecord.get("id"));
		}

		// Sorting the List

		Collections.sort(actual_emails);
		Collections.sort(expected_emails);

		Collections.sort(actual_id);
		Collections.sort(expected_id);

		// Validating emails
		Assert.assertEquals(expected_emails, actual_emails);

		// Asserting Id
		Assert.assertEquals(expected_id, actual_emails);

	}

}
