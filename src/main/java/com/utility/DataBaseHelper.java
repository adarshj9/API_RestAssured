package com.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseHelper extends BaseTest {
	private static Logger log = LogManager.getLogger(DataBaseHelper.class);
	ResultSet result;

	// Connection connection = null;

	public ResultSet connectdb(String query) {

		// This method will execute the query and return the result set
		try {
			// Connect to DB
			Connection connection = DriverManager.getConnection(prop_reader.getProperty("dburl"),
					prop_reader.getProperty("dbusername"), prop_reader.getProperty("dbpassword"));

			// Execute Query
			Statement statement = connection.createStatement();
			log.info("Executing Query " + query);
			result = statement.executeQuery(query);
			log.info("Query executed successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Return the result set
		return result;

	}

}
