package API_Automation.Rest_Assured;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.lessThan;

import com.utility.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetGroups_Test extends BaseTest {
	// Logger statement
	private static Logger log = LogManager.getLogger(GetGroups_Test.class.getName());

	GetGroups gp = new GetGroups();
	Response groupsAPI;
	List<String> actual_store_group;
	List<String> expected_store_groups;

	@Test(description = "validating get groups API status code")
	public void getGroups() {
		groupsAPI = gp.getGroupsAPI();
		log.info("Inside " + this.getClass().getSimpleName());
		System.out.println("Inside " + this.getClass().getSimpleName());

		// Check status code
		groupsAPI.then().assertThat().statusCode(200);

		// creating the API response to JSON path
		JsonPath jp = new JsonPath(groupsAPI.asString());

		// Extracting the store groups from the API response
		actual_store_group = (List<String>) jp.get("data.name");

		ResultSet groupsDB = gp.getGroupsDB();
		expected_store_groups = new ArrayList<String>();
		try {
			while (groupsDB.next()) {
				expected_store_groups.add(groupsDB.getString("name"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Looping through the resultset in " + this.getClass().getSimpleName());
			e.printStackTrace();
		}

		validateStoreGroupList();
		validateStoreGroupResponseTime();

		log.info("Exeuction of " + this.getClass().getSimpleName() + " completed");
	}

	// Comparing the store groups got from API response and the Data Base
	@Test(description = "validate storegroups list")
	public void validateStoreGroupList() {
		System.out.println(actual_store_group);
		System.out.println(actual_store_group);

		Collections.sort(actual_store_group);
		Collections.sort(expected_store_groups);
		Assert.assertEquals(actual_store_group, expected_store_groups);
	}

	@Test(description = "Validate get groups API response time")
	public void validateStoreGroupResponseTime() {
		checkResponseTime(groupsAPI);

	}
}
