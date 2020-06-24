package API_Automation.Rest_Assured;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

	@Test(description="validating divisions, status code and time")
	public void getGroups() {
		Response groupsAPI = gp.getGroupsAPI();
		log.info("Inside " + this.getClass().getSimpleName());
		System.out.println("Inside "+this.getClass().getSimpleName());

		// Check status code
		groupsAPI.then().assertThat().statusCode(200).and().time(lessThan(3000L));

		// creating the API response to JSON path
		JsonPath jp = new JsonPath(groupsAPI.asString());

		// Extracting the store groups from the API response
		List<String> actual_store_group = (List<String>) jp.get("data.name");
		Collections.sort(actual_store_group);

		ResultSet groupsDB = gp.getGroupsDB();
		List<String> expected_store_groups = new ArrayList<String>();
		try {
			while (groupsDB.next()) {
				expected_store_groups.add(groupsDB.getString("name"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Looping through the resultset in " + this.getClass().getSimpleName());
			e.printStackTrace();
		}
		Collections.sort(expected_store_groups);

		// Comparing the store groups got from API response and the Data Base
		Assert.assertEquals(actual_store_group, expected_store_groups);
		log.info("Exeuction of " + this.getClass().getSimpleName()+" completed");
	}

}
