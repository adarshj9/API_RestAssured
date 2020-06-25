package API_Automation.Rest_Assured;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.utility.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetProfile_Test extends GetProfile {
	private static Logger log = LogManager.getLogger(GetProfile.class.getName());

	GetProfile gp = new GetProfile();
	Response profileAPI;
	ResultSet profileDB;
	JsonPath jp;

	@Test(priority = 0, groups = "GetProfileValidations", description = "Validate Profile API status code")
	public void valideteGetProfile() {
		System.out.println("Inside " + this.getClass().getSimpleName());

		profileAPI = gp.getProfileAPI();
		profileDB = gp.getProfileDB();
		log.info("Inside " + this.getClass().getSimpleName());
		profileAPI.then().assertThat().statusCode(200).and().body("status", equalTo("Success"));
		jp = new JsonPath(profileAPI.asString());
		log.info("Exeuction of " + this.getClass().getSimpleName() + " completed");

	}

	@Test(priority = 1, groups = "GetProfileValidations", description = "Validate user role")
	public void validateRole() {
		try {
			while (profileDB.next()) {
				// Validate role
				profileAPI.then().assertThat().body("data.userRoles.roleId.role[0]",
						equalTo(profileDB.getString("name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 2, groups = "GetProfileValidations", description = "Validate first name and last name")
	public void validateFirstAndLastName() {
		try {
			while (profileDB.next()) {
				// Validate first and last name
				profileAPI.then().assertThat().body("data.firstName", equalTo(profileDB.getString("first_name")));
				profileAPI.then().assertThat().body("data.lastName", equalTo(profileDB.getString("last_name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 3, groups = "GetProfileValidations", description = "Validate Active flag")
	public void validateActiveFlag() {
		try {
			while (profileDB.next()) {
				if (profileDB.getString("active_flag") == "1") {
					profileAPI.then().assertThat().body("data.active", equalTo("true"));

				}
				if (profileDB.getString("active_flag") == "0") {
					profileAPI.then().assertThat().body("data.active", equalTo("false"));

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 4, groups = "GetProfileValidations", description = "Validate email id")
	public void validateEmail() {
		try {
			while (profileDB.next()) {
				// validate email
				profileAPI.then().assertThat().body("data.email", equalTo(profileDB.getString("email")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 5, groups = "GetProfileValidations", description = "Validate get profile API response time")
	public void validateGetProfileResponseTime() {
		checkResponseTime(profileAPI);

	}

}
