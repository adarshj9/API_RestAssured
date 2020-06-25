package API_Automation.Rest_Assured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.utility.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StatusChange_Test extends ChangeStatus {
	private static Logger log = LogManager.getLogger(StatusChange_Test.class.getName());

	ChangeStatus cs = new ChangeStatus();
	Response changeStatusAPI;
	JsonPath status_jp;

	@Test(priority = 0, groups = "Change Status", description = "validating status change API status code and status")
	public void validateChangeStatus() {
		log.info("Inside " + this.getClass().getSimpleName());
		changeStatusAPI = cs.changeStatusAPI();
		status_jp = new JsonPath(changeStatusAPI.asString());
		// Assert status code
		changeStatusAPI.then().assertThat().statusCode(200);

		// Assert status
		changeStatusAPI.then().assertThat().body("status", equalTo("Success"));
		validateStatusResponseTime();
		System.out.println("Inside " + this.getClass().getSimpleName());
		log.info("Exeuction of " + this.getClass().getSimpleName() + " completed");

	}

	@Test(priority = 1, groups = "Change Status", description = "Validate status change in DB")
	public void validateVarianceId() {

		ResultSet varianceStatusDB = cs.getVarianceStatusDB();
		try {
			while (varianceStatusDB.next()) {
				try {
					Assert.assertEquals(status_jp.getString("data[0].id"), varianceStatusDB.getString("id"));
					Assert.assertEquals(prop_reader.getProperty("Price_change_open_status"),
							varianceStatusDB.getString("status_id"));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(priority = 2, groups = "Change Status", description = "Validate status API response time")
	public void validateStatusResponseTime() {
		checkResponseTime(changeStatusAPI);

	}

}
