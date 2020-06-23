package API_Automation.Rest_Assured;

import com.utility.BaseTest;
import static io.restassured.RestAssured.given;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.response.Response;

public class GetProfile extends BaseTest {
	private static Logger log = LogManager.getLogger(GetProfile.class.getName());

	public Response getProfileAPI() {
		Response response = given().header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accesstoken).when().get(prop_reader.getProperty("profile"));
		log.info("Calling profile API");
		return response;
	}

	public ResultSet getProfileDB() {
		String query = " SELECT * FROM user u join user_has_role uhr on (u.id=uhr.user_id) join role r on(r.id=uhr.role_id) where email='"
				+ prop_reader.getProperty("username") + "';";
		ResultSet result = db.connectdb(query);
		return result;
	}

}
