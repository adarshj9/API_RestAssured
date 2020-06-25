package API_Automation.Rest_Assured;

import static io.restassured.RestAssured.given;

import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.utility.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ChangeStatus extends BaseTest {
	private static Logger log = LogManager.getLogger(ChangeStatus.class.getName());

	GetGroups gp = new GetGroups();
	JsonPath jp;
	JsonPath jp1;
	JsonPath jp2;
	String variance_id;
	Response status_respons;

	public String getOpenVariance() {

		jp = new JsonPath(gp.getGroupsAPI().asString());

		// get variance list - Dashboard list
		Response response = given().param("status", prop_reader.getProperty("open_variance_status"))
				.header("Content-Type", "application/json").header("Authorization", "Bearer " + accesstoken)
				.get(prop_reader.getProperty("variance_list") + jp.getString("data.id[0]"));

		jp1 = new JsonPath(response.asString());

		// get variance details - variance detail page
		Response response2 = given().param("groupId", jp.getString("data.id[0]"))
				.header("Content-Type", "application/json").header("Authorization", "Bearer " + accesstoken)
				.get(prop_reader.getProperty("variance_details"), jp1.get("data[0].productNumber"),
						jp1.get("data[0].distributorId"), prop_reader.getProperty("open_variance_status"));

		jp2 = new JsonPath(response2.asString());
		variance_id = jp2.getString("data.varianceDetailsDtoList[0].id");
		String body = "{\"varianceIdsList\":[" + variance_id + "],\"currentStatus\":"
				+ prop_reader.getProperty("Price_change_open_status") + ",\"groupId\":" + jp.getString("data.id[0]")
				+ ",\"previousStatus\":" + prop_reader.getProperty("open_variance_status") + ",\"comment\":"
				+ prop_reader.getProperty("comments") + "}";
		

		System.out.println(variance_id);
		log.info("Changing status of variance id = " + variance_id);

		return body;

	}

	public Response changeStatusAPI() {
		String body = getOpenVariance();

		status_respons = given().header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accesstoken).body(body).when()
				.post(prop_reader.getProperty("variance_status"));

		log.info("calling Status change API");
		return status_respons;

	}

	public ResultSet getVarianceStatusDB() {
		String query = "select * from variance where id=" + variance_id + " ;";
		ResultSet connectdb = db.connectdb(query);
		return connectdb;
	}
}
