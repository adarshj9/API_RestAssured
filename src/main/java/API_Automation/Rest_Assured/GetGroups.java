package API_Automation.Rest_Assured;

import static io.restassured.RestAssured.given;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utility.BaseTest;

import io.restassured.response.Response;

public class GetGroups extends BaseTest {
	private static Logger log = LogManager.getLogger(GetGroups.class.getName());

	public Response getGroupsAPI() {
		Response response = given().header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accesstoken).when().get(prop_reader.getProperty("groups"));
		log.info("Making call to get groups API");
		return response;

	}

	public ResultSet getGroupsDB() {
		String query = "select id,name from store_group where id in( select store_group_id FROM user_has_store_group where user_id="
				+ "(select id from user where email='" + prop_reader.getProperty("username") + "'))and delete_flag=0;";
		return db.connectdb(query);

	}

}
