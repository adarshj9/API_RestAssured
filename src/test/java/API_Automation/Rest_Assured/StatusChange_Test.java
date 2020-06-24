package API_Automation.Rest_Assured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.utility.BaseTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StatusChange_Test extends BaseTest {
	private static Logger log = LogManager.getLogger(StatusChange_Test.class.getName());

	ChangeStatus cs = new ChangeStatus();

	@Test(description="validating status change API, request chaining")
	public void validateChangeStatus() {
		log.info("Inside "+this.getClass().getSimpleName());
		Response changeStatusAPI = cs.changeStatusAPI();
		JsonPath jp = new JsonPath(changeStatusAPI.asString());
		//Assert status code
		changeStatusAPI.then().assertThat().statusCode(200).and().time(lessThan(3000L));
		
		//Assert status
		changeStatusAPI.then().assertThat().body("status",equalTo("Success"));
		System.out.println("Inside "+this.getClass().getSimpleName());
		log.info("Exeuction of " + this.getClass().getSimpleName()+" completed");
		
		
	}

}
