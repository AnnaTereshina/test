package ru.metaprime.testtask.daoimpl;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringJoiner;

import ru.metaprime.testtask.DataParser;
import ru.metaprime.testtask.dao.OrganizationDao;
import ru.metaprime.testtask.entity.Organization;

public class OrganizationDaoImpl implements OrganizationDao {

	String connectionProperties;

	public OrganizationDaoImpl(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	@Override
	public void importTableToDB(String tableName, DataParser dataParser) {
		if (tableName == null || dataParser == null) {
			throw new IllegalArgumentException("Caught IllegalArgumentException: input data must not be a null.");
		}
		if (tableName.isEmpty()) {
			throw new IllegalArgumentException("Caught IllegalArgumentException: input data must not be empty.");
		}
		ArrayList<String> namesOrganizations = receiveNamesOrganizationsFromDB(connectionProperties);
		ArrayList<Organization> organizations = dataParser.receiveListOfOrganization(tableName, namesOrganizations);
		String query = madeQuery(organizations);
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

			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<String> receiveNamesOrganizationsFromDB(String connectionProperties) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> namesOrganizations = new ArrayList<>();
		try {
			String connectionParametersPath = "src/main/resources/" + connectionProperties;
			Properties appProps = new Properties();
			appProps.load(new FileInputStream(connectionParametersPath));

			String URL = appProps.getProperty("URL");
			String USER = appProps.getProperty("USER");
			String PASS = appProps.getProperty("PASS");

			conn = DriverManager.getConnection(URL, USER, PASS);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT (name) FROM organizations;");
			while (rs.next()) {
				namesOrganizations.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return namesOrganizations;
	}

	private String madeQuery(ArrayList<Organization> organizations) {
		if (organizations.isEmpty()) {
			throw new IllegalArgumentException("All organizations are already in the database.");
		}
		StringJoiner query = new StringJoiner("");
		query.add("INSERT INTO organizations (name, area, address, phone) VALUES ");

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);
			query.add("('" + organization.getName() + "',");
			query.add("'" + organization.getArea() + "',");
			query.add("'" + organization.getAddress() + "',");
			query.add("'" + organization.getPhone() + "')");

			if (i < organizations.size() - 1) {
				query.add(",");
				continue;
			}
			query.add(";");
		}
		return query.toString();
	}

}
