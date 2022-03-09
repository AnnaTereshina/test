package ru.metaprime.testtask;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBInitializer {

	String connectionProperties;

	public DBInitializer(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public void createTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			String connectionParametersPath = "src/main/resources/" + connectionProperties;
			Properties appProps = new Properties();
			appProps.load(new FileInputStream(connectionParametersPath));

			String URL = appProps.getProperty("URL");
			String USER = appProps.getProperty("USER");
			String PASS = appProps.getProperty("PASS");

			conn = DriverManager.getConnection(URL, USER, PASS);
			stmt = conn.createStatement();

			String query = "CREATE TABLE IF NOT EXISTS organizations " + "(name character varying NOT NULL, "
					+ " area character varying, " + " address character varying, " + " phone character varying)";

			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
