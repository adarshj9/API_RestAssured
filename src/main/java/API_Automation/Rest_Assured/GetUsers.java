package API_Automation.Rest_Assured;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.BaseTest;
import com.utility.ExcelHandler;

import io.restassured.response.Response;

public class GetUsers extends BaseTest {

	public Response getUsersAPI() {

		return given().header("Content-Type", "application/json").header("Authorization", "Bearer " + accesstoken)
				.when().get(prop_reader.getProperty("users"));

		// System.out.println(response.asString());

	}

	public void writeResponseToCSV(Response response) {
		ExcelHandler ex = new ExcelHandler();
		JSONObject jb = new JSONObject(response.asString());
		Object object = jb.get("data");
		// System.out.println("Object: " + object);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Map> map = mapper.readValue(object.toString(), List.class);
			ex.createCSV("get_users", map.get(0));
			map.forEach(data -> {
				ex.setRows(data);
			});
			ex.flushData();

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
