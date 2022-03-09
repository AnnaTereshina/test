package ru.metaprime.testtask;

import ru.metaprime.testtask.daoimpl.OrganizationDaoImpl;

public class App {
	public static void main(String[] args) {
		String connectionProperties = "ConnectionParameters.properties";
		String tableName = "data.xml";

		DBInitializer initializer = new DBInitializer(connectionProperties);
		initializer.createTable();

		OrganizationDaoImpl importer = new OrganizationDaoImpl(connectionProperties);
		DataParser dataParser = new DataParser();
		importer.importTableToDB(tableName, dataParser);
	}
}
