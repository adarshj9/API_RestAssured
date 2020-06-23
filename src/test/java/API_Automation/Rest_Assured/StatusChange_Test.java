package API_Automation.Rest_Assured;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.utility.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StatusChange_Test extends BaseTest {
	ChangeStatus cs = new ChangeStatus();

	@Test
	public void validateChangeStatus() {
		
		Response changeStatusAPI = cs.changeStatusAPI();
		JsonPath jp = new JsonPath(changeStatusAPI.asString());
		//Assert status code
		changeStatusAPI.then().assertThat().statusCode(200);
		
		//Assert status
		changeStatusAPI.then().assertThat().body("status",equalTo("Success"));
		
		
	}

}
