package API_Automation.Rest_Assured;

import static io.restassured.RestAssured.given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.utility.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ChangeStatus extends BaseTest {
	private static Logger log = LogManager.getLogger(ChangeStatus.class.getName());

	public String getOpenVariance() {
		GetGroups gp = new GetGroups();
		JsonPath jp = new JsonPath(gp.getGroupsAPI().asString());

		// get variance list - Dashboard list
		Response response = given().param("status", "1").header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accesstoken)
				.get(prop_reader.getProperty("variance_list") + jp.getString("data.id[0]"));

		JsonPath jp1 = new JsonPath(response.asString());

		// get variance details - variance detail page
		Response response2 = given().param("groupId", jp.getString("data.id[0]"))
				.header("Content-Type", "application/json").header("Authorization", "Bearer " + accesstoken)
				.get(prop_reader.getProperty("variance_details"), jp1.get("data[0].productNumber"),
						jp1.get("data[0].distributorId"), 1);

		JsonPath jp2 = new JsonPath(response2.asString());
		String variance_id = jp2.getString("data.varianceDetailsDtoList[0].id");
		String body = "{\"varianceIdsList\":[" + variance_id
				+ "],\"currentStatus\":2,\"groupId\":9,\"previousStatus\":1,\"comment\":\"send for price update - test - Automation\"}";
		System.out.println(variance_id);
		log.info("Changing status of variance id = "+variance_id);

		return body;

	}

	public Response changeStatusAPI() {
		String body = getOpenVariance();

		Response response = given().header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accesstoken).body(body).when()
				.post(prop_reader.getProperty("variance_status"));

		System.out.println(response.asString());
		log.info("calling Status change API");
		return response;

	}
}
