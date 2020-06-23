package com.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
	public String getProperty(String key) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("../Rest_Assured/property.properties");
			prop.load(fis);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return prop.getProperty(key);

	}

}
