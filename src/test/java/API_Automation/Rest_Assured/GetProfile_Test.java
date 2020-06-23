package API_Automation.Rest_Assured;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.utility.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetProfile_Test extends BaseTest {
	private static Logger log = LogManager.getLogger(GetProfile.class.getName());

	GetProfile gp = new GetProfile();

	@Test
	public void valideteGetProfile() {

		Response profileAPI = gp.getProfileAPI();
		ResultSet profileDB = gp.getProfileDB();
		log.info("Inside " + this.getClass().getSimpleName());
		profileAPI.then().assertThat().statusCode(200).body("status", equalTo("Success"));
		JsonPath jp = new JsonPath(profileAPI.asString());
		try {
			while (profileDB.next()) {
				// validate email
				profileAPI.then().assertThat().body("data.email", equalTo(profileDB.getString("email")));
				// validate active_flag
				if (profileDB.getString("active_flag") == "1") {
					profileAPI.then().assertThat().body("data.active", equalTo("true"));

				}
				if (profileDB.getString("active_flag") == "0") {
					profileAPI.then().assertThat().body("data.active", equalTo("false"));

				}

				// Validate first and last name
				profileAPI.then().assertThat().body("data.firstName", equalTo(profileDB.getString("first_name")));
				profileAPI.then().assertThat().body("data.lastName", equalTo(profileDB.getString("last_name")));

				// Validate role
				profileAPI.then().assertThat().body("data.userRoles.roleId.role[0]",
						equalTo(profileDB.getString("name")));

			}

		} catch (SQLException e) {
			log.error("Looping through the resultset in " + this.getClass().getSimpleName());
			e.printStackTrace();
		}

	}

}
