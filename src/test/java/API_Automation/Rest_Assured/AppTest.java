package API_Automation.Rest_Assured;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.Test;

import com.utility.BaseTest;
import com.utility.DataBaseHelper;
import com.utility.ExcelHandler;
import com.utility.PropertyFileReader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTest {
	@Test
	public  void test() throws SQLException 
	{
		System.out.println("Inside "+this.getClass().getSimpleName());
		/*ExcelHandler ex = new ExcelHandler();
		Map map=new HashMap();  
	    //Adding elements to map  
	    map.put(1,"Amit");  
	    map.put(5,"Rahul");  
	    map.put(2,"Jai");  
	    map.put(6,"Amit");  
	    
	    Map map1=new HashMap();  
	    //Adding elements to map  
	    map1.put(1,"Amit");  
	    map1.put(5,"Rahul");  
	    map1.put(2,"Jai");  
	    map1.put(6,"Amit");  
	    
	    ex.createCSV("new", map);
	    ex.setRows(map1);
	    ex.setRows(map1);
	    ex.setRows(map1);
		  
	    ex.flushData();*/
		
		
		String s="{\"email\":\""+prop_reader.getProperty("username")+"\",\"password\":\""+prop_reader.getProperty("password")+"\"}";
		prop_reader.getProperty("username");
		System.out.println(s);
	  
		
	
	}
}
