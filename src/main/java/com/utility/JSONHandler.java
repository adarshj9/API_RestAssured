package com.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {

	public void readJSON(String file_name){
		try {
			Object obj = new JSONParser().parse(new FileReader("./src/main/java/json_files/"+file_name+".json"));
			JSONObject jo = (JSONObject) obj; 
			Set entrySet = jo.entrySet();
			System.out.println(entrySet);
			System.out.println(jo.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
