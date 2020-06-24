package com.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyFileReader {
	private static Logger log = LogManager.getLogger(PropertyFileReader.class.getName());

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

		log.info("Reading property " + key);

		return prop.getProperty(key);

	}

}
